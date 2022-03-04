package jp.learningpark.modules.pop.dto;

/**
 * <p>保護者既読未読詳細一覧画面（ニュース）</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/06 : xiong: 新規<br />
 * @version 1.0
 */
public class F04007Dto {
    /**
     * 生徒ＩＤ
     */
    String stuId;
    /**
     * 保護者ＩＤ
     */
    String guardId;
    /**
     * 生徒氏名
     */
    String stunm;
    /**
     * 保護者氏名
     */
    String guardnm;
    /**
     * メールアドレス
     */
    String mailad;

    /**
     * ステータス
     */
    String readingStsDiv;

    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    public String getMailad() {
        return mailad;
    }

    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 生徒ＩＤを取得する
     * @return
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 生徒ＩＤを設定する
     * @param stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 保護者ＩＤを取得する
     * @return
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 保護者ＩＤを設定する
     * @return
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 生徒氏名を取得する
     * @return
     */
    public String getStunm() {
        return stunm;
    }

    /**
     * 生徒氏名を設定する
     * @return
     */
    public void setStunm(String stunm) {
        this.stunm = stunm;
    }

    /**
     * 保護者氏名を取得する
     * @return
     */
    public String getGuardnm() {
        return guardnm;
    }

    /**
     * 保護者氏名を設定する
     * @return
     */
    public void setGuardnm(String guardnm) {
        this.guardnm = guardnm;
    }
}
