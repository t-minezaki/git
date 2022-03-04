package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21062Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
 * 2020/05/19 ： NWT)hxl ： 新規作成
 * @date 2020/05/19 18:00
 */
@Mapper
public interface F21062Dao {
    /**
     * <p>メッセージマスタを元に、本組織及び下位組織の塾ニュース情報を取得する</p>
     *
     * @param sOrgId         session 組織ＩＤ
     * @param orgIdList      session 組織ＩＤ及び下位組織ＩＤ
     * @param orgId          画面．検索条件．組織ＩＤ
     * @param id             画面．検索条件．メッセージID
     * @param messageTitle   画面．検索条件．件名
     * @param pubPlanStartDt 画面．検索条件．掲載開始日時
     * @param pubPlanEndDt   画面．検索条件．掲載終了日時
     * @param limit          １ページ最大件数
     * @param page           現在のページ数
     * @return
     */

    List<F21062Dto> searchOrg(@Param("sOrgId") String sOrgId,
                              @Param("orgIdList") List<String> orgIdList,
                              @Param("orgId") String orgId,
                              @Param("id") Integer id,
                              @Param("messageTitle") String messageTitle,
                              @Param("pubPlanStartDt") Date pubPlanStartDt,
                              @Param("pubPlanEndDt") Date pubPlanEndDt,
                              @Param("limit") Integer limit,
                              @Param("page") Integer page);

    /**
     * <p>総件数をとる</p>
     *
     * @param sOrgId            session 組織ＩＤ
     * @param orgIdList         session 組織ＩＤ及び下位組織ＩＤ
     * @param orgId             画面．検索条件．組織ＩＤ
     * @param id                画面．検索条件．メッセージID
     * @param messageTitle      画面．検索条件．件名
     * @param pubPlanStartDt    画面．検索条件．掲載開始日時
     * @param pubPlanEndDt      画面．検索条件．掲載終了日時
     * @return
     */
    Integer totalCount(@Param("sOrgId") String sOrgId,
                       @Param("orgIdList") List<String> orgIdList,
                       @Param("orgId") String orgId,
                       @Param("id") Integer id,
                       @Param("messageTitle") String messageTitle,
                       @Param("pubPlanStartDt") Date pubPlanStartDt,
                       @Param("pubPlanEndDt") Date pubPlanEndDt);
}
