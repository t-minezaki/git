/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

/**
 * <p>F30424_保護者面談記録詳細画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/21 : wq: 新規<br />
 * @version 1.0
 */
public class F30424Dto {

    /**
     * メッセージ．タイトル
     */
    private String messageTitle;

    /**
     * メッセージ閲覧状況．ID
     */
    private Integer readingStsId;

    /**
     * メッセージ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;

    /**
     * 面談記録．ID
     */
    private Integer talkId;

    /**
     * メッセージ閲覧状況．質問事項表示フラグ
     */
    private String askDispFlg;

    /**
     * メッセージ閲覧状況．面談事項表示フラグ
     */
    private String talkDispFlg;

    /**
     * 備考
     */
    private String noteCont;

    /**
     * メッセージ．タイトルを取得する
     *
     * @return messageTitle メッセージ．タイトル
     */
    public String getMessageTitle() {
        return this.messageTitle;
    }

    /**
     * メッセージ．タイトルを設定する
     *
     * @param messageTitle メッセージ．タイトル
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    /**
     * メッセージ閲覧状況．閲覧状況区分を取得する
     *
     * @return readingStsDiv メッセージ閲覧状況．閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * メッセージ閲覧状況．閲覧状況区分を設定する
     *
     * @param readingStsDiv メッセージ閲覧状況．閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * メッセージ閲覧状況．IDを取得する
     *
     * @return readingStsId メッセージ閲覧状況．ID
     */
    public Integer getReadingStsId() {
        return this.readingStsId;
    }

    /**
     * メッセージ閲覧状況．IDを設定する
     *
     * @param readingStsId メッセージ閲覧状況．ID
     */
    public void setReadingStsId(Integer readingStsId) {
        this.readingStsId = readingStsId;
    }

    /**
     * 面談記録．IDを取得する
     *
     * @return talkId 面談記録．ID
     */
    public Integer getTalkId() {
        return this.talkId;
    }

    /**
     * 面談記録．IDを設定する
     *
     * @param talkId 面談記録．ID
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    /**
     * メッセージ閲覧状況．質問事項表示フラグを取得する
     *
     * @return askDispFlg メッセージ閲覧状況．質問事項表示フラグ
     */
    public String getAskDispFlg() {
        return this.askDispFlg;
    }

    /**
     * メッセージ閲覧状況．質問事項表示フラグを設定する
     *
     * @param askDispFlg メッセージ閲覧状況．質問事項表示フラグ
     */
    public void setAskDispFlg(String askDispFlg) {
        this.askDispFlg = askDispFlg;
    }

    /**
     * メッセージ閲覧状況．面談事項表示フラグを取得する
     *
     * @return talkDispFlg メッセージ閲覧状況．面談事項表示フラグ
     */
    public String getTalkDispFlg() {
        return this.talkDispFlg;
    }

    /**
     * メッセージ閲覧状況．面談事項表示フラグを設定する
     *
     * @param talkDispFlg メッセージ閲覧状況．面談事項表示フラグ
     */
    public void setTalkDispFlg(String talkDispFlg) {
        this.talkDispFlg = talkDispFlg;
    }

    /**
     * 備考を取得する
     *
     * @return noteCont 備考
     */
    public String getNoteCont() {
        return this.noteCont;
    }

    /**
     * 備考を設定する
     *
     * @param noteCont 備考
     */
    public void setNoteCont(String noteCont) {
        this.noteCont = noteCont;
    }
}
