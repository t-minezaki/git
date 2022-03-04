/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

/**
 * <p>学習状況分析画面(F10401,F20008,F30104)</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/08 : hujunjie: 新規<br />
 * @version 1.0
 */
public class LearningSituationAnalysisForm {
    /**
     * 教科名（コード値）
     */
    private String subjtVal;

    /**
     * 学習理解度（コード値）
     */
    private String stuLearnLevUndsCd;

    /**
     * ブロック数
     */
    private String countId;

    /**
     * 計画学習時間（分）
     */
    private int planlearnTmSum;

    /**
     * 生徒計画学習時間（分）和
     */
    private int stuPlanLearnTmSum;

    /**
     * 実績学習時間（分）和
     */
    private int perfLearnTmSum;

    /**
     * 円中心数字
     */
    private double enCenterNmb;

    /**
     * 教科名（コード値）を取得する
     *
     * @return subjtVal 教科名（コード値）
     */
    public String getSubjtVal() {
        return subjtVal;
    }

    /**
     * 教科名（コード値）を設定する
     *
     * @param subjtVal 教科名（コード値）
     */
    public void setSubjtVal(String subjtVal) {
        this.subjtVal = subjtVal;
    }

    /**
     * 学習理解度（コード値）を取得する
     *
     * @return stuLearnLevUndsCd 学習理解度（コード値）
     */
    public String getStuLearnLevUndsCd() {
        return stuLearnLevUndsCd;
    }

    /**
     * 学習理解度（コード値）を設定する
     *
     * @param stuLearnLevUndsCd 学習理解度（コード値）
     */
    public void setStuLearnLevUndsCd(String stuLearnLevUndsCd) {
        this.stuLearnLevUndsCd = stuLearnLevUndsCd;
    }

    /**
     * countIdを取得する
     *
     * @return countId ブロック数
     */
    public String getCountId() {
        return countId;
    }

    /**
     * countIdを設定する
     *
     * @param countId ブロック数
     */
    public void setCountId(String countId) {
        this.countId = countId;
    }

    /**
     * planlearnTmSumを取得する
     *
     * @return planlearnTmSum 計画学習時間（分）
     */
    public int getPlanlearnTmSum() {
        return planlearnTmSum;
    }

    /**
     * planlearnTmSumを設定する
     *
     * @param planlearnTmSum 計画学習時間（分）
     */
    public void setPlanlearnTmSum(int planlearnTmSum) {
        this.planlearnTmSum = planlearnTmSum;
    }

    /**
     * stuPlanLearnTmSumを取得する
     *
     * @return stuPlanLearnTmSum 生徒計画学習時間（分）和
     */
    public int getStuPlanLearnTmSum() {
        return stuPlanLearnTmSum;
    }

    /**
     * stuPlanLearnTmSumを設定する
     *
     * @param stuPlanLearnTmSum 生徒計画学習時間（分）和
     */
    public void setStuPlanLearnTmSum(int stuPlanLearnTmSum) {
        this.stuPlanLearnTmSum = stuPlanLearnTmSum;
    }

    /**
     * perfLearnTmSumを取得する
     *
     * @return perfLearnTmSum 実績学習時間（分）和
     */
    public int getPerfLearnTmSum() {
        return perfLearnTmSum;
    }

    /**
     * perfLearnTmSumを設定する
     *
     * @param perfLearnTmSum 実績学習時間（分）和
     */
    public void setPerfLearnTmSum(int perfLearnTmSum) {
        this.perfLearnTmSum = perfLearnTmSum;
    }

    /**
     * enCenterNmbを取得する
     *
     * @return enCenterNmb 円中心数字
     */
    public double getEnCenterNmb() {
        return enCenterNmb;
    }

    /**
     * enCenterNmbを設定する
     *
     * @param enCenterNmb 円中心数字
     */
    public void setEnCenterNmb(double enCenterNmb) {
        this.enCenterNmb = enCenterNmb;
    }
}
