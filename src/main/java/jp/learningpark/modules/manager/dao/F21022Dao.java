package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21022Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F21022Dao {
    List<F21022Dto> init(@Param("orgId")String orgId, @Param("time")String complimentDt,@Param("stuId")String stuId, @Param("limit") Integer limit, @Param("page") Integer page);
    Integer getCount(@Param("orgId")String orgId, @Param("time")String complimentDt,@Param("stuId")String stuId);
}
