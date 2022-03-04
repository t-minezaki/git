package jp.learningpark.modules.job.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class BTGKA1002Dto implements Serializable {

    /**
     *  イベント．カテゴリ
     */
    private String ctgyNm;

    /**
     *  イベント．ID
     */
    private Integer id;

    /**
     *  保護者イベント申込状況．組織ID
     */
    private String orgId;

    /**
     *  保護者イベント申込状況．生徒ID
     */
    private String stuId;

    /**
     *  保護者イベント申込状況．保護者ID
     */
    private String guardId;

    /**
     *  保護者基本マスタ．姓名_姓
     */
    private String flnmNm;

    /**
     *  保護者基本マスタ．姓名_名
     */
    private String flnmLnm;

    /**
     *  保護者基本マスタ．メールアドレス
     */
    private String mailAddress;

    /**
     *  組織マスタ．ブランドコード
     */
    private String brandCd;

    /**
     *  組織マスタ．組織名
     */
    private String orgNm;

    /**
     *  イベント日程(詳細)．日程予定日
     */
    private Date sgdPlanDate;

    /**
     *  イベント日程(詳細)．日程開始日時
     */
    private Timestamp sgdStartDatime;
    /**
     * イベントタイトル
     */
    private String eventTitle;
    /**
     * タイトル画像パス
     */
    private String titleImgPath;

    /**
     * イベント．カテゴリ
     *
     * @return ctgyNm イベント．カテゴリ
     */
    public String getCtgyNm() {
        return ctgyNm;
    }

    /**
     * 画面．カテゴリ
     *
     * @param ctgyNm 画面．カテゴリ
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }

    /**
     * イベント．ID
     *
     * @return id イベント．ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 画面．ID
     *
     * @param id 画面．ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 保護者イベント申込状況．組織ID
     *
     * @return orgId 保護者イベント申込状況．組織ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 画面．組織ID
     *
     * @param orgId 画面．組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 保護者イベント申込状況．生徒ID
     *
     * @return stuId 保護者イベント申込状況．生徒ID
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 画面．生徒ID
     *
     * @param stuId 画面．生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 保護者イベント申込状況．保護者ID
     *
     * @return guardId 保護者イベント申込状況．保護者ID
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 画面．保護者ID
     *
     * @param guardId 画面．保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 保護者基本マスタ．姓名_姓
     *
     * @return flnmNm 保護者基本マスタ．姓名_姓
     */
    public String getFlnmNm() {
        return flnmNm;
    }

    /**
     * 画面．姓名_姓
     *
     * @param flnmNm 画面．姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 保護者基本マスタ．姓名_名
     *
     * @return flnmLnm 保護者基本マスタ．姓名_名
     */
    public String getFlnmLnm() {
        return flnmLnm;
    }

    /**
     * 画面．姓名_名
     *
     * @param flnmLnm 画面．姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 保護者基本マスタ．メールアドレス
     *
     * @return mailAddress 保護者基本マスタ．メールアドレス
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * 画面．メールアドレス
     *
     * @param mailAddress 画面．メールアドレス
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * 組織マスタ．ブランドコード
     *
     * @return brandCd 組織マスタ．ブランドコード
     */
    public String getBrandCd() {
        return brandCd;
    }

    /**
     * 画面．ブランドコード
     *
     * @param brandCd 画面．ブランドコード
     */
    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }

    /**
     * 組織マスタ．組織名
     *
     * @return orgNm 組織マスタ．組織名
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * 画面．組織名
     *
     * @param orgNm 画面．組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * イベント日程(詳細)．日程予定日
     *
     * @return sgdPlanDate イベント日程(詳細)．日程予定日
     */
    public Date getSgdPlanDate() {
        return sgdPlanDate;
    }

    /**
     * 画面．日程予定日
     *
     * @param sgdPlanDate 画面．日程予定日
     */
    public void setSgdPlanDate(Date sgdPlanDate) {
        this.sgdPlanDate = sgdPlanDate;
    }

    /**
     * イベント日程(詳細)．日程開始日時
     *
     * @return sgdStartDatime イベント日程(詳細)．日程開始日時
     */
    public Timestamp getSgdStartDatime() {
        return sgdStartDatime;
    }

    /**
     * 画面．日程開始日時
     *
     * @param sgdStartDatime 画面．日程開始日時
     */
    public void setSgdStartDatime(Timestamp sgdStartDatime) {
        this.sgdStartDatime = sgdStartDatime;
    }

    /**
     * イベントタイトルを取得する
     *
     * @return eventTitle イベントタイトル
     */
    public String getEventTitle() {
        return this.eventTitle;
    }

    /**
     * イベントタイトルを設定する
     *
     * @param eventTitle イベントタイトル
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * タイトル画像パスを取得する
     *
     * @return titleImgPath タイトル画像パス
     */
    public String getTitleImgPath() {
        return this.titleImgPath;
    }

    /**
     * タイトル画像パスを設定する
     *
     * @param titleImgPath タイトル画像パス
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }
}
