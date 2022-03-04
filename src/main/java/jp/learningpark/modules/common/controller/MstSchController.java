/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstSchEntity;
import jp.learningpark.modules.common.service.MstSchService;
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
 * 学校マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstsch")
public class MstSchController {
    @Autowired
    private MstSchService mstSchService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstsch:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstSchService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstsch:info")
    public R info(@PathVariable("id") Integer id){
        MstSchEntity mstSch = mstSchService.getById(id);

        return R.ok().put("mstSch", mstSch);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstsch:save")
    public R save(@RequestBody MstSchEntity mstSch){
        mstSchService.save(mstSch);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstsch:update")
    public R update(@RequestBody MstSchEntity mstSch){
        ValidatorUtils.validateEntity(mstSch);
        mstSchService.updateById(mstSch);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstsch:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstSchService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstsch:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstSchService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
