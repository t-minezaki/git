/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guardeventapplysts")
public class GuardEventApplyStsController {
    @Autowired
    private GuardEventApplyStsService guardEventApplyStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventapplysts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guardEventApplyStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventapplysts:info")
    public R info(@PathVariable("id") Integer id){
        GuardEventApplyStsEntity guardEventApplySts = guardEventApplyStsService.getById(id);

        return R.ok().put("guardEventApplySts", guardEventApplySts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventapplysts:save")
    public R save(@RequestBody GuardEventApplyStsEntity guardEventApplySts){
        guardEventApplyStsService.save(guardEventApplySts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventapplysts:update")
    public R update(@RequestBody GuardEventApplyStsEntity guardEventApplySts){
        ValidatorUtils.validateEntity(guardEventApplySts);
        guardEventApplyStsService.updateById(guardEventApplySts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventapplysts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guardEventApplyStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventapplysts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guardEventApplyStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
