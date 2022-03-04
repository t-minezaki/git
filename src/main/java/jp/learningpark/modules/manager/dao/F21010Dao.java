package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21010Dto;
import jp.learningpark.modules.pop.dto.F04007Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F21010Dao {
    List<F21010Dto> select(@Param("orgId")String orgId,@Param("paramsMap") Map<String,String> paramsMap,@Param("limit") Integer limit,@Param("offset") Integer offset);
    Integer count(@Param("orgId")String orgId,@Param("paramsMap") Map<String,String> paramsMap);
    List<F04007Dto> getDetail(@Param("guidReprDeliverCd")String guidReprDeliverCd, @Param("readFlg") String readFlg,@Param("orgId")String orgId);
}
