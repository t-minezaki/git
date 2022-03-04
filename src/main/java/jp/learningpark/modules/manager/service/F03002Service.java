/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F03002Dto;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/08 : gong: 新規<br />
 * @version 1.0
 */
public interface F03002Service {
    /**
     * <p>教科書情報と教科書単元情報を表示するため、教科書マスタ、教科書デフォルトターム情報を元に、前画面引渡の教科書IDを取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @return              教科書情報
     */
    List<F03002Dto> getTexdiff(Integer textbId, Integer crmLearnPrdId);

    /**
     * <p>編集保存ボタン押下</p>
     *
     * @param dtos 画面の情報
     * @return     編集情報
     */
    R editFn(List<F03002Dto> dtos);

    /**
     * <p>7．教科書作成ボタン押下</p>
     *
     * @param dtoList 画面の情報
     * @return        教科書作成情報
     */
    R createFn(List<F03002Dto> dtoList);
}
