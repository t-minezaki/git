/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21013Dao;
import jp.learningpark.modules.manager.dto.F21013Dto;
import jp.learningpark.modules.manager.service.F21013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2019/12/5 : lyh: 新規<br />
 * @version 1.0
 */
@Service
public class F21013ServiceImpl  implements F21013Service {

    @Autowired
    F21013Dao f21013Dao;

    @Override
    public List<F21013Dto> select(String orgId, String userId, String month, String roleDiv,Set<String> stuIdListLast,String years) {
        return f21013Dao.select(orgId, userId, month, roleDiv,stuIdListLast,years);
    }

    @Override
    public List<F21013Dto> reSelect(String orgId, Set<String> stuIdList, String value, String cd, String month, Set<String> stuIdListLast, String roleDiv,String years) {
        return f21013Dao.reSelect(orgId, stuIdList, value, cd, month,stuIdListLast, roleDiv,years);
    }
    @Override
    public List<F21013Dto> selectTen(String orgId, String month, Set<String> stu, String cd,String years) {
        return f21013Dao.selectTen(orgId, month,stu,cd,years);
    }
}
