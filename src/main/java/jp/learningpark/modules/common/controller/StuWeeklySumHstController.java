/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuWeeklySumHstEntity;
import jp.learningpark.modules.common.service.StuWeeklySumHstService;
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
 * 子供ニュース週次集計履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stuweeklysumhst")
public class StuWeeklySumHstController {
    @Autowired
    private StuWeeklySumHstService stuWeeklySumHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklysumhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuWeeklySumHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklysumhst:info")
    public R info(@PathVariable("id") Integer id){
        StuWeeklySumHstEntity stuWeeklySumHst = stuWeeklySumHstService.getById(id);

        return R.ok().put("stuWeeklySumHst", stuWeeklySumHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklysumhst:save")
    public R save(@RequestBody StuWeeklySumHstEntity stuWeeklySumHst){
        stuWeeklySumHstService.save(stuWeeklySumHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklysumhst:update")
    public R update(@RequestBody StuWeeklySumHstEntity stuWeeklySumHst){
        ValidatorUtils.validateEntity(stuWeeklySumHst);
        stuWeeklySumHstService.updateById(stuWeeklySumHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklysumhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuWeeklySumHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklysumhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuWeeklySumHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
