package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21011Dao;
import jp.learningpark.modules.manager.dto.F21011Dto;
import jp.learningpark.modules.manager.service.F21011Service;
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
public class F21011ServiceImpl implements F21011Service {
    @Autowired
    F21011Dao f21011Dao;

    @Override
    public List<F21011Dto> select(String orgId, String guidRepeCd, Integer page, Integer limit) {
        return f21011Dao.select(orgId,guidRepeCd,page,limit);
    }
    @Override
    public List<F21011Dto> selectTotal(String orgId, String guidRepeCd) {
        return f21011Dao.selectTotal(orgId,guidRepeCd);
    }
    @Override
    public List<F21011Dto> reselect(Object stuIdList, Integer page, Integer limit) {
        return f21011Dao.reselect(stuIdList,page,limit);
    }
    @Override
    public List<F21011Dto> reselectTotal(Object stuIdList) {
        return f21011Dao.reselectTotal(stuIdList);
    }
    @Override
    public List<F21011Dto> selectStatus() {
        return f21011Dao.selectStatus();
    }
}
