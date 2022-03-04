package jp.learningpark.modules.manager.service;

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
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 14:19
 */
public interface F21067Service {
    /**
     * 全体の送信生徒を抽出
     *
     * @param orgId     組織ID
     * @param messageId メッセージＩＤ
     * @return
     */
    R init(String orgId, Integer messageId);
}
