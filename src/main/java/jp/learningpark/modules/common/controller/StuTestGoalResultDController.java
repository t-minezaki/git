/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuTestGoalResultDEntity;
import jp.learningpark.modules.common.service.StuTestGoalResultDService;
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
 * 生徒テスト目標結果_明細
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stutestgoalresultd")
public class StuTestGoalResultDController {
    @Autowired
    private StuTestGoalResultDService stuTestGoalResultDService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stutestgoalresultd:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuTestGoalResultDService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stutestgoalresultd:info")
    public R info(@PathVariable("id") Integer id){
        StuTestGoalResultDEntity stuTestGoalResultD = stuTestGoalResultDService.getById(id);

        return R.ok().put("stuTestGoalResultD", stuTestGoalResultD);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stutestgoalresultd:save")
    public R save(@RequestBody StuTestGoalResultDEntity stuTestGoalResultD){
        stuTestGoalResultDService.save(stuTestGoalResultD);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stutestgoalresultd:update")
    public R update(@RequestBody StuTestGoalResultDEntity stuTestGoalResultD){
        ValidatorUtils.validateEntity(stuTestGoalResultD);
        stuTestGoalResultDService.updateById(stuTestGoalResultD);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stutestgoalresultd:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuTestGoalResultDService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stutestgoalresultd:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuTestGoalResultDService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
