/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

/**
 * <p>テスト目標結果登録画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/08 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F10502Form {
    /**
     * 学年区分（コード値）
     */
    private String schyVal;

    /**
     * テスト分類名
     */
    private String testTypeVal;

    /**
     * テスト種別名
     */
    private String testKindDIvVal;

    /**
     * 教科名
     */
    private String subjtVal;

    /**
     * コードCD
     */
    private String codCd;

    /**
     * schyValを取得する
     *
     * @return schyVal 学年区分（コード値）
     */
    public String getSchyVal() {
        return schyVal;
    }

    /**
     * schyValを設定する
     *
     * @param schyVal 学年区分（コード値）
     */
    public void setSchyVal(String schyVal) {
        this.schyVal = schyVal;
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
     * testKindDIvValを取得する
     *
     * @return testKindDIvVal テスト種別名
     */
    public String getTestKindDIvVal() {
        return testKindDIvVal;
    }

    /**
     * testKindDIvValを設定する
     *
     * @param testKindDIvVal テスト種別名
     */
    public void setTestKindDIvVal(String testKindDIvVal) {
        this.testKindDIvVal = testKindDIvVal;
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
     * codCdを取得する
     *
     * @return codCd コードCD
     */
    public String getCodCd() {
        return codCd;
    }

    /**
     * codCdを設定する
     *
     * @param codCd コードCD
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }
}
