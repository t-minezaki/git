package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通知アプリ連携API
 *
 * @author NWT 楊
 */
public interface NoticeApiService {

    /**
     * ブランド取得API
     *
     * @param orgId ログイン者に所属する組織（教室）コード
     * @param type
     * @return
     */
    R getBrandCd(String type, HttpServletRequest request);

    /**
     * 通知アプリのログインAPI
     *
     * @param loginId 送信JSON１．loginId
     * @param Password マナミルパスワード
     * @param orgId 本塾に属するブランドコード
     * @param deviceToken アプリ端末のdeviceToken
     * @param phoneType 端末のタイプ
     * @param request
     * @return
     */
    R appLoginCheck(String loginId, String Password, String deviceToken, String phoneType, HttpServletRequest request, HttpServletResponse response);

    /**
     * 未読件数取得Api
     *
     * @param request
     * @param token 端末のdeviceToken
     * @return
     */
    R unReadGet(HttpServletRequest request, String token, Integer flg);

    /**
     * メッセージ送信
     *
     * @param data 送信data(JSON)
     * @return
     */
    R sendMessage(String data);

    /**
     * 送信失敗データの取得(errorDataGet)
     *
     * @param startDateTime 送信時間区間の開始時間
     * @param endDateTime 送信時間区間の終了時間
     * @return
     */
    R getErrorData(Long startDateTime, Long endDateTime);

    /**
     * プッシュ失敗データに登録
     *
     * @param result
     * @return
     */
    R insert(String result);

    /**
     * メッセージの備考内容の取得
     *
     * @return
     */
    String getMessageDetail(String codCd);

    /**
     * ログアウト（logout）(ログアウトAPIを改修 )
     *
     * @param loginId
     * 2020/11/11 modify yang
     * @return
     */
    R logout(String loginId, HttpServletRequest request);

}
