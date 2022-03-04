/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstNoticeEntity;

import java.util.List;

/**
 * <p>F05003_知らせ編集画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/7 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
public class F05003Dto extends MstNoticeEntity {
    /**
     * 画面．配信先組織リスト
     */
    private List<F05003DtoIn> stuList;

    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;
    /**
     * 再通知flg
     */
    private String reSentFlg;


    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     *  最新更新日時
     */
    private String updateStr;

    /**
     * 排他チェックの利用更新日時
     */
    private String updateStrCheck;

    /**
     * filePaths
     */
    private String filePaths;

    /**
     * テンプレートId
     */
    private Integer tempId;

//    /**
//     * 画面．配信先組織リストを設定する
//     *
//     * @return orgList 画面．配信先組織リスト
//     */
//    public List<OrgAndLowerOrgIdDto> getOrgList() {
//        return this.orgList;
//    }
//
//    /**
//     * 画面．配信先組織リストを取得する
//     *
//     * @param orgList 画面．配信先組織リスト
//     */
//    public void setOrgList(List<OrgAndLowerOrgIdDto> orgList) {
//        this.orgList = orgList;
//    }

    /**
     * 掲載予定開始日時を設定する
     *
     * @return pubPlanStartDtStr 掲載予定開始日時
     */
    public String getPubPlanStartDtStr() {
        return this.pubPlanStartDtStr;
    }

    /**
     * 掲載予定開始日時を取得する
     *
     * @param pubPlanStartDtStr 掲載予定開始日時
     */
    public void setPubPlanStartDtStr(String pubPlanStartDtStr) {
        this.pubPlanStartDtStr = pubPlanStartDtStr;
    }

    /**
     * 掲載予定終了日時を設定する
     *
     * @return pubPlanEndDtStr 掲載予定終了日時
     */
    public String getPubPlanEndDtStr() {
        return this.pubPlanEndDtStr;
    }

    /**
     * 掲載予定終了日時を取得する
     *
     * @param pubPlanEndDtStr 掲載予定終了日時
     */
    public void setPubPlanEndDtStr(String pubPlanEndDtStr) {
        this.pubPlanEndDtStr = pubPlanEndDtStr;
    }

    /**
     * 生徒IDを設定する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを取得する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 保護者IDを設定する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを取得する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 最新更新日時を設定する
     *
     * @return updateStr 最新更新日時
     */
    public String getUpdateStr() {
        return this.updateStr;
    }

    /**
     * 最新更新日時を取得する
     *
     * @param updateStr 最新更新日時
     */
    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }

    /**
     * 排他チェックの利用更新日時を設定する
     *
     * @return updateStrCheck 排他チェックの利用更新日時
     */
    public String getUpdateStrCheck() {
        return this.updateStrCheck;
    }

    /**
     * 排他チェックの利用更新日時を取得する
     *
     * @param updateStrCheck 排他チェックの利用更新日時
     */
    public void setUpdateStrCheck(String updateStrCheck) {
        this.updateStrCheck = updateStrCheck;
    }

    public List<F05003DtoIn> getStuList() {
        return stuList;
    }
    /**
     * <p>get student dto list by student id list</p>
     * @param stuIdList student id list
     * @return
     */
    public void setStuList(List<F05003DtoIn> stuList) {
        this.stuList = stuList;
    }

    public String getReSentFlg() {
        return reSentFlg;
    }

    public void setReSentFlg(String reSentFlg) {
        this.reSentFlg = reSentFlg;
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
