/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21071Dao;
import jp.learningpark.modules.manager.dto.F21071Dto;
import jp.learningpark.modules.manager.service.F21071Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <p>F21071_配信先既読未読状態確認一覧画面（インフォメーション）serviceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/06/01 : yang: 新規<br />
 * @version 7.0
 */
@Service
public class F21071ServiceImpl implements F21071Service {
    /**
     * f21017dao
     */
    @Autowired
    F21071Dao f21071Dao;

    /**
     * 既読未読集計一覧を取得する。
     * @param msgId
     * @param orgIdList
     * @param limit
     * @param page
     * @param flg
     * @return
     */
    @Override
    public List<F21071Dto> getGridList(Integer msgId, List<String> orgIdList, Integer limit, Integer page, boolean flg) {
        return f21071Dao.getGridList(msgId,orgIdList, limit, page,flg);
    }

}
