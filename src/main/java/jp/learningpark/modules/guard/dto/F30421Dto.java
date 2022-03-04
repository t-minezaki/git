/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import java.sql.Timestamp;

/**
 * <p>保護者知らせ画面(学研教室モード)</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/11 : wq: 新規<br />
 * @version 1.0
 */
public class F30421Dto {

    /**
     * お知らせ.ID
     */
    private Integer noticeId;

    /**
     * イベント.ID
     */
    private Integer eventId;

    /**
     * お知らせ/イベント.タイトル
     */
    private String title;

    /**
     * お知らせ．掲載予定開始日時
     */
    private Timestamp pubPlanStartDt;

    /**
     * イベント.公開開始日時
     */
    private Timestamp pubStartDt;

    /**
     *
     */
    private String startDt;

    /**
     * イベント.イベントCD
     */
    private String eventCd;

    /**
     * お知らせ/イベント．内容
     */
    private String contents;

    /**
     * お知らせ.タップ区分
     */
    private String noticeTypeDiv;

    /**
     * お知らせ/イベント.画像パス
     */
    private String titleImgPath;

    /**
     * お知らせ/イベント．レベル区分
     */
    private String levelDiv;

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分
     */
    private String noticeReadingStsDiv;

    /**
     * 保護者イベント申込状況．閲覧状況区分
     */
    private String eventReadingStsDiv;

    /**
     * 保護者イベント申込状況.再送信日時
     */
    private Timestamp againSendDt;

    /**
     * 対応status
     */
    private String corrspdSts;

    /**
     * お知らせ.IDを取得する
     *
     * @return noticeId お知らせ.ID
     */
    public Integer getNoticeId() {
        return this.noticeId;
    }

    /**
     * お知らせ.IDを設定する
     *
     * @param noticeId お知らせ.ID
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * イベント.IDを取得する
     *
     * @return eventId イベント.ID
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * イベント.IDを設定する
     *
     * @param eventId イベント.ID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * お知らせ．掲載予定開始日時を取得する
     *
     * @return pubPlanStartDt お知らせ．掲載予定開始日時
     */
    public Timestamp getPubPlanStartDt() {
        return this.pubPlanStartDt;
    }

    /**
     * お知らせ．掲載予定開始日時を設定する
     *
     * @param pubPlanStartDt お知らせ．掲載予定開始日時
     */
    public void setPubPlanStartDt(Timestamp pubPlanStartDt) {
        this.pubPlanStartDt = pubPlanStartDt;
    }

    /**
     * イベント.公開開始日時を取得する
     *
     * @return pubStartDt イベント.公開開始日時
     */
    public Timestamp getPubStartDt() {
        return this.pubStartDt;
    }

    /**
     * イベント.公開開始日時を設定する
     *
     * @param pubStartDt イベント.公開開始日時
     */
    public void setPubStartDt(Timestamp pubStartDt) {
        this.pubStartDt = pubStartDt;
    }

    /**
     * イベント.イベントCDを取得する
     *
     * @return eventCd イベント.イベントCD
     */
    public String getEventCd() {
        return this.eventCd;
    }

    /**
     * イベント.イベントCDを設定する
     *
     * @param eventCd イベント.イベントCD
     */
    public void setEventCd(String eventCd) {
        this.eventCd = eventCd;
    }

    /**
     * お知らせイベント．内容を取得する
     *
     * @return contents お知らせイベント．内容
     */
    public String getContents() {
        return this.contents;
    }

    /**
     * お知らせイベント．内容を設定する
     *
     * @param contents お知らせイベント．内容
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * お知らせイベント.画像パスを取得する
     *
     * @return titleImgPath お知らせイベント.画像パス
     */
    public String getTitleImgPath() {
        return this.titleImgPath;
    }

    /**
     * お知らせイベント.画像パスを設定する
     *
     * @param titleImgPath お知らせイベント.画像パス
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    /**
     * お知らせイベント．レベル区分を取得する
     *
     * @return levelDiv お知らせイベント．レベル区分
     */
    public String getLevelDiv() {
        return this.levelDiv;
    }

    /**
     * お知らせイベント．レベル区分を設定する
     *
     * @param levelDiv お知らせイベント．レベル区分
     */
    public void setLevelDiv(String levelDiv) {
        this.levelDiv = levelDiv;
    }

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分を取得する
     *
     * @return noticeReadingStsDiv 保護者お知らせ閲覧状況．閲覧状況区分
     */
    public String getNoticeReadingStsDiv() {
        return this.noticeReadingStsDiv;
    }

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分を設定する
     *
     * @param noticeReadingStsDiv 保護者お知らせ閲覧状況．閲覧状況区分
     */
    public void setNoticeReadingStsDiv(String noticeReadingStsDiv) {
        this.noticeReadingStsDiv = noticeReadingStsDiv;
    }

    /**
     * 保護者イベント申込状況．閲覧状況区分を取得する
     *
     * @return eventReadingStsDiv 保護者イベント申込状況．閲覧状況区分
     */
    public String getEventReadingStsDiv() {
        return this.eventReadingStsDiv;
    }

    /**
     * 保護者イベント申込状況．閲覧状況区分を設定する
     *
     * @param eventReadingStsDiv 保護者イベント申込状況．閲覧状況区分
     */
    public void setEventReadingStsDiv(String eventReadingStsDiv) {
        this.eventReadingStsDiv = eventReadingStsDiv;
    }

    /**
     * 保護者イベント申込状況.再送信日時を取得する
     *
     * @return againSendDt 保護者イベント申込状況.再送信日時
     */
    public Timestamp getAgainSendDt() {
        return this.againSendDt;
    }

    /**
     * 保護者イベント申込状況.再送信日時を設定する
     *
     * @param againSendDt 保護者イベント申込状況.再送信日時
     */
    public void setAgainSendDt(Timestamp againSendDt) {
        this.againSendDt = againSendDt;
    }

    /**
     * を取得する
     *
     * @return startDt
     */
    public String getStartDt() {
        return this.startDt;
    }

    /**
     * を設定する
     *
     * @param startDt
     */
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    /**
     * お知らせタイトルを取得する
     *
     * @return title お知らせタイトル
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * お知らせタイトルを設定する
     *
     * @param title お知らせタイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * お知らせ.タップ区分を取得する
     *
     * @return noticeTypeDiv お知らせ.タップ区分
     */
    public String getNoticeTypeDiv() {
        return this.noticeTypeDiv;
    }

    /**
     * お知らせ.タップ区分を設定する
     *
     * @param noticeTypeDiv お知らせ.タップ区分
     */
    public void setNoticeTypeDiv(String noticeTypeDiv) {
        this.noticeTypeDiv = noticeTypeDiv;
    }

    /**
     * 対応statusを取得する
     *
     * @return corrspdSts 対応status
     */
    public String getCorrspdSts() {
        return this.corrspdSts;
    }

    /**
     * 対応statusを設定する
     *
     * @param corrspdSts 対応status
     */
    public void setCorrspdSts(String corrspdSts) {
        this.corrspdSts = corrspdSts;
    }
}
