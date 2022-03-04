/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
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
@RequestMapping("common/eventscheplandel")
public class EventSchePlanDelController {
    @Autowired
    private EventSchePlanDelService eventSchePlanDelService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:eventscheplandel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = eventSchePlanDelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:eventscheplandel:info")
    public R info(@PathVariable("id") Integer id){
        EventSchePlanDelEntity eventSchePlanDel = eventSchePlanDelService.getById(id);

        return R.ok().put("eventSchePlanDel", eventSchePlanDel);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:eventscheplandel:save")
    public R save(@RequestBody EventSchePlanDelEntity eventSchePlanDel){
        eventSchePlanDelService.save(eventSchePlanDel);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:eventscheplandel:update")
    public R update(@RequestBody EventSchePlanDelEntity eventSchePlanDel){
        ValidatorUtils.validateEntity(eventSchePlanDel);
        eventSchePlanDelService.updateById(eventSchePlanDel);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:eventscheplandel:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            eventSchePlanDelService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:eventscheplandel:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        eventSchePlanDelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
