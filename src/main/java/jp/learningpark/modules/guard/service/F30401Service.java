/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30401Dto;

import java.util.List;

/**
 * <p>塾からのイベント情報一覧画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/29 : hujiaxing: 新規<br />
 * @version 1.0
 */
public interface F30401Service {
    /**
     * 塾からのイベント情報一覧画面、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @param limit   limit
     * @param page    page
     * @return
     */
    List<F30401Dto> selectNews(String stuId, String guardId, Integer limit, Integer page);

    /**
     * 塾からのイベント情報一覧画面、画面で表示される。
     *
     * @param stuId   生徒Id
     * @param guardId 保護者Id
     * @param orgId   組織Id
     * @return
     */
    Integer selectNewsCount(String stuId, String guardId);

    /**
     * お知らせ未読カウント数
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    Integer getNewsCount(String guardId, String stuId);
}
