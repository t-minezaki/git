package jp.learningpark.modules.job.service;

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
 * 2020/8/26 ： NWT)hxl ： 新規作成
 * @date 2020/8/26 12:14
 */
public interface BTGKA1004Service {

    /**
     * csvファイルからデータをインポートする
     * @return R
     */
    R importDateFromCsvFile();

    R updatePassword();
}
