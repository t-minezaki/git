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
 * 生徒ウィークリー計画実績設定
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_weekly_plan_perf")
public class StuWeeklyPlanPerfEntity implements Serializable {
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
    private Integer stuTermPlanId;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * ブロック種類区分
     */
    @NotNull
    private String blockTypeDiv;

    /**
     * 計画年月日
     */
    @NotNull
    private Date planYmd;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 教科
     */
    private String subjtNm;

    /**
     * 生徒計画学習時間（分）
     */
    private Integer stuPlanLearnTm;

    /**
     * 計画学習時期ID
     */
    private Integer planLearnSeasnId;

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
     * 学習理解度
     */
    private String learnLevUnds;

    /**
     * 積み残し対象フラグ
     */
    private String remainDispFlg;

    /**
     * タイマー時間（秒）
     */
    private Integer timerTm;

    /**
     * 生徒削除フラグ
     */
    @NotNull
    private String stuDelFlg;

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
     * ブロック種類区分を設定する
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }
    
    /**
     * ブロック種類区分を取得する
     */
    public String getBlockTypeDiv() {
        return blockTypeDiv;
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
     * 教科を設定する
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }
    
    /**
     * 教科を取得する
     */
    public String getSubjtNm() {
        return subjtNm;
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
     * 積み残し対象フラグを設定する
     */
    public void setRemainDispFlg(String remainDispFlg) {
        this.remainDispFlg = remainDispFlg;
    }
    
    /**
     * 積み残し対象フラグを取得する
     */
    public String getRemainDispFlg() {
        return remainDispFlg;
    }
    /**
     * 生徒削除フラグを設定する
     */
    public void setStuDelFlg(String stuDelFlg) {
        this.stuDelFlg = stuDelFlg;
    }
    
    /**
     * 生徒削除フラグを取得する
     */
    public String getStuDelFlg() {
        return stuDelFlg;
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

    /**
     * タイマー時間（秒）を取得する
     *
     * @return timerTm タイマー時間（秒）
     */
    public Integer getTimerTm() {
        return this.timerTm;
    }

    /**
     * タイマー時間（秒）を設定する
     *
     * @param timerTm タイマー時間（秒）
     */
    public void setTimerTm(Integer timerTm) {
        this.timerTm = timerTm;
    }
}
