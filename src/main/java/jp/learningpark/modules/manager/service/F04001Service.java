/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F04001Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>F04001_塾ニュース一覧画面 Service</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/02/26 : tan: 新規<br />
 * @version 1.0
 */
public interface F04001Service {

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
    List<F04001Dto> getSearchOrg(String sOrgId, String orgId, Integer id, String noticeTitle, Date pubPlanStartDt, Date pubPlanEndDt, Integer limit, Integer page);

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
    Integer getTotalCount(String sOrgId, String orgId, Integer id, String noticeTitle, Date pubPlanStartDt, Date pubPlanEndDt);
}
