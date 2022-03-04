/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

/**
 * <p>褒めポイント詳細画面 Dto</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/05 : wq: 新規<br />
 * @version 1.0
 */
public class F30423Dto {
    /**
     * お知らせId
     */
    private Integer id;

    /**
     * お知らせタイトル
     */
    private String noticeTitle;

    /**
     * 褒めポイント管理．スタン区分
     */
    private String complimentDiv;

    /**
     * 褒めポイント管理．コメント
     */
    private String complimentCont;

    /**
     * コートマスター．コート値
     */
    private String codValue;

    /**
     * お知らせタイトルを取得する
     *
     * @return noticeTitle お知らせタイトル
     */
    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    /**
     * お知らせタイトルを設定する
     *
     * @param noticeTitle お知らせタイトル
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * 褒めポイント管理．スタン区分を取得する
     *
     * @return complimentDiv 褒めポイント管理．スタン区分
     */
    public String getComplimentDiv() {
        return this.complimentDiv;
    }

    /**
     * 褒めポイント管理．スタン区分を設定する
     *
     * @param complimentDiv 褒めポイント管理．スタン区分
     */
    public void setComplimentDiv(String complimentDiv) {
        this.complimentDiv = complimentDiv;
    }

    /**
     * 褒めポイント管理．コメントを取得する
     *
     * @return complimentCont 褒めポイント管理．コメント
     */
    public String getComplimentCont() {
        return this.complimentCont;
    }

    /**
     * 褒めポイント管理．コメントを設定する
     *
     * @param complimentCont 褒めポイント管理．コメント
     */
    public void setComplimentCont(String complimentCont) {
        this.complimentCont = complimentCont;
    }

    /**
     * コートマスター．コート値を取得する
     *
     * @return codValue コートマスター．コート値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コートマスター．コート値を設定する
     *
     * @param codValue コートマスター．コート値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * お知らせIdを取得する
     *
     * @return id お知らせId
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * お知らせIdを設定する
     *
     * @param id お知らせId
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
