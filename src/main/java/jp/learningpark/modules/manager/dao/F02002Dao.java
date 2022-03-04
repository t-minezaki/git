/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F02002Dto;
import jp.learningpark.modules.manager.dto.F02002UnitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * F00002 F02002_単元情報検索一覧画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F02002Dao {

    /**
     * 本組織及び上下層組織リストの取得
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    List<F02002Dto> getOrgList(@Param("orgId") String orgId,@Param("brandCd") String brandCd);

    /**
     * 単元情報検索一覧を表示するため
     * @param currentOrgId セッションデータ．組織ＩＤ
     * @param pageSize
     * @param limit
     * @param orgIdList    上位組織、下位組織、本組織
     * @return
     */
    List<F02002UnitDto> getUnitList(@Param("currentOrgId") String currentOrgId, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit, @Param("orgIdList")List orgIdList);

    /**
     * 単元情報検索一覧総数
     * @param currentOrgId セッションデータ．組織ＩＤ
     * @param orgIdList    上位組織、下位組織、本組織
     * @return
     */
    Integer getUnitListCount(@Param("currentOrgId") String currentOrgId, @Param("orgIdList")List orgIdList);

    /**
     * 単元情報検索一覧を表示するため search
     * @param currentOrgId セッションデータ．組織ＩＤ
     * @param pageSize
     * @param limit
     * @param orgIdList        search組織ＩＤ
     * @param schyDiv      学年
     * @param subjtDiv     教科
     * @param unitNm       単元名
     * @return
     */
    List<F02002UnitDto> searchUnitList(@Param("currentOrgId") String currentOrgId, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit,
                                       @Param("orgIdList")List orgIdList, @Param("schyDiv")String schyDiv, @Param("subjtDiv")String subjtDiv, @Param("unitNm")String unitNm);


    /**
     * 単元情報検索一覧総数 search
     * @param orgIdList    search組織ＩＤ
     * @param schyDiv      学年
     * @param subjtDiv     教科
     * @param unitNm       単元名
     * @return
     */
    Integer searchUnitListCount(@Param("orgIdList")List orgIdList, @Param("schyDiv")String schyDiv, @Param("subjtDiv")String subjtDiv, @Param("unitNm")String unitNm);

    /**
     * 全部の下層組織
     * @param orgId 組織ID
     * @return
     */
    List<MstOrgEntity> selectLowerOrg(@Param("orgId") String orgId);

    /**
     * 全部の上層組織
     * @param orgId 組織ID
     * @return
     */
    List<MstOrgEntity> selectUpOrg(@Param("orgId") String orgId);

}
