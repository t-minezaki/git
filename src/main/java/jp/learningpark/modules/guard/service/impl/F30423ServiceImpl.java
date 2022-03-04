/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30423Dao;
import jp.learningpark.modules.guard.dto.F30423Dto;
import jp.learningpark.modules.guard.service.F30423Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>褒めポイント詳細画面 ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/05 : wq: 新規<br />
 * @version 1.0
 */
@Service
public class F30423ServiceImpl implements F30423Service {

    /**
     * 褒めポイント詳細画面 Dao
     */
    @Autowired
    F30423Dao f30423Dao;

    /**
     * @param noticeId お知らせID
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public F30423Dto getInitData(Integer noticeId, String orgId, String stuId) {
        return f30423Dao.selectInitData(noticeId, orgId, stuId);
    }
}
