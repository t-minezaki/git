/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

/**
 * <p>定期テスト科目別成績詳細画面(F20011,F30107)</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/08 : hujunjie: 新規<br />
 * @version 1.0
 */
public class RegularTestSubjectDivGradeDetailedForm {
    /**
     * テスト種別（コード値）
     */
    private String testKindVal;

    /**
     * 教科（コード値）
     */
    private String subjtVal;

    /**
     * 総合目標
     */
    private double goalAvg;

    /**
     * 総合点数
     */
    private double rpAvg;

    /**
     * 総合平均点
     */
    private double resultAvg;

    /**
     * testKindValを取得する
     *
     * @return testKindVal テスト種別（コード値）
     */
    public String getTestKindVal() {
        return testKindVal;
    }

    /**
     * testKindValを設定する
     *
     * @param testKindVal テスト種別（コード値）
     */
    public void setTestKindVal(String testKindVal) {
        this.testKindVal = testKindVal;
    }

    /**
     * subjtValを取得する
     *
     * @return subjtVal 教科
     */
    public String getSubjtVal() {
        return subjtVal;
    }

    /**
     * subjtValを設定する
     *
     * @param subjtVal 教科
     */
    public void setSubjtVal(String subjtVal) {
        this.subjtVal = subjtVal;
    }

    /**
     * goalAvgを取得する
     *
     * @return goalAvg 総合目標
     */
    public double getGoalAvg() {
        return goalAvg;
    }

    /**
     * goalAvgを設定する
     *
     * @param goalAvg 総合目標
     */
    public void setGoalAvg(double goalAvg) {
        this.goalAvg = goalAvg;
    }

    /**
     * rpAvgを取得する
     *
     * @return rpAvg 総合点数
     */
    public double getRpAvg() {
        return rpAvg;
    }

    /**
     * rpAvgを設定する
     *
     * @param rpAvg 総合点数
     */
    public void setRpAvg(double rpAvg) {
        this.rpAvg = rpAvg;
    }

    /**
     * resultAvgを取得する
     *
     * @return resultAvg 総合平均点
     */
    public double getResultAvg() {
        return resultAvg;
    }

    /**
     * resultAvgを設定する
     *
     * @param resultAvg 総合平均点
     */
    public void setResultAvg(double resultAvg) {
        this.resultAvg = resultAvg;
    }
}
