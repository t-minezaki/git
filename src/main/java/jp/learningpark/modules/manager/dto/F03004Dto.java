/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstUnitEntity;

/**
 * <p>F03002_教科書単元編集画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/26 : gong: 新規<br />
 * @version 1.0
 */
public class F03004Dto extends MstUnitEntity {
    /**
     * 学年
     */
    private String schy;

    /**
     * 教科
     */
    private String subjt;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 学年を設定する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を取得する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 教科を設定する
     *
     * @return subjt 教科
     */
    public String getSubjt() {
        return this.subjt;
    }

    /**
     * 教科を取得する
     *
     * @param subjt 教科
     */
    public void setSubjt(String subjt) {
        this.subjt = subjt;
    }

    /**
     * 組織IDを設定する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを取得する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織名を設定する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を取得する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }
}
