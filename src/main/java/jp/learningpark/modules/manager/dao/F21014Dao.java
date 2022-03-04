package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21014AttendBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * F21014Dao*
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/05 ： NWT)hxl ： 新規作成
 * @date 2019/12/05 10:55
 */
@Mapper
public interface F21014Dao {
    /**
     * 日ごとに指定学生の出席簿を得る
     *
     * @param date 年月日
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    List<F21014AttendBookDto> getAttendBookByDay(@Param("date") String date, @Param("orgId") String orgId, @Param("stuId") String stuId);

    /**
     * 該当生徒の全部教科を取得する
     *
     * @param month 年月
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    List<String> getSubjectByMonth(@Param("month") String month, @Param("orgId") String orgId, @Param("stuId") String stuId);
}
