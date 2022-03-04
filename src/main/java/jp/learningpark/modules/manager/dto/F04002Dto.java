/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstNoticeEntity;

import java.util.List;

/**
 * <p>F04002 塾ニュース新規画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/06 : wen: 新規<br />
 * @version 1.0
 */
public class F04002Dto extends MstNoticeEntity {

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 学生リスト
     */
    private List<F04002DtoIn> stuList;


    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;
    /**
     * 組織名+学生名
     */

    private String guardStuNm;
    /**
     * 学生リストを取得する
     */

    public List<F04002DtoIn> getStuList() {
        return stuList;
    }
    /**
     * 学生リストを設定する
     */
    public void setStuList(List<F04002DtoIn> stuList) {
        this.stuList = stuList;
    }

    /**
     * 組織名+学生名を取得する
     */
    public String getGuardStuNm() {
        return guardStuNm;
    }
    /**
     * 組織名+学生名を設定する
     */

    public void setGuardStuNm(String guardStuNm) {
        this.guardStuNm = guardStuNm;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
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
}
