/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ユーザ基本マスタ
 *
 * @author NWT
 */
@TableName("mst_usr")
public class MstUsrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * ユーザID
     */
    private String usrId;

    /**
     * ユーザログインPW
     */
    private String usrPassword;

    /**
     * 初回登録フラグ
     */
    private String fstLoginFlg;

    /**
     * ユーザ名
     */
    private String usrNm;

    /**
     * ロール区分
     */
    @NotNull
    private String roleDiv;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * PW更新フラグ
     */
    @NotNull
    private String pwUpFlg;

    /**
     * ユーザステータス
     */
    @NotNull
    private String usrSts;

    /**
     * 特殊権限フラグ
     */
    @NotNull
    private String specAuthFlg;

    /**
     * 変更後ユーザーID
     */
    @NotNull
    private String afterUsrId;

    /**
     * エラー回数
     */
    @NotNull
    private Integer errorCount;

    /**
     * ロックフラグ
     */
    @NotNull
    private String lockFlg;

    /**
     * 学研IDプライマリキー
     */
    private String gidpk;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * 他システム区分
     */
    private String systemKbn;

    /**
     * 組織共用キー
     */
    private String orgCommKey;

    /**
     * GakkenID規約フラグ
     */
    private String gidRuleFlg;

    /**
     * マナミル規約フラグ
     */
    private String manaRuleFlg;

    /**
     * 個人情報保護規約フラグ
     */
    private String perlInfoRuleFlg;

    /**
     * 自分修正可否フラグ
     */
    private String safModifyFlg;

    /**
     * 管理者修正可否フラグ
     */
    private String mgrModifyFlg;

    /**
     * 所属組織フラグ
     */
    private String ownerOrgFlg;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新ユーザＩＤ
     */
    private String updUsrId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;

    /**
     * IDを設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * IDを取得する
     */
    public Integer getId() {
        return id;
    }

    /**
     * ユーザIDを設定する
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * ユーザIDを取得する
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     * ユーザログインPWを設定する
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    /**
     * ユーザログインPWを取得する
     */
    public String getUsrPassword() {
        return usrPassword;
    }

    /**
     * 初回登録フラグを設定する
     */
    public void setFstLoginFlg(String fstLoginFlg) {
        this.fstLoginFlg = fstLoginFlg;
    }

    /**
     * 初回登録フラグを取得する
     */
    public String getFstLoginFlg() {
        return fstLoginFlg;
    }

    /**
     * ユーザ名を設定する
     */
    public void setUsrNm(String usrNm) {
        this.usrNm = usrNm;
    }

    /**
     * ユーザ名を取得する
     */
    public String getUsrNm() {
        return usrNm;
    }

    /**
     * ロール区分を設定する
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }

    /**
     * ロール区分を取得する
     */
    public String getRoleDiv() {
        return roleDiv;
    }

    /**
     * 組織IDを設定する
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織IDを取得する
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * PW更新フラグを設定する
     */
    public void setPwUpFlg(String pwUpFlg) {
        this.pwUpFlg = pwUpFlg;
    }

    /**
     * PW更新フラグを取得する
     */
    public String getPwUpFlg() {
        return pwUpFlg;
    }

    /**
     * ユーザステータスを設定する
     */
    public void setUsrSts(String usrSts) {
        this.usrSts = usrSts;
    }

    /**
     * ユーザステータスを取得する
     */
    public String getUsrSts() {
        return usrSts;
    }

    /**
     * 特殊権限フラグを設定する
     */
    public void setSpecAuthFlg(String specAuthFlg) {
        this.specAuthFlg = specAuthFlg;
    }

    /**
     * 特殊権限フラグを取得する
     */
    public String getSpecAuthFlg() {
        return specAuthFlg;
    }

    /**
     * 変更後ユーザーIDを設定する
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 変更後ユーザーIDを取得する
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * エラー回数を設定する
     */
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * エラー回数を取得する
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * ロックフラグを設定する
     */
    public void setLockFlg(String lockFlg) {
        this.lockFlg = lockFlg;
    }

    /**
     * ロックフラグを取得する
     */
    public String getLockFlg() {
        return lockFlg;
    }

    /**
     * 作成日時を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 作成日時を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }

    /**
     * 作成ユーザＩＤを設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 作成ユーザＩＤを取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }

    /**
     * 更新日時を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }

    /**
     * 更新ユーザＩＤを設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * 更新ユーザＩＤを取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }

    /**
     * 削除フラグを設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * 削除フラグを取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }

    /**
     * 学研IDプライマリキーを取得する
     *
     * @return gidpk 学研IDプライマリキー
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 学研IDプライマリキーを設定する
     *
     * @param gidpk 学研IDプライマリキー
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
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
     * 他システム区分を取得する
     *
     * @return systemKbn 他システム区分
     */
    public String getSystemKbn() {
        return this.systemKbn;
    }

    /**
     * 他システム区分を設定する
     *
     * @param systemKbn 他システム区分
     */
    public void setSystemKbn(String systemKbn) {
        this.systemKbn = systemKbn;
    }

    /**
     * 組織共用キーを取得する
     *
     * @return orgCommKey 組織共用キー
     */
    public String getOrgCommKey() {
        return this.orgCommKey;
    }

    /**
     * 組織共用キーを設定する
     *
     * @param orgCommKey 組織共用キー
     */
    public void setOrgCommKey(String orgCommKey) {
        this.orgCommKey = orgCommKey;
    }

    /**
     * GakkenID規約フラグを取得する
     *
     * @return gidRuleFlg GakkenID規約フラグ
     */
    public String getGidRuleFlg() {
        return this.gidRuleFlg;
    }

    /**
     * GakkenID規約フラグを設定する
     *
     * @param gidRuleFlg GakkenID規約フラグ
     */
    public void setGidRuleFlg(String gidRuleFlg) {
        this.gidRuleFlg = gidRuleFlg;
    }

    /**
     * マナミル規約フラグを取得する
     *
     * @return manaRuleFlg マナミル規約フラグ
     */
    public String getManaRuleFlg() {
        return this.manaRuleFlg;
    }

    /**
     * マナミル規約フラグを設定する
     *
     * @param manaRuleFlg マナミル規約フラグ
     */
    public void setManaRuleFlg(String manaRuleFlg) {
        this.manaRuleFlg = manaRuleFlg;
    }

    /**
     * 個人情報保護規約フラグを取得する
     *
     * @return perlInfoRuleFlg 個人情報保護規約フラグ
     */
    public String getPerlInfoRuleFlg() {
        return this.perlInfoRuleFlg;
    }

    /**
     * 個人情報保護規約フラグを設定する
     *
     * @param perlInfoRuleFlg 個人情報保護規約フラグ
     */
    public void setPerlInfoRuleFlg(String perlInfoRuleFlg) {
        this.perlInfoRuleFlg = perlInfoRuleFlg;
    }

    /**
     * 自分修正可否フラグを取得する
     *
     * @return safModifyFlg 自分修正可否フラグ
     */
    public String getSafModifyFlg() {
        return this.safModifyFlg;
    }

    /**
     * 自分修正可否フラグを設定する
     *
     * @param safModifyFlg 自分修正可否フラグ
     */
    public void setSafModifyFlg(String safModifyFlg) {
        this.safModifyFlg = safModifyFlg;
    }

    /**
     * 管理者修正可否フラグを取得する
     *
     * @return mgrModifyFlg 管理者修正可否フラグ
     */
    public String getMgrModifyFlg() {
        return this.mgrModifyFlg;
    }

    /**
     * 管理者修正可否フラグを設定する
     *
     * @param mgrModifyFlg 管理者修正可否フラグ
     */
    public void setMgrModifyFlg(String mgrModifyFlg) {
        this.mgrModifyFlg = mgrModifyFlg;
    }

    /**
     * 所属組織フラグを取得する
     *
     * @return ownerOrgFlg 所属組織フラグ
     */
    public String getOwnerOrgFlg() {
        return this.ownerOrgFlg;
    }

    /**
     * 所属組織フラグを設定する
     *
     * @param ownerOrgFlg 所属組織フラグ
     */
    public void setOwnerOrgFlg(String ownerOrgFlg) {
        this.ownerOrgFlg = ownerOrgFlg;
    }
}
