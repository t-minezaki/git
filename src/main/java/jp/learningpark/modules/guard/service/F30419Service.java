package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30419Dto;

import java.util.List;

/**
 * <p>
 * F30419Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/18 ： NWT)hxl ： 新規作成
 * @date 2020/02/18 14:30
 */
public interface F30419Service {
    /**
     * チャンネルを取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @param limit limit
     * @return
     */
    List<F30419Dto> selectNews(String guardId, String orgId,String stuId, Integer offset, Integer limit);
    /**
     * チャンネルを取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer selectCount(String guardId, String orgId,String stuId);

    /**
     * チャンネルの総数を取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer selectNewsCount(String guardId, String orgId,String stuId);

    /**
     * 未読チャンネルの総数を取得し、画面で表示される。
     *
     * @param guardId 保護者Id
     * @param orgId 組織Id
     * @return
     */
    Integer selectUnreadCount(String guardId, String orgId,String stuId);
}
