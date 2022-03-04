package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * スマホ_学習情報登録｜タイマー登録２ Dao
 * </p>
 *
 * @author NWT)zmh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/12 ： NWT)zmh ： 新規作成
 * @date 2020/11/12
 */
@Mapper
public interface F11007Dao {

    /**
     * 該当生徒より、コードマスタ_明細から科目を取得する。
     *
     * @param stuId 学生コード
     * @return 明細から科目を取得
     */
    List<F11001Dto> getSubjt(@Param("stuId")String stuId);
}
