package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F09002Dto implements Serializable {

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 変更されたID
     */
    private String afterUsrId;

    /**
     * 生徒名
     */
    private String stuNm;

    /**
     * 学年
     */
    private String schyDiv;

    /**
     * 二次元コード
     */
    private String qrCod;

    /**
     * オリジナルCD
     */
    private String oriaCd;

    /**
     * 組織名
     *
     * @return 組織名
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * 組織名
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 組織ID
     *
     * @return
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 組織ID
     *
     * @param orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 生徒ID
     *
     * @return 生徒ID
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 生徒ID
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 変更されたID
     *
     * @return
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * 変更されたID
     *
     * @param afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 生徒名
     *
     * @return 生徒名
     */
    public String getStuNm() {
        return stuNm;
    }

    /**
     * 生徒名
     *
     * @param stuNm 生徒名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 学年
     *
     * @return 学年
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * 学年
     *
     * @param schyDiv 学年
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 二次元コード
     *
     * @return 二次元コード
     */
    public String getQrCod() {
        return qrCod;
    }

    /**
     * 二次元コード
     *
     * @param qrCod 二次元コード
     */
    public void setQrCod(String qrCod) {
        this.qrCod = qrCod;
    }

    /**
     * オリジナルCD
     *
     * @return オリジナルCD
     */
    public String getOriaCd() {
        return oriaCd;
    }

    /**
     * オリジナルCD
     *
     * @param oriaCd オリジナルCD
     */
    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }
}
