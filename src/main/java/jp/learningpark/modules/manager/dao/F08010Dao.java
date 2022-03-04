package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dto.F08010Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F08010Dao {

    /**
     * @param eventId イベントID
     * @param orgIdList 組織ID
     * @param guard model
     * NWT崔 manmiru1-726 2021/07/07 edit
     * @return
     */
    List<F08010Dto> selectGuardAndStudent(@Param("eventId") Integer eventId, @Param("orgIdList") List<OrgAndLowerOrgIdDto> orgIdList, @Param("guard") Boolean guard);

    /**
     * @param eventId イベントID
     * @return
     */
    Integer selectGeasCount(@Param("eventId") Integer eventId);

    /**
     * @param mstEventEntity イベント
     * @return
     */
    Integer updateEvent(MstEventEntity mstEventEntity);

    /**
     * @param eventId イベントID
     * @param orgIdStrList 組織List
     * NWT崔 manmiru1-726 2021/07/06 edit
     * @param guard model
     * NWT崔 manmiru1-726 2021/07/08 add
     */
    void deleteData(@Param("eventId") Integer eventId, @Param("orgIdList") List<String> orgIdStrList, @Param("guard") Boolean guard);

    /**
     * @param resendUsers
     * @return
     */
    int resendDataUpdate(@Param("resendUsers") List<GuardEventApplyStsEntity> resendUsers, @Param("flag") String flag);

}
