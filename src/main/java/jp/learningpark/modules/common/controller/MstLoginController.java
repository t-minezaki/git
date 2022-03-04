/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstLoginEntity;
import jp.learningpark.modules.common.service.MstLoginService;
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
 * ログイン管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstlogin")
public class MstLoginController {
    @Autowired
    private MstLoginService mstLoginService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstlogin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstLoginService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstlogin:info")
    public R info(@PathVariable("id") Integer id){
        MstLoginEntity mstLogin = mstLoginService.getById(id);

        return R.ok().put("mstLogin", mstLogin);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstlogin:save")
    public R save(@RequestBody MstLoginEntity mstLogin){
        mstLoginService.save(mstLogin);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstlogin:update")
    public R update(@RequestBody MstLoginEntity mstLogin){
        ValidatorUtils.validateEntity(mstLogin);
        mstLoginService.updateById(mstLogin);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstlogin:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstLoginService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstlogin:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstLoginService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
