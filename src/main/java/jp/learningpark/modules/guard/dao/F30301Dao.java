/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>トップニュース一覧画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/02/25: xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30301Dao {
    /**
     * お知らせ未読カウント数
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    Integer getNewsCount(@Param("guardId")String guardId,@Param("stuId")String stuId,@Param("orgId")String orgId);
}

