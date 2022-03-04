/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstCodHEntity;
import jp.learningpark.modules.common.service.MstCodHService;
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
 * コードマスタ_ヘッダ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstcodh")
public class MstCodHController {
    @Autowired
    private MstCodHService mstCodHService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcodh:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstCodHService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{codId}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcodh:info")
    public R info(@PathVariable("codId") Integer codId){
        MstCodHEntity mstCodH = mstCodHService.getById(codId);

        return R.ok().put("mstCodH", mstCodH);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcodh:save")
    public R save(@RequestBody MstCodHEntity mstCodH){
        mstCodHService.save(mstCodH);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcodh:update")
    public R update(@RequestBody MstCodHEntity mstCodH){
        ValidatorUtils.validateEntity(mstCodH);
        mstCodHService.updateById(mstCodH);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcodh:deleteOne")
    public R deletedeleteOne(@RequestParam("codId") Integer codId){
            mstCodHService.removeById(codId);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcodh:delete")
    public R delete(@RequestParam("codIds") Integer[] codIds){
        mstCodHService.removeByIds(Arrays.asList(codIds));

        return R.ok();
    }

}
