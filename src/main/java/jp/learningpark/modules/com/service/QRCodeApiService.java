package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>
 * マナミルQR認識APIサービス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/18 ： NWT)hxl ： 新規作成
 * @date 2019/12/18 11:24
 */
public interface QRCodeApiService {
    //V8.0 new version
//    /**
//     * ログイン
//     *
//     * @param orgId         組織ID
//     * @param loginId       入退室アプリログイン用ID
//     * @param password      入退室アプリログイン用パスワード
//     * @return
//     */
//    R login(String orgId, String loginId, String password);
//
//    /**
//     * 更新トークン
//     *
//     * @param orgId     組織ID
//     * @param loginId   入退室アプリログイン用ID
//     * @param token     トークン
//     * @return
//     */
//    R tokenRefresh(String orgId, String loginId, String token);
//
//    /**
//     * 生徒登校または下校
//     *
//     * @param orgId         組織ID
//     * @param loginId       入退室アプリログイン用ID
//     * @param studentId     生徒ID
//     * @param token         トークン
//     * @return
//     */
//    R studentLogin(String orgId, String loginId, String studentId, String token);

    //V6.0 old version
    /**
     * ログイン
     *
     * @param brandCode ブランドコード
     * @param orgId     入退室アプリログイン用ID
     * @param password  入退室アプリログイン用パスワード
     * @return
     */
    R login(String brandCode, String orgId, String password);

    /**
     * 更新トークン
     *
     * @param brandCode ブランドコード
     * @param orgId     入退室アプリログイン用ID
     * @param token     トークン
     * @return
     */
    R tokenRefresh(String brandCode, String orgId, String token);

    /**
     * 生徒登校または下校
     *
     * @param brandCode ブランドコード
     * @param orgId     入退室アプリログイン用ID
     * @param studentId 生徒ID
     * @param token     トークン
     * @return
     */
    R studentLogin(String brandCode, String orgId, String studentId, String token);

    void setTestFlag(String testFlag);

    String getTestFlag();
}
