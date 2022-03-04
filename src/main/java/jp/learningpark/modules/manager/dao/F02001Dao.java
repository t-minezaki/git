/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F02001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F02001 単元情報インポート・エクスポート画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/08 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F02001Dao {

    /**
     * <p>単元情報を取得する</p>
     *
     * @param orgId      組織ID
     * @param schyDiv    学年コード
     * @param subjtDiv   教科コード
     * @param upOrgId    上層組織リスト
     * @param lowerOrgId 下層組織リスト
     * @return 単元情報
     */
    List<F02001Dto> selectMstUnitInfo(
            @Param("orgId") String orgId,
            @Param("schyDiv") String schyDiv,
            @Param("subjtDiv") String subjtDiv, @Param("upOrgId") List<String> upOrgId, @Param("lowerOrgId") List<String> lowerOrgId);

    /**
     * <p>上層組織リストを取得する</p>
     *
     * @param orgId 組織ID
     * @return
     */
    List<String> selectLowerOrg(@Param("orgId") String orgId);

    /**
     * <p>下層組織リストを取得する</p>
     *
     * @param orgId 組織ID
     * @return
     */
    List<String> selectUpOrg(@Param("orgId") String orgId);
}
