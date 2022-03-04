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
 * 2020/06/03 ： NWT)hxl ： 新規作成
 * @date 2020/06/03 11:32
 */
public interface F21073Service {
    /**
     * 全体の送信管理者と先生を抽出
     *
     * @param orgId     組織ID
     * @param messageId メッセージＩＤ
     * @return
     */
    R init(String orgId, Integer messageId);
}
