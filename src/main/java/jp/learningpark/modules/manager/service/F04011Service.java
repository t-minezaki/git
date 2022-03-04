package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F04011Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>F04011_マナミルチャンネル一覧画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/06 : lyh: 新規<br />
 * @version 1.0
 */
public interface F04011Service {
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
    List<F04011Dto> getSearchOrg(String sOrgId, String orgId, Integer id, String noticeTitle, Date pubPlanStartDt, Date pubPlanEndDt, Integer limit, Integer page);
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
