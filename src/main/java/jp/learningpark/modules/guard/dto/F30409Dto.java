package jp.learningpark.modules.guard.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class F30409Dto {
    /**
     * 申込情報エリア＿生徒氏名
     */
    public String stuNm;
    /**
     * 生徒ID
     */
    public String stuId;
    /**
     * 保護者ＩＤ
     */
    public String guardId;
    /**
     * 申込情報エリア＿日時
     */
    public String sgdStartDatime;
    /**
     * イベント日程(詳細)．日程予定日
     */
    public Date sgdPlanDate;
    /**
     * イベント．変更可能期間
     */
    public Integer chgLimtDays;
    /**
     * メッセージ内容
     */
    public String errorMsg;
    /**
     * 表示flg
     */
    public Integer flg;
    /**
     * eventId
     */
    public Integer eventId;
    /**
     * 申込み可能終了日時
     */
    public Timestamp applyEndDate;
    /**
     * 保護者イベント申込ID
     */
    public Integer applyId;
    /* 2020/11/16 V9.0 cuikailin add start */
    /**
     * 質問番号
     */
    private Integer askNum;
    /**
     * 設問名
     */
    private  String questionName;
    /**
     * 回答形式区分
     */
    private  String answerTypeDiv;
    /**
     * 回答結果内容
     */
    private String answerReltCont;
    /**
     * 備考
     */
    private  String noteCont;
    /**
     *面談記録ID
     */
    private  Integer talkId;
    /**
     * 設問名List
     */
    private List<F30409Dto> qNList = new ArrayList<>();
    /* 2020/11/16 V9.0 cuikailin add end */
    /**
     * 関連タイプ
     */
    private String refType;
    /**
     * 申込情報エリア＿生徒氏名を取得する
     *
     * @return stuNm 申込情報エリア＿生徒氏名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 申込情報エリア＿生徒氏名を設定する
     *
     * @param stuNm 申込情報エリア＿生徒氏名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 申込情報エリア＿日時を取得する
     *
     * @return sgdStartDatime 申込情報エリア＿日時
     */
    public String getSgdStartDatime() {
        return this.sgdStartDatime;
    }

    /**
     * 申込情報エリア＿日時を設定する
     *
     * @param sgdStartDatime 申込情報エリア＿日時
     */
    public void setSgdStartDatime(String sgdStartDatime) {
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
     * イベント．変更可能期間を取得する
     *
     * @return chgLimtDays イベント．変更可能期間
     */
    public Integer getChgLimtDays() {
        return this.chgLimtDays;
    }

    /**
     * イベント．変更可能期間を設定する
     *
     * @param chgLimtDays イベント．変更可能期間
     */
    public void setChgLimtDays(Integer chgLimtDays) {
        this.chgLimtDays = chgLimtDays;
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
     * 保護者ＩＤを取得する
     *
     * @return guardId 保護者ＩＤ
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者ＩＤを設定する
     *
     * @param guardId 保護者ＩＤ
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 表示flgを取得する
     *
     * @return flg
     */
    public Integer getFlg() {
        return this.flg;
    }

    /**
     * 表示flgを設定する
     *
     * @param flg
     */
    public void setFlg(Integer flg) {
        this.flg = flg;
    }

    /**
     * eventIdを取得する
     *
     * @return eventId eventId
     */
    public Integer getEventId() {
        return this.eventId;
    }

    /**
     * eventIdを設定する
     *
     * @param eventId eventId
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 申込み可能終了日時を取得する
     *
     * @return applyEndDate 申込み可能終了日時
     */
    public Timestamp getApplyEndDate() {
        return this.applyEndDate;
    }

    /**
     * 申込み可能終了日時を設定する
     *
     * @param applyEndDate 申込み可能終了日時
     */
    public void setApplyEndDate(Timestamp applyEndDate) {
        this.applyEndDate = applyEndDate;
    }

    /**
     * 保護者イベント申込IDを取得する
     *
     * @return applyId 保護者イベント申込ID
     */
    public Integer getApplyId() {
        return this.applyId;
    }

    /**
     * 保護者イベント申込IDを設定する
     *
     * @param applyId 保護者イベント申込ID
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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
    public List<F30409Dto> getQNList() {
        return this.qNList;
    }

    /**
     * 設問名Listを設定する
     *
     * @param qNList 設問名List
     */
    public void setQNList(List<F30409Dto> qNList) {
        this.qNList = qNList;
    }

    /**
     * を取得する
     *
     * @return refType
     */
    public String getRefType() {
        return this.refType;
    }

    /**
     * を設定する
     *
     * @param refType
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
}
