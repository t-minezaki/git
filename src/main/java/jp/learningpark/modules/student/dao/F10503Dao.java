/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F10503Dto;
import jp.learningpark.modules.student.form.F10503Form;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>定期テスト結果確認画面 Dao</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/12 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10503Dao {
    /**
     * <p>科目別得点推移図エリア取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv 　テスト分類区分
     * @param schyDiv     　学年区分
     * @return
     */
    List<F10503Form> getResultPointsArea(@Param("stuId") String stuId, @Param("testTypeDiv") String testTypeDiv, @Param("schyDiv") String schyDiv);

    /**
     * <p>学年リスト取得</p>
     *
     * @param stuId       生徒ID
     * @param testTypeDiv テスト分類区分
     * @return
     */
    List<F10503Form> getSchyList(@Param("stuId") String stuId, @Param("testTypeDiv") String testTypeDiv);

    /**
     * <p>生徒学年、学年区分取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    F10503Form getStuSchy(@Param("stuId") String stuId);

    /**
     * 教科タイトルリスト取得
     *
     * @param schyDiv 学年区分
     * @return
     */
    List<F10503Dto> getSubjtList(@Param("schyDiv") String schyDiv);
}
