package jp.learningpark.modules.job.dao;

import jp.learningpark.modules.job.entity.BTGKA1003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BTGKA1003Dao {

    List<BTGKA1003Dto> getUserList(@Param("sysTime") String sysTime, @Param("roleType") String roleType, @Param("delType") Integer delType);

    List<BTGKA1003Dto> getOrgList(@Param("sysTime") String sysTime, @Param("delType") Integer delType);

    List<BTGKA1003Dto> getOrgAllList(@Param("sysTime") String sysTime, @Param("delType") Integer delType);

    List<BTGKA1003Dto> getPwList(@Param("sysTime") String sysTime, @Param("delType") Integer delType);
}
