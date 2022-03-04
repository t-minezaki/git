package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>F08015_配信設定/状況確認の代理登録画面(POP) Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/19 : yang: 新規<br />
 * @version 1.0
 */

public class F08015Dto {
    /**
     * イベントID
     */
    public Integer eventId;
    /**
     *予約日
     */
    public Date planDate;
    /**
     * 開始時間
     */
    public Timestamp startTime;
    /**
     * 終了時間
     */
    public Timestamp endTime;
    /**
     * イベント．ID
     */
    public Integer Id;
    /**
     *イベント．関連タイプ
     */
    public String refType;
    /**
     * 関連ID
     */
    public String refId;
    /**
     * 先生名
     */
    public String displayNm;
    /**
     * 生徒名
     */
    public String stuName;
    /**
     * イベント．イベントタイトル
     */
    public String eventTitle;
    /**
     * 生徒ID
     */
    public String stuId;
    /**
     * 取消フラグ
     */
    public String cancelFlg;
    /**
     * 保護者ID
     */
    public String guardId;

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

    /**
     * 予約日を取得する
     *
     * @return planDate 予約日
     */
    public Date getPlanDate() {
        return this.planDate;
    }

    /**
     * 予約日を設定する
     *
     * @param planDate 予約日
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    /**
     * 開始時間を取得する
     *
     * @return startTime 開始時間
     */
    public Timestamp getStartTime() {
        return this.startTime;
    }

    /**
     * 開始時間を設定する
     *
     * @param startTime 開始時間
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * 終了時間を取得する
     *
     * @return endTime 終了時間
     */
    public Timestamp getEndTime() {
        return this.endTime;
    }

    /**
     * 終了時間を設定する
     *
     * @param endTime 終了時間
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * イベント．IDを取得する
     *
     * @return Id イベント．ID
     */
    public Integer getId() {
        return this.Id;
    }

    /**
     * イベント．IDを設定する
     *
     * @param Id イベント．ID
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * イベント．関連タイプを取得する
     *
     * @return refType イベント．関連タイプ
     */
    public String getRefType() {
        return this.refType;
    }

    /**
     * イベント．関連タイプを設定する
     *
     * @param refType イベント．関連タイプ
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }

    /**
     * 生徒名を取得する
     *
     * @return stuName 生徒名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuName 生徒名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * イベント．イベントタイトルを取得する
     *
     * @return eventTitle イベント．イベントタイトル
     */
    public String getEventTitle() {
        return this.eventTitle;
    }

    /**
     * イベント．イベントタイトルを設定する
     *
     * @param eventTitle イベント．イベントタイトル
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
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
     * 先生名を取得する
     *
     * @return displayNm 先生名
     */
    public String getDisplayNm() {
        return this.displayNm;
    }

    /**
     * 先生名を設定する
     *
     * @param displayNm 先生名
     */
    public void setDisplayNm(String displayNm) {
        this.displayNm = displayNm;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
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
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }
}
