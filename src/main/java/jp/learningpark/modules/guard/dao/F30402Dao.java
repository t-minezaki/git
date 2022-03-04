/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30402Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>塾からのイベント情報詳細画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/30: hujiaxing: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30402Dao {
    /**
     *
     * @param id お知らせID
     * @param guardId 保護者ＩＤ
     * @param stuId 生徒ＩＤ
     * @return
     */
    F30402Dto getEventNews(@Param("id") Integer id, @Param("guardId") String guardId, @Param("stuId") String stuId);
}

