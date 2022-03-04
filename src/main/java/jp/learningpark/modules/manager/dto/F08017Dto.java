package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstAskTalkTmplateEntity;


public class F08017Dto extends MstAskTalkTmplateEntity {
    /**
     * 事項類型名
     */
    private String itemName;
    /**
     * 設問形式名
     */
    private String answerName;

    /**
     * 事項類型名を取得する
     *
     * @return itemName 事項類型名
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 事項類型名を設定する
     *
     * @param itemName 事項類型名
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 設問形式名を取得する
     *
     * @return answerName 設問形式名
     */
    public String getAnswerName() {
        return this.answerName;
    }

    /**
     * 設問形式名を設定する
     *
     * @param answerName 設問形式名
     */
    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }
}
