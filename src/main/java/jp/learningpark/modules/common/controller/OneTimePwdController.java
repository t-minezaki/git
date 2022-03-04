/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.OneTimePwdEntity;
import jp.learningpark.modules.common.service.OneTimePwdService;
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
 * ONETIMEパスワード管理
 *
 *@author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/4 : xie: 新規<br />
 * @version 1.0
 * */
@RestController
@RequestMapping("common/onetimepwd")
public class OneTimePwdController {
    @Autowired
    private OneTimePwdService oneTimePwdService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:onetimepwd:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = oneTimePwdService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:onetimepwd:info")
    public R info(@PathVariable("id") Integer id){
        OneTimePwdEntity onetimepwd = oneTimePwdService.getById(id);

        return R.ok().put("onetimepwd", onetimepwd);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:onetimepwd:save")
    public R save(@RequestBody OneTimePwdEntity onetimepwd){
        oneTimePwdService.save(onetimepwd);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:onetimepwd:update")
    public R update(@RequestBody OneTimePwdEntity onetimepwd){
        ValidatorUtils.validateEntity(onetimepwd);
        oneTimePwdService.updateById(onetimepwd);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:onetimepwd:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            oneTimePwdService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:onetimepwd:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        oneTimePwdService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
