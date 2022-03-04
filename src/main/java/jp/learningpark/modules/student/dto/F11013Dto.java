/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/05/12
 */

import java.util.Date;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/05/12 : lyh: 新規<br />
 * @version 1.0
 */
public class F11013Dto {
    /**
     * 生徒メッセージ閲覧状況．ＩＤ
     */
    private Integer id;
    /**
     * イベントＩＤ
     */
    private Integer eventId;

    /**
     * イベント．イベントタイトル
     */
    private String eventTitle;

    /**
     * イベント．イベント内容
     */
    private String eventCont;

    /**
     *イベント．添付ファイルパス
     */
    private String attachFilePath;
    /**
     * イベント画像パス
     */
    private String titleImgPath;


    /**
     * 生徒メッセージ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;
    /**
     *生徒メッセージ閲覧状況.閲覧回答区分
     */
    private String replyStsDiv;
    /**
     *  イベント．申込開始日時
     */
    private Date pubStartDt;
    /**
     *  イベント．申込終了日時
     */
    private Date pubEndDt;
    /**
     * 申込み可能開始日時
     */
    private Date applyStartDt;
    /**
     * 申込み可能終了日時
     */
    private Date applyEndDt;

    /**
     * 生徒メッセージ閲覧状況．ＩＤを取得する
     *
     * @return id 生徒メッセージ閲覧状況．ＩＤ
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 生徒メッセージ閲覧状況．ＩＤを設定する
     *
     * @param id 生徒メッセージ閲覧状況．ＩＤ
     */
    public void setId(Integer id) {
        this.id = id;
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
     * イベント．イベント内容を取得する
     *
     * @return eventCont イベント．イベント内容
     */
    public String getEventCont() {
        return this.eventCont;
    }

    /**
     * イベント．イベント内容を設定する
     *
     * @param eventCont イベント．イベント内容
     */
    public void setEventCont(String eventCont) {
        this.eventCont = eventCont;
    }

    /**
     * イベント．添付ファイルパスを取得する
     *
     * @return attachFilePath イベント．添付ファイルパス
     */
    public String getAttachFilePath() {
        return this.attachFilePath;
    }

    /**
     * イベント．添付ファイルパスを設定する
     *
     * @param attachFilePath イベント．添付ファイルパス
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }

    /**
     * イベント画像パスを取得する
     *
     * @return titleImgPath イベント画像パス
     */
    public String getTitleImgPath() {
        return this.titleImgPath;
    }

    /**
     * イベント画像パスを設定する
     *
     * @param titleImgPath イベント画像パス
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    /**
     * 生徒メッセージ閲覧状況．閲覧状況区分を取得する
     *
     * @return readingStsDiv 生徒メッセージ閲覧状況．閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 生徒メッセージ閲覧状況．閲覧状況区分を設定する
     *
     * @param readingStsDiv 生徒メッセージ閲覧状況．閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * イベント．申込開始日時を取得する
     *
     * @return pubStartDt イベント．申込開始日時
     */
    public Date getPubStartDt() {
        return this.pubStartDt;
    }

    /**
     * イベント．申込開始日時を設定する
     *
     * @param pubStartDt イベント．申込開始日時
     */
    public void setPubStartDt(Date pubStartDt) {
        this.pubStartDt = pubStartDt;
    }

    /**
     * イベント．申込終了日時を取得する
     *
     * @return pubEndDt イベント．申込終了日時
     */
    public Date getPubEndDt() {
        return this.pubEndDt;
    }

    /**
     * イベント．申込終了日時を設定する
     *
     * @param pubEndDt イベント．申込終了日時
     */
    public void setPubEndDt(Date pubEndDt) {
        this.pubEndDt = pubEndDt;
    }

    /**
     * イベントＩＤを取得する
     *
     * @return eventId イベントＩＤ
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * イベントＩＤを設定する
     *
     * @param eventId イベントＩＤ
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 生徒メッセージ閲覧状況.閲覧回答区分を取得する
     *
     * @return replyStsDiv 生徒メッセージ閲覧状況.閲覧回答区分
     */
    public String getReplyStsDiv() {
        return this.replyStsDiv;
    }

    /**
     * 生徒メッセージ閲覧状況.閲覧回答区分を設定する
     *
     * @param replyStsDiv 生徒メッセージ閲覧状況.閲覧回答区分
     */
    public void setReplyStsDiv(String replyStsDiv) {
        this.replyStsDiv = replyStsDiv;
    }

    /**
     * 申込み可能開始日時を取得する
     *
     * @return applyStartDt 申込み可能開始日時
     */
    public Date getApplyStartDt() {
        return this.applyStartDt;
    }

    /**
     * 申込み可能開始日時を設定する
     *
     * @param applyStartDt 申込み可能開始日時
     */
    public void setApplyStartDt(Date applyStartDt) {
        this.applyStartDt = applyStartDt;
    }

    /**
     * 申込み可能終了日時を取得する
     *
     * @return applyEndDt 申込み可能終了日時
     */
    public Date getApplyEndDt() {
        return this.applyEndDt;
    }

    /**
     * 申込み可能終了日時を設定する
     *
     * @param applyEndDt 申込み可能終了日時
     */
    public void setApplyEndDt(Date applyEndDt) {
        this.applyEndDt = applyEndDt;
    }

    /**
     * イベント．申込終了日時を取得する
     *
     * @return pubEndtDt イベント．申込終了日時
     */

}