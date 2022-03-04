package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09005Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Mapper
public interface F09005Dao {
    /**
     *
     * @param stuidlist 選択されたstuidの集合
     * @return
     */
    List<F09005Dto> init(@Param("stuidlist") List<String> stuidlist,@Param("status") String status);
    //2021/10/14　MANAMIRU1-809 huangxinliang　Edit　Start
    List<F09005Dto> getlist(@Param("orgId") String orgId, @Param("day") Date time, @Param("stuidlist") List<String> stuidlist,
                            @Param("startTime") Timestamp startTime, @Param("endTime")Timestamp endTime);
    // 2021/10/14　MANAMIRU1-809 huangxinliang　Edit　End
}
