package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>
 * F21004Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/26 ： NWT)hxl ： 新規作成
 * @date 2019/11/26 13:59
 */
public interface F21004Service {
    /**
     * IDで詳細な遅刻欠席連絡情報を取得
     *
     * @param id 遅刻欠席連絡履歴.ID
     * @return
     */
    R getDetail(Integer id);
}
