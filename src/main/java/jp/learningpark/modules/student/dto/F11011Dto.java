/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

/**
 * <p>スマホ_メッセージ詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/29 : zpa: 新規<br />
 * @version 7.0
 */
public class F11011Dto {

    /**
     * 生徒メッセージ閲覧状況．ＩＤ
     */
    private Integer id;

    /**
     * メッセージ．メッセージタイトル
     */
    private String messageTitle;

    /**
     * メッセージ．メッセージ内容
     */
    private String messageCont;

    /**
     * メッセージ．タイトル画像パス
     */
    private String titleImgPath;

    /**
     * コードマスタ．コード値（メッセージレベル）
     */
    private String codCd;
    /**
     * 添付ファイルパス
     */
    private String attachFilePath;

    /**
     * 生徒メッセージ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;

    // 2020/11/12 zhangminghao modify start
    /**
     * 開封状況区分
     */
    private String openedFlg;
    /**
     * メッセージレベル区分
     */
    private String messageLevelDiv;

    public String getMessageLevelDiv() {
        return messageLevelDiv;
    }

    public void setMessageLevelDiv(String messageLevelDiv) {
        this.messageLevelDiv = messageLevelDiv;
    }

    // 2020/11/12 zhangminghao modify end
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageCont() {
        return messageCont;
    }

    public void setMessageCont(String messageCont) {
        this.messageCont = messageCont;
    }

    public String getTitleImgPath() {
        return titleImgPath;
    }

    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    public String getCodCd() {
        return codCd;
    }

    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
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
    // 2020/11/12 zhangminghao modify start
    public String getOpenedFlg() {
        return openedFlg;
    }

    public void setOpenedFlg(String openedFlg) {
        this.openedFlg = openedFlg;
    }
    // 2020/11/12 zhangminghao modify end
}