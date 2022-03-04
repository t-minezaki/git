package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09009Dto;

public interface F09009Service {
    /**
     * 初期表示
     */
    R init();

    R edit(F09009Dto dto);
}
