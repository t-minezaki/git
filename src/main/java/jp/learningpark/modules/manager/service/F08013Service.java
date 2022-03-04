/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.manager.dto.F08013Dto;
import jp.learningpark.modules.manager.dto.F08013GuardApplyDto;

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
public interface F08013Service {
    /**
     * @param orgId 組織ID
     * @param inputNm 画面．ユーザ検索エリア．ユーザー名
     * @return
     */
    List<F08013Dto> getUserCheckList(String orgId, String inputNm);

    /**
     * @param orgId 組織ID
     * @param startDate 対象週開始日
     * @param endDate 対象週終了日
     * @return
     */
    List<F08013GuardApplyDto> getScheduleInfo(String orgId, Date startDate, Date endDate);

    /**
     * @param orgId 組織ID
     * @param eventTitle イベントタイトル
     * @return
     */
    List<MstEventEntity> getEventInfo(String orgId, String eventTitle);

    /**
     * @param orgId 組織ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return
     */
    List<F08013GuardApplyDto> getEventScheDelInfo(String orgId, Date startDate, Date endDate);

    /**
     * @param eventScheDelId イベント日程(明細)ID
     * @return
     */
    F08013GuardApplyDto getGuardInfo(Integer eventScheDelId);

    /**
     * @param eventScheDelId イベント日程(明細)ID
     * @return
     */
    F08013GuardApplyDto getStudentInfo(Integer eventScheDelId);
}
