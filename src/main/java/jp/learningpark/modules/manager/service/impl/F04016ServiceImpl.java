/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F04016Dao;
import jp.learningpark.modules.manager.dto.F04016Dto;
import jp.learningpark.modules.manager.service.F04016Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（マナミルチャンネル）ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/10 : yang: 新規<br />
 * @version 6.0
 */
@Service
public class F04016ServiceImpl implements F04016Service {
    /**
     * f04016Dao
     */
    @Autowired
    F04016Dao f04016Dao;

    /**
     * 保護者お知らせ閲覧状況より、取得する
     * @param noticeId
     * @param flg
     * @param limit
     * @param page
     * @return
     */
    @Override
    public List<F04016Dto> init(Integer noticeId, String flg,String orgId, Integer limit, Integer page) {
        return f04016Dao.init(noticeId, flg,orgId,limit, page);
    }

    /**
     * 件数の取得
     * @param noticeId
     * @param flg
     * @return
     */
    @Override
    public Integer getTotalCount(Integer noticeId, String flg,String orgId) {
        return f04016Dao.getTotalCount(noticeId, flg,orgId);
    }
}

