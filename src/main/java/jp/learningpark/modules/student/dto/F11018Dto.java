/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import java.sql.Timestamp;

/**
 * <p>生徒面談の申込内容キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
public class F11018Dto {

    /**
     * 面談記録．ID
     */
    private Integer trhId;

    /**
     * 面談記録．部活の両立状況
     */
    private String megState;

    /**
     * 面談記録．予約事・アドバイス内容
     */
    private String subeCont;

    /**
     * 面談記録．備考
     */
    private String noteCont;

    /**
     * 生徒基本マスタ．姓名
     */
    private String stuNm;

    /**
     * イベント日程(詳細)．日程開始日時
     */
    private Timestamp sgdStartDatime;

    /**
     * 画面表示時間文字列
     */
    private String displayTime;

    /**
     * 質問面談イベント．設問名
     */
    private String questionName;

    /**
     * 面談記録詳細．質問番号
     */
    private String askNum;

    /**
     * 面談記録詳細．回答結果内容
     */
    private String answerReltCont;

    /**
     * 面談記録詳細．回答形式区分
     */
    private String answerTypeDiv;

    /**
     * 面談記録．IDを取得する
     *
     * @return trhId 面談記録．ID
     */
    public Integer getTrhId() {
        return this.trhId;
    }

    /**
     * 面談記録．IDを設定する
     *
     * @param trhId 面談記録．ID
     */
    public void setTrhId(Integer trhId) {
        this.trhId = trhId;
    }

    /**
     * 面談記録．部活の両立状況を取得する
     *
     * @return megState 面談記録．部活の両立状況
     */
    public String getMegState() {
        return this.megState;
    }

    /**
     * 面談記録．部活の両立状況を設定する
     *
     * @param megState 面談記録．部活の両立状況
     */
    public void setMegState(String megState) {
        this.megState = megState;
    }

    /**
     * 面談記録．予約事・アドバイス内容を取得する
     *
     * @return subeCont 面談記録．予約事・アドバイス内容
     */
    public String getSubeCont() {
        return this.subeCont;
    }

    /**
     * 面談記録．予約事・アドバイス内容を設定する
     *
     * @param subeCont 面談記録．予約事・アドバイス内容
     */
    public void setSubeCont(String subeCont) {
        this.subeCont = subeCont;
    }

    /**
     * 面談記録．備考を取得する
     *
     * @return noteCont 面談記録．備考
     */
    public String getNoteCont() {
        return this.noteCont;
    }

    /**
     * 面談記録．備考を設定する
     *
     * @param noteCont 面談記録．備考
     */
    public void setNoteCont(String noteCont) {
        this.noteCont = noteCont;
    }

    /**
     * 生徒基本マスタ．姓名を取得する
     *
     * @return stuNm 生徒基本マスタ．姓名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒基本マスタ．姓名を設定する
     *
     * @param stuNm 生徒基本マスタ．姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * イベント日程(詳細)．日程開始日時を取得する
     *
     * @return sgdStartDatime イベント日程(詳細)．日程開始日時
     */
    public Timestamp getSgdStartDatime() {
        return this.sgdStartDatime;
    }

    /**
     * イベント日程(詳細)．日程開始日時を設定する
     *
     * @param sgdStartDatime イベント日程(詳細)．日程開始日時
     */
    public void setSgdStartDatime(Timestamp sgdStartDatime) {
        this.sgdStartDatime = sgdStartDatime;
    }

    /**
     * 質問面談イベント．設問名を取得する
     *
     * @return questionName 質問面談イベント．設問名
     */
    public String getQuestionName() {
        return this.questionName;
    }

    /**
     * 質問面談イベント．設問名を設定する
     *
     * @param questionName 質問面談イベント．設問名
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * 面談記録詳細．質問番号を取得する
     *
     * @return askNum 面談記録詳細．質問番号
     */
    public String getAskNum() {
        return this.askNum;
    }

    /**
     * 面談記録詳細．質問番号を設定する
     *
     * @param askNum 面談記録詳細．質問番号
     */
    public void setAskNum(String askNum) {
        this.askNum = askNum;
    }

    /**
     * 面談記録詳細．回答結果内容を取得する
     *
     * @return answerReltCont 面談記録詳細．回答結果内容
     */
    public String getAnswerReltCont() {
        return this.answerReltCont;
    }

    /**
     * 面談記録詳細．回答結果内容を設定する
     *
     * @param answerReltCont 面談記録詳細．回答結果内容
     */
    public void setAnswerReltCont(String answerReltCont) {
        this.answerReltCont = answerReltCont;
    }

    /**
     * 画面表示時間文字列を取得する
     *
     * @return displayTime 画面表示時間文字列
     */
    public String getDisplayTime() {
        return this.displayTime;
    }

    /**
     * 画面表示時間文字列を設定する
     *
     * @param displayTime 画面表示時間文字列
     */
    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    /**
     * 面談記録詳細．回答形式区分を取得する
     *
     * @return answerTypeDiv 面談記録詳細．回答形式区分
     */
    public String getAnswerTypeDiv() {
        return this.answerTypeDiv;
    }

    /**
     * 面談記録詳細．回答形式区分を設定する
     *
     * @param answerTypeDiv 面談記録詳細．回答形式区分
     */
    public void setAnswerTypeDiv(String answerTypeDiv) {
        this.answerTypeDiv = answerTypeDiv;
    }
}
