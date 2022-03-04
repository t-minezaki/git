/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstGrpEntity;

/**
 * <p>F00051 グループ検索一覧画面 Dto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/18 : wen: 新規<br />
 * @version 1.0
 */
public class F00051Dto extends MstGrpEntity {


    /**
     * 排他チェック用更新日時
     */
    private String updateTimeForCheck;
    /**
     *
     */
    private String dayWeekStr;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 排他チェック用更新日時を取得する
     *
     * @return updateTimeForCheck 排他チェック用更新日時
     */
    public String getUpdateTimeForCheck() {
        return this.updateTimeForCheck;
    }

    /**
     * 排他チェック用更新日時を設定する
     *
     * @param updateTimeForCheck 排他チェック用更新日時
     */
    public void setUpdateTimeForCheck(String updateTimeForCheck) {
        this.updateTimeForCheck = updateTimeForCheck;
    }

    /**
     * を取得する
     *
     * @return dayWeekStr
     */
    public String getDayWeekStr() {
        return this.dayWeekStr;
    }

    /**
     * を設定する
     *
     * @param dayWeekStr
     */
    public void setDayWeekStr(String dayWeekStr) {
        this.dayWeekStr = dayWeekStr;
    }

    /**
     *
     * @return
     */
    @Override
    public String getOrgId() {
        return orgId;
    }

    /**
     * 組織ID
     * @param orgId　組織ID
     */
    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 組織名
     * @return 組織名
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * 組織名
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }
}
