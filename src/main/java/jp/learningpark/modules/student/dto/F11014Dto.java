/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;
/**
 * GakkenAppApplication
 */

import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;

import javax.validation.constraints.NotNull;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/05/12 : lyh: 新規<br />
 * @version 1.0
 */
public class F11014Dto extends EventSchePlanDelEntity {
    /**
     *id
     */
    private String refId;
    /**
     * 質問回答内容
     */
    private String replyCnt;
    /**
     * 生徒基本マスタ．姓名_姓
     */
    private String flnmNm;
    /**
     * 生徒基本マスタ．姓名_名
     */
    private String flnmLnm;
    /**
     * 時間
     */
    private String timeLine;
    /**
     * 日付文字列
     */
    private String tgtDay;
    /**
     * 記号
     */
    private String flag;
    /**
     *
     */
    private Integer id;
    /**
     * 事項類型区分
     */
    @NotNull
    private String itemTypeDiv;

    /**
     * 利用可区分
     */
    @NotNull
    private String useDiv;

    /**
     * 設問名
     */
    @NotNull
    private String questionName;
    /***
     * 質問番号
     */
    private String askNum;
    /**
     * 設問形式区分
     */
    @NotNull
    private String answerTypeDiv;

    /**
     * 選択肢１
     */
    private String optCont1;

    /**
     * 選択肢2
     */
    private String optCont2;

    /**
     * 選択肢3
     */
    private String optCont3;

    /**
     * 選択肢4
     */
    private String optCont4;

    /**
     * 選択肢5
     */
    private String optCont5;

    /**
     * 質問面談イベント.選択肢６
     */
    private String optCont6;

    /**
     * 質問面談イベント.選択肢７
     */
    private String optCont7;

    /**
     * 質問面談イベント.選択肢８
     */
    private String optCont8;

    /**
     * 質問面談イベント.選択肢９
     */
    private String optCont9;

    /**
     * 質問面談イベント.選択肢１０
     */
    private String optCont10;

    /**
     * 質問回答内容を取得する
     *
     * @return replyCnt 質問回答内容
     */
    public String getReplyCnt() {
        return this.replyCnt;
    }

    /**
     * 質問回答内容を設定する
     *
     * @param replyCnt 質問回答内容
     */
    public void setReplyCnt(String replyCnt) {
        this.replyCnt = replyCnt;
    }

    /**
     * 生徒基本マスタ．姓名_姓を取得する
     *
     * @return flnmNm 生徒基本マスタ．姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 生徒基本マスタ．姓名_姓を設定する
     *
     * @param flnmNm 生徒基本マスタ．姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 生徒基本マスタ．姓名_名を取得する
     *
     * @return flnmLnm 生徒基本マスタ．姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 生徒基本マスタ．姓名_名を設定する
     *
     * @param flnmLnm 生徒基本マスタ．姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * を取得する
     *
     * @return timeLine
     */
    public String getTimeLine() {
        return this.timeLine;
    }

    /**
     * を設定する
     *
     * @param timeLine
     */
    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    /**
     * 日付文字列を取得する
     *
     * @return tgtDay 日付文字列
     */
    public String getTgtDay() {
        return this.tgtDay;
    }

    /**
     * 日付文字列を設定する
     *
     * @param tgtDay 日付文字列
     */
    public void setTgtDay(String tgtDay) {
        this.tgtDay = tgtDay;
    }

    /**
     * 記号を取得する
     *
     * @return flag 記号
     */
    public String getFlag() {
        return this.flag;
    }

    /**
     * 記号を設定する
     *
     * @param flag 記号
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * を取得する
     *
     * @return refId
     */
    @Override
    public String getRefId() {
        return this.refId;
    }

    /**
     * を設定する
     *
     * @param refId
     */
    @Override
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * 事項類型区分を設定する
     */
    public void setItemTypeDiv(String itemTypeDiv) {
        this.itemTypeDiv = itemTypeDiv;
    }

    /**
     * 事項類型区分を取得する
     */
    public String getItemTypeDiv() {
        return itemTypeDiv;
    }

    /**
     * 利用可区分を設定する
     */
    public void setUseDiv(String useDiv) {
        this.useDiv = useDiv;
    }

