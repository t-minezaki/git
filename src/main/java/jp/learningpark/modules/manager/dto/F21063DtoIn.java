package jp.learningpark.modules.manager.dto;

/**
 * <p>F21063_メッセージ作成画面 Dto</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/20 ： NWT)hxl ： 新規作成
 * 2020/11/11 ： NWT)文 ： 要件変更
 */
public class F21063DtoIn {
    /**
     * student name
     */
    private String sFlnm;
    /**
     * user ID (student id)
     */
    private String usrId;
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * student id(after id)
     */
    private String sId;

    /**
     * student nameを取得する
     *
     * @return sFlnm student name
     */
    public String getSFlnm() {
        return this.sFlnm;
    }

    /**
     * student nameを設定する
     *
     * @param sFlnm student name
     */
    public void setSFlnm(String sFlnm) {
        this.sFlnm = sFlnm;
    }

    /**
     * user ID (student id)を取得する
     *
     * @return usrId user ID (student id)
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * user ID (student id)を設定する
     *
     * @param usrId user ID (student id)
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

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
     * student id(after id)を取得する
     *
     * @return sId student id(after id)
     */
    public String getSId() {
        return this.sId;
    }

    /**
     * student id(after id)を設定する
     *
     * @param sId student id(after id)
     */
    public void setSId(String sId) {
        this.sId = sId;
    }
}
