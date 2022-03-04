/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.service.MstCrmschLearnPrdService;
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
 * 塾学習期間マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstcrmschlearnprd")
public class MstCrmschLearnPrdController {
    @Autowired
    private MstCrmschLearnPrdService mstCrmschLearnPrdService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcrmschlearnprd:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstCrmschLearnPrdService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcrmschlearnprd:info")
    public R info(@PathVariable("id") Integer id){
        MstCrmschLearnPrdEntity mstCrmschLearnPrd = mstCrmschLearnPrdService.getById(id);

        return R.ok().put("mstCrmschLearnPrd", mstCrmschLearnPrd);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcrmschlearnprd:save")
    public R save(@RequestBody MstCrmschLearnPrdEntity mstCrmschLearnPrd){
        mstCrmschLearnPrdService.save(mstCrmschLearnPrd);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcrmschlearnprd:update")
    public R update(@RequestBody MstCrmschLearnPrdEntity mstCrmschLearnPrd){
        ValidatorUtils.validateEntity(mstCrmschLearnPrd);
        mstCrmschLearnPrdService.updateById(mstCrmschLearnPrd);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcrmschlearnprd:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstCrmschLearnPrdService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcrmschlearnprd:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstCrmschLearnPrdService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
