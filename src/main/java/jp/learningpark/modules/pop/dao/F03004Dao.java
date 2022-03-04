/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.manager.dto.F03004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/14 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F03004Dao {
    /**
     * <p>単元マスタへ単元情報を取得して表示する。</p>
     *
     * @param schyDiv  学年区分
     * @param subjtDiv 教科区分
     * @param sectnNm  節名
     * @param chaptNm  章名
     * @param orgId    組織ID
     * @param unitNm   単元名
     * @param limit
     * @param page
     * @return
     */
    List<F03004Dto> selectUnitList(@Param("schyDiv") String schyDiv, @Param("subjtDiv") String subjtDiv, @Param("sectnNm") String sectnNm, @Param("chaptNm") String chaptNm, @Param("orgId") String orgId, @Param("unitNm") String unitNm, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * <p>単元マスタへ単元情報の数量を取得</p>
     *
     * @param schyDiv  学年区分
     * @param subjtDiv 教科区分
     * @param sectnNm  節名
     * @param chaptNm  章名
     * @param orgId    組織ID
     * @param unitNm   単元名
     * @return
     */
    Integer selectUnitCount(@Param("schyDiv") String schyDiv, @Param("subjtDiv") String subjtDiv, @Param("sectnNm") String sectnNm, @Param("chaptNm") String chaptNm, @Param("orgId") String orgId, @Param("unitNm") String unitNm);
}
