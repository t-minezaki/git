/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import java.util.Date;

/**
 * <p>チャンネル詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/20 : zpa: 新規<br />
 * @version 1.0
 */
public class F30420Dto {
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
     * お知らせ画像パス
     */
    private String titleImgPath;
    /**
     * お知らせレベル
     */
    private String codValue;
    /**
     * 保護者お知らせ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;

    /**
     * お知らせ.更新日時
     */
    private Date updTime;
    /**
     * 添付ファイルパス
     */
    private String attachFilePath;
/**2020/11/12 cuikailin modify start ***/
    /**
     * お知らせレベル区分
     */
    private String noticeLevelDiv;
/**2020/11/12 cuikailin modify end ***/
/**2020/12/4 cuikailin add start ***/
    /**
     * 開封状況区分
     */
    private String openedFlg;
/**2020/12/4 cuikailin add end ***/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeCont() {
        return noticeCont;
    }

    public void setNoticeCont(String noticeCont) {
        this.noticeCont = noticeCont;
    }

    public String getTitleImgPath() {
        return titleImgPath;
    }

    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * 添付ファイルパスを取得する
     *
     * @return attachFilePath 添付ファイルパス
     */
    public String getAttachFilePath() {
        return this.attachFilePath;
    }

    /**
     * 添付ファイルパスを設定する
     *
     * @param attachFilePath 添付ファイルパス
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }
/**2020/11/12 cuikailin modify start ***/
    /**
     * お知らせレベル区分を取得する
     *
     * @return noticeLevelDiv お知らせレベル区分
     */
    public String getNoticeLevelDiv() {
        return this.noticeLevelDiv;
    }

    /**
     * お知らせレベル区分を設定する
     *
     * @param noticeLevelDiv お知らせレベル区分
     */
    public void setNoticeLevelDiv(String noticeLevelDiv) {
        this.noticeLevelDiv = noticeLevelDiv;
    }
/**2020/11/12 cuikailin modify end ***/
/**2020/12/4 cuikailin add start ***/
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
/**2020/12/4 cuikailin add end ***/
}
