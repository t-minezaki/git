/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;

/**
 * <p>F10303_復習教科選択画面(POP) Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/26 : gong: 新規<br />
 * @version 1.0
 */
public class F10303Dto {

    /**
     * 単元ID
     */
    private Integer unitId;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * ブロック種類区分
     */
    private String blockTypeDiv;

    /**
     * 計画年月日Str
     */
    private String planYmdStr;

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
     * 計画学習開始時間
     */
    private String planLearnStartTime;

    /**
     * 単元IDを設定する
     *
     * @return unitId 単元ID
     */
    public Integer getUnitId() {
        return this.unitId;
    }

    /**
     * 単元IDを取得する
     *
     * @param unitId 単元ID
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
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
     * 教科区分を設定する
     *
     * @return subjtDiv 教科区分
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科区分を取得する
     *
     * @param subjtDiv 教科区分
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 教科を設定する
     *
     * @return subjtNm 教科
     */
    public String getSubjtNm() {
        return this.subjtNm;
    }

    /**
     * 教科を取得する
     *
     * @param subjtNm 教科
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
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
