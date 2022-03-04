/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30408Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>塾からのイベント情報詳細画面</p >
 */
@Mapper
public interface F30408Dao {
    /**
     *
     * @param eventId イベントID
     * @return
     */
    List<F30408Dto> getEventNews(@Param("eventId") Integer eventId, @Param("guardId") String guardId);
}

