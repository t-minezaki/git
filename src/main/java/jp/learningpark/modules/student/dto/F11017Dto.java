/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>生徒面談の申込内容変更・キャンセル画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/14 : wq: 新規<br />
 * @version 1.0
 */
public class F11017Dto {
    /**
     * 生徒イベント申込状況.ID
     */
    private Integer applyId;

    /**
     * 生徒イベント申込状況.イベントID
     */
    private Integer eventId;

    /**
     * 生徒基本マスタ.姓名_姓
     */
    private String flnmNm;

    /**
     * 生徒基本マスタ.姓名_名
     */
    private String flnmLnm;

    /**
     * イベント日程(詳細)．日程開始日時
     */
    private Timestamp sgdStartDatime;

    /**
     * イベント日程(詳細)．日程予定日
     */
    private Date sgdPlanDate;

    /**
     * イベントマスタ．変更可能期間
     */
    private Integer chgLimtDays;

    /**
     * イベントマスタ．申込み可能終了日時
     */
    private Timestamp applyEndDt;

    /**
     * メッセージ内容
     */
    public String errorMsg;

    /**
     * 表示flg
     */
    public Integer flg;

    /**
     * 画面表示時間文字列
     */
    private String displayTime;

    /**
     * 生徒名
     */
    private String stuNm;

    /* 2020/11/18 V9.0 cuikailin add start */
    /**
     * 質問番号
     */
    private Integer askNum;
    /**
     * 設問名
     */
    private String questionName;
    /**
     * 回答形式区分
     */
    private String answerTypeDiv;
    /**
     * 回答結果内容
     */
    private String answerReltCont;
    /**
     * 備考
     */
    private String noteCont;
    /**
     * 面談記録ID
     */
    private Integer talkId;
    /**
     * 設問名List
     */
    private List<F11017Dto> qNList = new ArrayList<>();
    /* 2020/11/18 V9.0 cuikailin add end */
    /* 2020/12/29 V9.0 cuikailin add start */
    /**
     * 関連タイプ
     */
    private String refType;
    /* 2020/12/29 V9.0 cuikailin add end */

    /**
     * 生徒イベント申込状況.IDを取得する
     *
     * @return applyId 生徒イベント申込状況.ID
     */
    public Integer getApplyId() {
        return this.applyId;
    }

    /**
     * 生徒イベント申込状況.IDを設定する
     *
     * @param applyId 生徒イベント申込状況.ID
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 生徒イベント申込状況.イベントIDを取得する
     *
     * @return eventId 生徒イベント申込状況.イベントID
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * 生徒イベント申込状況.イベントIDを設定する
     *
     * @param eventId 生徒イベント申込状況.イベントID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 生徒基本マスタ.姓名_姓を取得する
     *
     * @return flnmNm 生徒基本マスタ.姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 生徒基本マスタ.姓名_姓を設定する
     *
     * @param flnmNm 生徒基本マスタ.姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 生徒基本マスタ.姓名_名を取得する
     *
     * @return flnmLnm 生徒基本マスタ.姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 生徒基本マスタ.姓名_名を設定する
     *
     * @param flnmLnm 生徒基本マスタ.姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
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
     * イベント日程(詳細)．日程予定日を取得する
     *
     * @return sgdPlanDate イベント日程(詳細)．日程予定日
     */
    public Date getSgdPlanDate() {
        return this.sgdPlanDate;
    }

    /**
     * イベント日程(詳細)．日程予定日を設定する
     *
     * @param sgdPlanDate イベント日程(詳細)．日程予定日
     */
    public void setSgdPlanDate(Date sgdPlanDate) {
        this.sgdPlanDate = sgdPlanDate;
    }

    /**
     * イベントマスタ．変更可能期間を取得する
     *
     * @return chgLimtDays イベントマスタ．変更可能期間
     */
    public Integer getChgLimtDays() {
        return this.chgLimtDays;
    }

