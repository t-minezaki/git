package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.TalkRecordDEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/14 ： NWT)hxl ： 新規作成
 */
public class F11020Dto extends TalkRecordDEntity {
    /**
     * メッセージ．メッセージタイトル
     */
    private String messageTitle;
    /**
     * 面談記録詳細．事項類型区分
     */
    private String itemTypeDiv;
    /**
     * 面談記録詳細．質問番号
     */
    private Integer askNumber;
    /**
     * 面談記録詳細．回答結果内容
     */
    private String answerReltCont;
    /**
     * メッセージ閲覧状況．質問事項表示フラグ
     * ※　0の場合：質問と回答非表示する
     * ※　1の場合：質問と回答表示する
     */
    private String askDispFlg;
    /**
     * メッセージ閲覧状況．面談事項表示フラグ
     * ※　0の場合：面談事項と回答非表示する
     * ※　1の場合：面談事項と回答表示する
     */
    private String talkDispFlg;
    /**
     * メッセージ閲覧状況．閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * コードマスタ．コード値
     */
    private String codValue;
    /**
     * 設問名
     */
    private String questionName;

    /**
     * 備考
     */
    private String noteCont;

    /**
     * メッセージ．メッセージタイトルを取得する
     *
     * @return messageTitle メッセージ．メッセージタイトル
     */
    public String getMessageTitle() {
        return this.messageTitle;
    }

    /**
     * メッセージ．メッセージタイトルを設定する
     *
     * @param messageTitle メッセージ．メッセージタイトル
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    /**
     * 面談記録詳細．事項類型区分を取得する
     *
     * @return itemTypeDiv 面談記録詳細．事項類型区分
     */
    public String getItemTypeDiv() {
        return this.itemTypeDiv;
    }

    /**
     * 面談記録詳細．事項類型区分を設定する
     *
     * @param itemTypeDiv 面談記録詳細．事項類型区分
     */
    public void setItemTypeDiv(String itemTypeDiv) {
        this.itemTypeDiv = itemTypeDiv;
    }

    /**
     * 面談記録詳細．質問番号を取得する
     *
     * @return askNumber 面談記録詳細．質問番号
     */
    public Integer getAskNumber() {
        return this.askNumber;
    }

    /**
     * 面談記録詳細．質問番号を設定する
     *
     * @param askNumber 面談記録詳細．質問番号
     */
    public void setAskNumber(Integer askNumber) {
        this.askNumber = askNumber;
    }

    /**
     * 面談記録詳細．回答結果内容を取得する
     *
     * @return answerReltCont 面談記録詳細．回答結果内容
     */
    public String getAnswerReltCont() {
        return this.answerReltCont;
    }

    /**
     * 面談記録詳細．回答結果内容を設定する
     *
     * @param answerReltCont 面談記録詳細．回答結果内容
     */
    public void setAnswerReltCont(String answerReltCont) {
        this.answerReltCont = answerReltCont;
    }

    /**
     * メッセージ閲覧状況．質問事項表示フラグ      ※　0の場合：質問と回答非表示する      ※　1の場合：質問と回答表示するを取得する
     *
     * @return askDispFlg メッセージ閲覧状況．質問事項表示フラグ      ※　0の場合：質問と回答非表示する      ※　1の場合：質問と回答表示する
     */
    public String getAskDispFlg() {
        return this.askDispFlg;
    }

    /**
     * メッセージ閲覧状況．質問事項表示フラグ      ※　0の場合：質問と回答非表示する      ※　1の場合：質問と回答表示するを設定する
     *
     * @param askDispFlg メッセージ閲覧状況．質問事項表示フラグ      ※　0の場合：質問と回答非表示する      ※　1の場合：質問と回答表示する
     */
    public void setAskDispFlg(String askDispFlg) {
        this.askDispFlg = askDispFlg;
    }

    /**
     * メッセージ閲覧状況．面談事項表示フラグ      ※　0の場合：面談事項と回答非表示する      ※　1の場合：面談事項と回答表示するを取得する
     *
     * @return talkDispFlg メッセージ閲覧状況．面談事項表示フラグ      ※　0の場合：面談事項と回答非表示する      ※　1の場合：面談事項と回答表示する
     */
    public String getTalkDispFlg() {
        return this.talkDispFlg;
    }

    /**
     * メッセージ閲覧状況．面談事項表示フラグ      ※　0の場合：面談事項と回答非表示する      ※　1の場合：面談事項と回答表示するを設定する
     *
     * @param talkDispFlg メッセージ閲覧状況．面談事項表示フラグ      ※　0の場合：面談事項と回答非表示する      ※　1の場合：面談事項と回答表示する
     */
    public void setTalkDispFlg(String talkDispFlg) {
        this.talkDispFlg = talkDispFlg;
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
     * コードマスタ．コード値を取得する
     *
     * @return codValue コードマスタ．コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コードマスタ．コード値を設定する
     *
     * @param codValue コードマスタ．コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 設問名を取得する
     *
     * @return questionName 設問名
     */
    public String getQuestionName() {
        return this.questionName;
    }

    /**
     * 設問名を設定する
     *
     * @param questionName 設問名
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
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
