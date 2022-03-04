/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.NoticeMailSendHstEntity;
import jp.learningpark.modules.common.service.NoticeMailSendHstService;
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
 * 指導報告書明細
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/noticemailsendhst")
public class NoticeMailSendHstController {
    @Autowired
    private NoticeMailSendHstService noticeMailSendHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:noticemailsendhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = noticeMailSendHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:noticemailsendhst:info")
    public R info(@PathVariable("id") Integer id){
        NoticeMailSendHstEntity noticeMailSendHst = noticeMailSendHstService.getById(id);

        return R.ok().put("noticeMailSendHst", noticeMailSendHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:noticemailsendhst:save")
    public R save(@RequestBody NoticeMailSendHstEntity noticeMailSendHst){
        noticeMailSendHstService.save(noticeMailSendHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:noticemailsendhst:update")
    public R update(@RequestBody NoticeMailSendHstEntity noticeMailSendHst){
        ValidatorUtils.validateEntity(noticeMailSendHst);
        noticeMailSendHstService.updateById(noticeMailSendHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:noticemailsendhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            noticeMailSendHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:noticemailsendhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        noticeMailSendHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
