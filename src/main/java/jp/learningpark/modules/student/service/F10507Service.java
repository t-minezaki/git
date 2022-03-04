/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.student.form.F10507Form;

import java.util.List;

/**
 * <p>テスト目標結果一覧 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/15 : wen: 新規<br />
 * @version 1.0
 */
public interface F10507Service {
    /**
     * <p>生徒テスト目標結果一覧取得</p>
     *
     * @param stuId    生徒ID
     * @param startRow 開始位置
     * @return
     */
    List<F10507Form> getGoalResultList(String stuId, Integer startRow);

    /**
     * @param id       id
     * @param updateTm 更新時間
     * @return
     */
    R delete(Integer id, String updateTm);

    /**
     * @param stuId    生徒ID
     * @param startRow 開始位置
     * @return
     */
    Integer getGoalResultCount(String stuId, Integer startRow);
}
