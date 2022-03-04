package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21041Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/30
 */
@Mapper
public interface F21041Dao {
    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    List<F21041Dto> getDegree(@Param("stuId")String stuId, @Param("startDate")Date startDate,@Param("endDate")Date endDate);

    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    List<F21041Dto> getDegreeWeek(@Param("stuId")String stuId, @Param("startDate")Date startDate,@Param("endDate")Date endDate);

    /**
     *
     * @param stuId
     * @param startDate
     * @param endDate
     * @return
     */
    List<F21041Dto> getDegreeMonth(@Param("stuId") String stuId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * @param nowYear
     * @return
     */
    List<F21041Dto> getTalk(@Param("stuId") String stuId, @Param("nowYear") Date nowYear, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     *
     * @param id
     * @param orgId
     * @return
     */
    List<F21041Dto> getPop(@Param("id")Integer id,@Param("orgId")String orgId);
}
