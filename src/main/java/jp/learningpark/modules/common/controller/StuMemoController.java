/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuMemoEntity;
import jp.learningpark.modules.common.service.StuMemoService;
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
 * 生徒メモ情報
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stumemo")
public class StuMemoController {
    @Autowired
    private StuMemoService stuMemoService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stumemo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuMemoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stumemo:info")
    public R info(@PathVariable("id") Integer id){
        StuMemoEntity stuMemo = stuMemoService.getById(id);

        return R.ok().put("stuMemo", stuMemo);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stumemo:save")
    public R save(@RequestBody StuMemoEntity stuMemo){
        stuMemoService.save(stuMemo);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stumemo:update")
    public R update(@RequestBody StuMemoEntity stuMemo){
        ValidatorUtils.validateEntity(stuMemo);
        stuMemoService.updateById(stuMemo);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stumemo:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuMemoService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stumemo:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuMemoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
