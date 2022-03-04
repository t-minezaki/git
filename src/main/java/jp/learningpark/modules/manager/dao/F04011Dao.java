package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.manager.dto.F04011Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>F04011_マナミルチャンネル一覧画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/08 : lyh: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04011Dao extends BaseMapper<F04011Dto> {
    /**
     * <p>お知らせマスタを元に、本組織及び上位組織の塾ニュース情報を取得する</p>
     *
     * @param sOrgId session 組織ＩＤ
     * @param orgId 画面．検索条件．組織ＩＤ
     * @param id 画面．検索条件．ニュースID
     * @param noticeTitle 画面．検索条件．件名
     * @param pubPlanStartDt 画面．検索条件．掲載開始日時
     * @param pubPlanEndDt 画面．検索条件．掲載終了日時
     * @param limit １ページ最大件数
     * @param page 現在のページ数
     * @return
     */
    List<F04011Dto> searchOrg(
            @Param("sOrgId") String sOrgId,
            @Param("orgId") String orgId,
            @Param("id") Integer id,
            @Param("noticeTitle") String noticeTitle,
            @Param("pubPlanStartDt")
                    Date pubPlanStartDt, @Param("pubPlanEndDt") Date pubPlanEndDt, @Param("limit") Integer limit, @Param("page") Integer page);
    /**
     * <p>総件数をとる</p>
     *
     * @param sOrgId session 組織ＩＤ
     * @param orgId 画面．検索条件．組織ＩＤ
     * @param id 画面．検索条件．ニュースID
     * @param noticeTitle 画面．検索条件．件名
     * @param pubPlanStartDt 画面．検索条件．掲載開始日時
     * @param pubPlanEndDt 画面．検索条件．掲載終了日時
     * @return
     */
    Integer getTotalCount(
            @Param("sOrgId") String sOrgId,
            @Param("orgId") String orgId,
            @Param("id") Integer id,
            @Param("noticeTitle") String noticeTitle, @Param("pubPlanStartDt") Date pubPlanStartDt, @Param("pubPlanEndDt") Date pubPlanEndDt);
}
