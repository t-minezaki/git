/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.service.MstGuardService;
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
 * 保護者基本マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstguard")
public class MstGuardController {
    @Autowired
    private MstGuardService mstGuardService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstguard:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstGuardService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstguard:info")
    public R info(@PathVariable("id") Integer id){
        MstGuardEntity mstGuard = mstGuardService.getById(id);

        return R.ok().put("mstGuard", mstGuard);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstguard:save")
    public R save(@RequestBody MstGuardEntity mstGuard){
        mstGuardService.save(mstGuard);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstguard:update")
    public R update(@RequestBody MstGuardEntity mstGuard){
        ValidatorUtils.validateEntity(mstGuard);
        mstGuardService.updateById(mstGuard);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstguard:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstGuardService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstguard:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstGuardService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
