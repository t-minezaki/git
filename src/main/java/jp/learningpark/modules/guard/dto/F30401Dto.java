/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

/**
 * <p>塾からのイベント情報一覧画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/29 : hujiaxing: 新規<br />
 * @version 1.0
 */
public class F30401Dto {
    /**
     * イベントId
     */
    private Integer id;

    /**
     * イベントタイトル
     */
    private String eventTitle;
    /**
     * イベント画像パス
     */
    private String imgPath;

    /**
     * 閲覧状況区分
     */
    private String readStsDiv;

    /**
     * イベントタイトル
     */
    private String eventLevelDiv;
    /**
     * 公開期間日時
     */
    private String pubStartDt;
    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * eventTitleを取得する
     *
     * @return eventTitle eventTitle
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * eventTitleを設定する
     *
     * @param eventTitle eventTitle
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * noticeImgPathを取得する
     *
     * @return imgPath imgPath
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * noticeImgPathを設定する
     *
     * @param imgPath imgPath
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * readStsDivを取得する
     *
     * @return readStsDiv readStsDiv
     */
    public String getReadStsDiv() {
        return readStsDiv;
    }

    /**
     * readStsDivを設定する
     *
     * @param readStsDiv readStsDiv
     */
    public void setReadStsDiv(String readStsDiv) {
        this.readStsDiv = readStsDiv;
    }

    /**
     * eventLevelDivを取得する
     * @return eventLevelDiv eventLevelDiv
     */
    public String getEventLevelDiv() {
        return eventLevelDiv;
    }

    /**
     * eventLevelDivを設定する
     * @param eventLevelDiv eventLevelDiv
     */
    public void setEventLevelDiv(String eventLevelDiv) {
        this.eventLevelDiv = eventLevelDiv;
    }

    /**
     * pubStartDtを取得する
     * @return
     */
    public String getPubStartDt() {
        return pubStartDt;
    }

    /**
     * pubStartDtを設定する
     * @param pubStartDt
     */
    public void setPubStartDt(String pubStartDt) {
        this.pubStartDt = pubStartDt;
    }
}
