package jp.learningpark.modules.manager.dto;

import java.sql.Timestamp;

/**
 * <p></p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */
public class F21003Dto {
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
     * 姓名_姓
     */
    private String flnmNm;
    /**
     * 姓名_名
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
     *生徒基本マスタ・姓名
     */
    private String stuNm;
    /**
     * コードマスタ明細・コード値
     */
    private String codValue;

    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * メールアドレスを取得する
     *
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return this.mailad;
    }

    /**
     * メールアドレスを設定する
     *
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 姓名_姓を取得する
     *
     * @return flnmNm 姓名_姓
     */
    public String getFlnmNm() {
        return this.flnmNm;
    }

    /**
     * 姓名_姓を設定する
     *
     * @param flnmNm 姓名_姓
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 姓名_名を取得する
     *
     * @return flnmLnm 姓名_名
     */
    public String getFlnmLnm() {
        return this.flnmLnm;
    }

    /**
     * 姓名_名を設定する
     *
     * @param flnmLnm 姓名_名
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 対応ステータスを取得する
     *
     * @return corrspdSts 対応ステータス
     */
    public String getCorrspdSts() {
        return this.corrspdSts;
    }

    /**
     * 対応ステータスを設定する
     *
     * @param corrspdSts 対応ステータス
     */
    public void setCorrspdSts(String corrspdSts) {
        this.corrspdSts = corrspdSts;
    }

    /**
     * お知らせIDを取得する
     *
     * @return noticeId お知らせID
     */
    public Integer getNoticeId() {
        return this.noticeId;
    }

    /**
     * お知らせIDを設定する
     *
     * @param noticeId お知らせID
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 確認日付削除フラグを取得する
     *
     * @return corrspdDatime 確認日付削除フラグ
     */
    public Timestamp getCorrspdDatime() {
        return this.corrspdDatime;
    }

    /**
     * 確認日付削除フラグを設定する
     *
     * @param corrspdDatime 確認日付削除フラグ
     */
    public void setCorrspdDatime(Timestamp corrspdDatime) {
        this.corrspdDatime = corrspdDatime;
    }

    /**
     * 生徒基本マスタ・姓名を取得する
     *
     * @return stuNm 生徒基本マスタ・姓名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒基本マスタ・姓名を設定する
     *
     * @param stuNm 生徒基本マスタ・姓名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * コードマスタ明細・コード値を取得する
     *
     * @return codValue コードマスタ明細・コード値
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * コードマスタ明細・コード値を設定する
     *
     * @param codValue コードマスタ明細・コード値
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 対象年月日メールアドレスを取得する
     *
     * @return tgtYmd 対象年月日メールアドレス
     */
    public Timestamp getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 対象年月日メールアドレスを設定する
     *
     * @param tgtYmd 対象年月日メールアドレス
     */
    public void setTgtYmd(Timestamp tgtYmd) {
        this.tgtYmd = tgtYmd;
    }
    /**
     *削除フラグ
     */


}
