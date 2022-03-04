package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstMessageEntity;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/25 ： NWT)hxl ： 新規作成
 * @date 2020/05/25 11:22
 */
public class F21069Dto extends MstMessageEntity {
    /**
     * 画面．配信先組織リスト
     */
    private List<F21069DtoIn> adminList;
    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;
    /**
     * filePaths
     */
    private String filePaths;
    /**
     * テンプレートId
     */
    private Integer tempId;
    /**
     * 画面．配信先組織リストを取得する
     *
     * @return adminList 画面．配信先組織リスト
     */
    public List<F21069DtoIn> getAdminList() {
        return this.adminList;
    }

    /**
     * 画面．配信先組織リストを設定する
     *
     * @param adminList 画面．配信先組織リスト
     */
    public void setAdminList(List<F21069DtoIn> adminList) {
        this.adminList = adminList;
    }

    /**
     * 掲載予定開始日時を取得する
     *
     * @return pubPlanStartDtStr 掲載予定開始日時
     */
    public String getPubPlanStartDtStr() {
        return this.pubPlanStartDtStr;
    }

    /**
     * 掲載予定開始日時を設定する
     *
     * @param pubPlanStartDtStr 掲載予定開始日時
     */
    public void setPubPlanStartDtStr(String pubPlanStartDtStr) {
        this.pubPlanStartDtStr = pubPlanStartDtStr;
    }

    /**
     * 掲載予定終了日時を取得する
     *
     * @return pubPlanEndDtStr 掲載予定終了日時
     */
    public String getPubPlanEndDtStr() {
        return this.pubPlanEndDtStr;
    }

    /**
     * 掲載予定終了日時を設定する
     *
     * @param pubPlanEndDtStr 掲載予定終了日時
     */
    public void setPubPlanEndDtStr(String pubPlanEndDtStr) {
        this.pubPlanEndDtStr = pubPlanEndDtStr;
    }
    public String getFilePaths() {
        return filePaths;
    }

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
