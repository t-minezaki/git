/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuTextbChocOutEntity;
import jp.learningpark.modules.common.service.StuTextbChocOutService;
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
 * 生徒教科書選択管理（転入用）
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stutextbchocout")
public class StuTextbChocOutController {
    @Autowired
    private StuTextbChocOutService stuTextbChocOutService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stutextbchocout:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuTextbChocOutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stutextbchocout:info")
    public R info(@PathVariable("id") Integer id){
        StuTextbChocOutEntity stuTextbChocOut = stuTextbChocOutService.getById(id);

        return R.ok().put("stuTextbChocOut", stuTextbChocOut);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stutextbchocout:save")
    public R save(@RequestBody StuTextbChocOutEntity stuTextbChocOut){
        stuTextbChocOutService.save(stuTextbChocOut);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stutextbchocout:update")
    public R update(@RequestBody StuTextbChocOutEntity stuTextbChocOut){
        ValidatorUtils.validateEntity(stuTextbChocOut);
        stuTextbChocOutService.updateById(stuTextbChocOut);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stutextbchocout:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuTextbChocOutService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stutextbchocout:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuTextbChocOutService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
