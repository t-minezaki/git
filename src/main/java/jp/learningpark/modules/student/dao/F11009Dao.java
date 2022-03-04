package jp.learningpark.modules.student.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/22 ： NWT)hxl ： 新規作成
 * @date 2020/04/22 10:38
 */
@Mapper
public interface F11009Dao {
    /**
     * 理解度
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd終了日
     * @return
     */
    List<Map<String, Object>> getDegreeTotal(Map<String, Object> map);

    /**
     * 学習時間
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd終了日
     * @return
     */
    Map<String, Object> getLearnTimeCircleTotal(Map<String, Object> map);
}
