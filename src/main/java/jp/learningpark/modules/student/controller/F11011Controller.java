/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.student.dto.F11011Dto;
import jp.learningpark.modules.student.service.F11011Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>スマホ_メッセージ詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/29 : zpa: 新規<br />
 * @version 7.0
 */
@RestController
@RequestMapping("/student/F11011")
public class F11011Controller {
    @Autowired
    F11011Service f11011Service;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer messageId) {
        F11011Dto f11011Dto = f11011Service.init(messageId);
        if(f11011Dto != null){
            return R.ok().put("dto", f11011Dto);
        }
        return R.ok();
    }
    /**
     * 閲覧状況を更新する。
     */
    @PostMapping("noticeConfirm")
    public R noticeConfirm(Integer messageId){
        f11011Service.noticeConfirm(messageId);
        return R.ok();
    }
}