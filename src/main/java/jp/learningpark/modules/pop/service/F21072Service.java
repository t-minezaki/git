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
 * 2020/06/03 ： NWT)hxl ： 新規作成
 * @date 2020/06/03 14:58
 */
public interface F21072Service {
    /**
     * 組織情報、面談メッセージ閲覧状況より、取得する。
     *
     * @param pageSize  1ページのMAX件数30件
     * @param messageId セッションデータ．ＩＤ
     * @param orgId     セッションデータ．組織ＩＤ
     * @param readFlg   既読未読フラグ
     * @return
     */
    R init(Integer messageId, String orgId, Integer pageSize, String readFlg, Integer limit);
}
