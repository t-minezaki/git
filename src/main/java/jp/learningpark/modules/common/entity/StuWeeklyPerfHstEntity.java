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
 * 生徒ウィークリー実績履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_weekly_perf_hst")
public class StuWeeklyPerfHstEntity implements Serializable {
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
     * 生徒タームプラン設定ID
     */
    @NotNull
    private Integer stuTermPlanId;

    /**
     * 履歴番号
     */
    @NotNull
    private Integer hnum;

    /**
     * 計画年月日
     */
    @NotNull
    private Date planYmd;

    /**
     * 計画学習開始時間
     */
    private Timestamp planLearnStartTime;
    
    /**
     * 実績年月日
     */
    private Date perfYmd;
    
    /**
     * 実績学習開始時間
     */
    private Timestamp perfLearnStartTime;

    /**
     * 実績学習時間（分）
     */
    private Integer perfLearnTm;

    /**
     * 計画学習時期ID
     */
    private Integer planLearnSeasnId;

    /**
     * 生徒計画学習時間（分）
     */
    private Integer stuPlanLearnTm;

    /**
     * 学習理解度
     */
    private String learnLevUnds;

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
     * 生徒タームプラン設定IDを設定する
     */
    public void setStuTermPlanId(Integer stuTermPlanId) {
        this.stuTermPlanId = stuTermPlanId;
    }
    
    /**
     * 生徒タームプラン設定IDを取得する
     */
    public Integer getStuTermPlanId() {
        return stuTermPlanId;
    }
    /**
     * 履歴番号を設定する
     */
    public void setHnum(Integer hnum) {
        this.hnum = hnum;
    }
    
    /**
     * 履歴番号を取得する
     */
    public Integer getHnum() {
        return hnum;
    }
    /**
     * 計画年月日を設定する
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }
    
    /**
     * 計画年月日を取得する
     */
    public Date getPlanYmd() {
        return planYmd;
    }
    /**
     * 計画学習開始時間を設定する
     */
    public void setPlanLearnStartTime(Timestamp planLearnStartTime) {
        this.planLearnStartTime = planLearnStartTime;
    }
    
    /**
     * 計画学習開始時間を取得する
     */
    public Timestamp getPlanLearnStartTime() {
        return planLearnStartTime;
    }
    /**
     * 実績学習時間（分）を設定する
     */
    public void setPerfLearnTm(Integer perfLearnTm) {
        this.perfLearnTm = perfLearnTm;
    }
    
    /**
     * 実績学習時間（分）を取得する
     */
    public Integer getPerfLearnTm() {
        return perfLearnTm;
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
     * 生徒計画学習時間（分）を設定する
     */
    public void setStuPlanLearnTm(Integer stuPlanLearnTm) {
        this.stuPlanLearnTm = stuPlanLearnTm;
    }
    
    /**
     * 生徒計画学習時間（分）を取得する
     */
    public Integer getStuPlanLearnTm() {
        return stuPlanLearnTm;
    }
    /**
     * 学習理解度を設定する
     */
    public void setLearnLevUnds(String learnLevUnds) {
        this.learnLevUnds = learnLevUnds;
    }
    
    /**
     * 学習理解度を取得する
     */
    public String getLearnLevUnds() {
        return learnLevUnds;
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
     * 実績年月日を取得する
     */
    public Date getPerfYmd() {
        return perfYmd;
    }
    /**
     * 実績年月日を設定する
     */
    public void setPerfYmd(Date perfYmd) {
        this.perfYmd = perfYmd;
    }
    /**
     * 実績学習開始時間を取得する
     */
    public Timestamp getPerfLearnStartTime() {
        return perfLearnStartTime;
    }
    /**
     * 実績学習開始時間を設定する
     */
    public void setPerfLearnStartTime(Timestamp perfLearnStartTime) {
        this.perfLearnStartTime = perfLearnStartTime;
    }
}
