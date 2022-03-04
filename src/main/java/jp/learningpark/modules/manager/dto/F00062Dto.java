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
public class F00062Dto {
    //生徒
    /**
     * 生徒Id
     */
    private String stuId;

    /**
     * 生徒Id_隠し
     */
    private String stu;

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

    //メンター
    /**
     * メンターId
     */
    private String mentorId;

    /**
     * メンターId_隠し
     */
    private String mentor;

    /**
     * メンター・姓名＿姓
     */
    private String mentorSei;

    /**
     * メンター・姓名＿名
     */
    private String mentorMei;

    /**
     * メンター・カナ姓名＿姓
     */
    private String mentorKnSei;

    /**
     * メンター・カナ姓名＿名
     */
    private String mentorKnMei;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 更新時間
     */
    private Timestamp updDatime;

    /**
     * 更新時間Format
     */
    private String updDatm;

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
     * stuを取得する
     *
     * @return stu stu
     */
    public String getStu() {
        return stu;
    }

    /**
     * stuを設定する
     *
     * @param stu stu
     */
    public void setStu(String stu) {
        this.stu = stu;
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
     * mentorIdを取得する
     *
     * @return mentorId mentorId
     */
    public String getMentorId() {
        return mentorId;
    }

    /**
     * mentorIdを設定する
     *
     * @param mentorId mentorId
     */
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    /**
     * mentorを取得する
     *
     * @return mentor mentor
     */
    public String getMentor() {
        return mentor;
    }

    /**
     * mentorを設定する
     *
     * @param mentor mentor
     */
    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    /**
     * mentorSeiを取得する
     *
     * @return mentorSei mentorSei
     */
    public String getMentorSei() {
        return mentorSei;
    }

    /**
     * mentorSeiを設定する
     *
     * @param mentorSei mentorSei
     */
    public void setMentorSei(String mentorSei) {
        this.mentorSei = mentorSei;
    }

    /**
     * mentorMeiを取得する
     *
     * @return mentorMei mentorMei
     */
    public String getMentorMei() {
        return mentorMei;
    }

    /**
     * mentorMeiを設定する
     *
     * @param mentorMei mentorMei
     */
    public void setMentorMei(String mentorMei) {
        this.mentorMei = mentorMei;
    }

    /**
     * mentorKnSeiを取得する
     *
     * @return mentorKnSei mentorKnSei
     */
    public String getMentorKnSei() {
        return mentorKnSei;
    }

    /**
     * mentorKnSeiを設定する
     *
     * @param mentorKnSei mentorKnSei
     */
    public void setMentorKnSei(String mentorKnSei) {
        this.mentorKnSei = mentorKnSei;
    }

    /**
     * mentorKnMeiを取得する
     *
     * @return mentorKnMei mentorKnMei
     */
    public String getMentorKnMei() {
        return mentorKnMei;
    }

    /**
     * mentorKnMeiを設定する
     *
     * @param mentorKnMei mentorKnMei
     */
    public void setMentorKnMei(String mentorKnMei) {
        this.mentorKnMei = mentorKnMei;
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
}
