/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.service.F21075Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>面談記録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/25 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/pop/F21075")
public class F21075Controller {

    /**
     * 面談記録画面 Service
     */
    @Autowired
    private F21075Service f21075Service;

    /**
     * @param talkId
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer talkId, Integer eventId) {
        return f21075Service.getInitData(talkId, eventId);
    }

    /**
     * @param talkId
     * @param flg
     * @param resultList
     * @return
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public R commit(Integer talkId, String flg, String resultList, boolean isProxy) {
        return f21075Service.updateDB(talkId, flg, resultList, isProxy);
    }
}
