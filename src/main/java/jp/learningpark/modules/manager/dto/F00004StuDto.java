/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/28 : gong: 新規<br />
 * @version 1.0
 */
public class F00004StuDto {
    /**
     * ユーザID
     */
    private String usrId;

    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;

    /**
     * ユーザログインPW
     */
    private String usrPassword;

    /**
     * 性別区分
     */
    private String gendrDiv;

    /**
     * 生年月日
     */
    private String birthd;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 姓名_姓
     */
    private String flnmNm;

    /**
     * 姓名_名
     */
    private String flnmLnm;

    /**
     * 姓名_カナ姓
     */
    private String flnmKnNm;

    /**
     * 生徒名前
     */
    private String stuNm;

    /**
     * 保護者名前
     */
    private String guardNm;

    /**
     * 住所
     */
    private String adr;

    /**
     * 保護者変更後ユーザーID
     */
    private String guardAfterUsrId;

    /**
     * 姓名_カナ名
     */
    private String flnmKnLnm;

    /**
     * 学校コード
     */
    private String schCd;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * GIDPK
     */
    private String gidpk;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * オリジナルCD
     */
    private String oriaCd;

    /**
     * ユーザIDを設定する
     *
     * @return usrId ユーザID
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * ユーザIDを取得する
     *
     * @param usrId ユーザID
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * ユーザログインPWを設定する
     *
     * @return usrPassword ユーザログインPW
     */
    public String getUsrPassword() {
        return this.usrPassword;
    }

    /**
     * ユーザログインPWを取得する
     *
     * @param usrPassword ユーザログインPW
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    /**
     * 性別区分を設定する
     *
     * @return gendrDiv 性別区分
     */
    public String getGendrDiv() {
        return this.gendrDiv;
    }

    /**
     * 性別区分を取得する
     *
     * @param gendrDiv 性別区分
     */
    public void setGendrDiv(String gendrDiv) {
        this.gendrDiv = gendrDiv;
    }

    /**
     * 生年月日を設定する
     *
     * @return birthd 生年月日
     */
    public String getBirthd() {
        return this.birthd;
    }

    /**
     * 生年月日を取得する
     *
     * @param birthd 生年月日
     */
    public void setBirthd(String birthd) {
        this.birthd = birthd;
    }

    /**
     * 学年区分を設定する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を取得する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 姓名_姓を設定する
     *
     * @return flnmNm 姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 姓名_姓を取得する
     *
     * @param flnmNm 姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 姓名_名を設定する
     *
     * @return flnmLnm 姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 姓名_名を取得する
     *
     * @param flnmLnm 姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 姓名_カナ姓を設定する
     *
     * @return flnmKnNm 姓名_カナ姓
     */
    public String getFlnmKnNm() {
        return this.flnmKnNm;
    }

    /**
     * 姓名_カナ姓を取得する
     *
     * @param flnmKnNm 姓名_カナ姓
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * 姓名_カナ名を設定する
     *
     * @return flnmKnLnm 姓名_カナ名
     */
    public String getFlnmKnLnm() {
        return this.flnmKnLnm;
    }

    /**
     * 姓名_カナ名を取得する
     *
     * @param flnmKnLnm 姓名_カナ名
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * 学校コードを設定する
     *
     * @return schCd 学校コード
     */
    public String getSchCd() {
        return this.schCd;
    }

    /**
     * 学校コードを取得する
     *
     * @param schCd 学校コード
     */
    public void setSchCd(String schCd) {
        this.schCd = schCd;
    }

    /**
     * 保護者IDを設定する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを取得する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 生徒名前を設定する
     *
     * @return stuNm 生徒名前
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒名前を取得する
     *
     * @param stuNm 生徒名前
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 保護者名前を設定する
     *
     * @return guardNm 保護者名前
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者名前を取得する
     *
     * @param guardNm 保護者名前
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * 住所を設定する
     *
     * @return adr 住所
     */
    public String getAdr() {
        return this.adr;
    }

    /**
     * 住所を取得する
     *
     * @param adr 住所
     */
    public void setAdr(String adr) {
        this.adr = adr;
    }

    /**
     * 保護者変更後ユーザーIDを設定する
     *
     * @return guardAfterUsrId 保護者変更後ユーザーID
     */
    public String getGuardAfterUsrId() {
        return this.guardAfterUsrId;
    }

    /**
     * 保護者変更後ユーザーIDを取得する
     *
     * @param guardAfterUsrId 保護者変更後ユーザーID
     */
    public void setGuardAfterUsrId(String guardAfterUsrId) {
        this.guardAfterUsrId = guardAfterUsrId;
    }

    /**
     * GIDフラグを取得する
     *
     * @return gidFlg GIDフラグ
     */
    public String getGidFlg() {
        return this.gidFlg;
    }

    /**
     * GIDフラグを設定する
     *
     * @param gidFlg GIDフラグ
     */
    public void setGidFlg(String gidFlg) {
        this.gidFlg = gidFlg;
    }

    /**
     * GIDPKを取得する
     *
     * @return gidpk GIDPK
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * GIDPKを設定する
     *
     * @param gidpk GIDPK
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * オリジナルCDを取得する
     *
     * @return oriaCd オリジナルCD
     */
    public String getOriaCd() {
        return this.oriaCd;
    }

    /**
     * オリジナルCDを設定する
     *
     * @param oriaCd オリジナルCD
     */
    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }
}
