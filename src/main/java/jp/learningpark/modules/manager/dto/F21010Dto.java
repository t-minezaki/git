/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2019/11/29 : lyh: 新規<br />
 * @version 1.0
 */
public class F21010Dto {
    /**
     * 指導報告書配信コード
     */
    private String guidReprDeliverCd;
    /**
     * 公開開始日時
     */
    private Timestamp pubStartDt;
    /**
     * 公開終了日時
     */
    private Timestamp pubEndDt;
    /**
     * 作成者
     */
    private String cretUsrId;

    /**
     * 更新者
     */
    private String updUsrId;
//    /**
//     * 指導報告書配信管理．指導報告書ステータス区分
//     */
//    private String codValue;
    /**
     * 指導報告書配信管理．対象年月日
     */
    private Timestamp tgtYmd;
    /**
     * 指導報告書配信管理．作成日時
     */
    private Timestamp cretDatime;
    /**
     * 指導報告書配信管理．更新日時
     */
    private Timestamp updDatime;
    /**
     * 全体配信対象
     */
    private Integer countSend;
    /**
     * 配信対象かつ閲覧状況区分は'未読'
     */
    private Integer countNotRead;
    /**
     * 配信対象かつ閲覧状況区分は'既読'
     */
    private Integer countRead;

    /**
     * 指導報告書ステータス区分
     */
    private String eventStsDiv;
    /**
     * コードCD
     */
    private String codCd;

    /**
     * 指導報告書ステータス区分を取得する
     *
     * @return eventStsDiv 指導報告書ステータス区分
     */
    public String getEventStsDiv() {
        return this.eventStsDiv;
    }

    /**
     * 指導報告書ステータス区分を設定する
     *
     * @param eventStsDiv 指導報告書ステータス区分
     */
    public void setEventStsDiv(String eventStsDiv) {
        this.eventStsDiv = eventStsDiv;
    }

    /**
     * 指導報告書配信コードを取得する
     *
     * @return guidReprDeliverCd 指導報告書配信コード
     */
    public String getGuidReprDeliverCd() {
        return this.guidReprDeliverCd;
    }

    /**
     * 指導報告書配信コードを設定する
     *
     * @param guidReprDeliverCd 指導報告書配信コード
     */
    public void setGuidReprDeliverCd(String guidReprDeliverCd) {
        this.guidReprDeliverCd = guidReprDeliverCd;
    }

    /**
     * 公開開始日時を取得する
     *
     * @return pubStartDt 公開開始日時
     */
    public Timestamp getPubStartDt() {
        return this.pubStartDt;
    }

    /**
     * 公開開始日時を設定する
     *
     * @param pubStartDt 公開開始日時
     */
    public void setPubStartDt(Timestamp pubStartDt) {
        this.pubStartDt = pubStartDt;
    }

    /**
     * 公開終了日時を取得する
     *
     * @return pubEndDt 公開終了日時
     */
    public Timestamp getPubEndDt() {
        return this.pubEndDt;
    }

    /**
     * 公開終了日時を設定する
     *
     * @param pubEndDt 公開終了日時
     */
    public void setPubEndDt(Timestamp pubEndDt) {
        this.pubEndDt = pubEndDt;
    }


//    /**
//     * 指導報告書配信管理．指導報告書ステータス区分を取得する
//     *
//     * @return codValue 指導報告書配信管理．指導報告書ステータス区分
//     */
//    public String getCodValue() {
//        return this.codValue;
//    }
//
//    /**
//     * 指導報告書配信管理．指導報告書ステータス区分を設定する
//     *
//     * @param codValue 指導報告書配信管理．指導報告書ステータス区分
//     */
//    public void setCodValue(String codValue) {
//        this.codValue = codValue;
//    }

    /**
     * 指導報告書配信管理．対象年月日を取得する
     *
     * @return tgtYmd 指導報告書配信管理．対象年月日
     */
    public Timestamp getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 指導報告書配信管理．対象年月日を設定する
     *
     * @param tgtYmd 指導報告書配信管理．対象年月日
     */
    public void setTgtYmd(Timestamp tgtYmd) {
        this.tgtYmd = tgtYmd;
    }

    /**
     * 指導報告書配信管理．作成日時を取得する
     *
     * @return cretDatime 指導報告書配信管理．作成日時
     */
    public Timestamp getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 指導報告書配信管理．作成日時を設定する
     *
     * @param cretDatime 指導報告書配信管理．作成日時
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 指導報告書配信管理．更新日時を取得する
     *
     * @return updDatime 指導報告書配信管理．更新日時
     */
    public Timestamp getUpdDatime() {
        return this.updDatime;
    }

    /**
     * 指導報告書配信管理．更新日時を設定する
     *
     * @param updDatime 指導報告書配信管理．更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }


    /**
     * 全体配信対象を取得する
     *
     * @return countSend 全体配信対象
     */
    public Integer getCountSend() {
        return this.countSend;
    }

    /**
     * 全体配信対象を設定する
     *
     * @param countSend 全体配信対象
     */
    public void setCountSend(Integer countSend) {
        this.countSend = countSend;
    }

    /**
     * 配信対象かつ閲覧状況区分は'未読'を取得する
     *
     * @return countNotRead 配信対象かつ閲覧状況区分は'未読'
     */
    public Integer getCountNotRead() {
        return this.countNotRead;
    }

    /**
     * 配信対象かつ閲覧状況区分は'未読'を設定する
     *
     * @param countNotRead 配信対象かつ閲覧状況区分は'未読'
     */
    public void setCountNotRead(Integer countNotRead) {
        this.countNotRead = countNotRead;
    }

    /**
     * 配信対象かつ閲覧状況区分は'既読'を取得する
     *
     * @return countRead 配信対象かつ閲覧状況区分は'既読'
     */
    public Integer getCountRead() {
        return this.countRead;
    }

    /**
     * 配信対象かつ閲覧状況区分は'既読'を設定する
     *
     * @param countRead 配信対象かつ閲覧状況区分は'既読'
     */
    public void setCountRead(Integer countRead) {
        this.countRead = countRead;
    }

    /**
     * 作成者を取得する
     *
     * @return cretUsrId 作成者
     */
    public String getCretUsrId() {
        return this.cretUsrId;
    }

    /**
     * 作成者を設定する
     *
     * @param cretUsrId 作成者
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 更新者を取得する
     *
     * @return updUsrId 更新者
     */
    public String getUpdUsrId() {
        return this.updUsrId;
    }

    /**
     * 更新者を設定する
     *
     * @param updUsrId 更新者
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * コードCDを取得する
     *
     * @return codCd コードCD
     */
    public String getCodCd() {
        return this.codCd;
    }

    /**
     * コードCDを設定する
     *
     * @param codCd コードCD
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }
}