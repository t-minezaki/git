/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.Date;

/**
 * <p>ユーザー基本情報修正画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/15 : xiong: 新規<br />
 * @version 1.0
 */
public class F00043Dto {

    /**
     * 変更後ユーザーID
     */
    String afterUsrId;
    /**
     * ユーザステータス
     */
    String usrSts;

    /**
     * ユーザログインPW
     */
    private String usrPassword;

    /**
     * 学研IDプライマリキー
     */
    private String gidpk;

    /**
     * GIDフラグ
     */
    private String gidFlg;

    /**
     * コード値（ロール）
     */
    String codValue;
    /**
     * コードCD
     */
    String codCd;
    /**
     * 更新日時
     */
    Date updDatime;

    /**
     * afterUsrIdを取得する
     *
     * @return afterUsrId afterUsrId
     */
    public String getAfterUsrId() {
        return afterUsrId;
    }

    /**
     * afterUsrIdを設定する
     *
     * @param afterUsrId afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * usrStsを取得する
     *
     * @return usrSts usrSts
     */
    public String getUsrSts() {
        return usrSts;
    }

    /**
     * usrStsを設定する
     *
     * @param usrSts usrSts
     */
    public void setUsrSts(String usrSts) {
        this.usrSts = usrSts;
    }

    /**
     * codValueを取得する
     *
     * @return codValue codValue
     */
    public String getCodValue() {
        return codValue;
    }

    /**
     * codValueを設定する
     *
     * @param codValue codValue
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * updDatimeを取得する
     *
     * @return updDatime updDatime
     */
    public Date getUpdDatime() {
        return updDatime;
    }

    /**
     * updDatimeを設定する
     *
     * @param updDatime updDatime
     */
    public void setUpdDatime(Date updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * codCdを取得する
     *
     * @return codCd codCd
     */
    public String getCodCd() {
        return codCd;
    }

    /**
     * codCdを設定する
     *
     * @param codCd codCd
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    /**
     * ユーザログインPWを取得する
     *
     * @return usrPassword ユーザログインPW
     */
    public String getUsrPassword() {
        return this.usrPassword;
    }

    /**
     * ユーザログインPWを設定する
     *
     * @param usrPassword ユーザログインPW
     */
    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    /**
     * 学研IDプライマリキーを取得する
     *
     * @return gidpk 学研IDプライマリキー
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 学研IDプライマリキーを設定する
     *
     * @param gidpk 学研IDプライマリキー
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * GIDフラグを取得する
     *
     * @return gidFlg GIDフラグ
     */
    public String getGidFlg() {
        return this.gidFlg;
    }

    /**
     * GIDフラグを設定する
     *
     * @param gidFlg GIDフラグ
     */
    public void setGidFlg(String gidFlg) {
        this.gidFlg = gidFlg;
    }
}
