/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.AppVerMstEntity;
import jp.learningpark.modules.common.service.AppVerMstService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * アプリバージョン管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/appvermst")
public class AppVerMstController {
    @Autowired
    private AppVerMstService appVerMstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:appvermst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appVerMstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:appvermst:info")
    public R info(@PathVariable("id") Integer id){
        AppVerMstEntity appVerMst = appVerMstService.getById(id);

        return R.ok().put("appVerMst", appVerMst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:appvermst:save")
    public R save(@RequestBody AppVerMstEntity appVerMst){
        appVerMstService.save(appVerMst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:appvermst:update")
    public R update(@RequestBody AppVerMstEntity appVerMst){
        ValidatorUtils.validateEntity(appVerMst);
        appVerMstService.updateById(appVerMst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:appvermst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            appVerMstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:appvermst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        appVerMstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
