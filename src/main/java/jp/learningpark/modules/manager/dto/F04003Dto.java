/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstNoticeEntity;

import java.util.List;

/**
 * <p>F04003 塾ニュース編集画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/12 : wen: 新規<br />
 * @version 1.0
 */
public class F04003Dto extends MstNoticeEntity {
    /**
     * お知らせID
     */
    private Integer id;
    /**
     * 生徒ID
     */
    private String sId;
    /**
     * usrId
     */

    private String usrId;
    private String orgId;
    /**
     * 生徒氏名
     */

    private String stuName;
    /**
     * 保護者姓名
     */

    private String guardName;
    /**
     * 生徒+保護者姓名
     */

    private String guardStuNm;

    /**
     * 保護者ID
     */
    private String gId;

    /**
     * 画面．配信先組織リスト
     */
    private List<F04003DtoIn> stuList;


    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;
    /**
     * 日付を更新
     */

    private String UpdateStr;
    /**
     * 更新日時チェック
     */
    private String UpdateStrCheck;

    /**
     * お知らせIDを取得する
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * お知らせIDを設定する
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 生徒IDを取得する
     *
     * @return sId 生徒ID
     */
    public String getSId() {
        return this.sId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param sId 生徒ID
     */
    public void setSId(String sId) {
        this.sId = sId;
    }

    /**
     * usrIdを取得する
     *
     * @return usrId usrId
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * usrIdを設定する
     *
     * @param usrId usrId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * 生徒氏名を取得する
     *
     * @return stuName 生徒氏名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒氏名を設定する
     *
     * @param stuName 生徒氏名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 保護者姓名を取得する
     *
     * @return guardName 保護者姓名
     */
    public String getGuardName() {
        return this.guardName;
    }

    /**
     * 保護者姓名を設定する
     *
     * @param guardName 保護者姓名
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * 生徒+保護者姓名を取得する
     *
     * @return guardStuNm 生徒+保護者姓名
     */
    public String getGuardStuNm() {
        return this.guardStuNm;
    }

    /**
     * 生徒+保護者姓名を設定する
     *
     * @param guardStuNm 生徒+保護者姓名
     */
    public void setGuardStuNm(String guardStuNm) {
        this.guardStuNm = guardStuNm;
    }

    /**
     * 保護者IDを取得する
     *
     * @return gId 保護者ID
     */
    public String getGId() {
        return this.gId;
    }
    /**
     * 保護者ID
     */
    private String gUserId;
    /**
     * 保護者IDを設定する
     *
     * @param gId 保護者ID
     */
    public void setGId(String gId) {
        this.gId = gId;
    }

    /**
     * 画面．配信先組織リストを取得する
     *
     * @return stuList 画面．配信先組織リスト
     */
    public List<F04003DtoIn> getStuList() {
        return this.stuList;
    }

    /**
     * 画面．配信先組織リストを設定する
     *
     * @param stuList 画面．配信先組織リスト
     */
    public void setStuList(List<F04003DtoIn> stuList) {
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

    /**
     * 日付を更新を取得する
     *
     * @return UpdateStr 日付を更新
     */
    public String getUpdateStr() {
        return this.UpdateStr;
    }

    /**
     * 日付を更新を設定する
     *
     * @param UpdateStr 日付を更新
     */
    public void setUpdateStr(String UpdateStr) {
        this.UpdateStr = UpdateStr;
    }

    /**
     * 更新日時チェックを取得する
     *
     * @return UpdateStrCheck 更新日時チェック
     */
    public String getUpdateStrCheck() {
        return this.UpdateStrCheck;
    }

    /**
     * 更新日時チェックを設定する
     *
     * @param UpdateStrCheck 更新日時チェック
     */
    public void setUpdateStrCheck(String UpdateStrCheck) {
        this.UpdateStrCheck = UpdateStrCheck;
    }

    /**
     * を取得する
     *
     * @return orgId
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * を設定する
     *
     * @param orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    /**
     * 保護者IDを取得する
     */
    public String getgUserId() {
        return gUserId;
    }
    /**
     * 保護者IDを設定する
     */
    public void setgUserId(String gUserId) {
        this.gUserId = gUserId;
    }
}
