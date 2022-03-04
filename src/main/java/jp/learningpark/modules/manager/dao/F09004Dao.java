package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F09004Dao {

    /**
     *
     * @param orgId 組織ID
     * @param params　パラメータ
     * @return
     */
    List<F09004Dto> init(@Param("orgId") String orgId, @Param("params") Map<String, Object> params,@Param("roleDiv") String roleDiv,@Param("userId") String userId);
}
