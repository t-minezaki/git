package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21013Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)lyh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/05 ： NWT)lyh ： 新規作成
 * @date 2019/12/05 14:29
 */
@Mapper
public interface F21013Dao {
    List<F21013Dto> select(@Param("orgId")String orgId,@Param("userId")String userId,@Param("month")String month,@Param("roleDiv")String roleDiv,@Param("stuIdListLast")Set<String> stuIdListLast,@Param("years")String years);
    List<F21013Dto> reSelect(@Param("orgId")String orgId,@Param("stuIdList")Set<String> stuIdList,@Param("value")String value,@Param("cd")String cd,@Param("month")String month,@Param("stuIdListLast")Set<String> stuIdListLast, @Param("roleDiv")String roleDiv,@Param("years")String years);
    List<F21013Dto> selectTen(@Param("orgId")String orgId,@Param("month")String month,@Param("stuIdListLast")Set<String> stu,@Param("cd")String cd,@Param("years")String years);
}
