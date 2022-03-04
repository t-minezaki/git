package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstMessageEntity;

import java.util.List;

/**
 * <p>F21063_メッセージ作成画面 Dto</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/20 ： NWT)hxl ： 新規作成
 * 2020/11/11 ： NWT)文 ： 要件変更
 */
public class F21063Dto extends MstMessageEntity {
    /**
     * 画面．配信先組織リスト
     */
    private List<F21063DtoIn> stuList;
    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;
    private String reSentFlg;
    /**
     * テンプレートId
     */
    private Integer tempId;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    /**
     * filePaths
     */
    private String filePaths;
    /**
     * 画面．配信先組織リストを取得する
     *
     * @return stuList 画面．配信先組織リスト
     */
    public List<F21063DtoIn> getStuList() {
        return this.stuList;
    }

    /**
     * 画面．配信先組織リストを設定する
     *
     * @param stuList 画面．配信先組織リスト
     */
    public void setStuList(List<F21063DtoIn> stuList) {
        this.stuList = stuList;
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

    public String getReSentFlg() {
        return reSentFlg;
    }

    public void setReSentFlg(String reSentFlg) {
        this.reSentFlg = reSentFlg;
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
