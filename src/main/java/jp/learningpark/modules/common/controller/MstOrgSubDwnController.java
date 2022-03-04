/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstOrgSubDwnEntity;
import jp.learningpark.modules.common.service.MstOrgSubDwnService;
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
 * 組織別教科ダウンロード管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstorgsubdwn")
public class MstOrgSubDwnController {
    @Autowired
    private MstOrgSubDwnService mstOrgSubDwnService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstorgsubdwn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstOrgSubDwnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstorgsubdwn:info")
    public R info(@PathVariable("id") Integer id){
        MstOrgSubDwnEntity mstOrgSubDwn = mstOrgSubDwnService.getById(id);

        return R.ok().put("mstOrgSubDwn", mstOrgSubDwn);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstorgsubdwn:save")
    public R save(@RequestBody MstOrgSubDwnEntity mstOrgSubDwn){
        mstOrgSubDwnService.save(mstOrgSubDwn);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstorgsubdwn:update")
    public R update(@RequestBody MstOrgSubDwnEntity mstOrgSubDwn){
        ValidatorUtils.validateEntity(mstOrgSubDwn);
        mstOrgSubDwnService.updateById(mstOrgSubDwn);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstorgsubdwn:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstOrgSubDwnService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstorgsubdwn:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstOrgSubDwnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
