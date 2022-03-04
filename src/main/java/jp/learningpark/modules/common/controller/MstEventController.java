/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.service.MstEventService;
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
@RequestMapping("common/mstevent")
public class MstEventController {
    @Autowired
    private MstEventService mstEventService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstevent:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstEventService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstevent:info")
    public R info(@PathVariable("id") Integer id){
        MstEventEntity mstEvent = mstEventService.getById(id);

        return R.ok().put("mstEvent", mstEvent);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstevent:save")
    public R save(@RequestBody MstEventEntity mstEvent){
        mstEventService.save(mstEvent);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstevent:update")
    public R update(@RequestBody MstEventEntity mstEvent){
        ValidatorUtils.validateEntity(mstEvent);
        mstEventService.updateById(mstEvent);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstevent:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstEventService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstevent:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstEventService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
