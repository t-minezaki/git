package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F00001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F00001Dao {
    /**
     * 管理者基本マスタから取得し
     * @param userId
     * @return
     */
    List<F00001Dto> getUserFunList(@Param("userId") String userId);

    /**
     * 上層組織リスト
     * @param orgId
     * @param brandCd
     * @return
     */
    List<F00001Dto> getUpLvOrgList(@Param("orgId") String orgId,@Param("brandCd" )String brandCd);

    /**
     * 組織別機能一覧取得処理
     * @param orgId
     * @return
     */
    List<F00001Dto> getOrgFunList(@Param("orgId") String orgId,@Param("roleDiv")String roleDiv);

    /**
     * 機能マスタから全機能一覧を取得する
     * @return
     */
    List<F00001Dto> getAllFunList(@Param("roleDiv")String roleDiv);

    /**
     * 機能マスタから全機能一覧を取得する
     * @return
     */
    List<F00001Dto> getThisOrgId(@Param("brandCd")String brandCd,@Param("userId")String afterUsrId,@Param("manaFlag")String manaFlag,@Param("gid")String gid,@Param("tchCd")String tchCd,@Param("gidPk")String gidPk);
}
