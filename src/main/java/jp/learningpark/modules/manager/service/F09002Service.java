package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F09002Dto;

import java.util.List;

public interface F09002Service {

    /**
     *
     * @param orgId　塾ID
     * @param stuIdList 生徒リステ
     * @return
     */
    List<F09002Dto> init(String orgId, List<String> stuIdList,String userId,String roleDiv,Integer limit,Integer page);
}
