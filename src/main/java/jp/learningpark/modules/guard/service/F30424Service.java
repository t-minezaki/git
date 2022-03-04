/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.dto.F30424Dto;

/**
 * <p>F30424_保護者面談記録詳細画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/21 : wq: 新規<br />
 * @version 1.0
 */
public interface F30424Service {

    /**
     * @param guardId 保護者ID
     * @param messageId メッセージID
     * @param orgId 事項類型区分
     * @return
     */
    F30424Dto selectMessageInfo(String guardId, Integer messageId, String orgId);

    /**
     * @param messageId メッセージID
     * @return
     */
    R getInitData(Integer messageId);
}
