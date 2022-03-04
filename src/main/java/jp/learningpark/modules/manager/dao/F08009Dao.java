package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08009DspDto;
import jp.learningpark.modules.manager.dto.F08009Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F08009Dao {
    /**
     * @param limit             各ページの最大記録数
     * @param offset            現在のページ数
     * @param params
     * @return
     */
    List<F08009Dto> selectAll(@Param("limit") Integer limit, @Param("offset") Integer offset, @Param("params") Map<String,String> params);

    /**
     *
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    F08009Dto selectDspItems(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     *
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     *
     * @param F08009DspDto
     * @return
     */
    Integer insertDspItem(F08009DspDto F08009DspDto);

    /**
     *
     * @param F08009DspDto
     * @return
     */
    Integer updateDspItem(F08009DspDto F08009DspDto);

    /**
     *
     * @return
     */
    List<MstTmplateEntity> getTmplateAll();
    Integer countAll(@Param("params") Map<String,String> params);
}
