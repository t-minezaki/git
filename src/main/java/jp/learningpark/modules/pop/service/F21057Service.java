package jp.learningpark.modules.pop.service;

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
 * 2020/06/02 ： NWT)hxl ： 新規作成
 * @date 2020/06/02 14:13
 */
public interface F21057Service {
    /**
     * 塾の連絡通知を取得し
     *
     * @param usrId     セッションデータ．先生ＩＤ OR セッションデータ．管理者ＩＤ
     * @param messageId メッセージID
     * @return
     */
    R getDetail(String usrId, Integer messageId);
}
