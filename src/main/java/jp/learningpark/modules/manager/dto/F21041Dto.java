/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/30
 */

import jp.learningpark.modules.common.entity.TalkRecordHEntity;

import java.util.Date;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/30 : lyh: 新規<br />
 * @version 1.0
 */
public class F21041Dto extends TalkRecordHEntity {

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     *イベント．イベントタイトル（面談タイトル）
     */
    private String eventTitle;

    /**
     * コード値
     */
    private String codValue;
    /**
     * コード値
     */
    private String codValue2;

    /**
     * 実績年月日
     */
    private Date planYmd;

    /**
     *SUM ( 生徒ウィークリー計画実績設定．実績学習時間（分） )
     */
    private String perfLearnTm;

    /**
     * 設問名
     */
    private String questionName;
    /**
     * 事項類型区分
     */
    private String itemTypeDiv;
    /**
     * 回答結果内容
     */
    private String answerReltCont;

    /**
     * 教科区分を取得する
     *
     * @return subjtDiv 教科区分
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科区分を設定する
     *
     * @param subjtDiv 教科区分
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * コード値を取得する
     *
     * @return codValue コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コード値を設定する
     *
     * @param codValue コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 実績年月日を取得する
     *
     * @return planYmd 実績年月日
     */
    public Date getPlanYmd() {
        return this.planYmd;
    }

    /**
     * 実績年月日を設定する
     *
     * @param planYmd 実績年月日
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }

    /**
     * SUM ( 生徒ウィークリー計画実績設定．実績学習時間（分） )を取得する
     *
     * @return perfLearnTm SUM ( 生徒ウィークリー計画実績設定．実績学習時間（分） )
     */
    public String getPerfLearnTm() {
        return this.perfLearnTm;
    }

    /**
     * SUM ( 生徒ウィークリー計画実績設定．実績学習時間（分） )を設定する
     *
     * @param perfLearnTm SUM ( 生徒ウィークリー計画実績設定．実績学習時間（分） )
     */
    public void setPerfLearnTm(String perfLearnTm) {
        this.perfLearnTm = perfLearnTm;
    }

    /**
     * イベント．イベントタイトル（面談タイトル）を取得する
     *
     * @return eventTitle イベント．イベントタイトル（面談タイトル）
     */
    public String getEventTitle() {
        return this.eventTitle;
    }

    /**
     * イベント．イベントタイトル（面談タイトル）を設定する
     *
     * @param eventTitle イベント．イベントタイトル（面談タイトル）
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * コード値を取得する
     *
     * @return codValue2 コード値
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * コード値を設定する
     *
     * @param codValue2 コード値
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
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
     * 事項類型区分を取得する
     *
     * @return itemTypeDiv 事項類型区分
     */
    public String getItemTypeDiv() {
        return this.itemTypeDiv;
    }

    /**
     * 事項類型区分を設定する
     *
     * @param itemTypeDiv 事項類型区分
     */
    public void setItemTypeDiv(String itemTypeDiv) {
        this.itemTypeDiv = itemTypeDiv;
    }
}