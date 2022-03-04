package jp.learningpark.modules.job.dao;

import jp.learningpark.modules.job.entity.BTGKA1002Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface BTGKA1002Dao {

    /**
     *
     * @param sgdPlanDate 日程予定日
     * @return List<BTGKA1002Dto>
     */
    List<BTGKA1002Dto> getGEASList(@Param("sgdPlanDate") String sgdPlanDate);

}
