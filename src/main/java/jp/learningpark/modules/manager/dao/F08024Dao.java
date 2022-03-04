package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08024DspDto;
import jp.learningpark.modules.manager.dto.F08024Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface F08024Dao {
    /**
     *
     * @param limit             各ページの最大記録数
     * @param offset            現在のページ数
     * @param params
     * @return
     */
    List<F08024Dto> selectAll(@Param("limit") Integer limit, @Param("offset") Integer offset, @Param("params") Map<String, String> params);

    /**
     *
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    F08024Dto selectDspItems(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     *
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     *
     * @param f08024DspDto
     * @return
     */
    Integer insertDspItem(F08024DspDto f08024DspDto);

    /**
     *
     * @param f08024DspDto
     * @return
     */
    Integer updateDspItem(F08024DspDto f08024DspDto);

    /**
     *
     * @return
     */
    List<MstTmplateEntity> getTmplateAll(@Param("orgId") String orgId,@Param("tmplateTypeDiv") String tmplateTypeDiv);
    /**
     *
     * @return
     */
    List<MstTmplateEntity> getOrderTmplateAll(@Param("tmplateDiv") String tmplateDiv,@Param("orgId") String orgId);

}
