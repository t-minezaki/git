/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.guard.dto.F30101Dto;
import jp.learningpark.modules.guard.dto.F30101stuConplimentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>学習者の進捗一覧画面（デイリー）Dao</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/21 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30101Dao {
    /**
     * 対象日より、祝日情報取得する。
     *
     * @param date 対象日
     * @return 祝日情報
     */
    MstHolidayEntity selectHolidayByTgtYmd(@Param("date") Date date);

    /**
     * <p>対象日より、生徒固定スケジュール設定と個別スケジュール調整情報取得</p>
     *
     * @param stuId 生徒ID
     * @param date 対象日
     * @param flg 曜日フラグ
     * @return 生徒固定スケジュール設定と個別スケジュール調整情報
     */
    List<F30101Dto> getStuFixdAndAdjustSchdlInfo(@Param("stuId") String stuId, @Param("date") Date date, @Param("flg") String flg);

    /**
     * <p>対象日より、生徒ウィークリー計画実績設定情報取得</p>
     *
     * @param stuId 生徒ID
     * @param stgtYmd 当月開始日
     * @return 生徒ウィークリー計画実績設定情報
     */
    List<F30101Dto> getStuWeeklyPlanPerf(@Param("stuId") String stuId, @Param("stgtYmd") Date stgtYmd);

    /**
     * @param stuId 生徒
     * @param orgId 　組織ID
     * @param stgtYmd 　当月開始日
     * @return
     */
    List<F30101stuConplimentDto> getStuComplimentList(@Param("stuId") String stuId, @Param("orgId") String orgId, @Param("stgtYmd") Date stgtYmd);
}
