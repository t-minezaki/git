package jp.learningpark.modules.student.service;

import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.student.dto.F11001Dto;

import java.util.List;

/**
 * <p>
 * スマホ_学習情報登録｜タイマー登録２ Service
 * </p>
 *
 * @author NWT)zmh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/12 ： NWT)zmh ： 新規作成
 * @date 2020/11/12
 */
public interface F11007Service {

    /**
     * 該当生徒より、コードマスタ_明細から科目を取得する。
     *
     * @return 明細から科目を取得
     */
    List<F11001Dto> getSubjt();

    /**
     * マスタブロックからカテゴリを取得する。
     *
     * @return カテゴリを取得する
     */
    List<MstBlockEntity> getBlockType();

}
