/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.AskAboutRecordEntity;
import jp.learningpark.modules.common.service.AskAboutRecordService;
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
 * 問い合せ記録
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/askaboutrecord")
public class AskAboutRecordController {
    @Autowired
    private AskAboutRecordService askAboutRecordService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:askaboutrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = askAboutRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:askaboutrecord:info")
    public R info(@PathVariable("id") Integer id){
        AskAboutRecordEntity askAboutRecord = askAboutRecordService.getById(id);

        return R.ok().put("askAboutRecord", askAboutRecord);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:askaboutrecord:save")
    public R save(@RequestBody AskAboutRecordEntity askAboutRecord){
        askAboutRecordService.save(askAboutRecord);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:askaboutrecord:update")
    public R update(@RequestBody AskAboutRecordEntity askAboutRecord){
        ValidatorUtils.validateEntity(askAboutRecord);
        askAboutRecordService.updateById(askAboutRecord);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:askaboutrecord:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            askAboutRecordService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:askaboutrecord:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        askAboutRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
