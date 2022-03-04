/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
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
 * デバイスTOKEN管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstdevicetoken")
public class MstDeviceTokenController {
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstdevicetoken:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstDeviceTokenService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstdevicetoken:info")
    public R info(@PathVariable("id") Integer id){
        MstDeviceTokenEntity mstDeviceToken = mstDeviceTokenService.getById(id);

        return R.ok().put("mstDeviceToken", mstDeviceToken);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstdevicetoken:save")
    public R save(@RequestBody MstDeviceTokenEntity mstDeviceToken){
        mstDeviceTokenService.save(mstDeviceToken);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstdevicetoken:update")
    public R update(@RequestBody MstDeviceTokenEntity mstDeviceToken){
        ValidatorUtils.validateEntity(mstDeviceToken);
        mstDeviceTokenService.updateById(mstDeviceToken);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstdevicetoken:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstDeviceTokenService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstdevicetoken:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstDeviceTokenService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
