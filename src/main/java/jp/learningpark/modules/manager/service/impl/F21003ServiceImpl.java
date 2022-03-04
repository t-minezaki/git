package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21003Dao;
import jp.learningpark.modules.manager.dto.F21003Dto;
import jp.learningpark.modules.manager.service.F21003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */
@Service
public class F21003ServiceImpl implements F21003Service {

    @Autowired
    F21003Dao f21003Dao;

    @Override
    public List<F21003Dto> select(String userId, String orgId, String corrspdSts, String tgtYmd,String roleDiv,Integer limit, Integer offset) {
        return f21003Dao.select(userId,orgId,corrspdSts,tgtYmd,roleDiv,limit,offset);
    }
    @Override
    public Integer count(String userId, String orgId, String corrspdSts, String tgtYmd,String roleDiv) {
        return f21003Dao.count(userId,orgId,corrspdSts,tgtYmd,roleDiv);
    }
}
