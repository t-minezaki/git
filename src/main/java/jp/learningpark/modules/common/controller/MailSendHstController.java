/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MailSendHstEntity;
import jp.learningpark.modules.common.service.MailSendHstService;
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
 * メール送信履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mailsendhst")
public class MailSendHstController {
    @Autowired
    private MailSendHstService mailSendHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mailsendhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mailSendHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mailsendhst:info")
    public R info(@PathVariable("id") Integer id){
        MailSendHstEntity mailSendHst = mailSendHstService.getById(id);

        return R.ok().put("mailSendHst", mailSendHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mailsendhst:save")
    public R save(@RequestBody MailSendHstEntity mailSendHst){
        mailSendHstService.save(mailSendHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mailsendhst:update")
    public R update(@RequestBody MailSendHstEntity mailSendHst){
        ValidatorUtils.validateEntity(mailSendHst);
        mailSendHstService.updateById(mailSendHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mailsendhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mailSendHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mailsendhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mailSendHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
