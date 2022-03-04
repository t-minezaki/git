/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;

/**
 * <p>出欠席連絡一覧（スマホ）</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/25 : zpa: 新規<br />
 * @version 1.0
 */
public class F21024Dto {
    /**
     * id
     */
    private Integer id;
    /**
     * 対象年月日メールアドレス
     */
    private Timestamp tgtYmd;
    /**
     * メールアドレス
     */
    private String mailad;
    /**
     * 保護者基本マスタ.姓名_姓
     */
    private String flnmNm;
    /**
     * 保護者基本マスタ.姓名_名
     */
    private String flnmLnm;
    /**
     *対応ステータス
     */
    private String corrspdSts;
    /**
     *お知らせID
     */
    private Integer noticeId;
    /**
     *確認日付削除フラグ
     */
    private Timestamp corrspdDatime;
    /**
     * 生徒基本マスタ.姓名_姓
     */
    private String stuFN;
    /**
     * 生徒基本マスタ.姓名_名
     */
    private String stuFLn;
    /**
     * 生徒Id
     */
    private String stuId;
    /**
     * 電話番号
     */
    private String telnum;
    /**
     * コードマスタ明細・コード値
     */
    private String codValue;
    /**
     * 送信日
     */
    private String cretDatime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTgtYmd() {
        return tgtYmd;
    }

    public void setTgtYmd(Timestamp tgtYmd) {
        this.tgtYmd = tgtYmd;
    }

    public String getMailad() {
        return mailad;
    }

    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    public String getFlnmNm() {
        return flnmNm;
    }

    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    public String getFlnmLnm() {
        return flnmLnm;
    }

    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    public String getCorrspdSts() {
        return corrspdSts;
    }

    public void setCorrspdSts(String corrspdSts) {
        this.corrspdSts = corrspdSts;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Timestamp getCorrspdDatime() {
        return corrspdDatime;
    }

    public void setCorrspdDatime(Timestamp corrspdDatime) {
        this.corrspdDatime = corrspdDatime;
    }

    public String getStuFN() {
        return stuFN;
    }

    public void setStuFN(String stuFN) {
        this.stuFN = stuFN;
    }

    public String getStuFLn() {
        return stuFLn;
    }

    public void setStuFLn(String stuFLn) {
        this.stuFLn = stuFLn;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * 送信日を取得する
     *
     * @return cretDatime 送信日
     */
    public String getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 送信日を設定する
     *
     * @param cretDatime 送信日
     */
    public void setCretDatime(String cretDatime) {
        this.cretDatime = cretDatime;
    }
}
