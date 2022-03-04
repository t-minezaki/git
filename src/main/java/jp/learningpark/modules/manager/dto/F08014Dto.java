package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class F08014Dto implements Serializable {

    /**
     * イベント日程(詳細).日程予定日
     */
    private Date sgdPlanDate;

    /**
     * イベント日程(詳細).日程予定日もじれつ
     */
    private String sgdPlanDateStr;

    /**
     * イベント日程(詳細).日程開始日時
     */
    private Timestamp sgdStartDatime;

    /**
     * イベント日程(詳細).日程開始日時もじれつ
     */
    private String sgdStartDatimeStr;

    /**
     * イベント日程(詳細).日程終了日時
     */
    private Timestamp sgdEndDatime;

    /**
     * イベント日程(詳細).日程終了日時もじれつ
     */
    private String sgdEndDatimeStr;

    /**
     * イベント.イベントタイトル
     */
    private String eventTitle;

    /**
     * 先生の名前を分ける
     */
    private String divMentorNm;

    /**
     * 先生のお名前
     */
    private String mentorNm;

    /**
     * イベント.関連タイプ
     */
    private String refType;

    /**
     * イベント.カテゴリ
     */
    private String ctgyNm;

    /**
     * 時間文字列
     */
    private String timeStr;

    /**
     * 保護者名
     */
    private String guardNm;

    /**
     * 保護者基本マスタ.メールアドレス
     */
    private String mailad;

    /**
     * イベント.ID
     */
    private Integer eventId;

    /**
     * 生徒基本マスタ.生徒ID
     */
    private String stuId;

    /**
     * 保護者基本マスタ.保護者ID
     */
    private String guardId;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * イベント日程(詳細).組織名
     */
    private String orgNm;

    /**
     * 画面．日程予定日
     *
     * @return sgdPlanDate 画面．日程予定日
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
     * 画面．日程開始日時
     *
     * @return sgdStartDatime 画面．日程開始日時
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
     * 画面．日程終了日時
     *
     * @return sgdEndDatime 画面．日程終了日時
     */
    public Timestamp getSgdEndDatime() {
        return sgdEndDatime;
    }

    /**
     * 画面．日程終了日時
     *
     * @param sgdEndDatime 画面．日程終了日時
     */
    public void setSgdEndDatime(Timestamp sgdEndDatime) {
        this.sgdEndDatime = sgdEndDatime;
    }

    /**
     * 画面．イベントタイトル
     *
     * @return eventTitle 画面．イベントタイトル
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * 画面．イベントタイトル
     *
     * @param eventTitle 画面．イベントタイトル
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * 画面．先生の名前を分ける
     *
     * @return divMentorNm 画面．先生の名前を分ける
     */
    public String getDivMentorNm() {
        return divMentorNm;
    }

    /**
     * 画面．先生の名前を分ける
     *
     * @param divMentorNm 画面．先生の名前を分ける
     */
    public void setDivMentorNm(String divMentorNm) {
        this.divMentorNm = divMentorNm;
    }

    /**
     * 画面．先生のお名前
     *
     * @return mentorNm 画面．先生のお名前
     */
    public String getMentorNm() {
        return mentorNm;
    }

    /**
     * 画面．先生のお名前
     *
     * @param mentorNm 画面．先生のお名前
     */
    public void setMentorNm(String mentorNm) {
        this.mentorNm = mentorNm;
    }

    /**
     * 画面．関連タイプ
     *
     * @return refType 画面．関連タイプ
     */
    public String getRefType() {
        return refType;
    }

    /**
     * 画面．関連タイプ
     *
     * @param refType 画面．関連タイプ
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }

    /**
     * 画面．カテゴリ
     *
     * @return ctgyNm 画面．カテゴリ
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
     * 画面．時間文字列
     *
     * @return timeStr 画面．時間文字列
     */
    public String getTimeStr() {
        return timeStr;
    }

    /**
     * 画面．時間文字列
     *
     * @param timeStr 画面．時間文字列
     */
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    /**
     * 画面．日程予定日もじれつ
     *
     * @return sgdPlanDateStr 画面．日程予定日もじれつ
     */
    public String getSgdPlanDateStr() {
        return sgdPlanDateStr;
    }

    /**
     * 画面．日程予定日もじれつ
     *
     * @param sgdPlanDateStr 画面．日程予定日もじれつ
     */
    public void setSgdPlanDateStr(String sgdPlanDateStr) {
        this.sgdPlanDateStr = sgdPlanDateStr;
    }

    /**
     * 画面．日程開始日時もじれつ
     *
     * @return sgdStartDatimeStr 画面．日程開始日時もじれつ
     */
    public String getSgdStartDatimeStr() {
        return sgdStartDatimeStr;
    }

    /**
     * 画面．日程開始日時もじれつ
     *
     * @param sgdStartDatimeStr 画面．日程開始日時もじれつ
     */
    public void setSgdStartDatimeStr(String sgdStartDatimeStr) {
        this.sgdStartDatimeStr = sgdStartDatimeStr;
    }

    /**
     * 画面．日程終了日時もじれつ
     *
     * @return sgdEndDatimeStr 画面．日程終了日時もじれつ
     */
    public String getSgdEndDatimeStr() {
        return sgdEndDatimeStr;
    }

    /**
     * 画面．日程終了日時もじれつ
     *
     * @param sgdEndDatimeStr 画面．日程終了日時もじれつ
     */
    public void setSgdEndDatimeStr(String sgdEndDatimeStr) {
        this.sgdEndDatimeStr = sgdEndDatimeStr;
    }

    /**
     * 画面．保護者名
     *
     * @return guardNm 画面．保護者名
     */
    public String getGuardNm() {
        return guardNm;
    }

    /**
     * 画面．保護者名
     *
     * @param guardNm 画面．保護者名
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 画面．メールアドレス
     *
     * @return mailad 画面．メールアドレス
     */
    public String getMailad() {
        return mailad;
    }

    /**
     * 画面．メールアドレス
     *
     * @param mailad 画面．メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 画面．イベントID
     *
     * @return eventId 画面．イベントID
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * 画面．イベントID
     *
     * @param eventId 画面．イベントID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 画面．生徒ID
     *
     * @return stuId 画面．生徒ID
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
     * 画面．保護者ID
     *
     * @return guardId 画面．保護者ID
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
     * 画面．組織名
     *
     * @return orgId 画面．組織名
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
