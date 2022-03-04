/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/07 : wq: 新規<br />
 * @version 1.0
 */
public class F08005ScheduleDto {

    /**
     * ID
     */
    private Integer id;
    /**
     * 関連ID
     */
    private String refId;
    /**
     * 日程予定日
     */
    private Date sgdPlanDate;
    /**
     * 変化日数
     */
    private Integer varDays;
    /**
     * 日程開始日時
     */
    private Timestamp sgdStartDatime;

    /**
     * 日程終了日時
     */
    private Timestamp sgdEndDatime;
    /**
     * 開始時間
     */
    private String startTime;
    /**
     * 終了時間
     */
    private String endTime;
    /**
     * イベント表示名
     */
    private String displayNm;

    /**
     * イベント表示名を取得する
     *
     * @return displayNm イベント表示名
     */
    public String getDisplayNm() {
        return this.displayNm;
    }

    /**
     * イベント表示名を設定する
     *
     * @param displayNm イベント表示名
     */
    public void setDisplayNm(String displayNm) {
        this.displayNm = displayNm;
    }

    /**
     * IDを取得する
     *
     * @return id ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * IDを設定する
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 変化日数を取得する
     *
     * @return varDays 変化日数
     */
    public Integer getVarDays() {
        return this.varDays;
    }

    /**
     * 変化日数を設定する
     *
     * @param varDays 変化日数
     */
    public void setVarDays(Integer varDays) {
        this.varDays = varDays;
    }
}
