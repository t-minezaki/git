/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
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
 * 生徒ウィークリー計画実績設定
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stuweeklyplanperf")
public class StuWeeklyPlanPerfController {
    @Autowired
    private StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklyplanperf:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuWeeklyPlanPerfService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklyplanperf:info")
    public R info(@PathVariable("id") Integer id){
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerf = stuWeeklyPlanPerfService.getById(id);

        return R.ok().put("stuWeeklyPlanPerf", stuWeeklyPlanPerf);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklyplanperf:save")
    public R save(@RequestBody StuWeeklyPlanPerfEntity stuWeeklyPlanPerf){
        stuWeeklyPlanPerfService.save(stuWeeklyPlanPerf);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklyplanperf:update")
    public R update(@RequestBody StuWeeklyPlanPerfEntity stuWeeklyPlanPerf){
        ValidatorUtils.validateEntity(stuWeeklyPlanPerf);
        stuWeeklyPlanPerfService.updateById(stuWeeklyPlanPerf);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklyplanperf:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuWeeklyPlanPerfService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklyplanperf:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuWeeklyPlanPerfService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
