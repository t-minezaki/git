package jp.learningpark.modules.pop.dto;

/**
 * <p>配信先選択画面（POP） Dto</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * 変更履歴:
 * 2020/06/04 ： NWT)hxl ： 新規作成
 * 2020/11/09 ： NWT)文 ： 要件変更
 */
public class F21074Dto {
    /**
     * ユーザーID
     */
    private String userId;
    /**
     * 変更後ユーザーID
     */
    private String afterUserId;
    /**
     * ユーザー姓名
     */
    private String userName;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUserId 変更後ユーザーID
     */
    public String getAfterUserId() {
        return this.afterUserId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUserId 変更後ユーザーID
     */
    public void setAfterUserId(String afterUserId) {
        this.afterUserId = afterUserId;
    }

    /**
     * ユーザー姓名を取得する
     *
     * @return userName ユーザー姓名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * ユーザー姓名を設定する
     *
     * @param userName ユーザー姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * ユーザーIDを取得する
     *
     * @return userId ユーザーID
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * ユーザーIDを設定する
     *
     * @param userId ユーザーID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }
}
