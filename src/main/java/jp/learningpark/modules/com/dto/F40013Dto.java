/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dto;

/**
 * <p>教室選択画面</p>
 * <p>Dto</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
public class F40013Dto {
    /**
     * 組織マスタ．組織ID
     */
    private String orgId;
    /**
     * 組織マスタ．組織名
     */
    private String orgNm;
    /**
     * ユーザ基本マスタ．ロール区分
     */
    private Integer roleDiv;

    /**
     *  組織IDを取得する
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 組織IDを設定する
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     *  組織名を取得する
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * 組織名を設定する
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * ロール区分を取得する
     * @return roleDiv ロール区分
     */
    public Integer getRoleDiv() {
        return roleDiv;
    }

    /**
     * ロール区分を設定する
     * @param roleDiv ロール区分
     */
    public void setRoleDiv(Integer roleDiv) {
        this.roleDiv = roleDiv;
    }
}
