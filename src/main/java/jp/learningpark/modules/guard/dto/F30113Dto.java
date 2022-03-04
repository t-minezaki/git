package jp.learningpark.modules.guard.dto;

import java.sql.Timestamp;

/**
 * <p>塾ニュース詳細表示画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
public class F30113Dto {

    /**
     * 保護者お知らせ閲覧状況．ＩＤ
     */
    private Integer id;

    /**
     * お知らせ．お知らせタイトル
     */
    private String noticeTitle;

    /**
     * お知らせ．お知らせ内容
     */
    private String noticeCont;

    /**
     * お知らせ．添付ファイルパス
     */
    String attachFilePath;

    /**
     * お知らせ画像パス
     */
    private String titleImgPath;

    /**
     * コードマスタ（C1）．コード値（お知らせレベル）
     */
    private String codValue;

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;

    // 2020/11/25 zhangminghao modify start
    /**
     * お知らせレベル区分
     */
    private String noticeLevelDiv;

    /**
     * 開封状況区分
     */
    private String openedFlg;
    // 2020/11/25 zhangminghao modify end

    /**
     * 知らせ．更新日時
     */
    private Timestamp updDatime;
    //add at 2021/08/16 for V9.02 by NWT LiGX START
    /**
     * 知らせ．お知らせタップ区分
     */
    private String noticeTypeDiv;

    /**
     * 遅刻欠席連絡履歴．対象年月日
     */
    private Timestamp tgtYmd;

    /**
     * 遅刻欠席連絡履歴．遅欠連絡ステータス
     */
    private String absSts;

    /**
     * コードマスタ_明細・コード値（理由）
     */
    private String reason;

    /**
     * 遅刻欠席連絡履歴．備考
     */
    private String bikou;
    //add at 2021/08/16 for V9.02 by NWT LiGX END
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
     * noticeTitleを取得する
     *
     * @return noticeTitle noticeTitle
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }

    /**
     * noticeTitleを設定する
     *
     * @param noticeTitle noticeTitle
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * noticeContを取得する
     *
     * @return noticeCont noticeCont
     */
    public String getNoticeCont() {
        return noticeCont;
    }

    /**
     * noticeContを設定する
     *
     * @param noticeCont noticeCont
     */
    public void setNoticeCont(String noticeCont) {
        this.noticeCont = noticeCont;
    }

    /**
     * titleImgPathを取得する
     *
     * @return titleImgPath titleImgPath
     */
    public String getTitleImgPath() {
        return titleImgPath;
    }

    /**
     * titleImgPathを設定する
     *
     * @param titleImgPath titleImgPath
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    /**
     * codValueを取得する
     *
     * @return codValue codValue
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * codValueを設定する
     *
     * @param codValue codValue
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * readingStsDivを取得する
     *
     * @return readingStsDiv readingStsDiv
     */
    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    /**
     * readingStsDivを設定する
     *
     * @param readingStsDiv readingStsDiv
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * updDatimeを取得する
     *
     * @return updDatime updDatime
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }

    /**
     * updDatimeを設定する
     *
     * @param updDatime updDatime
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    // 2020/11/25 zhangminghao modify start
    public String getNoticeLevelDiv() {
        return noticeLevelDiv;
    }

    public void setNoticeLevelDiv(String noticeLevelDiv) {
        this.noticeLevelDiv = noticeLevelDiv;
    }

    public String getOpenedFlg() {
        return openedFlg;
    }

    public void setOpenedFlg(String openedFlg) {
        this.openedFlg = openedFlg;
    }

    // 2020/11/25 zhangminghao modify end

    /**
     * お知らせ．添付ファイルパスを取得する
     *
     * @return attachFilePath お知らせ．添付ファイルパス
     */
    public String getAttachFilePath() {
        return this.attachFilePath;
    }

    /**
     * お知らせ．添付ファイルパスを設定する
     *
     * @param attachFilePath お知らせ．添付ファイルパス
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }
    //add at 2021/08/16 for V9.02 by NWT LiGX START
    /**
     * 知らせ．お知らせタップ区分を取得する
     *
     * @return noticeTypeDiv 知らせ．お知らせタップ区分
     */
    public String getNoticeTypeDiv() {
        return this.noticeTypeDiv;
    }

    /**
     * お知らせ．お知らせタップ区分を設定する
     *
     * @param noticeTypeDiv 知らせ．お知らせタップ区分
     */
    public void setNoticeTypeDiv(String noticeTypeDiv) {
        this.noticeTypeDiv = noticeTypeDiv;
    }

    /**
     * 遅刻欠席連絡履歴．対象年月日を取得する
     *
     * @return tgtYmd 遅刻欠席連絡履歴．対象年月日
     */
    public Timestamp getTgtYmd() {
        return tgtYmd;
    }

    /**
     * 遅刻欠席連絡履歴．対象年月日を設定する
     *
     * @param tgtYmd 遅刻欠席連絡履歴．対象年月日
     */
    public void setTgtYmd(Timestamp tgtYmd) {
        this.tgtYmd = tgtYmd;
    }

    /**
     *コードマスタ_明細・コード値（理由）を取得する
     *
     * @return reason コードマスタ_明細・コード値（理由）
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * コードマスタ_明細・コード値（理由）を設定する
     *
     * @param reason コードマスタ_明細・コード値（理由）
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 遅刻欠席連絡履歴．遅欠連絡ステータスを取得する
     *
     * @return absSts 遅刻欠席連絡履歴．遅欠連絡ステータス
     */
    public String getAbsSts() { return this.absSts;}

    /**
     * 遅刻欠席連絡履歴．遅欠連絡ステータスを設定する
     *
     * @param absSts 遅刻欠席連絡履歴．遅欠連絡ステータス
     */
    public void setAbsSts(String absSts) {
        this.absSts = absSts;
    }

    /**
     * 遅刻欠席連絡履歴．備考を取得する
     *
     * @return bikou 遅刻欠席連絡履歴．備考
     */
    public String getBikou() { return this.bikou;}

    /**
     * 遅刻欠席連絡履歴．備考を設定する
     *
     * @param bikou 遅刻欠席連絡履歴．備考
     */
    public void setBikou(String bikou) {
        this.bikou = bikou;
    }
    //add at 2021/08/16 for V9.02 by NWT LiGX END
}
