/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstOrgFunListEntity;
import jp.learningpark.modules.common.service.MstOrgFunListService;
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
 * 組織別機能一覧マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstorgfunlist")
public class MstOrgFunListController {
    @Autowired
    private MstOrgFunListService mstOrgFunListService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstorgfunlist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstOrgFunListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstorgfunlist:info")
    public R info(@PathVariable("id") Integer id){
        MstOrgFunListEntity mstOrgFunList = mstOrgFunListService.getById(id);

        return R.ok().put("mstOrgFunList", mstOrgFunList);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstorgfunlist:save")
    public R save(@RequestBody MstOrgFunListEntity mstOrgFunList){
        mstOrgFunListService.save(mstOrgFunList);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstorgfunlist:update")
    public R update(@RequestBody MstOrgFunListEntity mstOrgFunList){
        ValidatorUtils.validateEntity(mstOrgFunList);
        mstOrgFunListService.updateById(mstOrgFunList);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstorgfunlist:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstOrgFunListService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstorgfunlist:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstOrgFunListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
