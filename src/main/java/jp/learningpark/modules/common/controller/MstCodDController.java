/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
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
 * コードマスタ_明細
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstcodd")
public class MstCodDController {
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcodd:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstCodDService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{codKey}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcodd:info")
    public R info(@PathVariable("codKey") String codKey){
        MstCodDEntity mstCodD = mstCodDService.getById(codKey);

        return R.ok().put("mstCodD", mstCodD);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcodd:save")
    public R save(@RequestBody MstCodDEntity mstCodD){
        mstCodDService.save(mstCodD);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcodd:update")
    public R update(@RequestBody MstCodDEntity mstCodD){
        ValidatorUtils.validateEntity(mstCodD);
        mstCodDService.updateById(mstCodD);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcodd:deleteOne")
    public R deletedeleteOne(@RequestParam("codKey") String codKey){
            mstCodDService.removeById(codKey);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcodd:delete")
    public R delete(@RequestParam("codKeys") String[] codKeys){
        mstCodDService.removeByIds(Arrays.asList(codKeys));

        return R.ok();
    }

}
