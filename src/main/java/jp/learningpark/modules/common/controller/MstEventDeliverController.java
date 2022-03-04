/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstEventDeliverEntity;
import jp.learningpark.modules.common.service.MstEventDeliverService;
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
@RequestMapping("common/msteventdeliver")
public class MstEventDeliverController {
    @Autowired
    private MstEventDeliverService mstEventDeliverService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:msteventdeliver:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstEventDeliverService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:msteventdeliver:info")
    public R info(@PathVariable("id") Integer id){
        MstEventDeliverEntity mstEventDeliver = mstEventDeliverService.getById(id);

        return R.ok().put("mstEventDeliver", mstEventDeliver);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:msteventdeliver:save")
    public R save(@RequestBody MstEventDeliverEntity mstEventDeliver){
        mstEventDeliverService.save(mstEventDeliver);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:msteventdeliver:update")
    public R update(@RequestBody MstEventDeliverEntity mstEventDeliver){
        ValidatorUtils.validateEntity(mstEventDeliver);
        mstEventDeliverService.updateById(mstEventDeliver);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:msteventdeliver:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstEventDeliverService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:msteventdeliver:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstEventDeliverService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
