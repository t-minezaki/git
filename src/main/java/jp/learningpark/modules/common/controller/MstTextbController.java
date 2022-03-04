/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.service.MstTextbService;
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
 * 教科書マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/msttextb")
public class MstTextbController {
    @Autowired
    private MstTextbService mstTextbService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:msttextb:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstTextbService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{textbId}",method = RequestMethod.GET)
    @RequiresPermissions("common:msttextb:info")
    public R info(@PathVariable("textbId") Integer textbId){
        MstTextbEntity mstTextb = mstTextbService.getById(textbId);

        return R.ok().put("mstTextb", mstTextb);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:msttextb:save")
    public R save(@RequestBody MstTextbEntity mstTextb){
        mstTextbService.save(mstTextb);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:msttextb:update")
    public R update(@RequestBody MstTextbEntity mstTextb){
        ValidatorUtils.validateEntity(mstTextb);
        mstTextbService.updateById(mstTextb);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:msttextb:deleteOne")
    public R deletedeleteOne(@RequestParam("textbId") Integer textbId){
            mstTextbService.removeById(textbId);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:msttextb:delete")
    public R delete(@RequestParam("textbIds") Integer[] textbIds){
        mstTextbService.removeByIds(Arrays.asList(textbIds));

        return R.ok();
    }

}
