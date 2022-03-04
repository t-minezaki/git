/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>生徒集計画面ExportDto</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/01/13 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F00007ExportDto {
    /**
     * ユーザID
     */
    private String usrId;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;

    /**
     * ユーザ名
     */
    private String usrNm;

    /**
     * ユーザステータス
     */
    private String usrSts;

    /**
     * 通塾ステータス
     */
    private String crmschSts;

    /**
     * usrIdを取得する
     *
     * @return usrId usrId
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     * usrIdを設定する
     *
     * @param usrId usrId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
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
     * afterUsrIdを取得する
     *
     * @return afterUsrId afterUsrId
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * afterUsrIdを設定する
     *
     * @param afterUsrId afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * usrNmを取得する
     *
     * @return usrNm usrNm
     */
    public String getUsrNm() {
        return usrNm;
    }

    /**
     * usrNmを設定する
     *
     * @param usrNm usrNm
     */
    public void setUsrNm(String usrNm) {
        this.usrNm = usrNm;
    }

    /**
     * usrStsを取得する
     *
     * @return usrSts usrSts
     */
    public String getUsrSts() {
        return usrSts;
    }

    /**
     * usrStsを設定する
     *
     * @param usrSts usrSts
     */
    public void setUsrSts(String usrSts) {
        this.usrSts = usrSts;
    }

    /**
     * crmschStsを取得する
     *
     * @return crmschSts crmschSts
     */
    public String getCrmschSts() {
        return crmschSts;
    }

    /**
     * crmschStsを設定する
     *
     * @param crmschSts crmschSts
     */
    public void setCrmschSts(String crmschSts) {
        this.crmschSts = crmschSts;
    }
}
