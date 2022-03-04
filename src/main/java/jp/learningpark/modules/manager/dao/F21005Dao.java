package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21005Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * F21005Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/19 ： NWT)hxl ： 新規作成
 * @date 2019/11/19 13:34
 */
@Mapper
public interface F21005Dao {
    /**
     * <p>欠席の申請記録を取得する</p>
     *
     * @param day    日付別
     * @param userId ログインユーザID
     * @param orgId  ログイン組織ID
     * @param limit  検索数
     * @param offset ページ数
     * @return
     */
    List<F21005Dto> getLateAbsHistoryByDay(@Param("roleDiv")String roleDiv,@Param("day") Date day, @Param("userId") String userId,
                                           @Param("orgId") String orgId, @Param("limit") Integer limit,
                                           @Param("offset") Integer offset);

    /**
     * <p>レコードの総数を取得する</p>
     *
     * @param day    日付別
     * @param userId ログインユーザID
     * @param orgId  ログイン組織ID
     * @return
     */
    Integer getCount(@Param("roleDiv")String roleDiv,@Param("day") Date day, @Param("userId") String userId,
                     @Param("orgId") String orgId);
}
