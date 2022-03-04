package jp.learningpark.modules.manager.dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/21 ： NWT)hxl ： 新規作成
 * @date 2020/05/21 15:50
 */
public class F21065Dto {
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
    /**
     *通知プッシュ失敗件数
     */
    private Integer errorDataList;
    //2020/11/11 zhangminghao modify start
    /**
     * 開封済み件数
     */
    private Integer opened;

    /**
     * 未開封件数
     */
    private Integer notOpened;
    //2020/11/11 zhangminghao modify start
    public Integer getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(Integer errorDataList) {
        this.errorDataList = errorDataList;
    }

    /**
     * 組織ＩＤを取得する
     *
     * @return orgId 組織ＩＤ
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織ＩＤを設定する
     *
     * @param orgId 組織ＩＤ
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 組織階層を取得する
     *
     * @return level 組織階層
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 組織階層を設定する
     *
     * @param level 組織階層
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 配信総件数を取得する
     *
     * @return countSend 配信総件数
     */
    public Integer getCountSend() {
        return this.countSend;
    }

    /**
     * 配信総件数を設定する
     *
     * @param countSend 配信総件数
     */
    public void setCountSend(Integer countSend) {
        this.countSend = countSend;
    }

    /**
     * 未読件数を取得する
     *
     * @return countNotRead 未読件数
     */
    public Integer getCountNotRead() {
        return this.countNotRead;
    }

    /**
     * 未読件数を設定する
     *
     * @param countNotRead 未読件数
     */
    public void setCountNotRead(Integer countNotRead) {
        this.countNotRead = countNotRead;
    }

    /**
     * 既読件数を取得する
     *
     * @return countRead 既読件数
     */
    public Integer getCountRead() {
        return this.countRead;
    }

    /**
     * 既読件数を設定する
     *
     * @param countRead 既読件数
     */
    public void setCountRead(Integer countRead) {
        this.countRead = countRead;
    }

    /**
     * 組織マスタ．管理フラグを取得する
     *
     * @return mgrFlg 組織マスタ．管理フラグ
     */
    public String getMgrFlg() {
        return this.mgrFlg;
    }

    /**
     * 組織マスタ．管理フラグを設定する
     *
     * @param mgrFlg 組織マスタ．管理フラグ
     */
    public void setMgrFlg(String mgrFlg) {
        this.mgrFlg = mgrFlg;
    }

    /**
     * 組織ＩＤ＋半角スペース＋組織名を取得する
     *
     * @return orgIdNm 組織ＩＤ＋半角スペース＋組織名
     */
    public String getOrgIdNm() {
        return this.orgIdNm;
    }

    /**
     * 組織ＩＤ＋半角スペース＋組織名を設定する
     *
     * @param orgIdNm 組織ＩＤ＋半角スペース＋組織名
     */
    public void setOrgIdNm(String orgIdNm) {
        this.orgIdNm = orgIdNm;
    }
    //2020/11/4 zhangminghao modify start
    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getNotOpened() {
        return notOpened;
    }

    public void setNotOpened(Integer notOpened) {
        this.notOpened = notOpened;
    }
    //2020/11/4 zhangminghao modify end
}
