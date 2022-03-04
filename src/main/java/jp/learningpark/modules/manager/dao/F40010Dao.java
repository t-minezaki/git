package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F40010DspDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface F40010Dao {

    /**
     * 組織の機能をリセットする
     * @param orgId
     * @param managerOrMentor
     * @param updUsrId
     * @param updDatime
     * @return
     */
    Integer updateMstOrgFunList(@Param("orgId") String orgId, @Param("managerOrMentor") Integer managerOrMentor, @Param("updUsrId") String updUsrId, @Param("updDatime")Timestamp updDatime);

    /**
     * その組織のユーザを取得する
     * @param orgId 組織ID
     * @param roleDiv
     * @return
     */
    List<F40010DspDto> selectMst(@Param("orgId") String orgId, @Param("roleDiv") String roleDiv);
}
