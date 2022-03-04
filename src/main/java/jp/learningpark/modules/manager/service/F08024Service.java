package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08024DspDto;
import jp.learningpark.modules.manager.dto.F08024Dto;

import java.util.List;
import java.util.Map;

public interface F08024Service {
    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params
     * @return
     */
    List<F08024Dto> selectAll(Integer limit, Integer page, Map<String, String> params);

    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    F08024Dto selectDspItem(String userId, String menuId);

    /**
     * @param userId ユーザーID
     * * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(String userId, String menuId);

    /**
     * @param F08024DspDto
     * @return
     */
    Integer insertDspItem(F08024DspDto F08024DspDto);

    /**
     * @param F08024DspDto
     * @return
     */
    Integer updateDspItem(F08024DspDto F08024DspDto);

    /**
     * @return
     */
    List<MstTmplateEntity> getTmplateAll(String orgId,String tmplateTypeDiv);
    /**
     * @return
     */
    List<MstTmplateEntity> getOrderTmplateAll(String tmplateDiv,String orgId);
}
