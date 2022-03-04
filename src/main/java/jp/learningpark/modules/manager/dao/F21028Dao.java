package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21028Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * F21028Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/13 ： NWT)hxl ： 新規作成
 * @date 2020/02/13 12:04
 */
@Mapper
public interface F21028Dao {
    /**
     * 学年の区別を取得する
     *
     * @return
     */
    List<F21028Dto> getSchy();

    /**
     * グループの区別を取得する
     *
     * @param orgId 組織ID
     * @return
     */
    List<F21028Dto> getGroup(@Param("orgId") String orgId);

    /**
     * 生徒情報を取得する
     *
     * @param params パラメータ
     * @return
     */
    List<F21028Dto> getStuList(@Param("params") Map<String, Object> params);

    /**
     * 生徒情報を取得する
     *
     * @param params パラメータ
     * @return
     */
    List<F21028Dto> getStuListByIdList(@Param("params") Map<String, Object> params);

    /**
     * 生徒情報を取得する
     *
     * @param stuIdList 生徒IDリスト
     * @param startDate 開始日期
     * @param endDate   終了日期
     * @return
     */
    List<F21028Dto> getDataByDay(@Param("stuIdList") List<String> stuIdList, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 生徒情報を取得する
     *
     * @param stuIdList 生徒IDリスト
     * @param startDate 開始日期
     * @param endDate   終了日期
     * @return
     */
    List<F21028Dto> getDataByWeek(@Param("stuIdList") List<String> stuIdList, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 生徒情報を取得する
     *
     * @param stuIdList 生徒IDリスト
     * @param startDate 開始日期
     * @param endDate   終了日期
     * @return
     */
    List<F21028Dto> getDataByMonth(@Param("stuIdList") List<String> stuIdList, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
