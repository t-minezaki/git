/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuGrpEntity;
import jp.learningpark.modules.common.service.StuGrpService;
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
 * 生徒グループ管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stugrp")
public class StuGrpController {
    @Autowired
    private StuGrpService stuGrpService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stugrp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuGrpService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stugrp:info")
    public R info(@PathVariable("id") Integer id){
        StuGrpEntity stuGrp = stuGrpService.getById(id);

        return R.ok().put("stuGrp", stuGrp);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stugrp:save")
    public R save(@RequestBody StuGrpEntity stuGrp){
        stuGrpService.save(stuGrp);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stugrp:update")
    public R update(@RequestBody StuGrpEntity stuGrp){
        ValidatorUtils.validateEntity(stuGrp);
        stuGrpService.updateById(stuGrp);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stugrp:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuGrpService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stugrp:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuGrpService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
