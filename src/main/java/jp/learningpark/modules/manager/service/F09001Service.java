package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09001HistoryDto;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * F09001サービス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/12 ： NWT)hxl ： 新規作成
 */
public interface F09001Service {
    /**
     * <p>ページ数に基づいてレコードを取得する</p>
     *
     * @param orgId 組織ID
     * @param startTime 開始時間範囲
     * @param endTime 終了時間範囲
     * @param limit 検索数
     * @param page ページ数
     * @return
     */
    R getEntryHistory(String orgId, String roleDiv, String userId, Timestamp startTime, Timestamp endTime, Integer limit, Integer page);

    /**
     * @param orgId 組織ID
     * @param startTime 開始時間範囲
     * @param endTime 終了時間範囲
     * @return
     */
    List<F09001HistoryDto> download(String orgId, String roleDiv, String userId, Timestamp startTime, Timestamp endTime);
}
