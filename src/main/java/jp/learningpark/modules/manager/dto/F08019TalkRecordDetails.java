package jp.learningpark.modules.manager.dto;

/**
 * <p>F08019未読・未回答者送信一覧画面 Dto</p>
 *
 * @author NWT)hxl
 * @version 9.0
 * <p>
 * 変更履歴:
 * 2020/07/09　：　NWT)hxl　：　新規作成
 * 2020/11/12　：　NWT)文　：　変更
 */
public class F08019TalkRecordDetails {
    /**
     * stuId;ask_num
     */
    private String detailsKey;
    /**
     * 回答結果内容
     */
    private String detailsValue;

    /**
     * stuId;ask_numを取得する
     *
     * @return detailsKey stuId;ask_num
     */
    public String getDetailsKey() {
        return this.detailsKey;
    }

    /**
     * stuId;ask_numを設定する
     *
     * @param detailsKey stuId;ask_num
     */
    public void setDetailsKey(String detailsKey) {
        this.detailsKey = detailsKey;
    }

    /**
     * 回答結果内容を取得する
     *
     * @return detailsValue 回答結果内容
     */
    public String getDetailsValue() {
        return this.detailsValue;
    }

    /**
     * 回答結果内容を設定する
     *
     * @param detailsValue 回答結果内容
     */
    public void setDetailsValue(String detailsValue) {
        this.detailsValue = detailsValue;
    }
}
