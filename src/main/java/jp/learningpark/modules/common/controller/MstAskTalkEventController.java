/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;
import jp.learningpark.modules.common.service.MstAskTalkEventService;
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
 * 質問面談イベント
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstasktalkevent")
public class MstAskTalkEventController {
    @Autowired
    private MstAskTalkEventService mstAskTalkEventService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstasktalkevent:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstAskTalkEventService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstasktalkevent:info")
    public R info(@PathVariable("id") Integer id){
        MstAskTalkEventEntity mstAskTalkEvent = mstAskTalkEventService.getById(id);

        return R.ok().put("mstAskTalkEvent", mstAskTalkEvent);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstasktalkevent:save")
    public R save(@RequestBody MstAskTalkEventEntity mstAskTalkEvent){
        mstAskTalkEventService.save(mstAskTalkEvent);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstasktalkevent:update")
    public R update(@RequestBody MstAskTalkEventEntity mstAskTalkEvent){
        ValidatorUtils.validateEntity(mstAskTalkEvent);
        mstAskTalkEventService.updateById(mstAskTalkEvent);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstasktalkevent:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstAskTalkEventService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstasktalkevent:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstAskTalkEventService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
