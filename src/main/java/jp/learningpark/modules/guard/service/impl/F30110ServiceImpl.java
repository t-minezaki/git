/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30110Dao;
import jp.learningpark.modules.guard.dto.F30110Dto;
import jp.learningpark.modules.guard.service.F30110Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>塾ニュース一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F30110ServiceImpl implements F30110Service {
    /**
     * 塾ニュース一覧画面 Dao
     */
    @Autowired
    F30110Dao f30110Dao;

    /**
     * 塾のニュースを取得し、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @param limit   limit
     * @param page    page
     * @return
     */
    @Override
    public List<F30110Dto> selectNews(String stuId, String guardId, String orgId, Integer limit, Integer page) {
        return f30110Dao.selectNews(stuId, guardId, orgId, limit, page);
    }

    /**
     * 塾のニュースを取得し、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @return
     */
    @Override
    public Integer selectNewsCount(String stuId, String guardId, String orgId) {
        return f30110Dao.selectNewsCount(stuId, guardId, orgId);
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
    public Integer getNewsCount(String guardId, String stuId, String orgId) {
        return f30110Dao.getNewsCount(guardId, stuId, orgId);
    }
}
