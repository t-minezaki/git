package jp.learningpark.modules.sys.entity;

import jp.learningpark.modules.common.entity.MstUsrEntity;

public class SysUserEntity extends MstUsrEntity {

    private static final long serialVersionUID = 1L;
    
    /**
     * 組織名
     */
    private String orgNm;

    /**
     * ロール名
     */
    private String roleName;
    
    /**
     * ログインタイプ
     */
    private String loginType;

    /**
     * ブランドコード
     */
    private String brandCd;
    
    /**
     * 組織名を設定する
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }
    
    /**
     * 組織名を取得する
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * ロール名を取得する
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * ロール名を設定する
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * ログインタイプを取得する
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * ログインタイプを設定する
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    /**
     * ブランドコードを取得する
     *
     * @return brandCd ブランドコード
     */
    public String getBrandCd() {
        return this.brandCd;
    }

    /**
     * ブランドコードを設定する
     *
     * @param brandCd ブランドコード
     */
    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }
}
 