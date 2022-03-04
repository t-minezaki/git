/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F00053Dao;
import jp.learningpark.modules.manager.dto.F00053Dto;
import jp.learningpark.modules.manager.service.F00053Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F00053_生徒グループ関係検索一覧画面 ServiceImpl</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/18 : tan: 新規<br />
 * @version 1.0
 */
@Service
public class F00053ServiceImpl implements F00053Service {
    /**
     * F00053_生徒グループ関係検索一覧画面 Dao
     */
    @Autowired
    private F00053Dao f00053Dao;


    /**
     * 初期化
     *
     * @param orgId    session 組織ＩＤ
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    @Override
    public List<F00053Dto> getGroupList(String orgId,String groupName,Integer limit, Integer page) {
        return f00053Dao.getGroupList(orgId, groupName,limit,page);
    }
    /**
     * 総件数をとる
     *
     * @param orgId    session 組織ＩＤ
     * @return
     */
    @Override
    public Integer getTotalCount(String orgId,String groupName) {
        return f00053Dao.getTotalCount(orgId,groupName);
    }
}

