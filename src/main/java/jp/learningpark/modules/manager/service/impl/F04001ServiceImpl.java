/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F04001Dao;
import jp.learningpark.modules.manager.dto.F04001Dto;
import jp.learningpark.modules.manager.service.F04001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>F04001_塾ニュース一覧画面 ServiceImpl</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/02/26 : tan: 新規<br />
 * @version 1.0
 */
@Service
public class F04001ServiceImpl implements F04001Service {
    /**
     * 塾ニュース一覧画面 Dao
     */
    @Autowired
    private F04001Dao f04001Dao;

    /**
     * <p>お知らせマスタを元に、本組織及び上位組織の塾ニュース情報を取得する</p>
     *
     * @param sOrgId session 組織ＩＤ
     * @param orgId 組織ＩＤ
     * @param id ニュースID
     * @param noticeTitle 件名
     * @param pubPlanStartDt 掲載開始日時
     * @param pubPlanEndDt 掲載終了日時
     * @param limit １ページ最大件数
     * @param page 現在のページ数
     * @return
     */

    @Override
    public List<F04001Dto> getSearchOrg(String sOrgId, String orgId, Integer id, String noticeTitle, Date pubPlanStartDt, Date pubPlanEndDt, Integer limit, Integer page) {
        return f04001Dao.searchOrg(sOrgId, orgId, id, noticeTitle, pubPlanStartDt, pubPlanEndDt, limit, page);
    }

    /**
     * <p>総件数をとる</p>
     *
     * @param sOrgId session 組織ＩＤ
     * @param orgId 組織ＩＤ
     * @param id ニュースID
     * @param noticeTitle 件名
     * @param pubPlanStartDt 掲載開始日時
     * @param pubPlanEndDt 掲載終了日時
     * @return
     */
    @Override
    public Integer getTotalCount(String sOrgId, String orgId, Integer id, String noticeTitle, Date pubPlanStartDt, Date pubPlanEndDt) {
        return f04001Dao.getTotalCount(sOrgId, orgId, id, noticeTitle, pubPlanStartDt, pubPlanEndDt);
    }
}

