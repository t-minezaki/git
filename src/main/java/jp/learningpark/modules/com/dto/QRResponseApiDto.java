package jp.learningpark.modules.com.dto;

import java.util.List;

/**
 * <p>
 * QR解答相关 Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/24 ： NWT)hxl ： 新規作成
 * @date 2020/02/24 11:48
 */
public class QRResponseApiDto {

    /**
     * checkver で固定
     */
    private String type;

    /**
     * 端末のバージョン情報配列名
     */
    private List<QRResponseVerInfoDto> verinfo;

    /**
     * アプリバージョン
     */
    private String appv;

    /**
     * 解答集アプリログイン用ID
     */
    private String orgId;

    /**
     * 解答集アプリログイン用ID
     */
    private String loginId;

    /**
     * 解答集アプリログイン用パスワード
     */
    private String password;

    /**
     * 姓名
     */
    private String userName;

    /**
     * ユーザーID
     */
    private String usrId;

    /**
     * パスワード
     */
    private String usrPassword;

    /**
     * 学研IDプライマリキー
     */
    private String gidpk;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * checkver で固定を取得する
     *
     * @return type checkver で固定
     */
    public String getType() {
        return this.type;
    }

    /**
     * checkver で固定を設定する
     *
     * @param type checkver で固定
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 端末のバージョン情報配列名を取得する
     *
     * @return verinfo 端末のバージョン情報配列名
     */
    public List<QRResponseVerInfoDto> getVerinfo() {
        return this.verinfo;
    }

    /**
     * 端末のバージョン情報配列名を設定する
     *
     * @param verinfo 端末のバージョン情報配列名
     */
    public void setVerinfo(List<QRResponseVerInfoDto> verinfo) {
        this.verinfo = verinfo;
    }

    /**
     * アプリバージョンを取得する
     *
     * @return appv アプリバージョン
     */
    public String getAppv() {
        return this.appv;
    }

    /**
     * アプリバージョンを設定する
     *
     * @param appv アプリバージョン
     */
    public void setAppv(String appv) {
        this.appv = appv;
    }

    /**
     * 解答集アプリログイン用IDを取得する
     *
     * @return orgId 解答集アプリログイン用ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 解答集アプリログイン用IDを設定する
     *
     * @param orgId 解答集アプリログイン用ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 解答集アプリログイン用IDを取得する
     *
     * @return loginId 解答集アプリログイン用ID
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * 解答集アプリログイン用IDを設定する
     *
     * @param loginId 解答集アプリログイン用ID
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * 解答集アプリログイン用パスワードを取得する
     *
     * @return password 解答集アプリログイン用パスワード
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 解答集アプリログイン用パスワードを設定する
     *
     * @param password 解答集アプリログイン用パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 姓名を取得する
     *
     * @return userName 姓名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 姓名を設定する
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * ユーザーIDを取得する
     *
     * @return usrId ユーザーID
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * ユーザーIDを設定する
     *
     * @param usrId ユーザーID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * パスワードを取得する
     *
     * @return usrPassword パスワード
     */
    public String getUsrPassword() {
        return this.usrPassword;
    }

    /**
     * パスワードを設定する
     *
     * @param usrPassword パスワード
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    /**
     * 学研IDプライマリキーを取得する
     *
     * @return gidpk 学研IDプライマリキー
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 学研IDプライマリキーを設定する
     *
     * @param gidpk 学研IDプライマリキー
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * GIDフラグを取得する
     *
     * @return gidFlg GIDフラグ
     */
    public String getGidFlg() {
        return this.gidFlg;
    }

    /**
     * GIDフラグを設定する
     *
     * @param gidFlg GIDフラグ
     */
    public void setGidFlg(String gidFlg) {
        this.gidFlg = gidFlg;
    }
}
