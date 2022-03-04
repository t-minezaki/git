package jp.learningpark.modules.pop.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/03 ： NWT)hxl ： 新規作成
 * @date 2020/06/03 11:44
 */
public class F21072Dto {
    /**
     * 変更後ユーザーＩＤ
     */
    private String afterUserId;
    /**
     * 氏名
     */
    private String userName;

    /**
     * 変更後ユーザーＩＤを取得する
     *
     * @return afterUserId 変更後ユーザーＩＤ
     */
    public String getAfterUserId() {
        return this.afterUserId;
    }

    /**
     * 変更後ユーザーＩＤを設定する
     *
     * @param afterUserId 変更後ユーザーＩＤ
     */
    public void setAfterUserId(String afterUserId) {
        this.afterUserId = afterUserId;
    }

    /**
     * 氏名を取得する
     *
     * @return userName 氏名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 氏名を設定する
     *
     * @param userName 氏名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
