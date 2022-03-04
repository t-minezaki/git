/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.entity;

/**
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/9/27 : xie: 新規<br />
 * @version 1.0
 */
public class BTGKA1004OrgCommonKeyDto {
    /**
     * 組織ID
     */
    private String orgId;
    /**
     *変更後ユーザーID
     */
    private String gidpk;
    /**
     *組織共用キー
     */
    private String orgCommKey;

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織共用キーを取得する
     *
     * @return orgCommonKey 組織共用キー
     */
    public String getOrgCommonKey() {
        return this.orgCommKey;
    }

    /**
     * 組織共用キーを設定する
     *
     * @param orgCommKey 組織共用キー
     */
    public void setOrgCommonKey(String orgCommKey) {
        this.orgCommKey = orgCommKey;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return gidpk 変更後ユーザーID
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param gidpk 変更後ユーザーID
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * 組織共用キーを取得する
     *
     * @return orgCommKey 組織共用キー
     */
    public String getOrgCommKey() {
        return this.orgCommKey;
    }

    /**
     * 組織共用キーを設定する
     *
     * @param orgCommKey 組織共用キー
     */
    public void setOrgCommKey(String orgCommKey) {
        this.orgCommKey = orgCommKey;
    }
}
