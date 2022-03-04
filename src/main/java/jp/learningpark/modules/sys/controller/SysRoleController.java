package jp.learningpark.modules.sys.controller;

import jp.learningpark.framework.annotation.SysLog;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.sys.entity.SysRoleEntity;
import jp.learningpark.modules.sys.service.SysRoleMenuService;
import jp.learningpark.modules.sys.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ロール管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * ロール一覧を取得する
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);
        return R.ok().put("page", page);
    }
    
    /**
     * ロールリストを取得する
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        Map<String, Object> map = new HashMap<>();

        Collection<SysRoleEntity> list = sysRoleService.listByMap(map);
        
        return R.ok().put("list", list);
    }
    
    /**
     * ロール詳細情報を取得する
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return R.ok().put("role", role);
    }
    
    /**
     * ロール登録処理
     */
    @SysLog("ロール登録処理")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role){
        if (StringUtils.isEmpty(role.getRoleName())) {
            throw new RRException(MessageUtils.getMessage("MSGCOMD0001", "ロール名"));
        }
        sysRoleService.saveRole(role);
        
        return R.ok();
    }
    
    /**
     * ロール更新処理
     */
    @SysLog("ロール更新処理")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role){
        if (StringUtils.isEmpty(role.getRoleName())) {
            throw new RRException(MessageUtils.getMessage("MSGCOMD0001", "ロール名"));
        }
        role.setCreateUserId(getUser().getId());
        sysRoleService.updateRole(role);
        
        return R.ok();
    }
    
    /**
     * ロール削除処理
     */
    @SysLog("ロール削除処理")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteRole(roleIds);
        return R.ok();
    }

}
