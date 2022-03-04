/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F00045Dto {
    //生徒
    /**
     * 生徒Id
     */
    private String stuId;

    /**
     * 生徒・姓名＿姓
     */
    private String stuSei;

    /**
     * 生徒・姓名＿名
     */
    private String stuMei;

    /**
     * 生徒・カナ姓名＿姓
     */
    private String stuKnSei;

    /**
     * 生徒・カナ姓名＿名
     */
    private String stuKnMei;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 生年月日Format
     */
    private String birth;

    /**
     * 性別
     */
    private String sex;

    /**
     * 学年
     */
    private String schy;

    /**
     * 更新時間
     */
    private Timestamp updDatime;

    /**
     * 更新時間Format
     */
    private String updDatm;

    //保護者
    /**
     * 保護者Id
     */
    private String guardId;
    /**
     * userId
     */
    private String userId;

    /**
     * 保護者・姓名＿姓
     */
    private String guardSei;

    /**
     * 保護者・姓名＿名
     */
    private String guardMei;

    /**
     * 保護者・カナ姓名＿姓
     */
    private String guardKnSei;

    /**
     * 保護者・カナ姓名＿名
     */
    private String guardKnMei;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;
    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 郵便番号_主番
     */
    private String postcdMnum;

    /**
     * 郵便番号_枝番
     */
    private String postcdBnum;

    /**
     * 住所１
     */
    private String adr1;

    /**
     * 住所２
     */
    private String adr2;

    /**
     * 続柄
     */
    private String tuzukiGara;
    /**
     * 住所
     */
    private String adr;

    /**
     * stuIdを取得する
     *
     * @return stuId stuId
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * stuIdを設定する
     *
     * @param stuId stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * stuSeiを取得する
     *
     * @return stuSei stuSei
     */
    public String getStuSei() {
        return stuSei;
    }

    /**
     * stuSeiを設定する
     *
     * @param stuSei stuSei
     */
    public void setStuSei(String stuSei) {
        this.stuSei = stuSei;
    }

    /**
     * stuMeiを取得する
     *
     * @return stuMei stuMei
     */
    public String getStuMei() {
        return stuMei;
    }

    /**
     * stuMeiを設定する
     *
     * @param stuMei stuMei
     */
    public void setStuMei(String stuMei) {
        this.stuMei = stuMei;
    }

    /**
     * stuKnSeiを取得する
     *
     * @return stuKnSei stuKnSei
     */
    public String getStuKnSei() {
        return stuKnSei;
    }

    /**
     * stuKnSeiを設定する
     *
     * @param stuKnSei stuKnSei
     */
    public void setStuKnSei(String stuKnSei) {
        this.stuKnSei = stuKnSei;
    }

    /**
     * stuKnMeiを取得する
     *
     * @return stuKnMei stuKnMei
     */
    public String getStuKnMei() {
        return stuKnMei;
    }

    /**
     * stuKnMeiを設定する
     *
     * @param stuKnMei stuKnMei
     */
    public void setStuKnMei(String stuKnMei) {
        this.stuKnMei = stuKnMei;
    }

    /**
     * birthdを取得する
     *
     * @return birthd birthd
     */
    public Date getBirthd() {
        return birthd;
    }

    /**
     * birthdを設定する
     *
     * @param birthd birthd
     */
    public void setBirthd(Date birthd) {
        this.birthd = birthd;
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
     * sexを取得する
     *
     * @return sex sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * sexを設定する
     *
     * @param sex sex
     */
    public void setSex(String sex) {
        this.sex = sex;
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
     * updDatimeを取得する
     *
     * @return updDatime updDatime
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }

    /**
     * updDatimeを設定する
     *
     * @param updDatime updDatime
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * updDatmを取得する
     *
     * @return updDatm updDatm
     */
    public String getUpdDatm() {
        return updDatm;
    }

    /**
     * updDatmを設定する
     *
     * @param updDatm updDatm
     */
    public void setUpdDatm(String updDatm) {
        this.updDatm = updDatm;
    }

    /**
     * guardIdを取得する
     *
     * @return guardId guardId
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * guardIdを設定する
     *
     * @param guardId guardId
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * guardSeiを取得する
     *
     * @return guardSei guardSei
     */
    public String getGuardSei() {
        return guardSei;
    }

    /**
     * guardSeiを設定する
     *
     * @param guardSei guardSei
     */
    public void setGuardSei(String guardSei) {
        this.guardSei = guardSei;
    }

    /**
     * guardMeiを取得する
     *
     * @return guardMei guardMei
     */
    public String getGuardMei() {
        return guardMei;
    }

    /**
     * guardMeiを設定する
     *
     * @param guardMei guardMei
     */
    public void setGuardMei(String guardMei) {
        this.guardMei = guardMei;
    }

    /**
     * guardKnSeiを取得する
     *
     * @return guardKnSei guardKnSei
     */
    public String getGuardKnSei() {
        return guardKnSei;
    }

    /**
     * guardKnSeiを設定する
     *
     * @param guardKnSei guardKnSei
     */
    public void setGuardKnSei(String guardKnSei) {
        this.guardKnSei = guardKnSei;
    }

    /**
     * guardKnMeiを取得する
     *
     * @return guardKnMei guardKnMei
     */
    public String getGuardKnMei() {
        return guardKnMei;
    }

    /**
     * guardKnMeiを設定する
     *
     * @param guardKnMei guardKnMei
     */
    public void setGuardKnMei(String guardKnMei) {
        this.guardKnMei = guardKnMei;
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
     * mailadを取得する
     *
     * @return mailad mailad
     */
    public String getMailad() {
        return mailad;
    }

    /**
     * mailadを設定する
     *
     * @param mailad mailad
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * postcdMnumを取得する
     *
     * @return postcdMnum postcdMnum
     */
    public String getPostcdMnum() {
        return postcdMnum;
    }

    /**
     * postcdMnumを設定する
     *
     * @param postcdMnum postcdMnum
     */
    public void setPostcdMnum(String postcdMnum) {
        this.postcdMnum = postcdMnum;
    }

    /**
     * postcdBnumを取得する
     *
     * @return postcdBnum postcdBnum
     */
    public String getPostcdBnum() {
        return postcdBnum;
    }

    /**
     * postcdBnumを設定する
     *
     * @param postcdBnum postcdBnum
     */
    public void setPostcdBnum(String postcdBnum) {
        this.postcdBnum = postcdBnum;
    }

    /**
     * adr1を取得する
     *
     * @return adr1 adr1
     */
    public String getAdr1() {
        return adr1;
    }

    /**
     * adr1を設定する
     *
     * @param adr1 adr1
     */
    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    /**
     * adr2を取得する
     *
     * @return adr2 adr2
     */
    public String getAdr2() {
        return adr2;
    }

    /**
     * adr2を設定する
     *
     * @param adr2 adr2
     */
    public void setAdr2(String adr2) {
        this.adr2 = adr2;
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
     * 住所を取得する
     *
     * @return adr 住所
     */
    public String getAdr() {
        return this.adr;
    }

    /**
     * 住所を設定する
     *
     * @param adr 住所
     */
    public void setAdr(String adr) {
        this.adr = adr;
    }

    /**
     * userIdを取得する
     *
     * @return userId userId
     */
    public String getUserId() {
        return this.userId;
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
     * urgTelnumを取得する
     * @return
     */
    public String getUrgTelnum() {
        return urgTelnum;
    }

    /**
     * urgTelnumを設定する
     * @param urgTelnum
     */
    public void setUrgTelnum(String urgTelnum) {
        this.urgTelnum = urgTelnum;
    }

}
