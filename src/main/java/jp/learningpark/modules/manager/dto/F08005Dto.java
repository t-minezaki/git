/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F08005 イベント管理日程の設定画面 Dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/31 : wq: 新規<br />
 * @version 1.0
 */
public class F08005Dto {

    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 組織名
     */
    private String orgNm;
    /**
     * ユーザーID
     */
    private String usrId;
    /**
     * ユーザー名
     */
    private String userName;

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    /**
     * ユーザーIDを取得する
     *
     * @return usrId ユーザーID
     */
    public String getusrId() {
        return this.usrId;
    }

    /**
     * ユーザーIDを設定する
     *
     * @param usrId ユーザーID
     */
    public void setusrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * ユーザー名を取得する
     *
     * @return userName ユーザー名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * ユーザー名を設定する
     *
     * @param userName ユーザー名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
