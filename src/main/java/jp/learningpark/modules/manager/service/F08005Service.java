/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F08005ScheduleDto;

/**
 * <p>F08005 イベント管理日程の設定画面 Service</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/31 : wq: 新規<br />
 * @version 1.0
 */
public interface F08005Service {

    /**
     * @param eventId イベントID
     * @param userName ユーザー名
     * @return
     */
    R getInitData(Integer eventId, String userName);

    /**
     * @param eventId 　イベントID
     * @param tgtYmd 　対象年月日
     * @return
     */
    R getEventScheduleData(Integer eventId, String tgtYmd);

    /**
     * @param entity
     * @return
     */
    R updateDB(F08005ScheduleDto entity);
}
