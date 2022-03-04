/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F11010Dao;
import jp.learningpark.modules.student.dto.F11010Dto;
import jp.learningpark.modules.student.service.F11010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>スマホ_メッセージ一覧</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/29 : zpa: 新規<br />
 * @version 7.0
 */
@Service
public class F11010ServiceImpl implements F11010Service {
    /**
     * f11010Dao
     */
    @Autowired
    F11010Dao f11010Dao;

    /**
     * 初期化
     * @param stuId     生徒Id
     * @param limit     limit
     * @param page      page
     * @return
     */
    @Override
    public List<F11010Dto> getList(String stuId, Integer limit, Integer page) {
        return f11010Dao.getList(stuId, limit, page);
    }

    @Override
    public Integer getCount(String stuId) {
        return f11010Dao.getCount(stuId);
    }

    /**
     * 未読カウント数
     * @param stuId     生徒Id
     * @return
     */
    @Override
    public Integer getUnreadCount(String stuId) {
        return f11010Dao.getUnreadCount(stuId);
    }
}