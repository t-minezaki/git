package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11005Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface F11005Dao {
    F11005Dto init(@Param("id") Integer id);
}
