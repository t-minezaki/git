/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F09019Dao;
import jp.learningpark.modules.manager.dto.F09019Dto;
import jp.learningpark.modules.manager.service.F09019Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F09019_一斉お知らせ配信（一覧）（スマホ） ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/19 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F09019ServiceImpl implements F09019Service {

    /**
     * F09019_一斉お知らせ配信（一覧）Dao
     */
    @Autowired
    F09019Dao f09019Dao;

    /**
     * @param orgId 組織ID
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<F09019Dto> getNotices(String orgId, Integer offset, Integer limit) {
        return f09019Dao.selectNotices(orgId, offset, limit);
    }

    /**
     * @param orgId 組織ID
     * @return
     */
    @Override
    public Integer getNoticesCount(String orgId) {
        return f09019Dao.selectNoticesCount(orgId);
    }
}
