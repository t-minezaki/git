/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.learningpark.framework.utils.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 塾学習期間マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_crmsch_learn_prd")
public class MstCrmschLearnPrdEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 時期名
     */
    @NotNull
    private String prdNm;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 学年区分
     */
    @NotNull
    private String schyDiv;

    /**
     * 計画期間開始日
     */
    @NotNull
    private Date planPrdStartDy;

    /**
     * 計画期間終了日
     */
    private Date planPrdEndDy;

    /**
     * 適用フラグ
     */
    @NotNull
    private String useFlg;

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
     * 時期名を設定する
     */
    public void setPrdNm(String prdNm) {
        this.prdNm = prdNm;
    }
    
    /**
     * 時期名を取得する
     */
    public String getPrdNm() {
        return prdNm;
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
     * 学年区分を設定する
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }
    
    /**
     * 学年区分を取得する
     */
    public String getSchyDiv() {
        return StringUtils.trim(schyDiv);
    }
    /**
     * 計画期間開始日を設定する
     */
    public void setPlanPrdStartDy(Date planPrdStartDy) {
        this.planPrdStartDy = planPrdStartDy;
    }
    
    /**
     * 計画期間開始日を取得する
     */
    public Date getPlanPrdStartDy() {
        return planPrdStartDy;
    }
    /**
     * 計画期間終了日を設定する
     */
    public void setPlanPrdEndDy(Date planPrdEndDy) {
        this.planPrdEndDy = planPrdEndDy;
    }
    
    /**
     * 計画期間終了日を取得する
     */
    public Date getPlanPrdEndDy() {
        return planPrdEndDy;
    }
    /**
     * 適用フラグを設定する
     */
    public void setUseFlg(String useFlg) {
        this.useFlg = useFlg;
    }
    
    /**
     * 適用フラグを取得する
     */
    public String getUseFlg() {
        return useFlg;
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
}
