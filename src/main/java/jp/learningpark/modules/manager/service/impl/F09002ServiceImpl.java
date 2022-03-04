package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F09002Dao;
import jp.learningpark.modules.manager.dto.F09002Dto;
import jp.learningpark.modules.manager.service.F09002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class F09002ServiceImpl implements F09002Service {

    /**
     * f09002Dao
     */
    @Autowired
    F09002Dao f09002Dao;

    /**
     *
     * @param orgId　塾ID
     * @param stuIdList 生徒リステ
     * @return
     */
    @Override
    public List<F09002Dto> init(String orgId, List<String> stuIdList,String userId,String roleDiv,Integer limit,Integer page) {
        return f09002Dao.init(orgId, stuIdList,userId,roleDiv,limit,page);
    }
}
