/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstSurgeryFormEntity;
import jp.learningpark.modules.common.service.MstSurgeryFormService;
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
 * 面談フォーム
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstsurgeryform")
public class MstSurgeryFormController {
    @Autowired
    private MstSurgeryFormService mstSurgeryFormService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstsurgeryform:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstSurgeryFormService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstsurgeryform:info")
    public R info(@PathVariable("id") Integer id){
        MstSurgeryFormEntity mstSurgeryForm = mstSurgeryFormService.getById(id);

        return R.ok().put("mstSurgeryForm", mstSurgeryForm);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstsurgeryform:save")
    public R save(@RequestBody MstSurgeryFormEntity mstSurgeryForm){
        mstSurgeryFormService.save(mstSurgeryForm);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstsurgeryform:update")
    public R update(@RequestBody MstSurgeryFormEntity mstSurgeryForm){
        ValidatorUtils.validateEntity(mstSurgeryForm);
        mstSurgeryFormService.updateById(mstSurgeryForm);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstsurgeryform:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstSurgeryFormService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstsurgeryform:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstSurgeryFormService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
