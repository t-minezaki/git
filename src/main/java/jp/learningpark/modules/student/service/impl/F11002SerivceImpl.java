/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/27
 */

import jp.learningpark.modules.student.dao.F11002Dao;
import jp.learningpark.modules.student.service.F11002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/27 : lyh: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F11002SerivceImpl implements F11002Service {

    @Autowired
    F11002Dao f11002Dao;

    /**
     *
     * @param stuId
     * @return
     */
    @Override
    public String getMax(String stuId) {
        return f11002Dao.getMax(stuId);
    }
}