/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F00061Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00061_メンター生徒関係検索一覧 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/18 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00061Dao {
    /**
     * 生徒メンター関係一覧を表示するため、下記条件でメンター生徒管理、生徒基本マスタ、メンター基本マスタを元に一覧情報を取得し画面で表示する。
     *
     * @param dto
     * @param limit
     * @param page
     * @return
     */
    List<F00061Dto> selectList(@Param("dto") F00061Dto dto, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 生徒メンター関係一覧の件数
     *
     * @param dto   画面．検索条件
     * @return
     */
    Integer count(F00061Dto dto);
}
