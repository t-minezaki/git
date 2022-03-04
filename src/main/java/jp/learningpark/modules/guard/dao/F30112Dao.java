/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30112Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>塾からの連絡通知一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30112Dao {
    /**
     * 通知とイベントのデータを取得する
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @param page page
     * @return
     */
    List<F30112Dto> selectNotices(
            @Param("stuId") String stuId,
            @Param("guardId") String guardId, @Param("orgId") String orgId, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 通知とイベントの総数を取得する
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer selectNoticeCount(@Param("stuId") String stuId, @Param("guardId") String guardId, @Param("orgId") String orgId);

    /**
     * お知らせ未読カウント数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    Integer getNoticeUnreadCount(@Param("guardId") String guardId, @Param("stuId") String stuId, @Param("orgId") String orgId, @Param("levDiv") String levDiv);

    /**
     * 塾・教室ニュース未読件数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    F30112Dto updateStatus(
            @Param("id") Integer id,
            @Param("orgId") String orgId,
            @Param("stuId") String stuId,
            @Param("guardId") String guardId, @Param("guidReprId") Integer guidReprId, @Param("guidReprDeliverCd") String deliverCd);

}
