package jp.learningpark.modules.guard.dto;

/**
 * <p>
 * F30419Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/18 ： NWT)hxl ： 新規作成
 * @date 2020/02/18 15:58
 */
public class F30419Dto {
    /**
     * チャンネルorgId
     */
    private String orgId;
    /**
     * チャンネルId
     */
    private Integer id;

    /**
     * チャンネルタイトル
     */
    private String noticeTitle;

    /**
     * チャンネル内容
     */
    private String noticeContent;

    /**
     * チャンネル画像パス
     */
    private String noticeImgPath;

    /**
     * チャンネルレベル区分
     */
    private String noticeLevDiv;

    /**
     * 閲覧状況区分
     */
    private String readStsDiv;

    /**
     * 公開開始日時
     */
    private String startTime;

    /**
     * チャンネルorgIdを取得する
     *
     * @return orgId チャンネルorgId
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * チャンネルorgIdを設定する
     *
     * @param orgId チャンネルorgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * チャンネルIdを取得する
     *
     * @return id チャンネルId
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * チャンネルIdを設定する
     *
     * @param id チャンネルId
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * チャンネルタイトルを取得する
     *
     * @return noticeTitle チャンネルタイトル
     */
    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    /**
     * チャンネルタイトルを設定する
     *
     * @param noticeTitle チャンネルタイトル
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * チャンネル内容を取得する
     *
     * @return noticeContent チャンネル内容
     */
    public String getNoticeContent() {
        return this.noticeContent;
    }

    /**
     * チャンネル内容を設定する
     *
     * @param noticeContent チャンネル内容
     */
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /**
     * チャンネル画像パスを取得する
     *
     * @return noticeImgPath チャンネル画像パス
     */
    public String getNoticeImgPath() {
        return this.noticeImgPath;
    }

    /**
     * チャンネル画像パスを設定する
     *
     * @param noticeImgPath チャンネル画像パス
     */
    public void setNoticeImgPath(String noticeImgPath) {
        this.noticeImgPath = noticeImgPath;
    }

    /**
     * チャンネルレベル区分を取得する
     *
     * @return noticeLevDiv チャンネルレベル区分
     */
    public String getNoticeLevDiv() {
        return this.noticeLevDiv;
    }

    /**
     * チャンネルレベル区分を設定する
     *
     * @param noticeLevDiv チャンネルレベル区分
     */
    public void setNoticeLevDiv(String noticeLevDiv) {
        this.noticeLevDiv = noticeLevDiv;
    }

    /**
     * 閲覧状況区分を取得する
     *
     * @return readStsDiv 閲覧状況区分
     */
    public String getReadStsDiv() {
        return this.readStsDiv;
    }

    /**
     * 閲覧状況区分を設定する
     *
     * @param readStsDiv 閲覧状況区分
     */
    public void setReadStsDiv(String readStsDiv) {
        this.readStsDiv = readStsDiv;
    }

    /**
     * 公開開始日時を取得する
     *
     * @return startTime 公開開始日時
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 公開開始日時を設定する
     *
     * @param startTime 公開開始日時
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
