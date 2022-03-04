/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30421Dto;

import java.util.List;

/**
 * <p>保護者知らせ画面(学研教室モード)</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/11 : wq: 新規<br />
 * @version 1.0
 */
public interface F30421Service {

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @param limit
     * @return
     */
    List<F30421Dto> getLists(String orgId, String guardId, String stuId,Integer offset, Integer limit);

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer selectCount(String orgId, String guardId, String stuId);

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer getNoticeCount(String orgId, String guardId, String stuId);

    /**
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer getEventCount(String guardId, String stuId);

    /**
     * @param orgId 組織ID
     * @param guardId 保護者ID
     * @param stuId 生徒ID
     * @return
     */
    Integer getCountChannel(String orgId, String guardId, String stuId);
}
