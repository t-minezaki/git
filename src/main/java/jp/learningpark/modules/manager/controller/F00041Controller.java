/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.service.F00041Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ユーザー基本情報一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/13 : hujunjie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00041")
@RestController
public class F00041Controller {
    /**
     * ユーザー基本情報一覧画面 Service
     */
    @Autowired
    F00041Service f00041Service;

    /**
     * 組織表示
     *
     * @return
     */
    @RequestMapping(value = "/org", method = RequestMethod.GET)
    public R init() {
        return f00041Service.orgInfo();
    }

    /**
     * 初期表示
     *
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String orgId, Integer limit, Integer page) {
        return f00041Service.initUserList(orgId, limit, page);
    }

    /**
     * ユーザ―一覧を取得
     *
     * @param usrRole ユーザ権限区分
     * @param name 姓名
     * @param knName カナ姓名
     * @param afterUsrId 変更後ユーザID
     * @param usrSts ユーザステータス
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R select(String usrRole, String specAuthFlg, String name, String knName, String afterUsrId, String usrSts, String schy, String orgId, String orgIdList, Integer limit, Integer page) {
        return f00041Service.retrieval(usrRole, specAuthFlg, name, knName, afterUsrId, usrSts, schy, orgId, orgIdList, limit, page);
    }

    /**
     * @param afterUsrId 変更後ユーザID
     * @param userId ユーザーID
     * @return
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public R change(String afterUsrId, String userId) {
        return f00041Service.changeStatus(afterUsrId, userId);
    }
}
