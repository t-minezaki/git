package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30419Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * F30419Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/18 ： NWT)hxl ： 新規作成
 * @date 2020/02/18 14:34
 */
@Mapper
public interface F30419Dao {

    /**
     * チャンネルを取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @param page page
     * @return
     */
    List<F30419Dto> selectNews(
            @Param("guardId") String guardId, @Param("orgId") String orgId,@Param("stuId")String stuId,  @Param("offset")Integer offset, @Param("limit") Integer limit);

    /**
     * チャンネルを取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @param page page
     * @return
     */
    Integer selectCount(
            @Param("guardId") String guardId, @Param("orgId") String orgId,@Param("stuId")String stuId);
    /**
     * チャンネルの総数を取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer selectNewsCount(@Param("guardId") String guardId, @Param("orgId") String orgId,@Param("stuId")String stuId);

    /**
     * 未読チャンネルの総数を取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer selectUnreadCount(@Param("guardId") String guardId, @Param("orgId") String orgId,@Param("stuId")String stuId);
}
