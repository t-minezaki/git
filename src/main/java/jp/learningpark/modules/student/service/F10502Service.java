/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.student.dto.F10502Dto;

import java.util.List;

/**
 * <p>F10502 テスト目標結果登録画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/21 : gong: 新規<br />
 * @version 1.0
 */
public interface F10502Service {
    /**
     * <p>1.4 生徒基本マスタ、教科書マスタ、コードマスタより、教科リストを取得し</p>
     * @param stuId 生徒Id
     * @return
     */
    List<MstCodDEntity> getSubjtDivByStuId(String stuId);

    /**
     * 1.5 生徒テスト目標結果_明細リストの情報
     * @param id
     * @param schyDiv
     * @return
     */
    List<F10502Dto> getWithCodById(Integer id,String schyDiv);

    /**
     * <p>「登録」ボタン押下</p>
     * @param dtos  画面．生徒テスト目標結果_明細リスト
     * @return
     */
    R update(List<F10502Dto> dtos);
    List<MstCodDEntity> getTestSubjt(String ShyDiv);
}
