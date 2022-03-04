package jp.learningpark.modules.com.service;


import jp.learningpark.framework.utils.R;

/**
 * <p>教室選択画面(生徒スマホ画面)</p >
 *
 * @author NWT : lpp <br />
 * 変更履歴 <br />
 * 2020/08/06 : lpp: 新規<br />
 * @version 8.0
 */
public interface F40016Service {

    /**
     * 組織情報データを取得する
     * @param afterUsrId セッションデータ．ログインID
     * @return
     */
    R search(String afterUsrId);


    /**
     * @param afterUsrId セッションデータ．ログインID
     * @param pageSize ページあたりのアイテム数
     * @return
     */
    R searchMore(String afterUsrId,Integer pageSize,String orgId,String orgName);
}
