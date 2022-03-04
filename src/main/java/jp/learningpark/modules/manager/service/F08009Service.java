package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08009DspDto;
import jp.learningpark.modules.manager.dto.F08009Dto;

import java.util.List;
import java.util.Map;

public interface F08009Service {
    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params
     * @return
     */
    List<F08009Dto> selectAll(Integer limit, Integer page, Map<String,String> params);

    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    F08009Dto selectDspItem(String userId, String menuId);

    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(String userId, String menuId);

    /**
     * @param F08009DspDto
     * @return
     */
    Integer insertDspItem(F08009DspDto F08009DspDto);

    /**
     * @param F08009DspDto
     * @return
     */
    Integer updateDspItem(F08009DspDto F08009DspDto);

    /**
     * @return
     */
    List<MstTmplateEntity> getTmplateAll();

    /**
     *
     * @param params
     * @return
     */
    Integer countAll(Map<String,String> params);
}
