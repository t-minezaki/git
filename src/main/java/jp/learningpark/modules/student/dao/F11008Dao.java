package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.student.dto.F11008Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/23 ： NWT)hxl ： 新規作成
 * @date 2020/04/23 11:02
 */
@Mapper
public interface F11008Dao {
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
     * @param date  対象月開始日
     * @param flg   曜日フラグ
     * @return 生徒固定スケジュール設定と個別スケジュール調整情報
     */
    List<F11008Dto> getStuFixdAndAdjustSchdlInfo(@Param("stuId") String stuId, @Param("tgtYmd") Date tgtYmd, @Param("date") Date date, @Param("flg") String flg);

    /**
     * <p>対象日より、生徒ウィークリー計画実績設定情報取得</p>
     *
     * @param stuId 生徒ID
     * @param date  対象日
     * @param flg フラグ
     * @return 生徒ウィークリー計画実績設定情報
     */
    List<F11008Dto> getStuWeeklyPlanPerfInfo(@Param("stuId") String stuId, @Param("date") Date date);
//    /**
//     *
//     */
//    List<StuComplimentMstEntity> getCompliment(@Param("stuId") String stuId, @Param("date") Date etgtYmd);
}
