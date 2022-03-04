/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.service.F30424Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F30424_保護者面談記録詳細画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/21 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/guard/F30424")
public class F30424Controller {

    /**
     * 保護者面談記録詳細画面 Service
     */
    @Autowired
    F30424Service f30424Service;

    /**
     * @param messageId メッセージID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R getInitData(Integer messageId) {
        return f30424Service.getInitData(messageId);
    }
}
