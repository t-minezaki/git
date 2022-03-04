/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import java.util.Date;

/**
 * <p>印刷ブロック(R10001印刷用) DTO。</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/10 : yangfan: 新規<br />
 * @version 1.0
 */
public class F1030105Dto {

    /**
     * 計画学習日
     */
    private Date planYmd;

    /**
     * 計画学習開始時間
     */
    private String startTime;

    /**
     * 計画学習終了時間
     */
    private String endTime;

    /**
     * 学習開始時間 障害票3.5 no21
     */
    private String checkStartTime;

    /**
     * 学習終了時間 障害票3.5 no21
     */
    private String checkEndTime;

    /**
     * 科目
     */
    private String subjtNm;

    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 単元名
     */
    private String unitNm;
    
    /**
     * 教科書ページ
     */
    private String textbPage;
    
    /**
     * 生徒計画学習時間（分）
     */
    private String stuPlanLearnTm;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;
    
    /**
     * ブロック種類区分
     * C1  通常
     * O1  その他「趣味」
     * O2  その他「習い事」
     * O3  その他「その他」
     * P1  予習
     * R1  復習
     * S1  学習
     */
    private String blockTypeDiv;

    /**
     * 計画学習日を設定する
     *
     * @return planYmd 計画学習日
     */
    public Date getPlanYmd() {
        return this.planYmd;
    }

    /**
     * 計画学習日を取得する
     *
     * @param planYmd 計画学習日
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }

    /**
     * 計画学習開始時間を設定する
     *
     * @return startTime 計画学習開始時間
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 計画学習開始時間を取得する
     *
     * @param startTime 計画学習開始時間
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 計画学習終了時間を設定する
     *
     * @return endTime 計画学習終了時間
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 計画学習終了時間を取得する
     *
     * @param endTime 計画学習終了時間
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 科目を設定する
     *
     * @return subjtNm 科目
     */
    public String getSubjtNm() {
        return this.subjtNm;
    }

    /**
     * 科目を取得する
     *
     * @param subjtNm 科目
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }

    /**
     * 章名を設定する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return this.chaptNm;
    }

    /**
     * 章名を取得する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * 単元名を設定する
     *
     * @return unitNm 単元名
     */
    public String getUnitNm() {
        return this.unitNm;
    }

    /**
     * 単元名を取得する
     *
     * @param unitNm 単元名
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }

    /**
     * 教科書ページを設定する
     *
     * @return textbPage 教科書ページ
     */
    public String getTextbPage() {
        return this.textbPage;
    }

    /**
     * 教科書ページを取得する
     *
     * @param textbPage 教科書ページ
     */
    public void setTextbPage(String textbPage) {
        this.textbPage = textbPage;
    }

    /**
     * 生徒計画学習時間（分）を設定する
     *
     * @return stuPlanLearnTm 生徒計画学習時間（分）
     */
    public String getStuPlanLearnTm() {
        return this.stuPlanLearnTm;
    }

    /**
     * 生徒計画学習時間（分）を取得する
     *
     * @param stuPlanLearnTm 生徒計画学習時間（分）
     */
    public void setStuPlanLearnTm(String stuPlanLearnTm) {
        this.stuPlanLearnTm = stuPlanLearnTm;
    }

    /**
     * ブロック表示名を設定する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return blockDispyNm;
    }

    /**
     * ブロック表示名を取得する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }
    
    /**
     * ブロック種類区分を設定する
     *
     * @return blockTypeDiv ブロック種類区分
     */
    public String getBlockTypeDiv() {
        return this.blockTypeDiv;
    }

    /**
     * ブロック種類区分を取得する
     *
     * @param blockTypeDiv ブロック種類区分
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }

    /**
     * 学習開始時間を取得する
     *
     * @return checkStartTime 学習開始時間
     */
    public String getCheckStartTime() {
        return this.checkStartTime;
    }

    /**
     * 学習開始時間を設定する
     *
     * @param checkStartTime 学習開始時間
     */
    public void setCheckStartTime(String checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    /**
     * 学習終了時間を取得する
     *
     * @return checkEndTime 学習終了時間
     */
    public String getCheckEndTime() {
        return this.checkEndTime;
    }

    /**
     * 学習終了時間を設定する
     *
     * @param checkEndTime 学習終了時間
     */
    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }
}
