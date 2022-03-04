package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/24
 */
@Mapper
public interface F11001Dao {
    /**
     *
     * @param stuId
     * @return
     */
    List<F11001Dto> getSubjt(@Param("stuId")String stuId);
}
