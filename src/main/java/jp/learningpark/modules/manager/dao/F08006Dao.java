/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08006Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F08006_イベント日程時間設定画面(POP) Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/01 : yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F08006Dao {
    /**
     * 候補者リストを取得
     *
     * @param mentorname
     * @param orgId
     * @return
     */
    List<F08006Dto> getMentorList(@Param("mentorName") String mentorname,
                                  @Param("orgId") String orgId);

    /**
     * ユーザー存在チェック
     *
     * @param orgId
     * @param mentorId
     * @return
     */
    F08006Dto getuserEntity(@Param("orgId") String orgId, @Param("mentorId") String mentorId);
}
