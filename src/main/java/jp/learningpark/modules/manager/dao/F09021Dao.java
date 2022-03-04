package jp.learningpark.modules.manager.dao;


import jp.learningpark.modules.manager.dto.F09021Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * F09021Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/28 ： NWT)hxl ： 新規作成
 * @date 2020/02/28 11:23
 */
@Mapper
public interface F09021Dao {

    /**
     * 本組織及下層組織リストの取得
     *
     * @param brandCd
     * @param orgId
     * @return
     */
    List<F09021Dto> selectLowerOrg(@Param("brandCd") String brandCd, @Param("orgId") String orgId);

    /**
     * 生徒情報のクエリ
     *
     * @param orgIdList 編集した組織ID
     * @param grpId     指定したグループID
     * @param schyDiv   指定した学年区分
     * @return
     */
    List<F09021Dto> selectStuList(@Param("orgIdList") List<String> orgIdList, @Param("grpId") Integer grpId, @Param("schyDiv") String schyDiv);
}
