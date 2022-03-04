/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F04001_塾ニュース一覧画面 Dto</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/02/26 : tan: 新規<br />
 * @version 1.0
 */
public class F00071Dto {
    /**
     * 組織マスタ．組織ＩＤ
     */
    private String orgId;
    /**
     * 組織マスタ．組織名
     */
    private String orgNm;
    /**
     * ユーザ基本マスタ．変更後ユーザID
     */
    private String afterUsrId;
    /**
     * ユーザ基本マスタ．ステータス
     */
    private String usrStatus;
    /**
     * 転出転入履歴．通塾ステータス
     */
    private String crmSts;
    /**
     * 転出転入履歴．異動年月日
     */
    private String moveYmd;
    /**
     * 生徒基本マスタ．姓名_姓
     */
    private String flnmNm;
    /**
     * 生徒基本マスタ．姓名_名
     */
    private String flnmLnm;
    /**
     * 本組織及下層組織の組織マスタ．組織ＩＤ
     */
    private String orgId1;
    /**
     * 本組織及下層組織の組織マスタ．組織名
     */
    private String orgNm1;
    /**
     * 本組織及下層組織の本組織区分
     */
    private String orgFlg;
    /**
     * 本組織及下層組織の本組織マスタ．階層
     */
    private String level;
    /**
     * ステータスリストのコードＣＤ
     */
    private String codCd2;
    /**
     * ステータスリストのコード値２
     */
    private String codValue2;
    /**
     * 異動ステータスリストのコードＣＤ
     */
    private String codCd;
    /**
     * 異動ステータスリストのコード値
     */
    private String codValue;

    /**
     * 組織マスタ．組織ＩＤを取得する
     *
     * @return orgId 組織マスタ．組織ＩＤ
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織マスタ．組織ＩＤを設定する
     *
     * @param orgId 組織マスタ．組織ＩＤ
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織マスタ．組織名を取得する
     *
     * @return orgNm 組織マスタ．組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織マスタ．組織名を設定する
     *
     * @param orgNm 組織マスタ．組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * ユーザ基本マスタ．変更後ユーザIDを取得する
     *
     * @return afterUsrId ユーザ基本マスタ．変更後ユーザID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * ユーザ基本マスタ．変更後ユーザIDを設定する
     *
     * @param afterUsrId ユーザ基本マスタ．変更後ユーザID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 転出転入履歴．通塾ステータスを取得する
     *
     * @return crmSts 転出転入履歴．通塾ステータス
     */
    public String getCrmSts() {
        return this.crmSts;
    }

    /**
     * 転出転入履歴．通塾ステータスを設定する
     *
     * @param crmSts 転出転入履歴．通塾ステータス
     */
    public void setCrmSts(String crmSts) {
        this.crmSts = crmSts;
    }

    /**
     * 転出転入履歴．異動年月日を取得する
     *
     * @return moveYmd 転出転入履歴．異動年月日
     */
    public String getMoveYmd() {
        return this.moveYmd;
    }

    /**
     * 転出転入履歴．異動年月日を設定する
     *
     * @param moveYmd 転出転入履歴．異動年月日
     */
    public void setMoveYmd(String moveYmd) {
        this.moveYmd = moveYmd;
    }

    /**
     * 生徒基本マスタ．姓名_姓を取得する
     *
     * @return flnmNm 生徒基本マスタ．姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 生徒基本マスタ．姓名_姓を設定する
     *
     * @param flnmNm 生徒基本マスタ．姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 生徒基本マスタ．姓名_名を取得する
     *
     * @return flnmLnm 生徒基本マスタ．姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 生徒基本マスタ．姓名_名を設定する
     *
     * @param flnmLnm 生徒基本マスタ．姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 本組織及下層組織の組織マスタ．組織ＩＤを取得する
     *
     * @return orgId1 本組織及下層組織の組織マスタ．組織ＩＤ
     */
    public String getOrgId1() {
        return this.orgId1;
    }

    /**
     * 本組織及下層組織の組織マスタ．組織ＩＤを設定する
     *
     * @param orgId1 本組織及下層組織の組織マスタ．組織ＩＤ
     */
    public void setOrgId1(String orgId1) {
        this.orgId1 = orgId1;
    }

    /**
     * 本組織及下層組織の組織マスタ．組織名を取得する
     *
     * @return orgNm1 本組織及下層組織の組織マスタ．組織名
     */
    public String getOrgNm1() {
        return this.orgNm1;
    }

    /**
     * 本組織及下層組織の組織マスタ．組織名を設定する
     *
     * @param orgNm1 本組織及下層組織の組織マスタ．組織名
     */
    public void setOrgNm1(String orgNm1) {
        this.orgNm1 = orgNm1;
    }

    /**
     * 本組織及下層組織の本組織区分を取得する
     *
     * @return orgFlg 本組織及下層組織の本組織区分
     */
    public String getOrgFlg() {
        return this.orgFlg;
    }

    /**
     * 本組織及下層組織の本組織区分を設定する
     *
     * @param orgFlg 本組織及下層組織の本組織区分
     */
    public void setOrgFlg(String orgFlg) {
        this.orgFlg = orgFlg;
    }

    /**
     * 本組織及下層組織の本組織マスタ．階層を取得する
     *
     * @return level 本組織及下層組織の本組織マスタ．階層
     */
    public String getLevel() {
        return this.level;
    }

    /**
     * 本組織及下層組織の本組織マスタ．階層を設定する
     *
     * @param level 本組織及下層組織の本組織マスタ．階層
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * ステータスリストのコードＣＤを取得する
     *
     * @return codCd2 ステータスリストのコードＣＤ
     */
    public String getCodCd2() {
        return this.codCd2;
    }

    /**
     * ステータスリストのコードＣＤを設定する
     *
     * @param codCd2 ステータスリストのコードＣＤ
     */
    public void setCodCd2(String codCd2) {
        this.codCd2 = codCd2;
    }

    /**
     * ステータスリストのコード値２を取得する
     *
     * @return codValue2 ステータスリストのコード値２
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * ステータスリストのコード値２を設定する
     *
     * @param codValue2 ステータスリストのコード値２
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }

    /**
     * 異動ステータスリストのコードＣＤを取得する
     *
     * @return codCd 異動ステータスリストのコードＣＤ
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * 異動ステータスリストのコードＣＤを設定する
     *
     * @param codCd 異動ステータスリストのコードＣＤ
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    /**
     * 異動ステータスリストのコード値を取得する
     *
     * @return codValue 異動ステータスリストのコード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * 異動ステータスリストのコード値を設定する
     *
     * @param codValue 異動ステータスリストのコード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * ユーザ基本マスタ．ステータスを取得する
     *
     * @return usrStatus ユーザ基本マスタ．ステータス
     */
    public String getUsrStatus() {
        return this.usrStatus;
    }

    /**
     * ユーザ基本マスタ．ステータスを設定する
     *
     * @param usrStatus ユーザ基本マスタ．ステータス
     */
    public void setUsrStatus(String usrStatus) {
        this.usrStatus = usrStatus;
    }
}
