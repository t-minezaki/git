/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuidReprHEntity;
import jp.learningpark.modules.common.service.GuidReprHService;
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
 * 指導報告書ヘーダ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guidreprh")
public class GuidReprHController {
    @Autowired
    private GuidReprHService guidReprHService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprh:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guidReprHService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprh:info")
    public R info(@PathVariable("id") Integer id){
        GuidReprHEntity guidReprH = guidReprHService.getById(id);

        return R.ok().put("guidReprH", guidReprH);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprh:save")
    public R save(@RequestBody GuidReprHEntity guidReprH){
        guidReprHService.save(guidReprH);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprh:update")
    public R update(@RequestBody GuidReprHEntity guidReprH){
        ValidatorUtils.validateEntity(guidReprH);
        guidReprHService.updateById(guidReprH);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprh:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guidReprHService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprh:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guidReprHService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
