/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuAttendPlanHstEntity;
import jp.learningpark.modules.common.service.StuAttendPlanHstService;
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
 * 生徒出席予定実績
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stuattendplanhst")
public class StuAttendPlanHstController {
    @Autowired
    private StuAttendPlanHstService stuAttendPlanHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stuattendplanhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuAttendPlanHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stuattendplanhst:info")
    public R info(@PathVariable("id") Integer id){
        StuAttendPlanHstEntity stuAttendPlanHst = stuAttendPlanHstService.getById(id);

        return R.ok().put("stuAttendPlanHst", stuAttendPlanHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stuattendplanhst:save")
    public R save(@RequestBody StuAttendPlanHstEntity stuAttendPlanHst){
        stuAttendPlanHstService.save(stuAttendPlanHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stuattendplanhst:update")
    public R update(@RequestBody StuAttendPlanHstEntity stuAttendPlanHst){
        ValidatorUtils.validateEntity(stuAttendPlanHst);
        stuAttendPlanHstService.updateById(stuAttendPlanHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stuattendplanhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuAttendPlanHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stuattendplanhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuAttendPlanHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
