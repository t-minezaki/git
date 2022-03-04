/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.modules.common.entity.EntryExitHstBakEntity;
import jp.learningpark.modules.common.service.EntryExitHstBakService;
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
 * 入退室履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/entryexithstbak")
public class EntryExitHstBakController {
    @Autowired
    private EntryExitHstBakService entryExitHstBakService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:entryexithstbak:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entryExitHstBakService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:entryexithstbak:info")
    public R info(@PathVariable("id") Integer id){
        EntryExitHstBakEntity entryExitHstBak = entryExitHstBakService.getById(id);

        return R.ok().put("entryExitHstBak", entryExitHstBak);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:entryexithstbak:save")
    public R save(@RequestBody EntryExitHstBakEntity entryExitHstBak){
        entryExitHstBakService.save(entryExitHstBak);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:entryexithstbak:update")
    public R update(@RequestBody EntryExitHstBakEntity entryExitHstBak){
        ValidatorUtils.validateEntity(entryExitHstBak);
        entryExitHstBakService.updateById(entryExitHstBak);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:entryexithstbak:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            entryExitHstBakService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:entryexithstbak:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        entryExitHstBakService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
