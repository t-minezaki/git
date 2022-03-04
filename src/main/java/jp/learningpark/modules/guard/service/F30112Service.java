/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30112Dto;

import java.util.List;

/**
 * <p>塾からの連絡通知一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F30112Service {
    /**
     * 通知とイベントのデータを取得する
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @param page page
     * @return
     */
    List<F30112Dto> getNotices(String stuId, String guardId, String orgId, Integer limit, Integer page);

    /**
     * 通知とイベントの総数を取得する
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer getNoticeCount(String stuId, String guardId, String orgId);

    /**
     * お知らせ未読カウント数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    Integer selectNoticeUnreadCount(String guardId, String stuId, String orgId, String levDiv);


    /**
     * 塾のニュースを取得し、画面で表示される。
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param id id
     * @param orgId orgId
     * @return
     */
    F30112Dto updateStatus(Integer id, String orgId, String stuId, String guardId, Integer guidReprId, String deliverCd);

}
