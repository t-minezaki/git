package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11010Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F11010Dao {
    /**
     * 初期化
     * @param stuId     生徒ID
     * @param limit     limit
     * @param page      ペイジー
     * @return
     */
    List<F11010Dto> getList(@Param("stuId") String stuId, @Param("limit") Integer limit
            , @Param("page") Integer page);

    Integer getCount(@Param("stuId") String stuId);

    Integer getUnreadCount(@Param("stuId") String stuId);
}
