
package jp.learningpark.modules.job.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>BTGKA1006_創学高等部生徒連携ファイル導入日次バッチ</p>
 *
 * @author NWT : liguangxin <br />
 * 変更履歴 <br />
 * 2020/12/24 : xie: 新規<br />
 * @version 1.0
 */
public interface BTGKA1006Service {

    /**
     * GoogleSheet ファイルからデータを導入する
     * @return R
     */
    R importDateFromGoogleSheet();
}
