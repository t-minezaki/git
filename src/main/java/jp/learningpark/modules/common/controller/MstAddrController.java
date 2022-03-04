/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstAddrEntity;
import jp.learningpark.modules.common.service.MstAddrService;
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
 * 住所マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstaddr")
public class MstAddrController {
    @Autowired
    private MstAddrService mstAddrService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstaddr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstAddrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstaddr:info")
    public R info(@PathVariable("id") Integer id){
        MstAddrEntity mstAddr = mstAddrService.getById(id);

        return R.ok().put("mstAddr", mstAddr);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstaddr:save")
    public R save(@RequestBody MstAddrEntity mstAddr){
        mstAddrService.save(mstAddr);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstaddr:update")
    public R update(@RequestBody MstAddrEntity mstAddr){
        ValidatorUtils.validateEntity(mstAddr);
        mstAddrService.updateById(mstAddr);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstaddr:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstAddrService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstaddr:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstAddrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
