
package jp.learningpark.modules.job.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>BTGKA1013_有効期限超過デバイストーケン無効化バッチ</p>
 *
 * @author NWT : liguangxin <br />
 * 変更履歴 <br />
 * 2021/10/08 : xie: 新規<br />
 * @version 1.0
 */
public interface BTGKA1013Service {

    /**
     * 有効期限超過デバイストーケン無効化バッチ
     * @return R
     */
    R updateDelFlgByDeviceId();
}
