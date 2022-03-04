/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuidReprDEntity;
import jp.learningpark.modules.common.service.GuidReprDService;
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
@RequestMapping("common/guidreprd")
public class GuidReprDController {
    @Autowired
    private GuidReprDService guidReprDService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprd:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guidReprDService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprd:info")
    public R info(@PathVariable("id") Integer id){
        GuidReprDEntity guidReprD = guidReprDService.getById(id);

        return R.ok().put("guidReprD", guidReprD);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprd:save")
    public R save(@RequestBody GuidReprDEntity guidReprD){
        guidReprDService.save(guidReprD);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprd:update")
    public R update(@RequestBody GuidReprDEntity guidReprD){
        ValidatorUtils.validateEntity(guidReprD);
        guidReprDService.updateById(guidReprD);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprd:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guidReprDService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprd:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guidReprDService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
