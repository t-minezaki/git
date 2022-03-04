/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.entity;

import jp.learningpark.modules.common.entity.MstUsrEntity;

/**
 * <p>作用描述</p>
 * <p>详细描述</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/9/17 : xie: 新規<br />
 * @version 1.0
 */
public class BTGKA1004UsrDto extends MstUsrEntity {
    /**
     * 管理者ID
     */
    private String mgrId;
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
     * 姓名_カナ姓
     */
    private String flnmKnNm;
    /**
     * 姓名_カナ名
     */
    private String flnmKnLnm;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 指導者管理コード
     */
    private String tchCd;

    /**
     * 管理者IDを取得する
     *
     * @return mgrId 管理者ID
     */
    public String getMgrId() {
        return this.mgrId;
    }

    /**
     * 管理者IDを設定する
     *
     * @param mgrId 管理者ID
     */
    public void setMgrId(String mgrId) {
        this.mgrId = mgrId;
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
     * 姓名_カナ姓を取得する
     *
     * @return flnmKnNm 姓名_カナ姓
     */
    public String getFlnmKnNm() {
        return this.flnmKnNm;
    }

    /**
     * 姓名_カナ姓を設定する
     *
     * @param flnmKnNm 姓名_カナ姓
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * 姓名_カナ名を取得する
     *
     * @return flnmKnLnm 姓名_カナ名
     */
    public String getFlnmKnLnm() {
        return this.flnmKnLnm;
    }

    /**
     * 姓名_カナ名を設定する
     *
     * @param flnmKnLnm 姓名_カナ名
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * 電話番号を取得する
     *
     * @return telnum 電話番号
     */
    public String getTelnum() {
        return this.telnum;
    }

    /**
     * 電話番号を設定する
     *
     * @param telnum 電話番号
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * 指導者管理コードを取得する
     *
     * @return tchCd 指導者管理コード
     */
    public String getTchCd() {
        return this.tchCd;
    }

    /**
     * 指導者管理コードを設定する
     *
     * @param tchCd 指導者管理コード
     */
    public void setTchCd(String tchCd) {
        this.tchCd = tchCd;
    }
}