    /**
     * イベントマスタ．変更可能期間を設定する
     *
     * @param chgLimtDays イベントマスタ．変更可能期間
     */
    public void setChgLimtDays(Integer chgLimtDays) {
        this.chgLimtDays = chgLimtDays;
    }

    /**
     * イベントマスタ．申込み可能終了日時を取得する
     *
     * @return applyEndDt イベントマスタ．申込み可能終了日時
     */
    public Timestamp getApplyEndDt() {
        return this.applyEndDt;
    }

    /**
     * イベントマスタ．申込み可能終了日時を設定する
     *
     * @param applyEndDt イベントマスタ．申込み可能終了日時
     */
    public void setApplyEndDt(Timestamp applyEndDt) {
        this.applyEndDt = applyEndDt;
    }

    /**
     * メッセージ内容を取得する
     *
     * @return errorMsg メッセージ内容
     */
    public String getErrorMsg() {
        return this.errorMsg;
    }

    /**
     * メッセージ内容を設定する
     *
     * @param errorMsg メッセージ内容
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 表示flgを取得する
     *
     * @return flg 表示flg
     */
    public Integer getFlg() {
        return this.flg;
    }

    /**
     * 表示flgを設定する
     *
     * @param flg 表示flg
     */
    public void setFlg(Integer flg) {
        this.flg = flg;
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
     * 生徒名を取得する
     *
     * @return stuNm 生徒名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒名を設定する
     *
     * @param stuNm 生徒名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 質問番号を取得する
     *
     * @return askNum 質問番号
     */
    public Integer getAskNum() {
        return this.askNum;
    }

    /**
     * 質問番号を設定する
     *
     * @param askNum 質問番号
     */
    public void setAskNum(Integer askNum) {
        this.askNum = askNum;
    }

    /**
     * 設問名を取得する
     *
     * @return questionName 設問名
     */
    public String getQuestionName() {
        return this.questionName;
    }

    /**
     * 設問名を設定する
     *
     * @param questionName 設問名
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * 回答形式区分を取得する
     *
     * @return answerTypeDiv 回答形式区分
     */
    public String getAnswerTypeDiv() {
        return this.answerTypeDiv;
    }

    /**
     * 回答形式区分を設定する
     *
     * @param answerTypeDiv 回答形式区分
     */
    public void setAnswerTypeDiv(String answerTypeDiv) {
        this.answerTypeDiv = answerTypeDiv;
    }

    /**
     * 回答結果内容を取得する
     *
     * @return answerReltCont 回答結果内容
     */
    public String getAnswerReltCont() {
        return this.answerReltCont;
    }

    /**
     * 回答結果内容を設定する
     *
     * @param answerReltCont 回答結果内容
     */
    public void setAnswerReltCont(String answerReltCont) {
        this.answerReltCont = answerReltCont;
    }

    /**
     * 備考を取得する
     *
     * @return noteCont 備考
     */
    public String getNoteCont() {
        return this.noteCont;
    }

    /**
     * 備考を設定する
     *
     * @param noteCont 備考
     */
    public void setNoteCont(String noteCont) {
        this.noteCont = noteCont;
    }

    /**
     * 面談記録IDを取得する
     *
     * @return talkId 面談記録ID
     */
    public Integer getTalkId() {
        return this.talkId;
    }

    /**
     * 面談記録IDを設定する
     *
     * @param talkId 面談記録ID
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    /**
     * 設問名Listを取得する
     *
     * @return qNList 設問名List
     */
    public List<F11017Dto> getQNList() {
        return this.qNList;
    }

    /**
     * 設問名Listを設定する
     *
     * @param qNList 設問名List
     */
    public void setQNList(List<F11017Dto> qNList) {
        this.qNList = qNList;
    }

    /**
     * 関連タイプを取得する
     *
     * @return refType 関連タイプ
     */
    public String getRefType() {
        return this.refType;
    }

    /**
     * 関連タイプを設定する
     *
     * @param refType 関連タイプ
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
}
