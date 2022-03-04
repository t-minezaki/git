/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.TextbDefTimeInfoService;
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
 * 教科書デフォルトターム情報
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/textbdeftimeinfo")
public class TextbDefTimeInfoController {
    @Autowired
    private TextbDefTimeInfoService textbDefTimeInfoService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:textbdeftimeinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = textbDefTimeInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:textbdeftimeinfo:info")
    public R info(@PathVariable("id") Integer id){
        TextbDefTimeInfoEntity textbDefTimeInfo = textbDefTimeInfoService.getById(id);

        return R.ok().put("textbDefTimeInfo", textbDefTimeInfo);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:textbdeftimeinfo:save")
    public R save(@RequestBody TextbDefTimeInfoEntity textbDefTimeInfo){
        textbDefTimeInfoService.save(textbDefTimeInfo);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:textbdeftimeinfo:update")
    public R update(@RequestBody TextbDefTimeInfoEntity textbDefTimeInfo){
        ValidatorUtils.validateEntity(textbDefTimeInfo);
        textbDefTimeInfoService.updateById(textbDefTimeInfo);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:textbdeftimeinfo:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            textbDefTimeInfoService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:textbdeftimeinfo:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        textbDefTimeInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
