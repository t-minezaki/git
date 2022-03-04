/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.form.F10507Form;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>テスト目標結果一覧 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/15 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10507Dao {
    /**
     * <p>生徒テスト目標結果一覧取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    List<F10507Form> selectGoalResultList(@Param("stuId") String stuId, @Param("startRow") Integer startRow);

    /**
     * <p>生徒テスト目標結果total</p>
     *
     * @param stuId    生徒ID
     * @param startRow 開始位置
     * @return
     */
    Integer selectGoalResultCount(@Param("stuId") String stuId, @Param("startRow") Integer startRow);
}
