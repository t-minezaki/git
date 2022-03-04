package jp.learningpark.modules.manager.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

public class F00001Dto implements Serializable {
    /**
     * 機能ID
     */
    private String funId;

    /**
     * 機能名
     */
    private String name;

    /**
     * タイプ
     */
    private String type;
    /**
     * ID
     */

    @NotNull
    private Integer id;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 上層組織ID
     */
    private String upplevOrgId;

    /**
     * 階層
     */
    @NotNull
    private Integer level;

    /**
     * 組織名
     */
    @NotNull
    private String orgNm;
    /**
     * 機能名
     */
    @NotNull
    private String funNm;

    /**
     * ブランドコード
     */
    @NotNull
    private String brandCd;

    /**
     * 管理フラグ
     */
    @NotNull
    private String mgrFlg;
    /**
     * メンターフラグ
     */
    @NotNull
    private String mentFlg;

    /**
     * 管理分類名
     */
    @NotNull
    private String mgrDivNm;
    /**
     * 機能分類名
     */
    @NotNull
    private String funDivNm;

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
     * 組織ID + 組織名
     */
    private String orgNmDisplay;
    /**
     * 組織区分
     */
    private String orgDiv;

    /**
     * IDを取得する
     *
     * @return funId ID
     */
    public String getFunId() {
        return this.funId;
    }

    /**
     * IDを設定する
     *
     * @param funId ID
     */
    public void setFunId(String funId) {
        this.funId = funId;
    }

    /**
     * 機能名を取得する
     *
     * @return name 機能名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 機能名を設定する
     *
     * @param name 機能名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * タイプを取得する
     *
     * @return type タイプ
     */
    public String getType() {
        return this.type;
    }

    /**
     * タイプを設定する
     *
     * @param type タイプ
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @NotNull IDを取得する
     *
     * @return id @NotNull
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @NotNull IDを設定する
     *
     * @param id @NotNull
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 上層組織IDを取得する
     *
     * @return upplevOrgId 上層組織ID
     */
    public String getUpplevOrgId() {
        return this.upplevOrgId;
    }

    /**
     * 上層組織IDを設定する
     *
     * @param upplevOrgId 上層組織ID
     */
    public void setUpplevOrgId(String upplevOrgId) {
        this.upplevOrgId = upplevOrgId;
    }

    /**
     * 階層を取得する
     *
     * @return level 階層
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 階層を設定する
     *
     * @param level 階層
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * ブランドコードを取得する
     *
     * @return brandCd ブランドコード
     */
    public String getBrandCd() {
        return this.brandCd;
    }

    /**
     * ブランドコードを設定する
     *
     * @param brandCd ブランドコード
     */
    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }

    /**
     * 管理フラグを取得する
     *
     * @return mgrFlg 管理フラグ
     */
    public String getMgrFlg() {
        return this.mgrFlg;
    }

    /**
     * 管理フラグを設定する
     *
     * @param mgrFlg 管理フラグ
     */
    public void setMgrFlg(String mgrFlg) {
        this.mgrFlg = mgrFlg;
    }

    /**
     * 作成日時を取得する
     *
     * @return cretDatime 作成日時
     */
    public Timestamp getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 作成日時を設定する
     *
     * @param cretDatime 作成日時
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 作成ユーザＩＤを取得する
     *
     * @return cretUsrId 作成ユーザＩＤ
     */
    public String getCretUsrId() {
        return this.cretUsrId;
    }

    /**
     * 作成ユーザＩＤを設定する
     *
     * @param cretUsrId 作成ユーザＩＤ
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 更新日時を取得する
     *
     * @return updDatime 更新日時
     */
    public Timestamp getUpdDatime() {
        return this.updDatime;
    }

    /**
     * 更新日時を設定する
     *
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 更新ユーザＩＤを取得する
     *
     * @return updUsrId 更新ユーザＩＤ
     */
    public String getUpdUsrId() {
        return this.updUsrId;
    }

    /**
     * 更新ユーザＩＤを設定する
     *
     * @param updUsrId 更新ユーザＩＤ
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * 削除フラグを取得する
     *
     * @return delFlg 削除フラグ
     */
    public Integer getDelFlg() {
        return this.delFlg;
    }

    /**
     * 削除フラグを設定する
     *
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * 組織ID + 組織名を取得する
     *
     * @return orgNmDisplay 組織ID + 組織名
     */
    public String getOrgNmDisplay() {
        return this.orgNmDisplay;
    }

    /**
     * 組織ID + 組織名を設定する
     *
     * @param orgNmDisplay 組織ID + 組織名
     */
    public void setOrgNmDisplay(String orgNmDisplay) {
        this.orgNmDisplay = orgNmDisplay;
    }

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
     * 機能名を取得する
     *
     * @return funNm 機能名
     */
    public String getFunNm() {
        return this.funNm;
    }

    /**
     * 機能名を設定する
     *
     * @param funNm 機能名
     */
    public void setFunNm(String funNm) {
        this.funNm = funNm;
    }

    /**
     * メンターフラグを取得する
     *
     * @return mentFlg メンターフラグ
     */
    public String getMentFlg() {
        return this.mentFlg;
    }

    /**
     * メンターフラグを設定する
     *
     * @param mentFlg メンターフラグ
     */
    public void setMentFlg(String mentFlg) {
        this.mentFlg = mentFlg;
    }

    /**
     * 管理分類名を取得する
     *
     * @return mgrDivNm 管理分類名
     */
    public String getMgrDivNm() {
        return this.mgrDivNm;
    }

    /**
     * 管理分類名を設定する
     *
     * @param mgrDivNm 管理分類名
     */
    public void setMgrDivNm(String mgrDivNm) {
        this.mgrDivNm = mgrDivNm;
    }

    /**
     * 機能分類名を取得する
     *
     * @return funDivNm 機能分類名
     */
    public String getFunDivNm() {
        return this.funDivNm;
    }

    /**
     * 機能分類名を設定する
     *
     * @param funDivNm 機能分類名
     */
    public void setFunDivNm(String funDivNm) {
        this.funDivNm = funDivNm;
    }
}
