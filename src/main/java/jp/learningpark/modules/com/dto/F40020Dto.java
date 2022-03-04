/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dto;
/**
 * <p></p>
 * <pF40020>Dto</p>
 * <p></p>
 *
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/9/22 : wang: 新規<br />
 * @version 1.0
 */
public class F40020Dto {
    /**
     * ロール区分
     */
    private String roleDiv;
    /**
     * 初回登録フラグ
     */
    private String fstLoginFlg;
    /**
     * 他システム区分
     */
    private String systemKbn;
    /**
     * Web申込利用フラグ
     */
    private String webUseFlg;
    /**
     * PW更新フラグ
     */
    private String pwUpFlg;

    /**
     * ロール区分を取得する
     * @return roleDiv ロール区分
     */
    public String getRoleDiv() {
        return roleDiv;
    }
    /**
     * 初回登録フラグ取得する
     * @return fstLoginFlg 初回登録フラグ
     */

    public String getFstLoginFlg(){ return fstLoginFlg;}
    /**
     * 他システム区分取得する
     * @return systemKbn 他システム区分
     */
    public String getSystemKbn(){return systemKbn;}
    /**
     * Web申込利用フラグ取得する
     * @return webUseFlg Web申込利用フラグ
     */
    public String getWebUseFlg(){return webUseFlg;}

    /**
     * ロール区分を設定する
     * roleDiv ロール区分
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }
    /**
     * 初回登録フラグ設定する
     *  fstLoginFlg 初回登録フラグ
     */
    public void setFstLoginFlg(String fstLoginFlg){ this.fstLoginFlg=fstLoginFlg;}
    /**
     * 他システム区分設定する
     *  systemKbn 他システム区分
     */
    public void setSystemKbn(String systemKbn){this.systemKbn=systemKbn;}
    /**
     * Web申込利用フラグ設定する
     * webUseFlg Web申込利用フラグ
     */
    public void setWebUseFlg(String webUseFlg){this.webUseFlg=webUseFlg;}

    /**
     * PW更新フラグを取得する
     *
     * @return pwUpFlg PW更新フラグ
     */
    public String getPwUpFlg() {
        return this.pwUpFlg;
    }

    /**
     * PW更新フラグを設定する
     *
     * @param pwUpFlg PW更新フラグ
     */
    public void setPwUpFlg(String pwUpFlg) {
        this.pwUpFlg = pwUpFlg;
    }
}
