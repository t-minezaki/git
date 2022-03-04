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
 * 生徒タームプラン設定
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_term_plan")
public class StuTermPlanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 単元ID
     */
    @NotNull
    private Integer unitId;

    /**
     * 教科書デフォルト単元ID
     */
    @NotNull
    private Integer textbDefUnitId;

    /**
     * 枝番
     */
    @NotNull
    private Integer bnum;

    /**
     * 計画学習時期ID
     */
    @NotNull
    private Integer planLearnSeasnId;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * 計画登録フラグ
     */
    @NotNull
    private String planRegFlg;

    /**
     * 表示順番
     */
    @NotNull
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
     * 生徒IDを設定する
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    
    /**
     * 生徒IDを取得する
     */
    public String getStuId() {
        return stuId;
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
     * 教科書デフォルト単元IDを設定する
     */
    public void setTextbDefUnitId(Integer textbDefUnitId) {
        this.textbDefUnitId = textbDefUnitId;
    }
    
    /**
     * 教科書デフォルト単元IDを取得する
     */
    public Integer getTextbDefUnitId() {
        return textbDefUnitId;
    }
    /**
     * 枝番を設定する
     */
    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }
    
    /**
     * 枝番を取得する
     */
    public Integer getBnum() {
        return bnum;
    }
    /**
     * 計画学習時期IDを設定する
     */
    public void setPlanLearnSeasnId(Integer planLearnSeasnId) {
        this.planLearnSeasnId = planLearnSeasnId;
    }
    
    /**
     * 計画学習時期IDを取得する
     */
    public Integer getPlanLearnSeasnId() {
        return planLearnSeasnId;
    }
    /**
     * ブロック表示名を設定する
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }
    
    /**
     * ブロック表示名を取得する
     */
    public String getBlockDispyNm() {
        return blockDispyNm;
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
     * 計画登録フラグを設定する
     */
    public void setPlanRegFlg(String planRegFlg) {
        this.planRegFlg = planRegFlg;
    }
    
    /**
     * 計画登録フラグを取得する
     */
    public String getPlanRegFlg() {
        return planRegFlg;
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
