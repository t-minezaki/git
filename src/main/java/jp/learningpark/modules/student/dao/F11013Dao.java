package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11013Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/05/12
 */
@Mapper
public interface F11013Dao {
    /**
     *
     * @param eventId
     * @param stuId
     * @return
     */
    F11013Dto init(@Param("eventId") Integer eventId, @Param("stuId") String stuId);

    /**
     *
     * @param stuId
     * @return
     */
    String count(@Param("stuId") String stuId);
}
