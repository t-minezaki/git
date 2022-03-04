package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F09004Dto;

import java.util.List;
import java.util.Map;

public interface F09004Service {

    /**
     *
     * @param orgId 組織ID
     * @param params　パラメータ
     * @return List<F09004Dto>
     */
    List<F09004Dto> init(String orgId, Map<String, Object> params,String roleDiv, String userId);
}
