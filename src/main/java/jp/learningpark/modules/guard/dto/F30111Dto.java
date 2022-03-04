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
public class F30111Dto {

    /**
     * 保護者お知らせ閲覧状況．ＩＤ
     */
    Integer id;

    /**
     * お知らせ．お知らせタイトル
     */
    String noticeTitle;

    /**
     * お知らせ．お知らせ内容
     */
    String noticeCont;

    /**
     * お知らせ．添付ファイルパス
     */
    String attachFilePath;

    /**
     * お知らせ．画像パス
     */
    String titleImgPath;

    /**
     * コードマスタ．コード値
     */
    String codValue;

    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分
     */
    String readingStsDiv;

    /**
     * お知らせ．更新日時
     */
    Timestamp updDatime;

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
}
