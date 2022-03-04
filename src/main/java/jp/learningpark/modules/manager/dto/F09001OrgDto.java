package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * F09001組織エンティティ
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/12 ： NWT)hxl ： 新規作成
 * @date 2019/11/12 10:14
 */
public class F09001OrgDto {
    /**
     * 組織名
     */
    private String orgName;
    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 組織名を取得する
     * @return
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 組織名を設定する
     * @param orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 組織IDを取得する
     * @return
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 組織IDを設定する
     * @param orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
