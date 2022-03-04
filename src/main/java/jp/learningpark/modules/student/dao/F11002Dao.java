package jp.learningpark.modules.student.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/27
 */
@Mapper
public interface F11002Dao {
    /**
     *
     * @param stuId
     * @return
     */
    String getMax(@Param("stuId")String stuId);
}
