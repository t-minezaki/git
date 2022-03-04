package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>F40006 学研コミュニケーションアプリ Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/28 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40006Service {
    /**
     * 生徒の場合のみ、保護者のメールアドレスより判定する。
     * @param userId
     * @param email
     * @return
     */
    R confirmId(String userId, String email, String brandCd);
}
