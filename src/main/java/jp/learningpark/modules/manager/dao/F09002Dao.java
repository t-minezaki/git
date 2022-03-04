package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09002Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F09002Dao {

    /**
     *
     * @param orgId 塾ID
     * @param stuIdList 生徒リステ
     * @return
     */
    List<F09002Dto> init(@Param("orgId") String orgId, @Param("stuIdList") List<String> stuIdList,@Param("userId")String userId,@Param("roleDiv")String roleDiv,@Param("limit")Integer limit,@Param("page")Integer page);
}
