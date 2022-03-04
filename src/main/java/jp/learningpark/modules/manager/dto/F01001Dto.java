package jp.learningpark.modules.manager.dto;

import java.util.Date;

public class F01001Dto {
    /**
     * ID
     */
    private Integer id;
    /**
     * 組織ID
     */
    private String orgNm;
    /**
     * 学年区分
     */
    private String schyDiv;
    /**
     * 計画期間終了日
     */
    private Date planPrdEndDy;
    /**
     * 計画期間終了日 String
     */
    private String planEndDy;
    /**
     * 計画期間開始日
     */
    private Date planPrdStartDy;
    /**
     * 計画期間開始日 String
     */
    private String planStartDy;
    /**
     * 本組織フラグ
     */
    private String useFlg;
    /**
     * 階層
     */
    private Integer level;
    /**
     * コード値（学年）
     */
    private String codValue;
    /**
     * 時期名
     */
    private String prdNm;
    /**
     * 本組織フラグ
     */
    private String flg;
    /**
     * 組織ID
     */
    private String orgId;

    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * orgNmを取得する
     *
     * @return orgNm orgNm
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * orgNmを設定する
     *
     * @param orgNm orgNm
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * schyDivを取得する
     *
     * @return schyDiv schyDiv
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * schyDivを設定する
     *
     * @param schyDiv schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * planPrdEndDyを取得する
     *
     * @return planPrdEndDy planPrdEndDy
     */
    public Date getPlanPrdEndDy() {
        return planPrdEndDy;
    }

    /**
     * planPrdEndDyを設定する
     *
     * @param planPrdEndDy planPrdEndDy
     */
    public void setPlanPrdEndDy(Date planPrdEndDy) {
        this.planPrdEndDy = planPrdEndDy;
    }

    /**
     * planEndDyを取得する
     *
     * @return planEndDy planEndDy
     */
    public String getPlanEndDy() {
        return planEndDy;
    }

    /**
     * planEndDyを設定する
     *
     * @param planEndDy planEndDy
     */
    public void setPlanEndDy(String planEndDy) {
        this.planEndDy = planEndDy;
    }

    /**
     * planPrdStartDyを取得する
     *
     * @return planPrdStartDy planPrdStartDy
     */
    public Date getPlanPrdStartDy() {
        return planPrdStartDy;
    }

    /**
     * planPrdStartDyを設定する
     *
     * @param planPrdStartDy planPrdStartDy
     */
    public void setPlanPrdStartDy(Date planPrdStartDy) {
        this.planPrdStartDy = planPrdStartDy;
    }

    /**
     * planStartDyを取得する
     *
     * @return planStartDy planStartDy
     */
    public String getPlanStartDy() {
        return planStartDy;
    }

    /**
     * planStartDyを設定する
     *
     * @param planStartDy planStartDy
     */
    public void setPlanStartDy(String planStartDy) {
        this.planStartDy = planStartDy;
    }

    /**
     * useFlgを取得する
     *
     * @return useFlg useFlg
     */
    public String getUseFlg() {
        return useFlg;
    }

    /**
     * useFlgを設定する
     *
     * @param useFlg useFlg
     */
    public void setUseFlg(String useFlg) {
        this.useFlg = useFlg;
    }

    /**
     * levelを取得する
     *
     * @return level level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * levelを設定する
     *
     * @param level level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * codValueを取得する
     *
     * @return codValue codValue
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * codValueを設定する
     *
     * @param codValue codValue
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * prdNmを取得する
     *
     * @return prdNm prdNm
     */
    public String getPrdNm() {
        return prdNm;
    }

    /**
     * prdNmを設定する
     *
     * @param prdNm prdNm
     */
    public void setPrdNm(String prdNm) {
        this.prdNm = prdNm;
    }

    /**
     * flgを取得する
     *
     * @return flg flg
     */
    public String getFlg() {
        return flg;
    }

    /**
     * flgを設定する
     *
     * @param flg flg
     */
    public void setFlg(String flg) {
        this.flg = flg;
    }

    /**
     * orgIdを取得する
     *
     * @return orgId orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * orgIdを設定する
     *
     * @param orgId orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
