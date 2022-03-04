package jp.learningpark.modules.sys.controller;

import jp.learningpark.framework.annotation.SysLog;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.framework.validator.group.AddGroup;
import jp.learningpark.framework.validator.group.UpdateGroup;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

	@Autowired
	private MstUsrService mstUsrService;

	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = mstUsrService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
	    Map<String, String> userInfo = new HashMap<String, String>();
	    userInfo.put("userName", ShiroUtils.getUserEntity().getUsrNm());
		return R.ok().put("user", userInfo);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("パスワード変更")
	@PostMapping("/password")
    @RequiresPermissions("sys:user:password")
    public R password(String password, String newPassword) {
	    String userId = ShiroUtils.getUserEntity().getUsrId();
        // 暗号化
        if (!ShiroUtils.sha256(password, userId).equals(ShiroUtils.getUserEntity().getUsrPassword())) {
            return R.error(MessageUtils.getMessage("MSGCOMN0058"));
        }
        MstUsrEntity entity = mstUsrService.getById(ShiroUtils.getUserEntity().getId());
        if (entity == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        // 更新項目
        entity.setUsrPassword(ShiroUtils.sha256(newPassword, userId));
        entity.setUpdDatime(DateUtils.getSysTimestamp());
        entity.setUpdUsrId(userId);
        mstUsrService.updateById(entity);
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
	    MstUsrEntity user = mstUsrService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
//		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCretUsrId(getUser().getUsrId());
		mstUsrService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCretUsrId(getUser().getUsrId());
        mstUsrService.updateById(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody List<Integer> userIds){
		if(userIds.contains(1)){
			return R.error("系统管理员不能删除");
		}
		
		if(userIds.contains(getUser().getId())){
			return R.error("当前用户不能删除");
		}
		
		mstUsrService.removeByIds(userIds);
		
		return R.ok();
	}
}
