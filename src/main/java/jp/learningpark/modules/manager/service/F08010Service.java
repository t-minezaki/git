package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dto.F08010Dto;

import java.util.List;

public interface F08010Service {

    /**
     * @param eventId イベントID
     * @param orgIdList 組織ID
     * @param guard model
     * @return
     * NWT崔 manmiru1-726 2021/07/07 edit
     */
    List<F08010Dto> selectGuardAndStudent(Integer eventId, List<OrgAndLowerOrgIdDto> orgIdList, Boolean guard);

    /**
     * @param eventId イベントID
     * @return
     */
    Integer getGeasCount(Integer eventId);

    /**
     * @param mstEventEntity イベント
     * @return
     */
    Integer updateEvent(MstEventEntity mstEventEntity);

    /**
     * @param resendUsers
     * @return
     */
    int resendDataUpdate(List<GuardEventApplyStsEntity> resendUsers, String flag);
}
