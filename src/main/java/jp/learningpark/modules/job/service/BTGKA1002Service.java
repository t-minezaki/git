package jp.learningpark.modules.job.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.entity.BTGKA1002Dto;

import java.util.List;
import java.util.Map;

public interface BTGKA1002Service {

    /**
     *
     * @return List<BTGKA1002Dto>
     */
    List<BTGKA1002Dto> getGEASList();

    /**
     *
     * @param params メールパラメータ
     * @return
     */
    R postEmail(Map<String, Object> params);

    /**
     *
     * @return R
     */
    void postMail();
}
