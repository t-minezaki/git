package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;


/**
 * <p>
 * F21014Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/05 ： NWT)hxl ： 新規作成
 * @date 2019/12/05 14:20
 */
public interface F21014Service {
    /**
     * 該当生徒の全部指導報告書及出席簿明細内容を取得する
     *
     * @param month 年月
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    R getAttendBookByMonth(String month, String orgId, String stuId);
}
