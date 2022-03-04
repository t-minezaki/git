package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstFunEntity;
import jp.learningpark.modules.manager.dto.F40010DspDto;
import jp.learningpark.modules.manager.dto.F40010ManagerTypeDto;

import java.sql.Timestamp;
import java.util.List;

public interface F40010Service {

    /**
     * 木構造を取得する
     * @param mstFunEntityList
     * @return
     */
    List<F40010ManagerTypeDto> getDiv(List<MstFunEntity> mstFunEntityList);

    /**
     * 組織の機能をリセットする
     * @param orgId
     * @param managerOrMentor
     * @param updUsrId
     * @param updDatime
     * @return
     */
    Integer updateMstOrgFunList(String orgId, Integer managerOrMentor, String updUsrId, Timestamp updDatime);

    /**
     * その組織のユーザを取得する
     * @param orgId 組織ID
     * @param roleDiv
     * @return
     */
    List<F40010DspDto> selectMst(String orgId , String roleDiv);
}
