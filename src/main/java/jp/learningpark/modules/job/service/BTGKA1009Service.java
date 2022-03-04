/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.service;

import jp.learningpark.framework.utils.R;

/**
 * 本棚入会日次バッチService
 *
 * @author NWT)ckl
 * @date 2021/3/22 16:27
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/22 ： NWT)ckl ： 新規作成
 */
public interface BTGKA1009Service {

    /**
     * 本棚へ連携
     * @return
     */
    R dayRegistbulk();

    R test();
}
