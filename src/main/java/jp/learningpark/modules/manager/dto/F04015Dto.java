/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;
/**
 * <p>F04015_配信先既読未読状態確認一覧画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/17 : zpa: 新規<br />
 * @version 1.0
 */
public class F04015Dto {
    /**
     * 組織ＩＤ
     */
    private String orgId;
    /**
     * 組織名
     */
    private String orgNm;
    /**
     * 組織階層
     */
    private Integer level;
    /**
     * 配信総件数
     */
    private Integer countSend;
    /**
     * 未読件数
     */
    private Integer countNotRead;
    /**
     * 既読件数
     */
    private Integer countRead;
    /**
     * 組織マスタ．管理フラグ
     */
    private String mgrFlg;
    /**
     * 組織ＩＤ＋半角スペース＋組織名
     */
    private String orgIdNm;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCountSend() {
        return countSend;
    }

    public void setCountSend(Integer countSend) {
        this.countSend = countSend;
    }

    public Integer getCountNotRead() {
        return countNotRead;
    }

    public void setCountNotRead(Integer countNotRead) {
        this.countNotRead = countNotRead;
    }

    public Integer getCountRead() {
        return countRead;
    }

    public void setCountRead(Integer countRead) {
        this.countRead = countRead;
    }

    public String getMgrFlg() {
        return mgrFlg;
    }

    public void setMgrFlg(String mgrFlg) {
        this.mgrFlg = mgrFlg;
    }

    public String getOrgIdNm() {
        return orgIdNm;
    }

    public void setOrgIdNm(String orgIdNm) {
        this.orgIdNm = orgIdNm;
    }
}
