package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F21003Dao {
    List<F21003Dto> select(@Param("userId")String userId, @Param("orgId")String orgId, @Param("corrspdSts")String corrspdSts, @Param("calendar")String tgtYmd,@Param("roleDiv")String roleDiv,@Param("limit") Integer limit,@Param("offset") Integer offset);
    Integer count(@Param("userId")String userId, @Param("orgId")String orgId, @Param("corrspdSts")String corrspdSts, @Param("calendar")String tgtYmd,@Param("roleDiv")String roleDiv);
}
