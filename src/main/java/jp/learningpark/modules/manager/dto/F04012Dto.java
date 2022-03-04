package jp.learningpark.modules.manager.dto;

import java.util.List;

/**
 * <p>
 * マナミルチャンネル新規·編集 Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/10 ： NWT)hxl ： 新規作成
 * @date 2020/02/10 13:31
 */
public class F04012Dto {
    /**
     * お知らせタイトル
     */
    private String title;
    /**
     * お知らせ内容
     */
    private String content;
    /**
     * お知らせレベル区分
     */
    private String noticeLevel;
    /**
     * 掲載予定開始日時
     */
    private String startDate;
    /**
     * 掲載予定終了日時
     */
    private String endDate;

    /**
     * 全体配信フラグ
     */
    private String allDeliverFlg;

    /**
     * 組織IDリスト
     */
    private List<String> orgIdList;

    /**
     * 保護者ＩＤ
     */
    private String guardId;
    /**
     * 組織ＩＤ
     */
    private String orgId;

    /**
     * mgrFlg
     */
    private String mgrFlg;

    /**
     * 生徒ＩＤ
     */
    private String stuId;

    /**
     * filePaths
     */
    private String filePaths;

    /**
     * テンプレートId
     */
    private Integer tempId;

    /**
     * 保護者ＩＤを取得する
     *
     * @return
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 保護者ＩＤを設定する
     *
     * @param guardId
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 組織ＩＤを取得する
     *
     * @return
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 組織ＩＤを設定する
     *
     * @param orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 全体配信フラグを取得する
     *
     * @return
     */
    public String getAllDeliverFlg() {
        return allDeliverFlg;
    }

    /**
     * 全体配信フラグを設定する
     *
     * @param allDeliverFlg
     */
    public void setAllDeliverFlg(String allDeliverFlg) {
        this.allDeliverFlg = allDeliverFlg;
    }

    /**
     * お知らせタイトルを取得する
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * お知らせタイトルを設定する
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * お知らせ内容を取得する
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * お知らせ内容を設定する
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * お知らせレベル区分を取得する
     *
     * @return
     */
    public String getNoticeLevel() {
        return noticeLevel;
    }

    /**
     * お知らせレベル区分を設定する
     *
     * @param noticeLevel
     */
    public void setNoticeLevel(String noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    /**
     * 掲載予定開始日時を取得する
     *
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 掲載予定開始日時を設定する
     *
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 掲載予定終了日時を取得する
     *
     * @return
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 掲載予定終了日時を設定する
     *
     * @param endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 組織IDリストを取得する
     *
     * @return
     */
    public List<String> getOrgIdList() {
        return orgIdList;
    }

    /**
     * 組織IDリストを設定する
     *
     * @param orgIdList
     */
    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    /**
     * mgrFlgを取得する
     *
     * @return mgrFlg mgrFlg
     */
    public String getMgrFlg() {
        return this.mgrFlg;
    }

    /**
     * mgrFlgを設定する
     *
     * @param mgrFlg mgrFlg
     */
    public void setMgrFlg(String mgrFlg) {
        this.mgrFlg = mgrFlg;
    }

    /**
     * 生徒ＩＤを取得する
     *
     * @return stuId 生徒ＩＤ
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒ＩＤを設定する
     *
     * @param stuId 生徒ＩＤ
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * filePathsを取得する
     *
     * @return filePaths filePaths
     */
    public String getFilePaths() {
        return this.filePaths;
    }

    /**
     * filePathsを設定する
     *
     * @param filePaths filePaths
     */
    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    /**
     * テンプレートIdを取得する
     *
     * @return tempId テンプレートId
     */
    public Integer getTempId() {
        return this.tempId;
    }

    /**
     * テンプレートIdを設定する
     *
     * @param tempId テンプレートId
     */
    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }
}
