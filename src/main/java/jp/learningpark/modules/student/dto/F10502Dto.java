/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.StuTestGoalResultDEntity;

/**
 * <p>生徒テスト目標結果_明細 Extend + コードマスタ_明細</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/21 : gong: 新規<br />
 * @version 1.0
 */
public class F10502Dto  extends StuTestGoalResultDEntity {
    /**
     * 排他チェックの更新日時
     */
    private String updateTimeString;

    /**
     * コードCD
     */
    private String codCd;

    /**
     * コード値
     */
    private String codValue;

    /**
     * コード値
     */
    private String codValue2;

    /**
     * コード値3
     */
    private String codValue3;

    /**
     * 教科のパス
     */
    private String codCont;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * テスト分類区分
     */
    private String testTypeDiv;

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
     * 生徒ID
     */
    private String stuId;

    /**
     * コードCDを設定する
     *
     * @return codCd コードCD
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * コードCDを取得する
     *
     * @param codCd コードCD
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    /**
     * コード値を設定する
     *
     * @return codValue コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コード値を取得する
     *
     * @param codValue コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 学年区分を設定する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を取得する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * テスト分類区分を設定する
     *
     * @return testTypeDiv テスト分類区分
     */
    public String getTestTypeDiv() {
        return this.testTypeDiv;
    }

    /**
     * テスト分類区分を取得する
     *
     * @param testTypeDiv テスト分類区分
     */
    public void setTestTypeDiv(String testTypeDiv) {
        this.testTypeDiv = testTypeDiv;
    }

    /**
     * テスト種別区分を設定する
     *
     * @return testKindDiv テスト種別区分
     */
    public String getTestKindDiv() {
        return this.testKindDiv;
    }

    /**
     * テスト種別区分を取得する
     *
     * @param testKindDiv テスト種別区分
     */
    public void setTestKindDiv(String testKindDiv) {
        this.testKindDiv = testKindDiv;
    }

    /**
     * テスト対象年を設定する
     *
     * @return testTgtY テスト対象年
     */
    public Integer getTestTgtY() {
        return this.testTgtY;
    }

    /**
     * テスト対象年を取得する
     *
     * @param testTgtY テスト対象年
     */
    public void setTestTgtY(Integer testTgtY) {
        this.testTgtY = testTgtY;
    }

    /**
     * テスト対象月を設定する
     *
     * @return testTgtM テスト対象月
     */
    public Integer getTestTgtM() {
        return this.testTgtM;
    }

    /**
     * テスト対象月を取得する
     *
     * @param testTgtM テスト対象月
     */
    public void setTestTgtM(Integer testTgtM) {
        this.testTgtM = testTgtM;
    }

    /**
     * 排他チェックの更新日時を設定する
     *
     * @return updateTimeString 排他チェックの更新日時
     */
    public String getUpdateTimeString() {
        return this.updateTimeString;
    }

    /**
     * 排他チェックの更新日時を取得する
     *
     * @param updateTimeString 排他チェックの更新日時
     */
    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    /**
     * 教科のパスを設定する
     *
     * @return codCont 教科のパス
     */
    public String getCodCont() {
        return this.codCont;
    }

    /**
     * 教科のパスを取得する
     *
     * @param codCont 教科のパス
     */
    public void setCodCont(String codCont) {
        this.codCont = codCont;
    }

    /**
     * コード値を設定する
     *
     * @return codValue2 コード値
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * コード値を取得する
     *
     * @param codValue2 コード値
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * コード値3を取得する
     *
     * @return codValue3 コード値3
     */
    public String getCodValue3() {
        return this.codValue3;
    }

    /**
     * コード値3を設定する
     *
     * @param codValue3 コード値3
     */
    public void setCodValue3(String codValue3) {
        this.codValue3 = codValue3;
    }
}
