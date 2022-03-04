package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21007Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>F21007_出席簿一覧画面 Service</p >
 *
 * @author NWT : LiYuHuan <br />
 * @author NWT文
 * @version 9.0要件変更 2020/11/11
 */
public interface F21007Service {
    /**
     * <p>出席簿情報を取得する</p>
     *
     * @param orgId 組織ID
     * @param date 　日付
     * @param grpNm グループ名
     * @return
     */
    List<F21007Dto> selectAll(String orgId, Date date, String grpNm);

    /**
     * <p>出席簿ヘーダから回数を算出する</p>
     *
     * @param orgId 　組織ID
     * @param grpId 　グループID
     * @param date 　日付
     * @return
     */
    Integer selectNumMax(String orgId, Integer grpId, Date date);

    /**
     * <p>グループ情報を取得する</p>
     *
     * @param orgId 　組織ID
     * @param grpIdList 　グループIDリスト
     * @param dayweekDiv 　曜日区分
     * @param grpNm グループ名
     * @return
     */
    List<F21007Dto> selectMstGrpNm(String orgId, List<Integer> grpIdList, String dayweekDiv, String grpNm);

}
