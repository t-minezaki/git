/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.service.MstGrpService;
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
 * グループマスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstgrp")
public class MstGrpController {
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstgrp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstGrpService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{grpId}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstgrp:info")
    public R info(@PathVariable("grpId") Integer grpId){
        MstGrpEntity mstGrp = mstGrpService.getById(grpId);

        return R.ok().put("mstGrp", mstGrp);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstgrp:save")
    public R save(@RequestBody MstGrpEntity mstGrp){
        mstGrpService.save(mstGrp);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstgrp:update")
    public R update(@RequestBody MstGrpEntity mstGrp){
        ValidatorUtils.validateEntity(mstGrp);
        mstGrpService.updateById(mstGrp);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstgrp:deleteOne")
    public R deletedeleteOne(@RequestParam("grpId") Integer grpId){
            mstGrpService.removeById(grpId);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstgrp:delete")
    public R delete(@RequestParam("grpIds") Integer[] grpIds){
        mstGrpService.removeByIds(Arrays.asList(grpIds));

        return R.ok();
    }

}
