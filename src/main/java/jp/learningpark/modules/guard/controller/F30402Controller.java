/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.service.F30402Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>塾からのイベント情報詳細画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/30: hujiaxing: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30402/")
public class F30402Controller {

    /**
     * 塾からのイベント情報詳細画面 Service
     */
    @Autowired
    F30402Service f30402Service;

    /**
     * 初期化  ページ
     * @param id イベントＩＤ
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer id) {
        return f30402Service.getEventNews(id);
    }
}