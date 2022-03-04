/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21017Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
public interface F21017Dao {

    /**
     * @param orgId 組織ID
     * @return
     */
    Integer orgDatasCount(@Param("orgId") String orgId);

    /**
     * @param mentorId 　先生ID
     * @param orgId 　組織ID
     * @return
     */
    Integer mentorDatasCount(@Param("mentorId") String mentorId, @Param("crmschId") String orgId);

    /**
     * 未確認連絡数の取得
     *
     * @param roleDiv
     * @param userId
     * @param orgId
     * @param date
     * @return
     */
    Integer getUnreadCount(@Param("roleDiv") String roleDiv, @Param("userId") String userId, @Param("orgId") String orgId, @Param("date") String date);

    /**
     * @param map
     * @return
     */
    List<F21017Dto> getStuInfo(Map<String, Object> map);

    /**
     * <p>学生総数を取得する。</p>
     * <p>
     * add at 2021/08/10 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    Integer getStudentCount(Map<String, Object> map);
}