    /**
     * 利用可区分を取得する
     */
    public String getUseDiv() {
        return useDiv;
    }

    /**
     * 設問名を設定する
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * 設問名を取得する
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * 設問形式区分を設定する
     */
    public void setAnswerTypeDiv(String answerTypeDiv) {
        this.answerTypeDiv = answerTypeDiv;
    }

    /**
     * 設問形式区分を取得する
     */
    public String getAnswerTypeDiv() {
        return answerTypeDiv;
    }

    /**
     * 選択肢１を設定する
     */
    public void setOptCont1(String optCont1) {
        this.optCont1 = optCont1;
    }

    /**
     * 選択肢１を取得する
     */
    public String getOptCont1() {
        return optCont1;
    }

    /**
     * 選択肢2を設定する
     */
    public void setOptCont2(String optCont2) {
        this.optCont2 = optCont2;
    }

    /**
     * 選択肢2を取得する
     */
    public String getOptCont2() {
        return optCont2;
    }

    /**
     * 選択肢3を設定する
     */
    public void setOptCont3(String optCont3) {
        this.optCont3 = optCont3;
    }

    /**
     * 選択肢3を取得する
     */
    public String getOptCont3() {
        return optCont3;
    }

    /**
     * 選択肢4を設定する
     */
    public void setOptCont4(String optCont4) {
        this.optCont4 = optCont4;
    }

    /**
     * 選択肢4を取得する
     */
    public String getOptCont4() {
        return optCont4;
    }

    /**
     * 選択肢5を設定する
     */
    public void setOptCont5(String optCont5) {
        this.optCont5 = optCont5;
    }

    /**
     * 選択肢5を取得する
     */
    public String getOptCont5() {
        return optCont5;
    }

    /**
     * を取得する
     *
     * @return id
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * を設定する
     *
     * @param id
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 質問番号を取得する
     *
     * @return askNum 質問番号
     */
    public String getAskNum() {
        return this.askNum;
    }

    /**
     * 質問番号を設定する
     *
     * @param askNum 質問番号
     */
    public void setAskNum(String askNum) {
        this.askNum = askNum;
    }

    /**
     * 質問面談イベント.選択肢６を取得する
     *
     * @return optCont6 質問面談イベント.選択肢６
     */
    public String getOptCont6() {
        return this.optCont6;
    }

    /**
     * 質問面談イベント.選択肢６を設定する
     *
     * @param optCont6 質問面談イベント.選択肢６
     */
    public void setOptCont6(String optCont6) {
        this.optCont6 = optCont6;
    }

    /**
     * 質問面談イベント.選択肢７を取得する
     *
     * @return optCont7 質問面談イベント.選択肢７
     */
    public String getOptCont7() {
        return this.optCont7;
    }

    /**
     * 質問面談イベント.選択肢７を設定する
     *
     * @param optCont7 質問面談イベント.選択肢７
     */
    public void setOptCont7(String optCont7) {
        this.optCont7 = optCont7;
    }

    /**
     * 質問面談イベント.選択肢８を取得する
     *
     * @return optCont8 質問面談イベント.選択肢８
     */
    public String getOptCont8() {
        return this.optCont8;
    }

    /**
     * 質問面談イベント.選択肢８を設定する
     *
     * @param optCont8 質問面談イベント.選択肢８
     */
    public void setOptCont8(String optCont8) {
        this.optCont8 = optCont8;
    }

    /**
     * 質問面談イベント.選択肢９を取得する
     *
     * @return optCont9 質問面談イベント.選択肢９
     */
    public String getOptCont9() {
        return this.optCont9;
    }

    /**
     * 質問面談イベント.選択肢９を設定する
     *
     * @param optCont9 質問面談イベント.選択肢９
     */
    public void setOptCont9(String optCont9) {
        this.optCont9 = optCont9;
    }

    /**
     * 質問面談イベント.選択肢１０を取得する
     *
     * @return optCont10 質問面談イベント.選択肢１０
     */
    public String getOptCont10() {
        return this.optCont10;
    }

    /**
     * 質問面談イベント.選択肢１０を設定する
     *
     * @param optCont10 質問面談イベント.選択肢１０
     */
    public void setOptCont10(String optCont10) {
        this.optCont10 = optCont10;
    }
}