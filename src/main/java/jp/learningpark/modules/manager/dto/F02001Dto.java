/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F02001 単元情報インポート・エクスポート画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/08 : wen: 新規<br />
 * @version 1.0
 */
public class F02001Dto {
    /**
     * ID
     */
    private Integer id;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 学年コード
     */
    private String schyDiv;

    /**
     * 教科コード
     */
    private String subjtDiv;

    /**
     * 単元管理CD
     */
    private String unitMstCd;

    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 節名
     */
    private String sectnNm;

    /**
     * 単元名
     */
    private String unitNm;

    /**
     * 組織区分（1：本組織）
     */
    private String isOrg;

    /**
     * 階層
     */

    private String level;

    /**
     * idを取得する
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @return id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 組織IDを取得する
     *
     * @return orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @return orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 学年コードを取得する
     *
     * @return schyDiv
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * 学年コードを設定する
     *
     * @return schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 教科コードを取得する
     *
     * @return subjtDiv
     */
    public String getSubjtDiv() {
        return subjtDiv;
    }

    /**
     * 教科コードを設定する
     *
     * @return subjtDiv
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 単元管理CDを取得する
     *
     * @return mstUnitCd
     */
    public String getUnitMstCd() {
        return unitMstCd;
    }

    /**
     * 単元管理CDを設定する
     *
     * @return mstUnitCd
     */
    public void setUnitMstCd(String unitMstCd) {
        this.unitMstCd = unitMstCd;
    }

    /**
     * 章名を取得する
     *
     * @return chaptNm
     */
    public String getChaptNm() {
        return chaptNm;
    }

    /**
     * 章名を設定する
     *
     * @return chaptNm
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * 節名を取得する
     *
     * @return sectnNm
     */
    public String getSectnNm() {
        return sectnNm;
    }

    /**
     * 節名を設定する
     *
     * @return sectnNm
     */
    public void setSectnNm(String sectnNm) {
        this.sectnNm = sectnNm;
    }

    /**
     * 単元名を取得する
     *
     * @return unitNm
     */
    public String getUnitNm() {
        return unitNm;
    }

    /**
     * 単元名を設定する
     *
     * @return unitNm
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }

    /**
     * 組織区分を取得する
     *
     * @return orgFlg
     */
    public String getIsOrg() {
        return isOrg;
    }

    /**
     * 組織区分を設定する
     *
     * @return orgFlg
     */
    public void setIsOrg(String isOrg) {
        this.isOrg = isOrg;
    }

    /**
     * 階層を取得する
     *
     * @return level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 階層を設定する
     *
     * @return level
     */
    public void setLevel(String level) {
        this.level = level;
    }
}
