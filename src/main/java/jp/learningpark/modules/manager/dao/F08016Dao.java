package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08016DspDto;
import jp.learningpark.modules.manager.dto.F08016Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F08016Dao {
    /**
     *
     * @param limit             各ページの最大記録数
     * @param offset            現在のページ数
     * @param params
     * @return
     */
    List<F08016Dto> selectAll(@Param("limit") Integer limit, @Param("offset") Integer offset, @Param("params") Map<String,String> params);

    /**
     *
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    F08016Dto selectDspItems(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     *
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     *
     * @param f08016DspDto
     * @return
     */
    Integer insertDspItem(F08016DspDto f08016DspDto);

    /**
     *
     * @param f08016DspDto
     * @return
     */
    Integer updateDspItem(F08016DspDto f08016DspDto);

    /**
     *
     * @return
     */
    List<MstTmplateEntity> getTmplateAll();

}
