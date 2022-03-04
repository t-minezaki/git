/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstOrgEntity;

/**
 * <p>F05004_知らせ照会画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/14 : gong: 新規<br />
 * @version 1.0
 */
public class F05004Dto extends MstOrgEntity {
    /**
     * 掲載予定開始日時
     */
    private String pubPlanStartDtStr;

    /**
     * 掲載予定終了日時
     */
    private String pubPlanEndDtStr;

    /**
     *  最新更新日時
     */
    private String updateStr;

    /**
     * orgId + ' ' +orgNm
     */
    private String orgNmDisplay;

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
     * orgId + ' ' +orgNmを設定する
     *
     * @return orgNmDisplay orgId + ' ' +orgNm
     */
    public String getOrgNmDisplay() {
        return this.orgNmDisplay;
    }

    /**
     * orgId + ' ' +orgNmを取得する
     *
     * @param orgNmDisplay orgId + ' ' +orgNm
     */
    public void setOrgNmDisplay(String orgNmDisplay) {
        this.orgNmDisplay = orgNmDisplay;
    }
}
