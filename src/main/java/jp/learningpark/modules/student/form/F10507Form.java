/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

/**
 * <p>テスト目標結果一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/08 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F10507Form {
    /**
     * 学年（コード値）
     */
    private String schyVal;

    /**
     * テスト分類名
     */
    private String testTypeVal;

    /**
     * テスト類別名
     */
    private String testKindVal;

    /**
     * テスト対象年
     */
    private Integer testTgtY;

    /**
     * テスト対象月
     */
    private Integer testTgtM;

    /**
     * id
     */
    private Integer id;

    /**
     * 更新日時
     */
    private String updateTm;

    /**
     * schyValを取得する
     *
     * @return schyVal 学年（コード値）
     */
    public String getSchyVal() {
        return schyVal;
    }

    /**
     * schyValを設定する
     *
     * @param schyVal 学年（コード値）
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
     * testKindValを取得する
     *
     * @return testKindVal テスト類別名
     */
    public String getTestKindVal() {
        return testKindVal;
    }

    /**
     * testKindValを設定する
     *
     * @param testKindVal テスト類別名
     */
    public void setTestKindVal(String testKindVal) {
        this.testKindVal = testKindVal;
    }

    /**
     * testTgtYを取得する
     *
     * @return testTgtY testTgtY
     */
    public Integer getTestTgtY() {
        return testTgtY;
    }

    /**
     * testTgtYを設定する
     *
     * @param testTgtY testTgtY
     */
    public void setTestTgtY(Integer testTgtY) {
        this.testTgtY = testTgtY;
    }

    /**
     * testTgtMを取得する
     *
     * @return testTgtM testTgtM
     */
    public Integer getTestTgtM() {
        return testTgtM;
    }

    /**
     * testTgtMを設定する
     *
     * @param testTgtM testTgtM
     */
    public void setTestTgtM(Integer testTgtM) {
        this.testTgtM = testTgtM;
    }

    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * updateTmを取得する
     *
     * @return upDatime upDatime
     */
    public String getUpdateTm() {
        return updateTm;
    }

    /**
     * updateTmを設定する
     *
     * @param updateTm updateTm
     */
    public void setUpdateTm(String updateTm) {
        this.updateTm = updateTm;
    }
}
