/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.form;

import jp.learningpark.modules.student.dto.F10503Dto;

import java.util.List;

/**
 * <p>定期テスト結果確認画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/06 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F10503Form {
    /**
     * 学年（コード値）
     */
    private String schyVal;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 時期テスト種別タイトル
     */
    private String seasnTitle;

    /**
     * テスト種別区分
     */
    private String testKindDiv;

    /**
     * テスト対象年
     */
    private Integer testTgtY;

    /**
     * テスト対象月
     */
    private Integer testTgtM;

    /**
     * 結果点数エリアMap
     */
    private List<F10503Dto> resultPointsAreaMap;

    /**
     * schyValを取得する
     *
     * @return schyVal schyVal
     */
    public String getSchyVal() {
        return schyVal;
    }

    /**
     * schyValを設定する
     *
     * @param schyVal schyVal
     */
    public void setSchyVal(String schyVal) {
        this.schyVal = schyVal;
    }

    /**
     * schyDivを取得する
     *
     * @return schyDiv schyDiv
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * schyDivを設定する
     *
     * @param schyDiv schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * seasnTitleを取得する
     *
     * @return seasnTitle seasnTitle
     */
    public String getSeasnTitle() {
        return seasnTitle;
    }

    /**
     * seasnTitleを設定する
     *
     * @param seasnTitle seasnTitle
     */
    public void setSeasnTitle(String seasnTitle) {
        this.seasnTitle = seasnTitle;
    }

    /**
     * testKindDivを取得する
     *
     * @return testKindDiv testKindDiv
     */
    public String getTestKindDiv() {
        return testKindDiv;
    }

    /**
     * testKindDivを設定する
     *
     * @param testKindDiv testKindDiv
     */
    public void setTestKindDiv(String testKindDiv) {
        this.testKindDiv = testKindDiv;
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
     * resultPointsAreaMapを取得する
     *
     * @return resultPointsAreaMap resultPointsAreaMap
     */
    public List<F10503Dto> getResultPointsAreaMap() {
        return resultPointsAreaMap;
    }

    /**
     * resultPointsAreaMapを設定する
     *
     * @param resultPointsAreaMap resultPointsAreaMap
     */
    public void setResultPointsAreaMap(List<F10503Dto> resultPointsAreaMap) {
        this.resultPointsAreaMap = resultPointsAreaMap;
    }
}
