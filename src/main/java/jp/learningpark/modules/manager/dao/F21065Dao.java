package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F21065Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/21 ： NWT)hxl ： 新規作成
 * @date 2020/05/21 16:07
 */
@Mapper
public interface F21065Dao {
    List<F21065Dto> init01(@Param("messageId") Integer messageId
            , @Param("limit") Integer limit
            , @Param("page") Integer page);

    List<F21065Dto> init02(@Param("orgIds") String orgIds, @Param("messageId") Integer messageId
            , @Param("limit") Integer limit
            , @Param("page") Integer page);

    List<MstOrgEntity> getOrg(@Param("brandCd") String brandCd, @Param("orgId") String orgId);
}
