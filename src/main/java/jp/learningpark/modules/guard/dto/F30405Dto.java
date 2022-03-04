/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;

/**
 * <p>保護者面談の日程設定画面 Dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/16 : wq: 新規<br />
 * @version 1.0
 */
public class F30405Dto extends EventSchePlanDelEntity {

    /**
     * 面談記録．ID
     */
    private Integer talkId;

    /**
     * 　保護者イベント申込状況．ID
     */
    private Integer applyId;

    /**
     * 　イベント日程（詳細)．ID
     */
    private Integer scheDelId;

    /**
     * 　備考
     */
    private String noteCont;

    /**
     * 　関連ID
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
     * 質問面談イベント.ID
     */
    private Integer atId;

    /**
     * 質問面談イベント.事項類型区分
     */
    private String itemTypeDiv;

    /**
     * 質問面談イベント.質問番号
     */
    private Integer askNum;

    /**
     * 質問面談イベント.設問名
     */
    private String questionName;

    /**
     * 質問面談イベント.設問形式区分
     */
    private String answerTypeDiv;

    /**
     * 質問面談イベント.選択肢１
     */
    private String optCont1;

    /**
     * 質問面談イベント.選択肢２
     */
    private String optCont2;

    /**
     * 質問面談イベント.選択肢３
     */
    private String optCont3;

    /**
     * 質問面談イベント.選択肢４
     */
    private String optCont4;

    /**
     * 質問面談イベント.選択肢５
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
     * 回答内容
     */
    private String content;

    /**
     * 面談記録．IDを取得する
     *
     * @return talkId 面談記録．ID
     */
    public Integer getTalkId() {
        return this.talkId;
    }

    /**
     * 面談記録．IDを設定する
     *
     * @param talkId 面談記録．ID
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    /**
     * 　保護者イベント申込状況．IDを取得する
     *
     * @return applyId 　保護者イベント申込状況．ID
     */
    public Integer getApplyId() {
        return this.applyId;
    }

    /**
     * 　保護者イベント申込状況．IDを設定する
     *
     * @param applyId 　保護者イベント申込状況．ID
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 　イベント日程（詳細)．IDを取得する
     *
     * @return scheDelId 　イベント日程（詳細)．ID
     */
    public Integer getScheDelId() {
        return this.scheDelId;
    }

    /**
     * 　イベント日程（詳細)．IDを設定する
     *
     * @param scheDelId 　イベント日程（詳細)．ID
     */
    public void setScheDelId(Integer scheDelId) {
        this.scheDelId = scheDelId;
    }

    /**
     * 　備考を取得する
     *
     * @return noteCont 　備考
     */
    public String getNoteCont() {
        return this.noteCont;
    }

    /**
     * 　備考を設定する
     *
     * @param noteCont 　備考
     */
    public void setNoteCont(String noteCont) {
        this.noteCont = noteCont;
    }

    /**
     * 　関連IDを取得する
     *
     * @return refId 　関連ID
     */
    public String getRefId() {
        return this.refId;
    }

    /**
     * 　関連IDを設定する
     *
     * @param refId 　関連ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

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
     * 時間を取得する
     *
     * @return timeLine 時間
     */
    public String getTimeLine() {
        return this.timeLine;
    }

    /**
     * 時間を設定する
     *
     * @param timeLine 時間
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
     * 質問面談イベント.IDを取得する
     *
     * @return atId 質問面談イベント.ID
     */
    public Integer getAtId() {
        return this.atId;
    }

    /**
     * 質問面談イベント.IDを設定する
     *
     * @param atId 質問面談イベント.ID
     */
    public void setAtId(Integer atId) {
        this.atId = atId;
    }

    /**
     * 質問面談イベント.事項類型区分を取得する
     *
     * @return itemTypeDiv 質問面談イベント.事項類型区分
     */
    public String getItemTypeDiv() {
        return this.itemTypeDiv;
    }

    /**
     * 質問面談イベント.事項類型区分を設定する
     *
     * @param itemTypeDiv 質問面談イベント.事項類型区分
     */
    public void setItemTypeDiv(String itemTypeDiv) {
        this.itemTypeDiv = itemTypeDiv;
    }

    /**
     * 質問面談イベント.質問番号を取得する
     *
     * @return askNum 質問面談イベント.質問番号
     */
    public Integer getAskNum() {
        return this.askNum;
    }

    /**
     * 質問面談イベント.質問番号を設定する
     *
     * @param askNum 質問面談イベント.質問番号
     */
    public void setAskNum(Integer askNum) {
        this.askNum = askNum;
    }

    /**
     * 質問面談イベント.設問名を取得する
     *
     * @return questionName 質問面談イベント.設問名
     */
    public String getQuestionName() {
        return this.questionName;
    }

    /**
     * 質問面談イベント.設問名を設定する
     *
     * @param questionName 質問面談イベント.設問名
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * 質問面談イベント.設問形式区分を取得する
     *
     * @return answerTypeDiv 質問面談イベント.設問形式区分
     */
    public String getAnswerTypeDiv() {
        return this.answerTypeDiv;
    }

    /**
     * 質問面談イベント.設問形式区分を設定する
     *
     * @param answerTypeDiv 質問面談イベント.設問形式区分
     */
    public void setAnswerTypeDiv(String answerTypeDiv) {
        this.answerTypeDiv = answerTypeDiv;
    }

    /**
     * 質問面談イベント.選択肢１を取得する
     *
     * @return optCont1 質問面談イベント.選択肢１
     */
    public String getOptCont1() {
        return this.optCont1;
    }

    /**
     * 質問面談イベント.選択肢１を設定する
     *
     * @param optCont1 質問面談イベント.選択肢１
     */
    public void setOptCont1(String optCont1) {
        this.optCont1 = optCont1;
    }

    /**
     * 質問面談イベント.選択肢２を取得する
     *
     * @return optCont2 質問面談イベント.選択肢２
     */
    public String getOptCont2() {
        return this.optCont2;
    }

    /**
     * 質問面談イベント.選択肢２を設定する
     *
     * @param optCont2 質問面談イベント.選択肢２
     */
    public void setOptCont2(String optCont2) {
        this.optCont2 = optCont2;
    }

    /**
     * 質問面談イベント.選択肢３を取得する
     *
     * @return optCont3 質問面談イベント.選択肢３
     */
    public String getOptCont3() {
        return this.optCont3;
    }

    /**
     * 質問面談イベント.選択肢３を設定する
     *
     * @param optCont3 質問面談イベント.選択肢３
     */
    public void setOptCont3(String optCont3) {
        this.optCont3 = optCont3;
    }

    /**
     * 質問面談イベント.選択肢４を取得する
     *
     * @return optCont4 質問面談イベント.選択肢４
     */
    public String getOptCont4() {
        return this.optCont4;
    }

    /**
     * 質問面談イベント.選択肢４を設定する
     *
     * @param optCont4 質問面談イベント.選択肢４
     */
    public void setOptCont4(String optCont4) {
        this.optCont4 = optCont4;
    }

    /**
     * 質問面談イベント.選択肢５を取得する
     *
     * @return optCont5 質問面談イベント.選択肢５
     */
    public String getOptCont5() {
        return this.optCont5;
    }

    /**
     * 質問面談イベント.選択肢５を設定する
     *
     * @param optCont5 質問面談イベント.選択肢５
     */
    public void setOptCont5(String optCont5) {
        this.optCont5 = optCont5;
    }

    /**
     * 回答内容を取得する
     *
     * @return content 回答内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 回答内容を設定する
     *
     * @param content 回答内容
     */
    public void setContent(String content) {
        this.content = content;
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
