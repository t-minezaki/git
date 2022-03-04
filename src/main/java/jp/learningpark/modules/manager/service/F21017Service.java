/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21017Dto;

import java.util.List;
import java.util.Map;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
public interface F21017Service {

    /**
     * @param orgId 組織ID
     * @return
     */
    Integer orgDatasCount(String orgId);

    /**
     * @param mentorId 　先生ID
     * @param orgId 　組織ID
     * @return
     */
    Integer mentorDatasCount(String mentorId, String orgId);

    /**
     * 未確認連絡数の取得
     *
     * @param roleDiv 　ロール区分
     * @param mentorId 　先生ID
     * @param orgId 　組織ID
     * @param date 　日付
     * @return
     */
    Integer getUnreadCount(String roleDiv, String mentorId, String orgId, String date);

    /**
     * @param map
     * @return
     */
    List<F21017Dto> selectStuInfo(Map<String, Object> map);

    /**
     * <p>学生総数を取得する。</p>
     * <p>
     * add at 2021/08/10 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    Integer selectStudentCount(Map<String, Object> map);
}
