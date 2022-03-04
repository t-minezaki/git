package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;

import java.util.Map;

/**
 * <p>F40007_パスワード再設定画面 Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/29 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40007Service {

    /**
     * パスワード再設定
     * @param userId
     * 2021/10/11　MANAMIRU1-776 cuikl　Edit
     * @return
     */
    R resetPwd(Map<String,String> userId);
}
