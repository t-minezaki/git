/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.utils.dto.WeekPreNextSeasonDto;
import jp.learningpark.modules.manager.dto.F20006Dto;
import jp.learningpark.modules.student.dto.F1030105Dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>学習者の進捗一覧画面(ウィークリー)（PC）Service。</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/16 : yangfan: 新規<br />
 * @version 1.0
 */
public interface F20006Service {

    /**
     * <p>画面情報取得</p>
     *
     * @param stuId 生徒ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 画面情報
     */
    List<F20006Dto> getWeekkyBlockInfo(String stuId, Date startDate, Date endDate);

    /**
     * <p>学習週の前週次週取得処理</p>
     *
     * @param stuId 生徒ID
     * @param mentorId メンターID
     * @param tgtYmd 対象日
     * @param crmLearnPrdId 塾学習期間ID
     * @return 学習週
     */
    WeekPreNextSeasonDto getWeekPreNextSeason(String stuId, String mentorId, Date tgtYmd, Integer crmLearnPrdId);

    /**
     *<p>生徒基本情報取得処理</p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    Map<String, String> getStudentInfo(String stuId);

    /**
     * <p>印刷用計画済みブロック情報取得処理</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 対象開始日
     * @param endYmd 対象終了日
     * @return 印刷用計画済みブロック情報
     */
    List<F1030105Dto> selectPrintPlannedBlock(String stuId, Date startYmd, Date endYmd);
}
