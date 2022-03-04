/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuidReprApplyStsEntity;
import jp.learningpark.modules.common.service.GuidReprApplyStsService;
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
@RequestMapping("common/guidreprapplysts")
public class GuidReprApplyStsController {
    @Autowired
    private GuidReprApplyStsService guidReprApplyStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprapplysts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guidReprApplyStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprapplysts:info")
    public R info(@PathVariable("id") Integer id){
        GuidReprApplyStsEntity guidReprApplySts = guidReprApplyStsService.getById(id);

        return R.ok().put("guidReprApplySts", guidReprApplySts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprapplysts:save")
    public R save(@RequestBody GuidReprApplyStsEntity guidReprApplySts){
        guidReprApplyStsService.save(guidReprApplySts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprapplysts:update")
    public R update(@RequestBody GuidReprApplyStsEntity guidReprApplySts){
        ValidatorUtils.validateEntity(guidReprApplySts);
        guidReprApplyStsService.updateById(guidReprApplySts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprapplysts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guidReprApplyStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprapplysts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guidReprApplyStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
