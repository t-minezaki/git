package jp.learningpark.modules.student.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/14 ： NWT)hxl ： 新規作成
 */
public interface F11020Service {

    /**
     * 面談記録を取得し
     *
     * @param messageId メッセージID
     * @param stuId 生徒ID
     * @param deliverId deliverId
     * @return
     */
    R getAskAndTalkList(Integer messageId, String stuId, String deliverId);
}
