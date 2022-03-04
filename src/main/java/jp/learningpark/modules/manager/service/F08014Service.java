package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F08014Dto;
import jp.learningpark.modules.manager.dto.F08014StudentDto;

import java.util.List;
import java.util.Map;

public interface F08014Service {

    /**
     *
     * @param id 保護者イベント申込ID
     * @return
     */
    F08014Dto selectEvent(Integer id);

    /**
     *
     * @param params メールパラメータ
     * @return
     */
    R postEmail(Map<String,Object> params);

    /**
     * @param espdId
     * @param userFlag 保護者フラグ
     * @return
     */
    List<F08014StudentDto> selectStudent(Integer espdId, Boolean userFlag);
}
