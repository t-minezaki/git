package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.util.Date;

/**
 * <p>
 * F21005Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/19 ： NWT)hxl ： 新規作成
 * @date 2019/11/19 14:13
 */
public interface F21005Service {
    /**
     * <p>欠席の申請記録を取得する</p>
     *
     * @param day    日付
     * @param userId ユーザーID
     * @param orgId  組織ID
     * @param limit  検索数
     * @param page   ページ数
     * @return
     */
    R getLateAbsHistoryByDay(String roleDiv,Date day, String userId, String orgId, Integer limit, Integer page);
}
