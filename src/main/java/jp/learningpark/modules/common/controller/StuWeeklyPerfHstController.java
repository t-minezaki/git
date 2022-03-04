/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuWeeklyPerfHstEntity;
import jp.learningpark.modules.common.service.StuWeeklyPerfHstService;
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
 * 生徒ウィークリー実績履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stuweeklyperfhst")
public class StuWeeklyPerfHstController {
    @Autowired
    private StuWeeklyPerfHstService stuWeeklyPerfHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklyperfhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuWeeklyPerfHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklyperfhst:info")
    public R info(@PathVariable("id") Integer id){
        StuWeeklyPerfHstEntity stuWeeklyPerfHst = stuWeeklyPerfHstService.getById(id);

        return R.ok().put("stuWeeklyPerfHst", stuWeeklyPerfHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklyperfhst:save")
    public R save(@RequestBody StuWeeklyPerfHstEntity stuWeeklyPerfHst){
        stuWeeklyPerfHstService.save(stuWeeklyPerfHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklyperfhst:update")
    public R update(@RequestBody StuWeeklyPerfHstEntity stuWeeklyPerfHst){
        ValidatorUtils.validateEntity(stuWeeklyPerfHst);
        stuWeeklyPerfHstService.updateById(stuWeeklyPerfHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stuweeklyperfhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuWeeklyPerfHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stuweeklyperfhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuWeeklyPerfHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
