/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;


import jp.learningpark.framework.utils.R;

/**
 * <p>F00008_パスワード初期化 Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/27 : xiong: 新規<br />
 * @version 1.0
 */
public interface F00008Service {

    /**
     * パスワード初期化
     * @param afterUserId
     * @return
     */
    R initialPwd(String afterUserId);
}
