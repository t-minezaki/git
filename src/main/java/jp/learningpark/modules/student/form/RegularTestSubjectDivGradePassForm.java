/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

/**
 * <p>定期テスト科目別成績推移画面(F20010,F30106)</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/08 : hujunjie: 新規<br />
 * @version 1.0
 */
public class RegularTestSubjectDivGradePassForm {
    /**
     * テスト種別名
     */
    private String testKindVal;

    /**
     * 教科名
     */
    private String subjtVal;

    /**
     * テスト分類名
     */
    private String testTypeVal;

    /**
     * 学年
     */
    private String schyVal;

    /**
     * 平均順位
     */
    private double rankAvg;

    /**
     * testKindValを取得する
     *
     * @return testKindVal テスト種別名
     */
    public String getTestKindVal() {
        return testKindVal;
    }

    /**
     * testKindValを設定する
     *
     * @param testKindVal テスト種別名
     */
    public void setTestKindVal(String testKindVal) {
        this.testKindVal = testKindVal;
    }

    /**
     * subjtValを取得する
     *
     * @return subjtVal 教科名
     */
    public String getSubjtVal() {
        return subjtVal;
    }

    /**
     * subjtValを設定する
     *
     * @param subjtVal 教科名
     */
    public void setSubjtVal(String subjtVal) {
        this.subjtVal = subjtVal;
    }

    /**
     * testTypeValを取得する
     *
     * @return testTypeVal テスト分類名
     */
    public String getTestTypeVal() {
        return testTypeVal;
    }

    /**
     * testTypeValを設定する
     *
     * @param testTypeVal テスト分類名
     */
    public void setTestTypeVal(String testTypeVal) {
        this.testTypeVal = testTypeVal;
    }

    /**
     * schyValを取得する
     *
     * @return schyVal 学年
     */
    public String getSchyVal() {
        return schyVal;
    }

    /**
     * schyValを設定する
     *
     * @param schyVal 学年
     */
    public void setSchyVal(String schyVal) {
        this.schyVal = schyVal;
    }

    /**
     * rankAvgを取得する
     *
     * @return rankAvg 平均順位
     */
    public double getRankAvg() {
        return rankAvg;
    }

    /**
     * rankAvgを設定する
     *
     * @param rankAvg 平均順位
     */
    public void setRankAvg(double rankAvg) {
        this.rankAvg = rankAvg;
    }
}
