package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.pop.dto.F08004Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface F08004Dao {

    /**
     *
     * @param eventId イベントID
     * @return F08004Dto
     */
    F08004Dto selectEventEntity(@Param("eventId") Integer eventId);

    /**
     *
     * @param eventId イベントID
     * @return  List<MstOrgEntity>
     */
    List<MstOrgEntity> selectOrgList(@Param("eventId") Integer eventId);
}