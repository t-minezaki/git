/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * F00002 F02002_単元情報検索一覧画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
public class F02002Dto {

    /**
     * 組織ＩＤ
     */
    String orgId;
    /**
     * 組織名
     */
    String orgNm;
    /**
     * 本組織区分
     */
    Integer flg;
    /**
     * 階層
     */
    Integer level;

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

    /**
     * flgを取得する
     *
     * @return flg flg
     */
    public Integer getFlg() {
        return flg;
    }

    /**
     * flgを設定する
     *
     * @param flg flg
     */
    public void setFlg(Integer flg) {
        this.flg = flg;
    }

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
}
