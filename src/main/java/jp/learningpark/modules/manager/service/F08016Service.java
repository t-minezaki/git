package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08016DspDto;
import jp.learningpark.modules.manager.dto.F08016Dto;

import java.util.List;
import java.util.Map;

public interface F08016Service {
    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params
     * @return
     */
    List<F08016Dto> selectAll(Integer limit, Integer page, Map<String,String> params);

    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    F08016Dto selectDspItem(String userId, String menuId);

    /**
     * @param userId ユーザーID
     * * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(String userId, String menuId);

    /**
     * @param f08016DspDto
     * @return
     */
    Integer insertDspItem(F08016DspDto f08016DspDto);

    /**
     * @param f08016DspDto
     * @return
     */
    Integer updateDspItem(F08016DspDto f08016DspDto);

    /**
     * @return
     */
    List<MstTmplateEntity> getTmplateAll();
}
