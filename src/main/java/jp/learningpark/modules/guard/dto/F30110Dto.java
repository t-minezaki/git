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
public class F30110Dto {
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
     * お知らせ画像パス
     */
    private String noticeImgPath;

    /**
     * お知らせレベル
     */
    private String noticeLev;

    /**
     * お知らせレベル区分
     */
    private String noticeLevDiv;

    /**
     * お知らせ．掲載予定開始日時
     */
    private String pubPlanStartDt;

    /**
     * 閲覧状況区分
     */
    private String readStsDiv;

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
     * noticeLevを取得する
     *
     * @return noticeLev noticeLev
     */
    public String getNoticeLev() {
        return noticeLev;
    }

    /**
     * noticeLevを設定する
     *
     * @param noticeLev noticeLev
     */
    public void setNoticeLev(String noticeLev) {
        this.noticeLev = noticeLev;
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
     * お知らせ．掲載予定開始日時を取得する
     *
     * @return pubPlanStartDt お知らせ．掲載予定開始日時
     */
    public String getPubPlanStartDt() {
        return this.pubPlanStartDt;
    }

    /**
     * お知らせ．掲載予定開始日時を設定する
     *
     * @param pubPlanStartDt お知らせ．掲載予定開始日時
     */
    public void setPubPlanStartDt(String pubPlanStartDt) {
        this.pubPlanStartDt = pubPlanStartDt;
    }
}
