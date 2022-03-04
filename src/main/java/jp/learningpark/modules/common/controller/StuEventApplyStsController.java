/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.service.StuEventApplyStsService;
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
 * 生徒イベント申込状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stueventapplysts")
public class StuEventApplyStsController {
    @Autowired
    private StuEventApplyStsService stuEventApplyStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stueventapplysts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuEventApplyStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stueventapplysts:info")
    public R info(@PathVariable("id") Integer id){
        StuEventApplyStsEntity stuEventApplySts = stuEventApplyStsService.getById(id);

        return R.ok().put("stuEventApplySts", stuEventApplySts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stueventapplysts:save")
    public R save(@RequestBody StuEventApplyStsEntity stuEventApplySts){
        stuEventApplyStsService.save(stuEventApplySts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stueventapplysts:update")
    public R update(@RequestBody StuEventApplyStsEntity stuEventApplySts){
        ValidatorUtils.validateEntity(stuEventApplySts);
        stuEventApplyStsService.updateById(stuEventApplySts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stueventapplysts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuEventApplyStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stueventapplysts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuEventApplyStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
