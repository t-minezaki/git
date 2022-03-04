package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.util.Date;

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
 * @date 2020/05/22 17:18
 */
public interface F21068Service {
    /**
     * メッセージ情報を取得する
     *
     * @param sOrgId
     * @param orgId
     * @param id
     * @param messageTitle
     * @param pubPlanStartDt
     * @param pubPlanEndDt
     * @param limit
     * @param page
     * @return
     */
    R getDetail(String sOrgId, String orgId, Integer id, String messageTitle, Date pubPlanStartDt, Date pubPlanEndDt, Integer limit, Integer page);
}
