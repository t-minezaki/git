/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstNumassEntity;
import jp.learningpark.modules.common.service.MstNumassService;
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
 * 採番マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstnumass")
public class MstNumassController {
    @Autowired
    private MstNumassService mstNumassService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnumass:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstNumassService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnumass:info")
    public R info(@PathVariable("id") Integer id){
        MstNumassEntity mstNumass = mstNumassService.getById(id);

        return R.ok().put("mstNumass", mstNumass);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnumass:save")
    public R save(@RequestBody MstNumassEntity mstNumass){
        mstNumassService.save(mstNumass);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnumass:update")
    public R update(@RequestBody MstNumassEntity mstNumass){
        ValidatorUtils.validateEntity(mstNumass);
        mstNumassService.updateById(mstNumass);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnumass:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstNumassService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnumass:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstNumassService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
