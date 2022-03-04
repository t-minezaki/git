/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/13 : wq: 新規<br />
 * @version 1.0
 */
public class F08013GuardApplyDto {

    /**
     * イベント日程．ID
     */
    private Integer scheduleId;
    /**
     * 関連ID
     */
    private String refId;
    /**
     * 保護者イベント申込ID
     */
    private Integer applyId;
    /**
     * イベントID
     */
    private Integer id;

    /**
     * イベントID
     */
    private Integer eventId;
    /**
     * 日程(詳細) ID
     */
    private Integer detailId;
    /**
     * 閲覧回答区分
     */
    private String replyDiv;
    /**
     * 関連タイプ
     */
    private String refTypeDiv;
    /**
     * 取消フラグ
     */
    private String cancelFlg;
    /**
     * 日程予定日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sgdPlanDate;
    /**
     * 日程開始日時
     */
    private Timestamp sgdStartDatime;
    /**
     * 日程終了日時
     */
    private Timestamp sgdEndDatime;
    /**
     * 定員
     */
    private Integer personsLimt;
    /**
     * 予定済人数
     */
    private Integer planedMember;
    /**
     * ユーザー名
     */
    private String userName;
    /**
     * 表示名
     */
    private String displayNm;
    /**
     * 開始時間
     */
    private String startTime;
    /**
     * 終了時間
     */
    private String endTime;

    /**
     * 保護者フラグ
     */
    private Boolean userFlag;

    /**
     * 保護者イベント申込IDを取得する
     *
     * @return applyId 保護者イベント申込ID
     */
    public Integer getApplyId() {
        return this.applyId;
    }

    /**
     * 保護者イベント申込IDを設定する
     *
     * @param applyId 保護者イベント申込ID
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * イベントIDを取得する
     *
     * @return id イベントID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * イベントIDを設定する
     *
     * @param id イベントID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 日程(詳細) IDを取得する
     *
     * @return detailId 日程(詳細) ID
     */
    public Integer getDetailId() {
        return this.detailId;
    }

    /**
     * 日程(詳細) IDを設定する
     *
     * @param detailId 日程(詳細) ID
     */
    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    /**
     * 取消フラグを取得する
     *
     * @return cancelFlg 取消フラグ
     */
    public String getCancelFlg() {
        return this.cancelFlg;
    }

    /**
     * 取消フラグを設定する
     *
     * @param cancelFlg 取消フラグ
     */
    public void setCancelFlg(String cancelFlg) {
        this.cancelFlg = cancelFlg;
    }

    /**
     * 日程予定日を取得する
     *
     * @return sgdPlanDate 日程予定日
     */
    public Date getSgdPlanDate() {
        return this.sgdPlanDate;
    }

    /**
     * 日程予定日を設定する
     *
     * @param sgdPlanDate 日程予定日
     */
    public void setSgdPlanDate(Date sgdPlanDate) {
        this.sgdPlanDate = sgdPlanDate;
    }

    /**
     * 日程開始日時を取得する
     *
     * @return sgdStartDatime 日程開始日時
     */
    public Timestamp getSgdStartDatime() {
        return this.sgdStartDatime;
    }

    /**
     * 日程開始日時を設定する
     *
     * @param sgdStartDatime 日程開始日時
     */
    public void setSgdStartDatime(Timestamp sgdStartDatime) {
        this.sgdStartDatime = sgdStartDatime;
    }

    /**
     * 日程終了日時を取得する
     *
     * @return sgdEndDatime 日程終了日時
     */
    public Timestamp getSgdEndDatime() {
        return this.sgdEndDatime;
    }

    /**
     * 日程終了日時を設定する
     *
     * @param sgdEndDatime 日程終了日時
     */
    public void setSgdEndDatime(Timestamp sgdEndDatime) {
        this.sgdEndDatime = sgdEndDatime;
    }

    /**
     * ユーザー名を取得する
     *
     * @return userName ユーザー名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * ユーザー名を設定する
     *
     * @param userName ユーザー名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * イベント日程．IDを取得する
     *
     * @return scheduleId イベント日程．ID
     */
    public Integer getScheduleId() {
        return this.scheduleId;
    }

    /**
     * イベント日程．IDを設定する
     *
     * @param scheduleId イベント日程．ID
     */
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * 関連IDを取得する
     *
     * @return refId 関連ID
     */
    public String getRefId() {
        return this.refId;
    }

    /**
     * 関連IDを設定する
     *
     * @param refId 関連ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * 表示名を取得する
     *
     * @return displayNm 表示名
     */
    public String getDisplayNm() {
        return this.displayNm;
    }

    /**
     * 表示名を設定する
     *
     * @param displayNm 表示名
     */
    public void setDisplayNm(String displayNm) {
        this.displayNm = displayNm;
    }

    /**
     * 開始時間を取得する
     *
     * @return startTime 開始時間
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 開始時間を設定する
     *
     * @param startTime 開始時間
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 終了時間を取得する
     *
     * @return endTime 終了時間
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 終了時間を設定する
     *
     * @param endTime 終了時間
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 定員を取得する
     *
     * @return personsLimt 定員
     */
    public Integer getPersonsLimt() {
        return this.personsLimt;
    }

    /**
     * 定員を設定する
     *
     * @param personsLimt 定員
     */
    public void setPersonsLimt(Integer personsLimt) {
        this.personsLimt = personsLimt;
    }

    /**
     * 予定済人数を取得する
     *
     * @return planedMember 予定済人数
     */
    public Integer getPlanedMember() {
        return this.planedMember;
    }

    /**
     * 予定済人数を設定する
     *
     * @param planedMember 予定済人数
     */
    public void setPlanedMember(Integer planedMember) {
        this.planedMember = planedMember;
    }

    /**
     * 閲覧回答区分を取得する
     *
     * @return replyDiv 閲覧回答区分
     */
    public String getReplyDiv() {
        return this.replyDiv;
    }

    /**
     * 閲覧回答区分を設定する
     *
     * @param replyDiv 閲覧回答区分
     */
    public void setReplyDiv(String replyDiv) {
        this.replyDiv = replyDiv;
    }

    /**
     * 関連タイプを取得する
     *
     * @return refTypeDiv 関連タイプ
     */
    public String getRefTypeDiv() {
        return this.refTypeDiv;
    }

    /**
     * 関連タイプを設定する
     *
     * @param refTypeDiv 関連タイプ
     */
    public void setRefTypeDiv(String refTypeDiv) {
        this.refTypeDiv = refTypeDiv;
    }

    /**
     * 保護者フラグを取得する
     *
     * @return userFlag 保護者フラグ
     */
    public Boolean getUserFlag() {
        return this.userFlag;
    }

    /**
     * 保護者フラグを設定する
     *
     * @param userFlag 保護者フラグ
     */
    public void setUserFlag(Boolean userFlag) {
        this.userFlag = userFlag;
    }

    /**
     * イベントIDを取得する
     *
     * @return eventId イベントID
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * イベントIDを設定する
     *
     * @param eventId イベントID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
