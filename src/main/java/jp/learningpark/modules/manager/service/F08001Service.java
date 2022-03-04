package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dto.F08001DspDto;
import jp.learningpark.modules.manager.dto.F08001Dto;

import java.util.List;
import java.util.Map;

public interface F08001Service {
    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params          条件を調べる
     * @return
     */
    List<F08001Dto> selectAll(Integer limit, Integer page, Map<String,String> params);

    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    F08001Dto selectDspItem(String userId, String menuId);

    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(String userId, String menuId);

    /**
     * @param f08001DspDto
     * @return
     */
    Integer insertDspItem(F08001DspDto f08001DspDto);

    /**
     * @param f08001DspDto
     * @return
     */
    Integer updateDspItem(F08001DspDto f08001DspDto);

    /**
     * @return
     */
    List<MstTmplateEntity> getTmplateAll();
}
