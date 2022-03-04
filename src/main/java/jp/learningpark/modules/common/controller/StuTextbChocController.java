/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuTextbChocEntity;
import jp.learningpark.modules.common.service.StuTextbChocService;
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
 * 生徒教科書選択管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stutextbchoc")
public class StuTextbChocController {
    @Autowired
    private StuTextbChocService stuTextbChocService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stutextbchoc:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuTextbChocService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stutextbchoc:info")
    public R info(@PathVariable("id") Integer id){
        StuTextbChocEntity stuTextbChoc = stuTextbChocService.getById(id);

        return R.ok().put("stuTextbChoc", stuTextbChoc);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stutextbchoc:save")
    public R save(@RequestBody StuTextbChocEntity stuTextbChoc){
        stuTextbChocService.save(stuTextbChoc);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stutextbchoc:update")
    public R update(@RequestBody StuTextbChocEntity stuTextbChoc){
        ValidatorUtils.validateEntity(stuTextbChoc);
        stuTextbChocService.updateById(stuTextbChoc);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stutextbchoc:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuTextbChocService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stutextbchoc:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuTextbChocService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
