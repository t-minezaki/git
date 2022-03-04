/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30421Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>保護者知らせ画面(学研教室モード)</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/11 : wq: 新規<br />
 * @version 1.0
 */
public interface F30421Dao {

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @param limit
     * @return
     */
    List<F30421Dto> selectLists(
            @Param("orgId") String orgId,
            @Param("guardId") String guardId, @Param("stuId") String stuId,@Param("offset")Integer offset, @Param("limit") Integer limit);

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @param limit
     * @return
     */
    Integer selectCount(
            @Param("orgId") String orgId,
            @Param("guardId") String guardId, @Param("stuId") String stuId);

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer selectNoticeCount(@Param("orgId") String orgId, @Param("guardId") String guardId, @Param("stuId") String stuId);

    /**
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer selectEventCount(@Param("guardId") String guardId, @Param("stuId") String stuId);

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer selectCountChannel(@Param("orgId") String orgId, @Param("guardId") String guardId, @Param("stuId") String stuId);
}
