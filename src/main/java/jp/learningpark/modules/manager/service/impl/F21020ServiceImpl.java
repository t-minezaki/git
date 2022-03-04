/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21020Dao;
import jp.learningpark.modules.manager.dto.F21020Dto;
import jp.learningpark.modules.manager.service.F21020Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F21020スマホ_褒めポイント登録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/14 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F21020ServiceImpl implements F21020Service {

    /**
     * F21020スマホ_褒めポイント登録画面 Dao
     */
    @Autowired
    F21020Dao f21020Dao;

    /**
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @param limit
     * @param page
     * @return
     */
    @Override
    public List<F21020Dto> getInitList(String orgId, String stuId, Integer limit, Integer page) {
        return f21020Dao.selectInitList(orgId, stuId, limit, page);
    }

    /**
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public Integer getInitListCount(String orgId, String stuId) {
        return f21020Dao.selectInitListCount(orgId, stuId);
    }
}
