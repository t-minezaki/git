/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F09019_一斉お知らせ配信（一覧）（スマホ） Dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/19 : wq: 新規<br />
 * @version 1.0
 */
public class F09019Dto {

    /**
     * お知らせID
     */
    private Integer id;

    /**
     * お知らせタイトル
     */
    private String noticeTitle;

    /**
     * お知らせ内容
     */
    private String noticeCont;

    /**
     * お知らせタップ区分
     */
    private String noticeTypeDiv;

    /**
     * 掲載予定開始日
     */
    private String startTimeStr;

    /**
     * 掲載予定終了日
     */
    private String endTimeStr;

    /**
     * お知らせタイトルを取得する
     *
     * @return noticeTitle お知らせタイトル
     */
    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    /**
     * お知らせタイトルを設定する
     *
     * @param noticeTitle お知らせタイトル
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
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
     * 掲載予定開始日を取得する
     *
     * @return startTimeStr 掲載予定開始日
     */
    public String getStartTimeStr() {
        return this.startTimeStr;
    }

    /**
     * 掲載予定開始日を設定する
     *
     * @param startTimeStr 掲載予定開始日
     */
    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    /**
     * 掲載予定終了日を取得する
     *
     * @return endTimeStr 掲載予定終了日
     */
    public String getEndTimeStr() {
        return this.endTimeStr;
    }

    /**
     * 掲載予定終了日を設定する
     *
     * @param endTimeStr 掲載予定終了日
     */
    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    /**
     * お知らせIDを取得する
     *
     * @return id お知らせID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * お知らせIDを設定する
     *
     * @param id お知らせID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * お知らせ内容を取得する
     *
     * @return noticeCont お知らせ内容
     */
    public String getNoticeCont() {
        return this.noticeCont;
    }

    /**
     * お知らせ内容を設定する
     *
     * @param noticeCont お知らせ内容
     */
    public void setNoticeCont(String noticeCont) {
        this.noticeCont = noticeCont;
    }
}
