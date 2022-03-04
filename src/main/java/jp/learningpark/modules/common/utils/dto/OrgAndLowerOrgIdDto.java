/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.dto;

import jp.learningpark.modules.common.entity.MstOrgEntity;

/**
 * <p>F04002 塾ニュース新規画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/02/26 : wen: 新規<br />
 * @version 1.0
 */
public class OrgAndLowerOrgIdDto extends MstOrgEntity {

    /**
     * 組織区分
     */
    private String orgDiv;

    /**
     * orgId + ' ' +orgNm
     */
    private String orgNmDisplay;


    /**
     * 組織区分を取得する
     *
     * @return orgDiv 組織区分
     */
    public String getOrgDiv() {
        return this.orgDiv;
    }

    /**
     * 組織区分を設定する
     *
     * @param orgDiv 組織区分
     */
    public void setOrgDiv(String orgDiv) {
        this.orgDiv = orgDiv;
    }


    /**
     * orgId + ' ' +orgNmを設定する
     *
     * @return orgNmDisplay orgId + ' ' +orgNm
     */
    public String getOrgNmDisplay() {
        return this.orgNmDisplay;
    }

    /**
     * orgId + ' ' +orgNmを取得する
     *
     * @param orgNmDisplay orgId + ' ' +orgNm
     */
    public void setOrgNmDisplay(String orgNmDisplay) {
        this.orgNmDisplay = orgNmDisplay;
    }
}
