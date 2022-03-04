package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F09004Dao;
import jp.learningpark.modules.manager.dto.F09004Dto;
import jp.learningpark.modules.manager.service.F09004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class F09004ServiceImpl implements F09004Service {


    @Autowired
    F09004Dao f09004Dao;

    /**
     *
     * @param orgId 組織ID
     * @param params　パラメータ
     * @return
     */
    @Override
    public List<F09004Dto> init(String orgId, Map<String, Object> params,String roleDiv,String userId) {
        return f09004Dao.init(orgId, params,roleDiv,userId);
    }
}
