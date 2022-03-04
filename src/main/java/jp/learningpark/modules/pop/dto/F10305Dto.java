/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;

/**
 * <p>教科書デフォルトターム情報 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public class F10305Dto extends TextbDefTimeInfoEntity {
    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;

    /**
     * 計画年月日Str
     */
    private String planYmdStr;

    /**
     * 計画学習開始時間
     */
    private String planLearnStartTime;

    /**
     * 生徒計画学習時間（分）
     */
    private Integer stuPlanLearnTm;

    /**
     * 教科
     */
    private String subjt;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 教科を設定する
     *
     * @return subjt 教科
     */
    public String getSubjt() {
        return this.subjt;
    }

    /**
     * 教科を取得する
     *
     * @param subjt 教科
     */
    public void setSubjt(String subjt) {
        this.subjt = subjt;
    }

    /**
     * ブロック表示名を設定する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return this.blockDispyNm;
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
     * 計画年月日Strを設定する
     *
     * @return planYmdStr 計画年月日Str
     */
    public String getPlanYmdStr() {
        return this.planYmdStr;
    }

    /**
     * 計画年月日Strを取得する
     *
     * @param planYmdStr 計画年月日Str
     */
    public void setPlanYmdStr(String planYmdStr) {
        this.planYmdStr = planYmdStr;
    }

    /**
     * 生徒計画学習時間（分）を設定する
     *
     * @return stuPlanLearnTm 生徒計画学習時間（分）
     */
    public Integer getStuPlanLearnTm() {
        return this.stuPlanLearnTm;
    }

    /**
     * 生徒計画学習時間（分）を取得する
     *
     * @param stuPlanLearnTm 生徒計画学習時間（分）
     */
    public void setStuPlanLearnTm(Integer stuPlanLearnTm) {
        this.stuPlanLearnTm = stuPlanLearnTm;
    }

    /**
     * 計画学習開始時間を設定する
     *
     * @return planLearnStartTime 計画学習開始時間
     */
    public String getPlanLearnStartTime() {
        return this.planLearnStartTime;
    }

    /**
     * 計画学習開始時間を取得する
     *
     * @param planLearnStartTime 計画学習開始時間
     */
    public void setPlanLearnStartTime(String planLearnStartTime) {
        this.planLearnStartTime = planLearnStartTime;
    }
}
