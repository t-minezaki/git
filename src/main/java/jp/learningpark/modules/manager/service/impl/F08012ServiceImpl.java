package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F08012Dao;
import jp.learningpark.modules.manager.dto.F08012Dto;
import jp.learningpark.modules.manager.service.F08012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class F08012ServiceImpl implements F08012Service {

    /**
     * F08010Dao
     */
    @Autowired
    private F08012Dao f08012Dao;

    /**
     * @param eventId イベントID
     * @param orgId 組織ID
     * @param params 検索条件
     * @param grpIdList グループID
     * @param schyDivList 学年区分
     * @return
     */
    @Override
    public List<F08012Dto> selectGuardAndStudent(Integer eventId, String orgId, Map<String, String> params, List<Integer> grpIdList, String schyDivList) {
        return f08012Dao.selectGuardAndStudent(eventId, orgId, params, grpIdList, schyDivList);
    }
}
