/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/24
 */

import jp.learningpark.modules.student.dao.F11001Dao;
import jp.learningpark.modules.student.dto.F11001Dto;
import jp.learningpark.modules.student.service.F11001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/24 : lyh: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F11001ServiceImpl implements F11001Service {
    @Autowired
    F11001Dao f11001Dao;

    @Override
    /**
     *
     * @param stuId
     * @return
     */
    public List<F11001Dto> getSubjt(String stuId) {
        return f11001Dao.getSubjt(stuId);
    }
}