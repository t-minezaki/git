/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21026Dto;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
public interface F21026Dao {

    /**
     *  生徒基本情報データの取得「管理者」の場合
     * @param orgId 組織ID
     * @return
     */
    //2021/10/14　MANAMIRU1-809 huangxinliang　Edit　Start
    F21026Dto getStu(@Param("orgId") String orgId, @Param("stuId")String stuId,@Param("date")String date, @Param("startTime") Timestamp startTime,
                     @Param("endTime")Timestamp endTime);
    Integer login(@Param("stuId")String stuId,@Param("date")String date, @Param("startTime") Timestamp startTime,
                  @Param("endTime")Timestamp endTime);
    // 2021/10/14　MANAMIRU1-809 huangxinliang　Edit　End
}
