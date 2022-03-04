/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>配信先既読未読状態確認一覧画面（ニュース）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/05 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F04006Dto {
    /**
     * 階層
     */
    private Integer level;

    /**
     * 組織フラグ
     */
    private Integer orgFlg;

    /**
     * 組織Id
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * カウント総件数
     */
    private Integer allCount;

    /**
     * 既読件数
     */
    private Integer readCount;

    /**
     * 未読件数
     */
    private Integer notReadCount;

    /**
     *通知プッシュ失敗件数
     */
    private Integer errorDataList;

    /**
     *未開封件数
     */
    private Integer notOpenedCount;

    /**
     * 開封済み件数
     */
    private Integer openedCount;

    public Integer getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(Integer errorDataList) {
        this.errorDataList = errorDataList;
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

    /**
     * orgFlgを取得する
     *
     * @return orgFlg orgFlg
     */
    public Integer getOrgFlg() {
        return orgFlg;
    }

    /**
     * orgFlgを設定する
     *
     * @param orgFlg orgFlg
     */
    public void setOrgFlg(Integer orgFlg) {
        this.orgFlg = orgFlg;
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

    /**
     * allCountを取得する
     *
     * @return allCount allCount
     */
    public Integer getAllCount() {
        return allCount;
    }

    /**
     * allCountを設定する
     *
     * @param allCount allCount
     */
    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    /**
     * readCountを取得する
     *
     * @return readCount readCount
     */
    public Integer getReadCount() {
        return readCount;
    }

    /**
     * readCountを設定する
     *
     * @param readCount readCount
     */
    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * notReadCountを取得する
     *
     * @return notReadCount notReadCount
     */
    public Integer getNotReadCount() {
        return notReadCount;
    }

    /**
     * notReadCountを設定する
     *
     * @param notReadCount notReadCount
     */
    public void setNotReadCount(Integer notReadCount) {
        this.notReadCount = notReadCount;
    }

    public Integer getNotOpenedCount() {
        return notOpenedCount;
    }

    public void setNotOpenedCount(Integer notOpenedCount) {
        this.notOpenedCount = notOpenedCount;
    }

    public Integer getOpenedCount() {
        return openedCount;
    }

    public void setOpenedCount(Integer openedCount) {
        this.openedCount = openedCount;
    }
}
