/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>F20014_積み残し設定画面(POP) Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
public class F20014Dto {

    /**
     * 生徒ウィークリー計画実績設定(※)Id
     */
    private Integer weeklyId;

    /**
     * 生徒タームプラン設定(※)Id
     */
    private Integer termPlanId;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 教科
     */
    private String subjtNm;

    /**
     * 計画年月日
     */
    private Date planYmd;

    /**
     * 学習理解度
     */
    private String learnLevUnds;

    /**
     * 学習理解度の表示
     */
    private String learnLevUndsDis;

    /**
     * 生徒計画学習時間（分）
     */
    private Integer stuPlanLearnTm;

    /**
     * 計画学習開始時間
     */
    private Timestamp planLearnStartTime;

    /**
     * 教科書ページ
     */
    private String textbPage;

    /**
     * 単元表示名
     */
    private String unitDispyNm;

    /**
     * 実績学習時間（分）
     */
    private Integer perfLearnTm;

    /**
     * 積み残し対象フラグ
     */
    private String remainDispFlg;

    /**
     * 計画登録フラグ
     */
    private String planRegFlg;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;


    /**
     * 生徒ウィークリー計画実績設定(※)Idを取得する
     *
     * @return weeklyId 生徒ウィークリー計画実績設定(※)Id
     */
    public Integer getWeeklyId() {
        return this.weeklyId;
    }

    /**
     * 生徒ウィークリー計画実績設定(※)Idを設定する
     *
     * @param weeklyId 生徒ウィークリー計画実績設定(※)Id
     */
    public void setWeeklyId(Integer weeklyId) {
        this.weeklyId = weeklyId;
    }

    /**
     * 生徒タームプラン設定(※)Idを取得する
     *
     * @return termPlanId 生徒タームプラン設定(※)Id
     */
    public Integer getTermPlanId() {
        return this.termPlanId;
    }

    /**
     * 生徒タームプラン設定(※)Idを設定する
     *
     * @param termPlanId 生徒タームプラン設定(※)Id
     */
    public void setTermPlanId(Integer termPlanId) {
        this.termPlanId = termPlanId;
    }

    /**
     * ブロック表示名を取得する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return this.blockDispyNm;
    }

    /**
     * ブロック表示名を設定する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * 教科を取得する
     *
     * @return subjtNm 教科
     */
    public String getSubjtNm() {
        return this.subjtNm;
    }

    /**
     * 教科を設定する
     *
     * @param subjtNm 教科
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }

    /**
     * 計画年月日を取得する
     *
     * @return planYmd 計画年月日
     */
    public Date getPlanYmd() {
        return this.planYmd;
    }

    /**
     * 計画年月日を設定する
     *
     * @param planYmd 計画年月日
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }

    /**
     * 学習理解度を取得する
     *
     * @return learnLevUnds 学習理解度
     */
    public String getLearnLevUnds() {
        return this.learnLevUnds;
    }

    /**
     * 学習理解度を設定する
     *
     * @param learnLevUnds 学習理解度
     */
    public void setLearnLevUnds(String learnLevUnds) {
        this.learnLevUnds = learnLevUnds;
    }

    /**
     * 学習理解度の表示を取得する
     *
     * @return learnLevUndsDis 学習理解度の表示
     */
    public String getLearnLevUndsDis() {
        return this.learnLevUndsDis;
    }

    /**
     * 学習理解度の表示を設定する
     *
     * @param learnLevUndsDis 学習理解度の表示
     */
    public void setLearnLevUndsDis(String learnLevUndsDis) {
        this.learnLevUndsDis = learnLevUndsDis;
    }

    /**
     * 生徒計画学習時間（分）を取得する
     *
     * @return stuPlanLearnTm 生徒計画学習時間（分）
     */
    public Integer getStuPlanLearnTm() {
        return this.stuPlanLearnTm;
    }

    /**
     * 生徒計画学習時間（分）を設定する
     *
     * @param stuPlanLearnTm 生徒計画学習時間（分）
     */
    public void setStuPlanLearnTm(Integer stuPlanLearnTm) {
        this.stuPlanLearnTm = stuPlanLearnTm;
    }

    /**
     * 計画学習開始時間を取得する
     *
     * @return planLearnStartTime 計画学習開始時間
     */
    public Timestamp getPlanLearnStartTime() {
        return this.planLearnStartTime;
    }

    /**
     * 計画学習開始時間を設定する
     *
     * @param planLearnStartTime 計画学習開始時間
     */
    public void setPlanLearnStartTime(Timestamp planLearnStartTime) {
        this.planLearnStartTime = planLearnStartTime;
    }

    /**
     * 教科書ページを取得する
     *
     * @return textbPage 教科書ページ
     */
    public String getTextbPage() {
        return this.textbPage;
    }

    /**
     * 教科書ページを設定する
     *
     * @param textbPage 教科書ページ
     */
    public void setTextbPage(String textbPage) {
        this.textbPage = textbPage;
    }

    /**
     * 単元表示名を取得する
     *
     * @return unitDispyNm 単元表示名
     */
    public String getUnitDispyNm() {
        return this.unitDispyNm;
    }

    /**
     * 単元表示名を設定する
     *
     * @param unitDispyNm 単元表示名
     */
    public void setUnitDispyNm(String unitDispyNm) {
        this.unitDispyNm = unitDispyNm;
    }

    /**
     * 実績学習時間（分）を取得する
     *
     * @return perfLearnTm 実績学習時間（分）
     */
    public Integer getPerfLearnTm() {
        return this.perfLearnTm;
    }

    /**
     * 実績学習時間（分）を設定する
     *
     * @param perfLearnTm 実績学習時間（分）
     */
    public void setPerfLearnTm(Integer perfLearnTm) {
        this.perfLearnTm = perfLearnTm;
    }

    /**
     * 積み残し対象フラグを取得する
     *
     * @return remainDispFlg 積み残し対象フラグ
     */
    public String getRemainDispFlg() {
        return this.remainDispFlg;
    }

    /**
     * 積み残し対象フラグを設定する
     *
     * @param remainDispFlg 積み残し対象フラグ
     */
    public void setRemainDispFlg(String remainDispFlg) {
        this.remainDispFlg = remainDispFlg;
    }

    /**
     * 計画登録フラグを取得する
     *
     * @return planRegFlg 計画登録フラグ
     */
    public String getPlanRegFlg() {
        return this.planRegFlg;
    }

    /**
     * 計画登録フラグを設定する
     *
     * @param planRegFlg 計画登録フラグ
     */
    public void setPlanRegFlg(String planRegFlg) {
        this.planRegFlg = planRegFlg;
    }

    /**
     * 計画学習時間（分）を取得する
     *
     * @return planLearnTm 計画学習時間（分）
     */
    public Integer getPlanLearnTm() {
        return this.planLearnTm;
    }

    /**
     * 計画学習時間（分）を設定する
     *
     * @param planLearnTm 計画学習時間（分）
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }

    /**
     * ブロック種類区分を取得する
     *
     * @return blockTypeDiv ブロック種類区分
     */
    public String getBlockTypeDiv() {
        return this.blockTypeDiv;
    }

    /**
     * ブロック種類区分を設定する
     *
     * @param blockTypeDiv ブロック種類区分
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }
}
