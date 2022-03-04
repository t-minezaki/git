/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstUsrFirtPwEntity;
import jp.learningpark.modules.common.service.MstUsrFirtPwService;
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
 * ユーザ初期パスワード管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstusrfirtpw")
public class MstUsrFirtPwController {
    @Autowired
    private MstUsrFirtPwService mstUsrFirtPwService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusrfirtpw:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstUsrFirtPwService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusrfirtpw:info")
    public R info(@PathVariable("id") Integer id){
        MstUsrFirtPwEntity mstUsrFirtPw = mstUsrFirtPwService.getById(id);

        return R.ok().put("mstUsrFirtPw", mstUsrFirtPw);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusrfirtpw:save")
    public R save(@RequestBody MstUsrFirtPwEntity mstUsrFirtPw){
        mstUsrFirtPwService.save(mstUsrFirtPw);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusrfirtpw:update")
    public R update(@RequestBody MstUsrFirtPwEntity mstUsrFirtPw){
        ValidatorUtils.validateEntity(mstUsrFirtPw);
        mstUsrFirtPwService.updateById(mstUsrFirtPw);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusrfirtpw:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstUsrFirtPwService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusrfirtpw:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstUsrFirtPwService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
