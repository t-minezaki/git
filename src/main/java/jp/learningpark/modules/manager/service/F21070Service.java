package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F21069Dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/25 ： NWT)hxl ： 新規作成
 * @date 2020/05/25 18:26
 */
public interface F21070Service {
    /**
     * 初期表示
     *
     * @param messageId メッセージID
     * @return
     */
    R init(Integer messageId);

    /**
     * 登録処理
     *
     * @param dto
     * @param messageCont 　メッセージの内容
     * @param orgId       組織ID
     * @return
     */
    R doInsert(F21069Dto dto, String messageCont, String orgId);
}
