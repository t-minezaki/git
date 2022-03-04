package jp.learningpark.modules.com.dto;

public class F40016Dto {

    /**
     * 組織ID
     */
    private String orgId;

    /**
     *組織名
     */
    private String orgNm;

    /**
     *ロール区分
     */
    private char roleDiv;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public char getRoleDiv() {
        return roleDiv;
    }

    public void setRoleDiv(char roleDiv) {
        this.roleDiv = roleDiv;
    }
}
