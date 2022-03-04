package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F21004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * F21004Dao
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/26 ： NWT)hxl ： 新規作成
 * @date 2019/11/26 13:36
 */
@Mapper
public interface F21004Dao {
    /**
     * IDで詳細な遅刻欠席連絡情報を取得
     * @param id    遅刻欠席連絡履歴.ID
     * @return
     */
    F21004Dto getDetail(@Param("id") Integer id);
}
