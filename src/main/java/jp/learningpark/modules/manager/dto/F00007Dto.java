/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>生徒集計画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/01/10 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F00007Dto {
    /**
     * 階層
     */
    private Integer level;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * levelを取得する
     *
     * @return level level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * levelを設定する
     *
     * @param level level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * orgIdを取得する
     *
     * @return orgId orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * orgIdを設定する
     *
     * @param orgId orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * orgNmを取得する
     *
     * @return orgNm orgNm
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * orgNmを設定する
     *
     * @param orgNm orgNm
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }
}
