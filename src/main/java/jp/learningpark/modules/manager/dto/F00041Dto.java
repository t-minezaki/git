/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;

/**
 * <p>ユーザー基本情報一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/12 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F00041Dto {
    /**
     * ユーザID
     */
    private String userId;

    /**
     * 変更後ユーザID
     */
    private String afterUsrId;

    /**
     * ユーザステータス
     */
    private String usrSts;

    /**
     * ユーザステータス値
     */
    private String usrVal;

    /**
     * 姓名＿姓
     */
    private String firstName;

    /**
     * 姓名＿名
     */
    private String lastName;

    /**
     * カナ姓名＿カナ姓
     */
    private String firstKnName;

    /**
     * カナ姓名＿かな名
     */
    private String lastKnName;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * PW更新フラグ
     */
    private String pwUpFlg;

    /**
     * パスワード
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * カナ姓名
     */
    private String knName;

    /**
     * 続柄
     */
    private String tuzukiGara;

    /**
     * 住所
     */
    private String adr;

    /**
     * 学年
     */
    private String schy;

    /**
     * 生年月日
     */
    private Timestamp birthd;

    /**
     * 生年月日Format
     */
    private String birth;

    /**
     * 性別
     */
    private String gendrVal;

    /**
     * 学校
     */
    private String schNm;
    /**
     * 削除フラグ
     */
    private Integer delFlg;
    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 組織共用キー
     */
    private String orgCommKey;

    /**
     * 他システム区分
     */
    private String systemKbn;

    /**
     * 特殊権限フラグ
     */
    private String specAuthFlg;

    /**
     * userIdを取得する
     *
     * @return userId userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userIdを設定する
     *
     * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * afterUsrIdを取得する
     *
     * @return afterUsrId afterUsrId
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * afterUsrIdを設定する
     *
     * @param afterUsrId afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * usrStsを取得する
     *
     * @return usrSts usrSts
     */
    public String getUsrSts() {
        return usrSts;
    }

    /**
     * usrStsを設定する
     *
     * @param usrSts usrSts
     */
    public void setUsrSts(String usrSts) {
        this.usrSts = usrSts;
    }

    /**
     * firstNameを取得する
     *
     * @return firstName firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * firstNameを設定する
     *
     * @param firstName firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * lastNameを取得する
     *
     * @return lastName lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * lastNameを設定する
     *
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * firstKnNameを取得する
     *
     * @return firstKnName firstKnName
     */
    public String getFirstKnName() {
        return firstKnName;
    }

    /**
     * firstKnNameを設定する
     *
     * @param firstKnName firstKnName
     */
    public void setFirstKnName(String firstKnName) {
        this.firstKnName = firstKnName;
    }

    /**
     * lastKnNameを取得する
     *
     * @return lastKnName lastKnName
     */
    public String getLastKnName() {
        return lastKnName;
    }

    /**
     * lastKnNameを設定する
     *
     * @param lastKnName lastKnName
     */
    public void setLastKnName(String lastKnName) {
        this.lastKnName = lastKnName;
    }

    /**
     * telnumを取得する
     *
     * @return telnum telnum
     */
    public String getTelnum() {
        return telnum;
    }

    /**
     * telnumを設定する
     *
     * @param telnum telnum
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * pwUpFlgを取得する
     *
     * @return pwUpFlg pwUpFlg
     */
    public String getPwUpFlg() {
        return pwUpFlg;
    }

    /**
     * pwUpFlgを設定する
     *
     * @param pwUpFlg pwUpFlg
     */
    public void setPwUpFlg(String pwUpFlg) {
        this.pwUpFlg = pwUpFlg;
    }

    /**
     * passwordを取得する
     *
     * @return password password
     */
    public String getPassword() {
        return password;
    }

    /**
     * passwordを設定する
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * nameを取得する
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定する
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * knNameを取得する
     *
     * @return knName knName
     */
    public String getKnName() {
        return knName;
    }

    /**
     * knNameを設定する
     *
     * @param knName knName
     */
    public void setKnName(String knName) {
        this.knName = knName;
    }

    /**
     * tuzukiGaraを取得する
     *
     * @return tuzukiGara tuzukiGara
     */
    public String getTuzukiGara() {
        return tuzukiGara;
    }

    /**
     * tuzukiGaraを設定する
     *
     * @param tuzukiGara tuzukiGara
     */
    public void setTuzukiGara(String tuzukiGara) {
        this.tuzukiGara = tuzukiGara;
    }

    /**
     * adrを取得する
     *
     * @return adr adr
     */
    public String getAdr() {
        return adr;
    }

    /**
     * adrを設定する
     *
     * @param adr adr
     */
    public void setAdr(String adr) {
        this.adr = adr;
    }

    /**
     * schyを取得する
     *
     * @return schy schy
     */
    public String getSchy() {
        return schy;
    }

    /**
     * schyを設定する
     *
     * @param schy schy
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * gendrValを取得する
     *
     * @return gendrVal gendrVal
     */
    public String getGendrVal() {
        return gendrVal;
    }

    /**
     * gendrValを設定する
     *
     * @param gendrVal gendrVal
     */
    public void setGendrVal(String gendrVal) {
        this.gendrVal = gendrVal;
    }

    /**
     * schNmを取得する
     *
     * @return schNm schNm
     */
    public String getSchNm() {
        return schNm;
    }

    /**
     * schNmを設定する
     *
     * @param schNm schNm
     */
    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    /**
     * birthdを設定する
     *
     * @param birthd birthd
     */
    public void setBirthd(Timestamp birthd) {
        this.birthd = birthd;
    }

    /**
     * birthdを取得する
     *
     * @return birthd birthd
     */
    public Timestamp getBirthd() {
        return birthd;
    }

    /**
     * birthを取得する
     *
     * @return birth birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * birthを設定する
     *
     * @param birth birth
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * usrValを取得する
     *
     * @return usrVal usrVal
     */
    public String getUsrVal() {
        return usrVal;
    }

    /**
     * usrValを設定する
     *
     * @param usrVal usrVal
     */
    public void setUsrVal(String usrVal) {
        this.usrVal = usrVal;
    }

    /**
     * 削除フラグを取得する
     *
     * @return delFlg 削除フラグ
     */
    public Integer getDelFlg() {
        return this.delFlg;
    }

    /**
     * 削除フラグを設定する
     *
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 組織共用キーを取得する
     *
     * @return orgCommKey 組織共用キー
     */
    public String getOrgCommKey() {
        return this.orgCommKey;
    }

    /**
     * 組織共用キーを設定する
     *
     * @param orgCommKey 組織共用キー
     */
    public void setOrgCommKey(String orgCommKey) {
        this.orgCommKey = orgCommKey;
    }

    /**
     * を取得する
     *
     * @return systemKbn
     */
    public String getSystemKbn() {
        return this.systemKbn;
    }

    /**
     * を設定する
     *
     * @param systemKbn
     */
    public void setSystemKbn(String systemKbn) {
        this.systemKbn = systemKbn;
    }

    /**
     * 特殊権限フラグを取得する
     *
     * @return specAuthFlg 特殊権限フラグ
     */
    public String getSpecAuthFlg() {
        return this.specAuthFlg;
    }

    /**
     * 特殊権限フラグを設定する
     *
     * @param specAuthFlg 特殊権限フラグ
     */
    public void setSpecAuthFlg(String specAuthFlg) {
        this.specAuthFlg = specAuthFlg;
    }
}
