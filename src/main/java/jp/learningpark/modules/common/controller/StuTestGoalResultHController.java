/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuTestGoalResultHEntity;
import jp.learningpark.modules.common.service.StuTestGoalResultHService;
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
 * 生徒テスト目標結果_ヘッダ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stutestgoalresulth")
public class StuTestGoalResultHController {
    @Autowired
    private StuTestGoalResultHService stuTestGoalResultHService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stutestgoalresulth:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuTestGoalResultHService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stutestgoalresulth:info")
    public R info(@PathVariable("id") Integer id){
        StuTestGoalResultHEntity stuTestGoalResultH = stuTestGoalResultHService.getById(id);

        return R.ok().put("stuTestGoalResultH", stuTestGoalResultH);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stutestgoalresulth:save")
    public R save(@RequestBody StuTestGoalResultHEntity stuTestGoalResultH){
        stuTestGoalResultHService.save(stuTestGoalResultH);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stutestgoalresulth:update")
    public R update(@RequestBody StuTestGoalResultHEntity stuTestGoalResultH){
        ValidatorUtils.validateEntity(stuTestGoalResultH);
        stuTestGoalResultHService.updateById(stuTestGoalResultH);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stutestgoalresulth:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuTestGoalResultHService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stutestgoalresulth:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuTestGoalResultHService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
