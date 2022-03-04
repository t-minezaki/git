package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/25 ： NWT)hxl ： 新規作成
 * @date 2020/05/25 11:22
 */
public class F21069DtoIn {
    /**
     * student name
     */
    private String aFlnm;
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
    private String aId;

    /**
     * student nameを取得する
     *
     * @return aFlnm student name
     */
    public String getAFlnm() {
        return this.aFlnm;
    }

    /**
     * student nameを設定する
     *
     * @param aFlnm student name
     */
    public void setAFlnm(String aFlnm) {
        this.aFlnm = aFlnm;
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
     * @return aId student id(after id)
     */
    public String getAId() {
        return this.aId;
    }

    /**
     * student id(after id)を設定する
     *
     * @param aId student id(after id)
     */
    public void setAId(String aId) {
        this.aId = aId;
    }
}
