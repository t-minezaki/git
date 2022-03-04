package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F00001Dto;
import jp.learningpark.modules.manager.dto.F00001ManagerTypeDto;

import java.util.List;

public interface F00001Service {
    /**
     * 管理者基本マスタから取得
     * @param userId
     * @return
     */
    List<F00001Dto> getUserFunList(String userId);

    /**
     * メニューフォーマット
     * @param mstFunEntityList
     * @return
     */
    List<F00001ManagerTypeDto> getDiv(List<F00001Dto> mstFunEntityList);

    /**
     * 上層組織リスト取得
     * @param orgId
     * @param brandCd
     * @return
     */
    List<F00001Dto> getUpLvOrgList(String orgId,String brandCd);

    /**
     * 組織別機能一覧取得処理
     * @param orgIdList
     * @return
     */
    List<F00001Dto> getOrgFunList(String orgIdList,String roleDiv);

    /**
     * 機能マスタから全機能一覧を取得する
     * @return
     */
    List<F00001Dto> getAllFunList(String roleDiv);
    /**
     * 機能マスタから全機能一覧を取得する
     * @return
     */
    List<F00001Dto> getThisOrgId(String brandCd,String afterUsrId,String manaFlag,String gid,String tchCd,String gidPk);
}
