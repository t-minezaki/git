package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.pop.dao.F21006Dao;
import jp.learningpark.modules.pop.service.F21006Service;
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
public class F21006ServiceImpl implements F21006Service {

    @Autowired
    F21006Dao f21006Dao;

    @Override
    public List<MstCodDEntity> getSubjt(String[] subjtDiv) {
        return f21006Dao.getSubjt(subjtDiv);
    }
}
