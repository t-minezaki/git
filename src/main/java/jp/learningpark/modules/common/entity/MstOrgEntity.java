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
import java.util.Objects;

/**
 * 組織マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_org")
public class MstOrgEntity implements Serializable, Comparable<MstOrgEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
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
     * 他システム区分
     */
    private String systemKbn;

    /**
     * Web申込利用フラグ
     */
    private String webUseFlg;

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
     * 所属区分
     */
    private String ownerKbn;

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
     * 上層組織IDを設定する
     */
    public void setUpplevOrgId(String upplevOrgId) {
        this.upplevOrgId = upplevOrgId;
    }
    
    /**
     * 上層組織IDを取得する
     */
    public String getUpplevOrgId() {
        return upplevOrgId;
    }
    /**
     * 階層を設定する
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    /**
     * 階層を取得する
     */
    public Integer getLevel() {
        return level;
    }
    /**
     * 組織名を設定する
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }
    
    /**
     * 組織名を取得する
     */
    public String getOrgNm() {
        return orgNm;
    }
    /**
     * ブランドコードを設定する
     */
    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }
    
    /**
     * ブランドコードを取得する
     */
    public String getBrandCd() {
        return brandCd;
    }
    /**
     * 管理フラグを設定する
     */
    public void setMgrFlg(String mgrFlg) {
        this.mgrFlg = mgrFlg;
    }
    
    /**
     * 管理フラグを取得する
     */
    public String getMgrFlg() {
        return mgrFlg;
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
     * Web申込利用フラグを取得する
     *
     * @return webUseFlg Web申込利用フラグ
     */
    public String getWebUseFlg() {
        return this.webUseFlg;
    }

    /**
     * Web申込利用フラグを設定する
     *
     * @param webUseFlg Web申込利用フラグ
     */
    public void setWebUseFlg(String webUseFlg) {
        this.webUseFlg = webUseFlg;
    }

    /**
     * 所属区分を取得する
     *
     * @return ownerKbn 所属区分
     */
    public String getOwnerKbn() {
        return this.ownerKbn;
    }

    /**
     * 所属区分を設定する
     *
     * @param ownerKbn 所属区分
     */
    public void setOwnerKbn(String ownerKbn) {
        this.ownerKbn = ownerKbn;
    }

    @Override
    public int compareTo(MstOrgEntity target) {
        if(Objects.equals(level, target.getLevel())){
            return 0;
        }

        return level > target.getLevel() ? -1 : 1;
    }

    /**
     * 組織IDは、組織が等しいかどうかを判断できます
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MstOrgEntity)) return false;
        MstOrgEntity that = (MstOrgEntity) o;
        return Objects.equals(orgId, that.orgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId);
    }
}
