package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;
/**
 * <p>ワンタイムパスワード認証結果（成功）画面</p>
 * <p>Service</p>
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/8/6 : wang: 新規<br />
 * @version 8.0
 * 2020/9/22 : wang: 修正<br />
 * @version 8.0(ph1.5)
 */

public interface F40020Service {
    /**
     * @return userId
     */
    R confirmId();
    R update();
}
