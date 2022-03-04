/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30111Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>塾ニュース詳細表示画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30111Dao {
    /**
     *
     * @param id お知らせID
     * @param guardId 保護者ＩＤ
     * @param stuId 生徒ＩＤ
     * @return
     */
    F30111Dto getNoticeNews(@Param("id")Integer id, @Param("guardId")String guardId, @Param("stuId")String stuId);
}

