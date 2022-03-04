/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30401Dao;
import jp.learningpark.modules.guard.dto.F30401Dto;
import jp.learningpark.modules.guard.service.F30401Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>塾からのイベント情報一覧画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/29 : hujiaxing: 新規<br />
 * @version 1.0
 */
@Service
public class F30401ServiceImpl implements F30401Service {
    /**
     * 塾からのイベント情報一覧画面 Dao
     */
    @Autowired
    F30401Dao f30401Dao;

    /**
     * 塾からのイベント情報一覧画面、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param limit   limit
     * @param page    page
     * @return
     */
    @Override
    public List<F30401Dto> selectNews(String stuId, String guardId, Integer limit, Integer page) {
        return f30401Dao.selectNews(stuId, guardId, limit, page);
    }

    /**
     * 塾からのイベント情報一覧画面、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @return
     */
    @Override
    public Integer selectNewsCount(String stuId, String guardId) {
        return f30401Dao.selectNewsCount(stuId, guardId);
    }

    /**
     * お知らせ未読カウント数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    @Override
    public Integer getNewsCount(String guardId, String stuId) {
        return f30401Dao.getNewsCount(guardId, stuId);
    }
}
