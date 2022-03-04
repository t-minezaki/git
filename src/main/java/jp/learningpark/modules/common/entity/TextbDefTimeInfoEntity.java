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
 * 教科書デフォルトターム情報
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("textb_def_time_info")
public class TextbDefTimeInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 教科書ID
     */
    @NotNull
    private Integer textbId;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 単元ID
     */
    @NotNull
    private Integer unitId;

    /**
     * 単元表示名
     */
    private String unitDispyNm;

    /**
     * 単元NO
     */
    private String unitNo;

    /**
     * 節NO
     */
    private String sectnNo;

    /**
     * 章NO
     */
    private String chaptNo;

    /**
     * 教科書ページ
     */
    private String textbPage;

    /**
     * 計画学習時期
     */
    private Integer planLearnSeasn;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * 表示順番
     */
    private Integer dispyOrder;

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
     * 教科書IDを設定する
     */
    public void setTextbId(Integer textbId) {
        this.textbId = textbId;
    }
    
    /**
     * 教科書IDを取得する
     */
    public Integer getTextbId() {
        return textbId;
    }
    /**
     * 教科区分を設定する
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }
    
    /**
     * 教科区分を取得する
     */
    public String getSubjtDiv() {
        return subjtDiv;
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
     * 単元IDを設定する
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
    
    /**
     * 単元IDを取得する
     */
    public Integer getUnitId() {
        return unitId;
    }
    /**
     * 単元表示名を設定する
     */
    public void setUnitDispyNm(String unitDispyNm) {
        this.unitDispyNm = unitDispyNm;
    }
    
    /**
     * 単元表示名を取得する
     */
    public String getUnitDispyNm() {
        return unitDispyNm;
    }
    /**
     * 単元NOを設定する
     */
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
    
    /**
     * 単元NOを取得する
     */
    public String getUnitNo() {
        return unitNo;
    }
    /**
     * 節NOを設定する
     */
    public void setSectnNo(String sectnNo) {
        this.sectnNo = sectnNo;
    }
    
    /**
     * 節NOを取得する
     */
    public String getSectnNo() {
        return sectnNo;
    }
    /**
     * 章NOを設定する
     */
    public void setChaptNo(String chaptNo) {
        this.chaptNo = chaptNo;
    }
    
    /**
     * 章NOを取得する
     */
    public String getChaptNo() {
        return chaptNo;
    }
    /**
     * 教科書ページを設定する
     */
    public void setTextbPage(String textbPage) {
        this.textbPage = textbPage;
    }
    
    /**
     * 教科書ページを取得する
     */
    public String getTextbPage() {
        return textbPage;
    }
    /**
     * 計画学習時期を設定する
     */
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }
    
    /**
     * 計画学習時期を取得する
     */
    public Integer getPlanLearnSeasn() {
        return planLearnSeasn;
    }
    /**
     * 計画学習時間（分）を設定する
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }
    
    /**
     * 計画学習時間（分）を取得する
     */
    public Integer getPlanLearnTm() {
        return planLearnTm;
    }
    /**
     * 表示順番を設定する
     */
    public void setDispyOrder(Integer dispyOrder) {
        this.dispyOrder = dispyOrder;
    }
    
    /**
     * 表示順番を取得する
     */
    public Integer getDispyOrder() {
        return dispyOrder;
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
