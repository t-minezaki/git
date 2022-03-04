/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dto;

import java.sql.Timestamp;

/**
 * <p>教室選択画面</p>
 * <p>Dto</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
public class F40018Dto {

    /**
     * ロール区分
     */
    private String roleDiv;
    /**
     * ONETIMEパスワード
     */
    private String oneTimePw;
    /**
     * メールアドレス
     */
    private String mailad;
    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * ロール区分を取得する
     * @return roleDiv ロール区分
     */

    public String getRoleDiv() {
        return roleDiv;
    }

    /**
     * ロール区分を設定する
     * @param roleDiv ロール区分
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }

    /**
     * ONETIMEパスワードを取得する
     * @return oneTimePw ONETIMEパスワード
     */
    public String getOneTimePw() {
        return oneTimePw;
    }

    /**
     * ONETIMEパスワードを設定する
     * @param oneTimePw ONETIMEパスワード
     */
    public void setOneTimePw(String oneTimePw) {
        this.oneTimePw = oneTimePw;
    }

    /**
     * メールアドレスを取得する
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return mailad;
    }

    /**
     * メールアドレスを設定する
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 更新日時を取得する
     * @return updDatime 更新日時
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }

    /**
     * 更新日時を設定する
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
}
