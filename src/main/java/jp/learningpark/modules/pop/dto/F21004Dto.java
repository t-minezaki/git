package jp.learningpark.modules.pop.dto;

/**
 * <p>
 * 確認連絡
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/26 ： NWT)hxl ： 新規作成
 * @date 2019/11/26 13:33
 */
public class F21004Dto {
    /**
     * 遅欠連絡ステータス
     */
    private String content;
    /**
     * 遅欠理由
     */
    private String reason;
    /**
     * 備考
     */
    private String remark;

    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;


    /**
     * 遅欠連絡ステータスを取得する
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 遅欠連絡ステータスを設定する
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 遅欠理由を取得する
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     * 遅欠理由を設定する
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 備考を取得する
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 備考を設定する
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }
}
