/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

/**
 * <p>塾ニュース一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F30112Dto {

    /**
     * 遅刻欠席連絡履歴．対応ステータス
     */
    private String corrspdSts;
    /**
     * お知らせorgId
     */
    private String orgId;
    /**
     * お知らせId
     */
    private Integer id;

    /**
     * お知らせタイトル
     */
    private String noticeTitle;

    /**
     * お知らせ内容
     */
    private String noticeContent;

    /**
     * お知らせタップ区分
     */
    private String noticeTypeDiv;

    /**
     * お知らせ画像パス
     */
    private String noticeImgPath;

    /**
     * お知らせレベル区分
     */
    private String noticeLevDiv;

    /**
     * 閲覧状況区分
     */
    private String readStsDiv;
    /**
     * イベントタイトル
     */
    private String eventTitle;
    /**
     * イベントCD
     */
    private String eventCd;
    /**
     * イベント画像パス
     */
    private String titleImgPath;
    /**
     * レベルタップ区分
     */
    private String eventLevelDiv;
    /**
     * 公開開始日時
     */
    private String pubStartDt;
    /**
     * 閲覧状況区分
     */
    private String readingStsDiv;

    /**
     * 保護者お知らせ閲覧状況
     */
    private Integer grsId;
    /**
     * 保護者指導報告閲覧状況．閲覧状況区分
     */

    private Integer grasId;

    /**
     *
     */
    private Integer grdId;
    /**
     * イベント．ＩＤ
     */
    private Integer eventId;

    /**
     * 指導報告書配信コード
     */
    private String deliverCd;

    /**
     * 通知カテゴリ
     */
    private String type;
    /**
     * 開封状況区分
     */
    private String openedFlg;
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
     * noticeTitieを取得する
     *
     * @return noticeTitie noticeTitie
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }

    /**
     * noticeTitieを設定する
     *
     * @param noticeTitle noticeTitle
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * noticeContentを取得する
     *
     * @return noticeContent noticeContent
     */
    public String getNoticeContent() {
        return noticeContent;
    }

    /**
     * noticeContentを設定する
     *
     * @param noticeContent noticeContent
     */
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /**
     * noticeImgPathを取得する
     *
     * @return noticeImgPath noticeImgPath
     */
    public String getNoticeImgPath() {
        return noticeImgPath;
    }

    /**
     * noticeImgPathを設定する
     *
     * @param noticeImgPath noticeImgPath
     */
    public void setNoticeImgPath(String noticeImgPath) {
        this.noticeImgPath = noticeImgPath;
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
     * noticeLevDivを取得する
     *
     * @return noticeLevDiv noticeLevDiv
     */
    public String getNoticeLevDiv() {
        return noticeLevDiv;
    }

    /**
     * noticeLevDivを設定する
     *
     * @param noticeLevDiv noticeLevDiv
     */
    public void setNoticeLevDiv(String noticeLevDiv) {
        this.noticeLevDiv = noticeLevDiv;
    }

    /**
     * お知らせorgIdを取得する
     *
     * @return orgId お知らせorgId
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * お知らせorgIdを設定する
     *
     * @param orgId お知らせorgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
     * レベルタップ区分を取得する
     *
     * @return eventLevelDiv レベルタップ区分
     */
    public String getEventLevelDiv() {
        return this.eventLevelDiv;
    }

    /**
     * レベルタップ区分を設定する
     *
     * @param eventLevelDiv レベルタップ区分
     */
    public void setEventLevelDiv(String eventLevelDiv) {
        this.eventLevelDiv = eventLevelDiv;
    }

    /**
     * を取得する
     *
     * @return eventCd
     */
    public String getEventCd() {
        return this.eventCd;
    }

    /**
     * を設定する
     *
     * @param eventCd
     */
    public void setEventCd(String eventCd) {
        this.eventCd = eventCd;
    }

    /**
     * 閲覧状況区分を取得する
     *
     * @return readingStsDiv 閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 閲覧状況区分を設定する
     *
     * @param readingStsDiv 閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * 保護者お知らせ閲覧状況を取得する
     *
     * @return grsId 保護者お知らせ閲覧状況
     */
    public Integer getGrsId() {
        return this.grsId;
    }

    /**
     * 保護者お知らせ閲覧状況を設定する
     *
     * @param grsId 保護者お知らせ閲覧状況
     */
    public void setGrsId(Integer grsId) {
        this.grsId = grsId;
    }

    /**
     * 保護者指導報告閲覧状況．閲覧状況区分を取得する
     *
     * @return grasId 保護者指導報告閲覧状況．閲覧状況区分
     */
    public Integer getGrasId() {
        return this.grasId;
    }

    /**
     * 保護者指導報告閲覧状況．閲覧状況区分を設定する
     *
     * @param grasId 保護者指導報告閲覧状況．閲覧状況区分
     */
    public void setGrasId(Integer grasId) {
        this.grasId = grasId;
    }
    /**
     * を取得する
     *
     * @return grdId
     */
    public Integer getGrdId() {
        return this.grdId;
    }

    /**
     * を設定する
     *
     * @param grdId
     */
    public void setGrdId(Integer grdId) {
        this.grdId = grdId;
    }

    /**
     * イベント．ＩＤを取得する
     *
     * @return eventId イベント．ＩＤ
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * イベント．ＩＤを設定する
     *
     * @param eventId イベント．ＩＤ
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 公開開始日時を取得する
     *
     * @return pubStartDt 公開開始日時
     */
    public String getPubStartDt() {
        return this.pubStartDt;
    }

    /**
     * 公開開始日時を設定する
     *
     * @param pubStartDt 公開開始日時
     */
    public void setPubStartDt(String pubStartDt) {
        this.pubStartDt = pubStartDt;
    }
    /**
     * お知らせタップ区分を取得する
     *
     * @return noticeTypeDiv お知らせタップ区分
     */
    public String getNoticeTypeDiv() {
        return this.noticeTypeDiv;
    }

    /**
     * お知らせタップ区分を設定する
     *
     * @param noticeTypeDiv お知らせタップ区分
     */
    public void setNoticeTypeDiv(String noticeTypeDiv) {
        this.noticeTypeDiv = noticeTypeDiv;
    }

    /**
     * を取得する
     *
     * @return deliverCd
     */
    public String getDeliverCd() {
        return this.deliverCd;
    }

    /**
     * を設定する
     *
     * @param deliverCd
     */
    public void setDeliverCd(String deliverCd) {
        this.deliverCd = deliverCd;
    }

    /**
     * を取得する
     *
     * @return corrspdSts
     */
    public String getCorrspdSts() {
        return this.corrspdSts;
    }

    /**
     * を設定する
     *
     * @param corrspdSts
     */
    public void setCorrspdSts(String corrspdSts) {
        this.corrspdSts = corrspdSts;
    }

    /**
     * 通知カテゴリを取得する
     *
     * @return type 通知カテゴリ
     */
    public String getType() {
        return this.type;
    }

    /**
     * 通知カテゴリを設定する
     *
     * @param type 通知カテゴリ
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 開封状況区分を取得する
     *
     * @return openedFlg 開封状況区分
     */
    public String getOpenedFlg() {
        return this.openedFlg;
    }

    /**
     * 開封状況区分を設定する
     *
     * @param openedFlg 開封状況区分
     */
    public void setOpenedFlg(String openedFlg) {
        this.openedFlg = openedFlg;
    }
}
