/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstGuardEntity;

/**
 * <p>F00044_生徒保護者関係検索一覧画面 Dto</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/15 : tan: 新規<br />
 * @version 1.0
 */
public class F00044Dto extends MstGuardEntity {
    /**
     * コードマスタ．コードＣＤ
     */
    private String codCd;
    /**
     * コードマスタ．コード値
     */
    private String codValue;
    /**
     * ユーザ基本マスタ（U1）．変更後ユーザＩＤ（生徒ID）
     */
    private String stuId;
    /**
     * ユーザID(生徒)
     */
    private String stuUi;
    /**
     * 生徒基本マスタ．姓名_姓
     */
    private String stuNm;
    /**
     * 生徒基本マスタ．姓名_名
     */
    private String stuLnm;
    /**
     * 生徒基本マスタ．生年月日
     */
    private String birthd;
    /**
     * ユーザ基本マスタ（U2）．変更後ユーザＩＤ（保護者ID）
     */
    private String guardId;
    /**
     * ユーザID(保護者)
     */
    private String guardUi;
    /**
     * 保護者姓名_姓
     */
    private String guardNm;
    /**
     * 保護者姓名_名
     */
    private String guardLnm;
    /**
     * コードマスタ．コード値（学年）
     */
    private String schy;
    /**
     * コードマスタ．コード値（性別）
     */
    private String gendr;
    /**
     * 生徒基本マスタ．保護者ID
     */
    private String stuGuardId;
    /**
     *組織マスタ．組織名
     */
    private String orgNm;
    /**
     *最初の保護者
     */
    private String firstGuard;
    /**
     * ユーザ基本マスタ（U1）．変更後ユーザＩＤ（生徒ID）を取得する
     *
     * @return stuId ユーザ基本マスタ（U1）．変更後ユーザＩＤ（生徒ID）
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * ユーザ基本マスタ（U1）．変更後ユーザＩＤ（生徒ID）を設定する
     *
     * @param stuId ユーザ基本マスタ（U1）．変更後ユーザＩＤ（生徒ID）
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * ユーザID(生徒)を取得する
     *
     * @return stuUi ユーザID(生徒)
     */
    public String getStuUi() {
        return this.stuUi;
    }

    /**
     * ユーザID(生徒)を設定する
     *
     * @param stuUi ユーザID(生徒)
     */
    public void setStuUi(String stuUi) {
        this.stuUi = stuUi;
    }

    /**
     * 生徒基本マスタ．姓名_姓を取得する
     *
     * @return stuNm 生徒基本マスタ．姓名_姓
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒基本マスタ．姓名_姓を設定する
     *
     * @param stuNm 生徒基本マスタ．姓名_姓
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 生徒基本マスタ．姓名_名を取得する
     *
     * @return stuLnm 生徒基本マスタ．姓名_名
     */
    public String getStuLnm() {
        return this.stuLnm;
    }

    /**
     * 生徒基本マスタ．姓名_名を設定する
     *
     * @param stuLnm 生徒基本マスタ．姓名_名
     */
    public void setStuLnm(String stuLnm) {
        this.stuLnm = stuLnm;
    }

    /**
     * 生徒基本マスタ．生年月日を取得する
     *
     * @return birthd 生徒基本マスタ．生年月日
     */
    public String getBirthd() {
        return this.birthd;
    }

    /**
     * 生徒基本マスタ．生年月日を設定する
     *
     * @param birthd 生徒基本マスタ．生年月日
     */
    public void setBirthd(String birthd) {
        this.birthd = birthd;
    }

    /**
     * ユーザ基本マスタ（U2）．変更後ユーザＩＤ（保護者ID）を取得する
     *
     * @return guardId ユーザ基本マスタ（U2）．変更後ユーザＩＤ（保護者ID）
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * ユーザ基本マスタ（U2）．変更後ユーザＩＤ（保護者ID）を設定する
     *
     * @param guardId ユーザ基本マスタ（U2）．変更後ユーザＩＤ（保護者ID）
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * ユーザID(保護者)を取得する
     *
     * @return guardUi ユーザID(保護者)
     */
    public String getGuardUi() {
        return this.guardUi;
    }

    /**
     * ユーザID(保護者)を設定する
     *
     * @param guardUi ユーザID(保護者)
     */
    public void setGuardUi(String guardUi) {
        this.guardUi = guardUi;
    }

    /**
     * 保護者姓名_姓を取得する
     *
     * @return guardNm 保護者姓名_姓
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者姓名_姓を設定する
     *
     * @param guardNm 保護者姓名_姓
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 保護者姓名_名を取得する
     *
     * @return guardLnm 保護者姓名_名
     */
    public String getGuardLnm() {
        return this.guardLnm;
    }

    /**
     * 保護者姓名_名を設定する
     *
     * @param guardLnm 保護者姓名_名
     */
    public void setGuardLnm(String guardLnm) {
        this.guardLnm = guardLnm;
    }

    /**
     * コードマスタ．コード値（学年）を取得する
     *
     * @return schy コードマスタ．コード値（学年）
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * コードマスタ．コード値（学年）を設定する
     *
     * @param schy コードマスタ．コード値（学年）
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * コードマスタ．コード値（性別）を取得する
     *
     * @return gendr コードマスタ．コード値（性別）
     */
    public String getGendr() {
        return this.gendr;
    }

    /**
     * コードマスタ．コード値（性別）を設定する
     *
     * @param gendr コードマスタ．コード値（性別）
     */
    public void setGendr(String gendr) {
        this.gendr = gendr;
    }

    /**
     * コードマスタ．コードＣＤを取得する
     *
     * @return codCd コードマスタ．コードＣＤ
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * コードマスタ．コードＣＤを設定する
     *
     * @param codCd コードマスタ．コードＣＤ
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    /**
     * コードマスタ．コード値を取得する
     *
     * @return codValue コードマスタ．コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コードマスタ．コード値を設定する
     *
     * @param codValue コードマスタ．コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 生徒基本マスタ．保護者IDを取得する
     *
     * @return stuGuardId 生徒基本マスタ．保護者ID
     */
    public String getStuGuardId() {
        return this.stuGuardId;
    }

    /**
     * 生徒基本マスタ．保護者IDを設定する
     *
     * @param stuGuardId 生徒基本マスタ．保護者ID
     */
    public void setStuGuardId(String stuGuardId) {
        this.stuGuardId = stuGuardId;
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
     * 最初の保護者を取得する
     *
     * @return firstGuard 最初の保護者
     */
    public String getFirstGuard() {
        return this.firstGuard;
    }

    /**
     * 最初の保護者を設定する
     *
     * @param firstGuard 最初の保護者
     */
    public void setFirstGuard(String firstGuard) {
        this.firstGuard = firstGuard;
    }
}
