/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstNoticeEntity;

import java.util.List;

/**
 * <p>F05002 知らせ新規画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/7 : gong: 新規<br />
 * 2019/06/24:hujiaxing: mod<br />
 * @version 1.0
 */
public class F05002Dto extends MstNoticeEntity {
    /**
     * 画面．配信先組織リスト
     */
//    private List<OrgAndLowerOrgIdDto> orgList;
    //orgList modify to stuList
    private List<F05002DtoIn> stuList;
    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 保護者ID
     */
    private String guardId;

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
     * dtoListを取得する
     *
     * @return
     */
    public List<F05002DtoIn> getStuList() {
        return stuList;
    }

    /**
     * dtoListを設定する
     *
     * @param stuList dtoList
     */
    public void setStuList(List<F05002DtoIn> stuList) {
        this.stuList = stuList;
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
     * テンプレートを取得する
     *
     * @return tempId テンプレート
     */
    public Integer getTempId() {
        return this.tempId;
    }

    /**
     * テンプレートを設定する
     *
     * @param tempId テンプレート
     */
    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }
}
