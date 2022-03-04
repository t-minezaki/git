/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.common.service.EventScheduleService;
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
 * イベント日程
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/eventschedule")
public class EventScheduleController {
    @Autowired
    private EventScheduleService eventScheduleService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:eventschedule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = eventScheduleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:eventschedule:info")
    public R info(@PathVariable("id") Integer id){
        EventScheduleEntity eventSchedule = eventScheduleService.getById(id);

        return R.ok().put("eventSchedule", eventSchedule);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:eventschedule:save")
    public R save(@RequestBody EventScheduleEntity eventSchedule){
        eventScheduleService.save(eventSchedule);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:eventschedule:update")
    public R update(@RequestBody EventScheduleEntity eventSchedule){
        ValidatorUtils.validateEntity(eventSchedule);
        eventScheduleService.updateById(eventSchedule);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:eventschedule:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            eventScheduleService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:eventschedule:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        eventScheduleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
