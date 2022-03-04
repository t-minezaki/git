/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuGetPointHstEntity;
import jp.learningpark.modules.common.service.StuGetPointHstService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 生徒付与ポイント履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stugetpointhst")
public class StuGetPointHstController {
    @Autowired
    private StuGetPointHstService stuGetPointHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stugetpointhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuGetPointHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stugetpointhst:info")
    public R info(@PathVariable("id") Integer id){
        StuGetPointHstEntity stuGetPointHst = stuGetPointHstService.getById(id);

        return R.ok().put("stuGetPointHst", stuGetPointHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stugetpointhst:save")
    public R save(@RequestBody StuGetPointHstEntity stuGetPointHst){
        stuGetPointHstService.save(stuGetPointHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stugetpointhst:update")
    public R update(@RequestBody StuGetPointHstEntity stuGetPointHst){
        ValidatorUtils.validateEntity(stuGetPointHst);
        stuGetPointHstService.updateById(stuGetPointHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stugetpointhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuGetPointHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stugetpointhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuGetPointHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
