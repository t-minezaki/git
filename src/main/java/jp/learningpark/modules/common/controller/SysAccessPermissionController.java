/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.SysAccessPermissionEntity;
import jp.learningpark.modules.common.service.SysAccessPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * システム訪問許可
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/sysaccesspermission")
public class SysAccessPermissionController {
    @Autowired
    private SysAccessPermissionService sysAccessPermissionService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:sysaccesspermission:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysAccessPermissionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:sysaccesspermission:info")
    public R info(@PathVariable("id") Integer id){
        SysAccessPermissionEntity sysAccessPermission = sysAccessPermissionService.getById(id);

        return R.ok().put("sysAccessPermission", sysAccessPermission);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:sysaccesspermission:save")
    public R save(@RequestBody SysAccessPermissionEntity sysAccessPermission){
        sysAccessPermissionService.save(sysAccessPermission);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:sysaccesspermission:update")
    public R update(@RequestBody SysAccessPermissionEntity sysAccessPermission){
        ValidatorUtils.validateEntity(sysAccessPermission);
        sysAccessPermissionService.updateById(sysAccessPermission);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:sysaccesspermission:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            sysAccessPermissionService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:sysaccesspermission:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        sysAccessPermissionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
