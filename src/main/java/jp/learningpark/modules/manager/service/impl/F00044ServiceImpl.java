/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;


import jp.learningpark.modules.manager.dao.F00044Dao;
import jp.learningpark.modules.manager.dto.F00044Dto;
import jp.learningpark.modules.manager.service.F00044Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F00044_生徒保護者関係検索一覧画面 ServiceImpl</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/15 : tan: 新規<br />
 * @version 1.0
 */
@Service
public class F00044ServiceImpl implements F00044Service {
    /**
     * F00044_生徒保護者関係検索一覧画面 Dao
     */
    @Autowired
    private F00044Dao f00044Dao;

    /**
     * 初期化学年区分を取得
     *
     * @return
     */
    @Override
    public List<F00044Dto> getSchySearch() {
        return f00044Dao.schySearch();
    }

    /**
     * 生徒基本マスタ、保護者基本マスタを元に一覧情報を取得
     *
     * @param sOrgId    session 組織ＩＤ
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param schy      画面．検索条件．学年
     * @param orgIdList     画面  检索条件  組織ID
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    @Override
    public List<F00044Dto> getSearch(String stuId,
                                     String guardId,
                                     String stuName,
                                     String guardName,
                                     String schy,
                                     Integer limit,
                                     Integer page,
                                     List<String> orgIdList) {
        return f00044Dao.search(stuId, guardId, stuName, guardName, schy, limit, page, orgIdList);
    }

    /**
     * 総件数をとる
     *
     * @param sOrgId    session 組織ＩＤ
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param schy      画面．検索条件．学年
     * @param orgIdList     画面  检索条件  組織ID
     * @return
     */
    @Override
    public Integer getTotalCount(String stuId, String guardId, String stuName, String guardName, String schy, List<String> orgIdList) {
        return f00044Dao.totalCount(stuId, guardId, stuName, guardName, schy, orgIdList);
    }
}

