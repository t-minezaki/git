package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F08012Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface F08012Service {

    /**
     *
     * @param eventId イベントID
     * @param orgId 組織ID
     * @param params 検索条件
     * @param grpIdList グループID
     * @param schyDivList 学年区分
     * @return
     */
    List<F08012Dto> selectGuardAndStudent(Integer eventId, String orgId, Map<String,String> params , @Param("grpIdList") List<Integer> grpIdList, String schyDivList);
}
