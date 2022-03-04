/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21024Dao;
import jp.learningpark.modules.manager.dto.F21024Dto;
import jp.learningpark.modules.manager.service.F21024Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>出欠席連絡一覧（スマホ）</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/25 : zpa: 新規<br />
 * @version 1.0
 */
@Service
public class F21024ServiceImpl implements F21024Service {
    @Autowired
    F21024Dao f21024Dao;
    @Override
    public List<F21024Dto> select(String userId, String orgId, String corrspdSts, String tgtYmd, String roleDiv, Integer limit, Integer offset) {
        return f21024Dao.init(userId,orgId,corrspdSts,tgtYmd,roleDiv,limit,offset);
    }

    @Override
    public Integer getCount(String userId, String orgId, String corrspdSts, String tgtYmd, String roleDiv, Integer limit, Integer offset) {
        return f21024Dao.getCount(userId,orgId,corrspdSts,tgtYmd,roleDiv,limit,offset);
    }
}
