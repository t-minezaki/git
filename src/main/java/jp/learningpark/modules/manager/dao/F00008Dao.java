/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>F00008_パスワード初期化 Dao</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/27 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00008Dao {
    /**
     * パスワード初期化
     *
     * @param userId
     * @param orgId
     * @return
     */
    MstUsrEntity selectByUserId(@Param("userId") String userId,@Param("orgId") String orgId);
}
