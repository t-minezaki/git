/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstNoticeDeliverEntity;
import jp.learningpark.modules.common.service.MstNoticeDeliverService;
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
 * お知らせ配信先
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstnoticedeliver")
public class MstNoticeDeliverController {
    @Autowired
    private MstNoticeDeliverService mstNoticeDeliverService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnoticedeliver:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstNoticeDeliverService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnoticedeliver:info")
    public R info(@PathVariable("id") Integer id){
        MstNoticeDeliverEntity mstNoticeDeliver = mstNoticeDeliverService.getById(id);

        return R.ok().put("mstNoticeDeliver", mstNoticeDeliver);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnoticedeliver:save")
    public R save(@RequestBody MstNoticeDeliverEntity mstNoticeDeliver){
        mstNoticeDeliverService.save(mstNoticeDeliver);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnoticedeliver:update")
    public R update(@RequestBody MstNoticeDeliverEntity mstNoticeDeliver){
        ValidatorUtils.validateEntity(mstNoticeDeliver);
        mstNoticeDeliverService.updateById(mstNoticeDeliver);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnoticedeliver:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstNoticeDeliverService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnoticedeliver:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstNoticeDeliverService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
