package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F11004Dao {
    List<F11004Dto> init(@Param("userId") String userId);
}
