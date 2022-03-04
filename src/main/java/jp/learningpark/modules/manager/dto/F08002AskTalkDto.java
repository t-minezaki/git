package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/12 ： NWT)hxl ： 新規作成
 * @date 2020/05/12 18:02
 */
public class F08002AskTalkDto extends MstAskTalkEventEntity {
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
