package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08012Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F08012Dao {

    /**
     *
     * @param eventId イベントID
     * @param orgId 組織ID
     * @param params 検索条件
     * @param grpIdList グループID
     * @param schyDivList 学年区分
     * @return
     */
    List<F08012Dto> selectGuardAndStudent(@Param("eventId") Integer eventId, @Param("orgId") String orgId, @Param("params") Map<String,String> params,
                                          @Param("grpIdList") List<Integer> grpIdList, @Param("schyDivList") String schyDivList);
}
