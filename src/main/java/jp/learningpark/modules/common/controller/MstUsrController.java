/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstUsrService;
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
 * ユーザ基本マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstusr")
public class MstUsrController {
    @Autowired
    private MstUsrService mstUsrService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstUsrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusr:info")
    public R info(@PathVariable("id") Integer id){
        MstUsrEntity mstUsr = mstUsrService.getById(id);

        return R.ok().put("mstUsr", mstUsr);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusr:save")
    public R save(@RequestBody MstUsrEntity mstUsr){
        mstUsrService.save(mstUsr);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusr:update")
    public R update(@RequestBody MstUsrEntity mstUsr){
        ValidatorUtils.validateEntity(mstUsr);
        mstUsrService.updateById(mstUsr);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusr:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstUsrService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusr:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstUsrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
