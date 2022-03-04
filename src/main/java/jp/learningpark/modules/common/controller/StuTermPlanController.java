/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.service.StuTermPlanService;
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
 * 生徒タームプラン設定
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stutermplan")
public class StuTermPlanController {
    @Autowired
    private StuTermPlanService stuTermPlanService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stutermplan:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuTermPlanService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stutermplan:info")
    public R info(@PathVariable("id") Integer id){
        StuTermPlanEntity stuTermPlan = stuTermPlanService.getById(id);

        return R.ok().put("stuTermPlan", stuTermPlan);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stutermplan:save")
    public R save(@RequestBody StuTermPlanEntity stuTermPlan){
        stuTermPlanService.save(stuTermPlan);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stutermplan:update")
    public R update(@RequestBody StuTermPlanEntity stuTermPlan){
        ValidatorUtils.validateEntity(stuTermPlan);
        stuTermPlanService.updateById(stuTermPlan);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stutermplan:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuTermPlanService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stutermplan:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuTermPlanService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
