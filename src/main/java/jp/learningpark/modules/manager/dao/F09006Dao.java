package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09006Dto;
import jp.learningpark.modules.manager.dto.F09006StuPointDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F09006Dao {
    /**
     * @param params 　パラメータ
     */
    List<F09006Dto> init(@Param("params") Map<String, Object> params, @Param("limit") Integer limit, @Param("page") Integer page);

    Integer initTotalCount(@Param("params") Map<String, Object> params);

    /**
     * @param stulist 　検索後のstulist
     */
    List<F09006Dto> check_after(@Param("stulist") List<String> stulist, @Param("orgId") String orgId, @Param("limit") Integer limit, @Param("page") Integer page);

    Integer checkAfterTotalCount(@Param("stulist") List<String> stulist, @Param("orgId") String orgId);

    /**
     * 調整ポイントを取得する
     * @param stuIdList ログインポイントを獲得した学生のコレクション
     * @param orgId 組織ID
     */
    List<F09006StuPointDto> moveHistoryPoint(@Param("stuIdList") List<F09006Dto> stuIdList,
                                       @Param("orgId") String orgId);

//    List<F09006StuPointDto> birthdayPoint(@Param("stuIdList") List<String> stuIdList, @Param("orgId") String orgId);
}
