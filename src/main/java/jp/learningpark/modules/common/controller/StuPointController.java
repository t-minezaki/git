/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuPointEntity;
import jp.learningpark.modules.common.service.StuPointService;
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
 * 生徒ポイント
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stupoint")
public class StuPointController {
    @Autowired
    private StuPointService stuPointService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stupoint:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuPointService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stupoint:info")
    public R info(@PathVariable("id") Integer id){
        StuPointEntity stuPoint = stuPointService.getById(id);

        return R.ok().put("stuPoint", stuPoint);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stupoint:save")
    public R save(@RequestBody StuPointEntity stuPoint){
        stuPointService.save(stuPoint);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stupoint:update")
    public R update(@RequestBody StuPointEntity stuPoint){
        ValidatorUtils.validateEntity(stuPoint);
        stuPointService.updateById(stuPoint);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stupoint:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuPointService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stupoint:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuPointService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
