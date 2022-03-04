/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30421Dao;
import jp.learningpark.modules.guard.dto.F30421Dto;
import jp.learningpark.modules.guard.service.F30421Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>保護者知らせ画面(学研教室モード)</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/11 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F30421ServiceImpl implements F30421Service {

    /**
     * 保護者知らせ画面 Dao
     */
    @Autowired
    F30421Dao f30421Dao;

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @param limit
     * @return
     */
    @Override
    public List<F30421Dto> getLists(String orgId, String guardId, String stuId,Integer offset, Integer limit) {
        return f30421Dao.selectLists(orgId, guardId, stuId, offset,limit);
    }

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @param limit
     * @return
     */
    @Override
    public Integer selectCount(String orgId, String guardId, String stuId) {
        return f30421Dao.selectCount(orgId, guardId, stuId);
    }

    /**
     *
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public Integer getNoticeCount(String orgId, String guardId, String stuId) {
        return f30421Dao.selectNoticeCount(orgId, guardId, stuId);
    }

    /**
     *
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public Integer getEventCount(String guardId, String stuId) {
        return f30421Dao.selectEventCount(guardId, stuId);
    }

    /**
     *
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public Integer getCountChannel(String orgId, String guardId, String stuId) {
        return f30421Dao.selectCountChannel(orgId, guardId, stuId);
    }
}
