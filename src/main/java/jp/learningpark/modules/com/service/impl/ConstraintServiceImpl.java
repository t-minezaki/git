/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service.impl;

import jp.learningpark.modules.com.dao.ConstraintDao;
import jp.learningpark.modules.com.service.ConstraintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/31 : wq: 新規<br />
 * @version 1.0
 */

@Service
@Transactional
public class ConstraintServiceImpl implements ConstraintService {

    @Autowired
    private ConstraintDao constraintDao;

    /**
     * 生徒IDの最大値を取得する。
     * @return
     */
    @Override
    public Integer getMaxStuId() {
        return constraintDao.getMaxStuId();
    }

    /**
     * 保護者IDの最大値を取得する。
     * @return
     */
    @Override
    public Integer getMaxGuardId() {
        return constraintDao.getMaxGuardId();
    }

    /**
     * 先生IDの最大値を取得する。
     * @return
     */
    @Override
    public Integer getMaxMentorId() {
        return constraintDao.getMaxMentorId();
    }

    /**
     * 管理者IDの最大値を取得する。
     * @return
     */
    @Override
    public Integer getMaxManagerId() {
        return constraintDao.getMaxManagerId();
    }
}
