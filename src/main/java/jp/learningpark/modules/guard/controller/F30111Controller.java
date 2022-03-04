/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.service.F30111Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>塾ニュース詳細表示画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30111/")
public class F30111Controller {

    /**
     * 塾ニュース詳細表示画面 Service
     */
    @Autowired
    F30111Service f30111Service;

    /**
     * 初期化  ページ
     *
     * @param id お知らせＩＤ
     * @param url 当画面URL
     * @return R
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f30111init(Integer id, String url) {

        return f30111Service.getNoticeNews(id, url);
    }
}