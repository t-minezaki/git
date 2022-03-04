/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.common.service.MstLearnSeasnService;
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
 * 学習時期マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstlearnseasn")
public class MstLearnSeasnController {
    @Autowired
    private MstLearnSeasnService mstLearnSeasnService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstlearnseasn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstLearnSeasnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstlearnseasn:info")
    public R info(@PathVariable("id") Integer id){
        MstLearnSeasnEntity mstLearnSeasn = mstLearnSeasnService.getById(id);

        return R.ok().put("mstLearnSeasn", mstLearnSeasn);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstlearnseasn:save")
    public R save(@RequestBody MstLearnSeasnEntity mstLearnSeasn){
        mstLearnSeasnService.save(mstLearnSeasn);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstlearnseasn:update")
    public R update(@RequestBody MstLearnSeasnEntity mstLearnSeasn){
        ValidatorUtils.validateEntity(mstLearnSeasn);
        mstLearnSeasnService.updateById(mstLearnSeasn);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstlearnseasn:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstLearnSeasnService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstlearnseasn:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstLearnSeasnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
