/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.utils.dto.WeekPreNextSeasonDto;
import jp.learningpark.modules.manager.dto.F20006Dto;
import jp.learningpark.modules.student.dto.F1030105Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>学習者の進捗一覧画面(ウィークリー)（PC）</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/12/10 : huangyong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F20006Dao {

    /**
     * <p>画面情報取得</p>
     *
     * @param stuId 生徒ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 画面情報
     */
    List<F20006Dto> getWeekkyBlockInfo(@Param("stuId") String stuId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * <p>学習週の前週次週取得処理</p>
     *
     * @param stuId 生徒ID
     * @param mentorId メンターID
     * @param tgtYmd 対象日
     * @param crmLearnPrdId 塾学習期間ID
     * @return 学習週
     */
    WeekPreNextSeasonDto getWeekPreNextSeason(
            @Param("stuId") String stuId, @Param("mentorId") String mentorId, @Param("tgtYmd") Date tgtYmd, @Param("crmLearnPrdId") Integer crmLearnPrdId);

    /**
     * <p>生徒基本情報取得処理</p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    Map<String, String> selectStudentInfo(@Param("stuId") String stuId);

    /**
     * <p>生徒基本情報取得処理</p>
     *
     * @param stuId 生徒ID
     * @return 生徒基本情報
     */
    List<F1030105Dto> selectPrintPlannedBlock(@Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("endYmd") Date endYmd);
}
