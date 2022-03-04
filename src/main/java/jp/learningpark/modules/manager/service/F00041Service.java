/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>ユーザー基本情報一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/13 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F00041Service {

    /**
     * @param afterUsrId 変更後ユーザID
     * @param userId ユーザーID
     * @return
     */
    R changeStatus(String afterUsrId, String userId);

    /**
     * @param usrRole 　ロール区分
     * @param specAuthFlg 　特殊権限フラグ
     * @param name 　画面入力した姓名
     * @param knName 　画面入力したカナ姓名
     * @param afterUsrId 　利用者ログインＩＤ
     * @param usrSts 　ステータス
     * @param schy 　学年
     * @param orgId 　組織
     * @param orgIdList 　組織リスト
     * @param limit
     * @param page
     * @return
     */
    R retrieval(String usrRole, String specAuthFlg, String name, String knName, String afterUsrId, String usrSts, String schy, String orgId, String orgIdList, Integer limit, Integer page);

    /**
     * @param orgId 組織ID
     * @param limit
     * @param page
     * @return
     */
    R initUserList(String orgId, Integer limit, Integer page);

    /**
     * @return
     */
    R orgInfo();
}
