/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/05/12
 */

import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.student.dao.F11013Dao;
import jp.learningpark.modules.student.dto.F11013Dto;
import jp.learningpark.modules.student.service.F11013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/05/12 : lyh: 新規<br />
 * @version 1.0
 */
@Service
public class F11013SerivceImpl implements F11013Service {
    @Autowired
    F11013Dao f11013Dao;

    /**
     *
     * @param eventId
     * @return
     */
    @Override
    public F11013Dto init(Integer eventId) {
        String stuId = ShiroUtils.getUserId();
        return f11013Dao.init(eventId, stuId);
    }

    /**
     *
     * @return
     */
    @Override
    public String count() {
        String stuId = ShiroUtils.getUserId();
        return f11013Dao.count(stuId);
    }
}