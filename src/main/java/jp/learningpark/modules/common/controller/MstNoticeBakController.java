/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.modules.common.entity.MstNoticeBakEntity;
import jp.learningpark.modules.common.service.MstNoticeBakService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
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
 * 保護者お知らせ閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstnoticebak")
public class MstNoticeBakController {
    @Autowired
    private MstNoticeBakService mstNoticeBakService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnoticebak:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstNoticeBakService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnoticebak:info")
    public R info(@PathVariable("id") Integer id){
        MstNoticeBakEntity mstNoticeBak = mstNoticeBakService.getById(id);

        return R.ok().put("mstNoticeBak", mstNoticeBak);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnoticebak:save")
    public R save(@RequestBody MstNoticeBakEntity mstNoticeBak){
        mstNoticeBakService.save(mstNoticeBak);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnoticebak:update")
    public R update(@RequestBody MstNoticeBakEntity mstNoticeBak){
        ValidatorUtils.validateEntity(mstNoticeBak);
        mstNoticeBakService.updateById(mstNoticeBak);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnoticebak:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstNoticeBakService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnoticebak:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstNoticeBakService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
