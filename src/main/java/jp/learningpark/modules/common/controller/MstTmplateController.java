/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstTmplateService;
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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/msttmplate")
public class MstTmplateController {
    @Autowired
    private MstTmplateService mstTmplateService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:msttmplate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstTmplateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:msttmplate:info")
    public R info(@PathVariable("id") Integer id){
        MstTmplateEntity mstTmplate = mstTmplateService.getById(id);

        return R.ok().put("mstTmplate", mstTmplate);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:msttmplate:save")
    public R save(@RequestBody MstTmplateEntity mstTmplate){
        mstTmplateService.save(mstTmplate);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:msttmplate:update")
    public R update(@RequestBody MstTmplateEntity mstTmplate){
        ValidatorUtils.validateEntity(mstTmplate);
        mstTmplateService.updateById(mstTmplate);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:msttmplate:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstTmplateService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:msttmplate:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstTmplateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
