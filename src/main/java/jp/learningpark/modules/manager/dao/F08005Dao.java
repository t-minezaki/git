/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.manager.dto.F08005Dto;
import jp.learningpark.modules.manager.dto.F08005ScheduleDto;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>F08005 イベント管理日程の設定画面 Dao</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/31 : wq: 新規<br />
 * @version 1.0
 */
public interface F08005Dao {

    /**
     * @param orgId 組織ID
     * @return
     */
    List<F08005Dto> selectUserCheckList(@Param("orgId") String orgId, @Param("userName") String userName);

    /**
     * @param orgNm 組織名
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param startDate 対象週開始日
     * @param endDate 対象週終了日
     * @return
     */
    List<F08005ScheduleDto> selectScheduleInfo(
            @Param("orgNm") String orgNm,
            @Param("orgId") String orgId, @Param("eventId") Integer eventId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param startDate 対象週開始日
     * @param endDate 対象週終了日
     * @return
     */
    List<F08005ScheduleDto> selectMentorScheduleInfo(
            @Param("orgId") String orgId, @Param("eventId") Integer eventId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return
     */
    Date selectMentorMinScheduleDate(@Param("orgId") String orgId, @Param("eventId") Integer eventId);

    /**
     * @param eventId
     * @param refId
     * @param sgdPlanDate
     * @param startTime
     * @param endTime
     * @return
     */
    List<EventScheduleEntity> selectScheduleForCheck(
            @Param("eventId") Integer eventId,
            @Param("refId") String refId, @Param("sgdPlanDate") Date sgdPlanDate, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);
}
