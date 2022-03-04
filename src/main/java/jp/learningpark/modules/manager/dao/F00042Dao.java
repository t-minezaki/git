/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>ユーザー初期基本情報＆新規発番画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/12: xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00042Dao {

    /**
     * 利用者ID重複チェック
     * @param userId   画面．利用者ログインＩＤ
     * @return
     */
    Integer getAfterUserId(@Param("userId") String userId);
}
