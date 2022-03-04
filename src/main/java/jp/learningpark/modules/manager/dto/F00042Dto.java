/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstOrgEntity;

import java.util.List;

/**
 * <p>ユーザー初期基本情報＆新規発番画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/12: xiong: 新規<br />
 * @version 1.0
 */
public class F00042Dto {
    /**
     * 階層
     */
    private Integer level;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * ユーザーID
     */
    private String usrId;
    /**
     * 利用者ロール
     */
    private String role;
    /**
     * 利用者ログインＩＤ
     */
    private String userId;
    /**
     * パスワード
     */
    private String password;
    /**
     * 姓名_姓
     */
    private String flnmNm;
    /**
     * 姓名_名
     */
    private String flnmLnm;
    /**
     * カナ姓名_姓
     */
    private String flnmKnNm;
    /**
     * カナ姓名_名
     */
    private String flnmKnLnm;
    /**
     * メールアドレス
     */
    private String email;
    /**
     * 電話番号
     */
    private String telNum;
    /**
     * 郵便番号
     */
    private String postCd;
    /**
     * 続柄
     */
    private String parent;
    /**
     * 住所1
     */
    private String addr1;
    /**
     * 住所2
     */
    private String addr2;
    /**
     * 学校
     */
    private String schoolName;
    /**
     * 学年
     */
    private String schoolYear;
    /**
     * 生年月日
     */
    private String birthDay;
    /**
     * 性別
     */
    private String gender;
    /**
     * ステータス
     */
    private String usrSts;
    /**
     * ＱＲコード
     */
    private String qrCod;
    /**
     * オリジナルCD
     */
    private String oriaCd;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * 学研IDプライマリキー
     */
    private String gidpk;

    /**
     * チェック用ログインID
     */
    private String oldLoginId;

    /**
     *　チェック用パスワード
     */
    private String oldPwd;

    /**
     * 緊急電話番号
     */
    private String urgTelNum;

    /**
     * ユーザー更新日時
     */
    private String updateTime;

    /**
     * 更新日時
     */
    private String updDatime;

    private List<MstOrgEntity> orgList;

    /**
     * roleを取得する
     *
     * @return role role
     */
    public String getRole() {
        return role;
    }

    /**
     * roleを設定する
     *
     * @param role role
     */
    public void setRole(String role) {
        this.role = role;
    }
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
     * flnmNmを取得する
     *
     * @return flnmNm flnmNm
     */
    public String getFlnmNm() {
        return flnmNm;
    }

    /**
     * flnmNmを設定する
     *
     * @param flnmNm flnmNm
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * flnmLnmを取得する
     *
     * @return flnmLnm flnmLnm
     */
    public String getFlnmLnm() {
        return flnmLnm;
    }

    /**
     * flnmLnmを設定する
     *
     * @param flnmLnm flnmLnm
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * flnmKnNmを取得する
     *
     * @return flnmKnNm flnmKnNm
     */
    public String getFlnmKnNm() {
        return flnmKnNm;
    }

    /**
     * flnmKnNmを設定する
     *
     * @param flnmKnNm flnmKnNm
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * flnmKnLnmを取得する
     *
     * @return flnmKnLnm flnmKnLnm
     */
    public String getFlnmKnLnm() {
        return flnmKnLnm;
    }

    /**
     * flnmKnLnmを設定する
     *
     * @param flnmKnLnm flnmKnLnm
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * emailを取得する
     *
     * @return email email
     */
    public String getEmail() {
        return email;
    }

    /**
     * emailを設定する
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * telNumを取得する
     *
     * @return telNum telNum
     */
    public String getTelNum() {
        return telNum;
    }

    /**
     * telNumを設定する
     *
     * @param telNum telNum
     */
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    /**
     * postCdを取得する
     *
     * @return postCd postCd
     */
    public String getPostCd() {
        return postCd;
    }

    /**
     * postCdを設定する
     *
     * @param postCd postCd
     */
    public void setPostCd(String postCd) {
        this.postCd = postCd;
    }

    /**
     * parentを取得する
     *
     * @return parent parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * parentを設定する
     *
     * @param parent parent
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * addr1を取得する
     *
     * @return addr1 addr1
     */
    public String getAddr1() {
        return addr1;
    }

    /**
     * addr1を設定する
     *
     * @param addr1 addr1
     */
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    /**
     * addr2を取得する
     *
     * @return addr2 addr2
     */
    public String getAddr2() {
        return addr2;
    }

    /**
     * addr2を設定する
     *
     * @param addr2 addr2
     */
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    /**
     * schoolNameを取得する
     *
     * @return schoolName schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * schoolNameを設定する
     *
     * @param schoolName schoolName
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * schoolYearを取得する
     *
     * @return schoolYear schoolYear
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     * schoolYearを設定する
     *
     * @param schoolYear schoolYear
     */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * birthDayを取得する
     *
     * @return birthDay birthDay
     */
    public String getBirthDay() {
        return birthDay;
    }

    /**
     * birthDayを設定する
     *
     * @param birthDay birthDay
     */
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * genderを取得する
     *
     * @return gender gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * genderを設定する
     *
     * @param gender gender
     */
    public void setGender(String gender) {
        this.gender = gender;
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
     * usrIdを取得する
     *
     * @return usrId usrId
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     * usrIdを設定する
     *
     * @param usrId usrId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * ＱＲコードを取得する
     *
     * @return qrCod ＱＲコード
     */
    public String getQrCod() {
        return this.qrCod;
    }

    /**
     * ＱＲコードを設定する
     *
     * @param qrCod ＱＲコード
     */
    public void setQrCod(String qrCod) {
        this.qrCod = qrCod;
    }

    /**
     * オリジナルCDを取得する
     *
     * @return oriaCd オリジナルCD
     */
    public String getOriaCd() {
        return this.oriaCd;
    }

    /**
     * オリジナルCDを設定する
     *
     * @param oriaCd オリジナルCD
     */
    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }

    /**
     * 緊急電話番号を取得する
     *
     * @return urgTelNum 緊急電話番号
     */
    public String getUrgTelNum() {
        return this.urgTelNum;
    }

    /**
     * 緊急電話番号を設定する
     *
     * @param urgTelNum 緊急電話番号
     */
    public void setUrgTelNum(String urgTelNum) {
        this.urgTelNum = urgTelNum;
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
     * を取得する
     *
     * @return oldLoginId
     */
    public String getOldLoginId() {
        return this.oldLoginId;
    }

    /**
     * を設定する
     *
     * @param oldLoginId
     */
    public void setOldLoginId(String oldLoginId) {
        this.oldLoginId = oldLoginId;
    }

    /**
     * を取得する
     *
     * @return oldPwd
     */
    public String getOldPwd() {
        return this.oldPwd;
    }

    /**
     * を設定する
     *
     * @param oldPwd
     */
    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdDatime() {
        return updDatime;
    }

    public void setUpdDatime(String updDatime) {
        this.updDatime = updDatime;
    }

    public List<MstOrgEntity> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<MstOrgEntity> orgList) {
        this.orgList = orgList;
    }

}
