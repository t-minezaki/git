/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F00004 利用者基本情報設定・修正 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/27 : gong: 新規<br />
 * @version 1.0
 */
public class F00004Dto {
    /**
     * ロール区分
     */
    private String roleDiv;

    /**
     * maxId
     */
    private Integer max;

    /**
     * ロール区分を設定する
     *
     * @return roleDiv ロール区分
     */
    public String getRoleDiv() {
        return this.roleDiv;
    }

    /**
     * ロール区分を取得する
     *
     * @param roleDiv ロール区分
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }

    /**
     * maxIdを設定する
     *
     * @return max maxId
     */
    public Integer getMax() {
        return this.max;
    }

    /**
     * maxIdを取得する
     *
     * @param max maxId
     */
    public void setMax(Integer max) {
        this.max = max;
    }
}
