/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00042Dto;
/**
 * <p>ユーザー初期基本情報＆新規発番画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/12: xiong: 新規<br />
 * @version 1.0
 */
public interface F00042Service {

    /**
     * 利用者ID重複チェック
     * @param userId   画面．利用者ログインＩＤ
     * @return
     */
    Integer getAfterUserId(String userId);
    /**
     * ユーザー初期基本情報
     * @param f00042Dto
     * @return
     */
    R getInformation(F00042Dto f00042Dto);
}
