/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.manager.dto.F08013Dto;
import jp.learningpark.modules.manager.dto.F08013GuardApplyDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>配信設定状況確認カレンダー表示画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/12 : wq: 新規<br />
 * @version 1.0
 */
public interface F08013Dao {

    /**
     * @param orgId 組織ID
     * @param inputNm 画面．ユーザ検索エリア．ユーザー名
     * @return
     */
    List<F08013Dto> selectUserCheckList(@Param("orgId") String orgId, @Param("inputNm") String inputNm);

    /**
     * @param orgId 組織ID
     * @param startDate 対象週開始日
     * @param endDate 対象週終了日
     * @return
     */
    List<F08013GuardApplyDto> selectScheduleInfo(@Param("orgId") String orgId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * @param orgId 組織ID
     * @param eventTitle イベントタイトル
     * @return
     */
    List<MstEventEntity> selectEventInfo(@Param("orgId") String orgId, @Param("eventTitle") String eventTitle);

    /**
     * @param eventScheDelId イベント日程(明細)ID
     * @return
     */
    F08013GuardApplyDto selectGuardInfo(@Param("eventScheDelId") Integer eventScheDelId);

    /**
     * @param eventScheDelId イベント日程(明細)ID
     * @return
     */
    F08013GuardApplyDto selectStudentInfo(@Param("eventScheDelId") Integer eventScheDelId);

    /**
     * @param orgId     組織ID
     * @param startDate 開始日
     * @param endDate   終了日
     * @return
     */
    List<F08013GuardApplyDto> selectEventScheDelInfo(@Param("orgId") String orgId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
