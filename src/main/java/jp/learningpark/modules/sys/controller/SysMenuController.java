/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.modules.sys.controller;

import jp.learningpark.framework.annotation.SysLog;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.sys.entity.SysMenuEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import jp.learningpark.modules.sys.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * メニュー
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;


	@GetMapping("/nav")
	public R nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUser().getId());
		Set<String> permissions = shiroService.getUserPermissions(getUser().getId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}

	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenuEntity> list(){
		List<SysMenuEntity> menuList = sysMenuService.list(null);
		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}

		return menuList;
	}
	

	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public R select(){
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("トップメニュー");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	

	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}
	

	@SysLog("メニュー新規処理")
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public R save(@RequestBody SysMenuEntity menu){
		verifyForm(menu);
		
		sysMenuService.save(menu);
		
		return R.ok();
	}
	

	@SysLog("メニュー更新処理")
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public R update(@RequestBody SysMenuEntity menu){
		verifyForm(menu);
				
		sysMenuService.updateById(menu);
		
		return R.ok();
	}

	@SysLog("メニュー削除処理")
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys:menu:delete")
	public R delete(@PathVariable("menuId") long menuId){

		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("サブメニューを削除してください");
		}

		sysMenuService.removeById(menuId);

		return R.ok();
	}

	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("メニュー");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("トップメニュー");
		}
	}
}
