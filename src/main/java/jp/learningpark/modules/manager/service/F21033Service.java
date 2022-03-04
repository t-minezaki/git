/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

public interface F21033Service {
    /**
     * 各対象日をもとに、組織単位で全て生徒の実績個数と計画個数を集計する
     *
     * @param ymdStart
     * @param ymdEnd
     * @param orgId
     * @return
     */
    Double getSchoolData(String ymdStart, String ymdEnd, String orgId);

    /**
     * 各対象日をもとに、指定したグループ単位で全て生徒の実績個数と計画個数を集計する
     * @param ymdStart
     * @param ymdEnd
     * @param grpId
     * @return
     */

    Double getGroupData(String ymdStart, String ymdEnd, Integer grpId);

    Double getMap2(String ymdStart, String ymdEnd, String orgId, String schyDiv, Integer grpId, String lineType);

    R getInformation(String usrId, Integer limit, Integer page);
}
