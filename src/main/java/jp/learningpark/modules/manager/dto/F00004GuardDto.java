/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/28 : gong: 新規<br />
 * @version 1.0
 */
public class F00004GuardDto {
    /**
     * ユーザID
     */
    private String usrId;

    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;

    /**
     * ユーザログインPW
     */
    private String usrPassword;

    /**
     * 姓名_姓
     */
    private String flnmNm;

    /**
     * 姓名_名
     */
    private String flnmLnm;

    /**
     * 姓名_カナ姓
     */
    private String flnmKnNm;

    /**
     * 姓名_カナ名
     */
    private String flnmKnLnm;

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 続柄区分
     */
    private String reltnspDiv;

    /**
     * 住所１
     */
    private String adr1;

    /**
     * 住所２
     */
    private String adr2;

    /**
     * 郵便番号
     */
    private String postcd;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * GIDPK
     */
    private String gidpk;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * ユーザIDを設定する
     *
     * @return usrId ユーザID
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * ユーザIDを取得する
     *
     * @param usrId ユーザID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * ユーザログインPWを設定する
     *
     * @return usrPassword ユーザログインPW
     */
    public String getUsrPassword() {
        return this.usrPassword;
    }

    /**
     * ユーザログインPWを取得する
     *
     * @param usrPassword ユーザログインPW
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    /**
     * 姓名_姓を設定する
     *
     * @return flnmNm 姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 姓名_姓を取得する
     *
     * @param flnmNm 姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 姓名_名を設定する
     *
     * @return flnmLnm 姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 姓名_名を取得する
     *
     * @param flnmLnm 姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 姓名_カナ姓を設定する
     *
     * @return flnmKnNm 姓名_カナ姓
     */
    public String getFlnmKnNm() {
        return this.flnmKnNm;
    }

    /**
     * 姓名_カナ姓を取得する
     *
     * @param flnmKnNm 姓名_カナ姓
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * 姓名_カナ名を設定する
     *
     * @return flnmKnLnm 姓名_カナ名
     */
    public String getFlnmKnLnm() {
        return this.flnmKnLnm;
    }

    /**
     * 姓名_カナ名を取得する
     *
     * @param flnmKnLnm 姓名_カナ名
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * メールアドレスを設定する
     *
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return this.mailad;
    }

    /**
     * メールアドレスを取得する
     *
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 電話番号を設定する
     *
     * @return telnum 電話番号
     */
    public String getTelnum() {
        return this.telnum;
    }

    /**
     * 電話番号を取得する
     *
     * @param telnum 電話番号
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * 続柄区分を設定する
     *
     * @return reltnspDiv 続柄区分
     */
    public String getReltnspDiv() {
        return this.reltnspDiv;
    }

    /**
     * 続柄区分を取得する
     *
     * @param reltnspDiv 続柄区分
     */
    public void setReltnspDiv(String reltnspDiv) {
        this.reltnspDiv = reltnspDiv;
    }

    /**
     * 住所１を設定する
     *
     * @return adr1 住所１
     */
    public String getAdr1() {
        return this.adr1;
    }

    /**
     * 住所１を取得する
     *
     * @param adr1 住所１
     */
    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    /**
     * 住所２を設定する
     *
     * @return adr2 住所２
     */
    public String getAdr2() {
        return this.adr2;
    }

    /**
     * 住所２を取得する
     *
     * @param adr2 住所２
     */
    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }

    /**
     * 郵便番号を設定する
     *
     * @return postcd 郵便番号
     */
    public String getPostcd() {
        return this.postcd;
    }

    /**
     * 郵便番号を取得する
     *
     * @param postcd 郵便番号
     */
    public void setPostcd(String postcd) {
        this.postcd = postcd;
    }

    /**
     * 緊急電話番号を取得する
     *
     * @return urgTelnum 緊急電話番号
     */
    public String getUrgTelnum() {
        return this.urgTelnum;
    }

    /**
     * 緊急電話番号を設定する
     *
     * @param urgTelnum 緊急電話番号
     */
    public void setUrgTelnum(String urgTelnum) {
        this.urgTelnum = urgTelnum;
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

    /**
     * GIDPKを取得する
     *
     * @return gidpk GIDPK
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * GIDPKを設定する
     *
     * @param gidpk GIDPK
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
