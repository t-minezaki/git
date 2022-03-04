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
import java.util.Date;

/**
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("guid_repr_deliver")
public class GuidReprDeliverEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 指導報告書配信コード
     */
    @NotNull
    private String guidReprDeliverCd;

    /**
     * 対象年月日
     */
    @NotNull
    private Date tgtYmd;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 公開開始日時
     */
    private Timestamp pubStartDt;

    /**
     * 公開終了日時
     */
    private Timestamp pubEndDt;

    /**
     * 指導報告書ステータス区分
     */
    private String eventStsDiv;

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
     * 指導報告書配信コードを設定する
     */
    public void setGuidReprDeliverCd(String guidReprDeliverCd) {
        this.guidReprDeliverCd = guidReprDeliverCd;
    }
    
    /**
     * 指導報告書配信コードを取得する
     */
    public String getGuidReprDeliverCd() {
        return guidReprDeliverCd;
    }
    /**
     * 対象年月日を設定する
     */
    public void setTgtYmd(Date tgtYmd) {
        this.tgtYmd = tgtYmd;
    }
    
    /**
     * 対象年月日を取得する
     */
    public Date getTgtYmd() {
        return tgtYmd;
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
     * 公開開始日時を設定する
     */
    public void setPubStartDt(Timestamp pubStartDt) {
        this.pubStartDt = pubStartDt;
    }
    
    /**
     * 公開開始日時を取得する
     */
    public Timestamp getPubStartDt() {
        return pubStartDt;
    }
    /**
     * 公開終了日時を設定する
     */
    public void setPubEndDt(Timestamp pubEndDt) {
        this.pubEndDt = pubEndDt;
    }
    
    /**
     * 公開終了日時を取得する
     */
    public Timestamp getPubEndDt() {
        return pubEndDt;
    }
    /**
     * 指導報告書ステータス区分を設定する
     */
    public void setEventStsDiv(String eventStsDiv) {
        this.eventStsDiv = eventStsDiv;
    }
    
    /**
     * 指導報告書ステータス区分を取得する
     */
    public String getEventStsDiv() {
        return eventStsDiv;
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
