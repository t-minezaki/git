package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dao.F08010Dao;
import jp.learningpark.modules.manager.dto.F08010Dto;
import jp.learningpark.modules.manager.service.F08010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class F08010ServiceImpl implements F08010Service {

    /**
     * F08010Dao
     */
    @Autowired
    private F08010Dao f08010Dao;

    /**
     * @param eventId イベントID
     * @param orgIdList 組織ID
     * @param guard model
     * @return
     * NWT崔 manmiru1-726 2021/07/07 edit
     */
    @Override
    public List<F08010Dto> selectGuardAndStudent(Integer eventId, List<OrgAndLowerOrgIdDto> orgIdList, Boolean guard) {
        return f08010Dao.selectGuardAndStudent(eventId, orgIdList, guard);
    }

    @Override
    public Integer getGeasCount(Integer eventId) {
        return f08010Dao.selectGeasCount(eventId);
    }

    /**
     * @param mstEventEntity イベント
     * @return
     */
    @Override
    public Integer updateEvent(MstEventEntity mstEventEntity) {
        return f08010Dao.updateEvent(mstEventEntity);
    }

    /**
     * @param resendUsers
     * @return
     */
    @Override
    public int resendDataUpdate(List<GuardEventApplyStsEntity> resendUsers, String flag) {
        return f08010Dao.resendDataUpdate(resendUsers, flag);
    }
}
