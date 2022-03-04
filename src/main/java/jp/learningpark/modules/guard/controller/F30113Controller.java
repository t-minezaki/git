/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.service.F30113Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>塾からの連絡通知詳細画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/11: hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30113/")
public class F30113Controller {

    @Autowired
    F30113Service f30113Service;

    /**
     * 初期化  ページ
     * @param id お知らせＩＤ
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f30113init(Integer id) {
        return f30113Service.getNoticeNews(id);
    }

    // 2020/11/11 zhangminghao modify start
    /**
     * 保護者お知らせ閲覧状況を更新する。
     */
    @PostMapping("noticeConfirm")
    public R noticeConfirm(Integer guardReadingId){
        f30113Service.noticeConfirm(guardReadingId);
        return R.ok();
    }
    // 2020/11/11 zhangminghao modify end
}