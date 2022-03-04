/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuMonthlySumHstEntity;
import jp.learningpark.modules.common.service.StuMonthlySumHstService;
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
 * 子供ニュース月次集計履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stumonthlysumhst")
public class StuMonthlySumHstController {
    @Autowired
    private StuMonthlySumHstService stuMonthlySumHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stumonthlysumhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuMonthlySumHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stumonthlysumhst:info")
    public R info(@PathVariable("id") Integer id){
        StuMonthlySumHstEntity stuMonthlySumHst = stuMonthlySumHstService.getById(id);

        return R.ok().put("stuMonthlySumHst", stuMonthlySumHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stumonthlysumhst:save")
    public R save(@RequestBody StuMonthlySumHstEntity stuMonthlySumHst){
        stuMonthlySumHstService.save(stuMonthlySumHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stumonthlysumhst:update")
    public R update(@RequestBody StuMonthlySumHstEntity stuMonthlySumHst){
        ValidatorUtils.validateEntity(stuMonthlySumHst);
        stuMonthlySumHstService.updateById(stuMonthlySumHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stumonthlysumhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuMonthlySumHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stumonthlysumhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuMonthlySumHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
