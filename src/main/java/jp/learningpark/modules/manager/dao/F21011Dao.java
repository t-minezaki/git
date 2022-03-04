package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21011Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F21011Dao {
    List<F21011Dto> select(@Param("orgId")String orgId,@Param("guidRepeCd")String guidRepeCd,@Param("page")Integer page,@Param("limit")Integer limit);
    List<F21011Dto> selectTotal(@Param("orgId")String orgId,@Param("guidRepeCd")String guidRepeCd);
    List<F21011Dto> reselect(@Param("stuIdList")Object stuIdList,@Param("page")Integer page,@Param("limit")Integer limit);
    List<F21011Dto> reselectTotal(@Param("stuIdList")Object stuIdList);
    List<F21011Dto> selectStatus();
}
