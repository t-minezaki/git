/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dto.F10305Dto;

/**
 * <p>教科書デフォルトターム情報 Extend Service</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/10/19 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F10305Service {
    /**
     * <p>当ブロックの単元情報を取得</p>
     *
     * @param wppId        生徒ウィークリー計画実績設定Id
     * @param orgId        塾ID
     * @param blockTypeDiv ブロック種類区分
     * @return
     */
    F10305Dto getTextbDefByUnitiId(Integer wppId, String orgId,String blockTypeDiv);

    /**
     * 登録
     *
     * @param stuId
     * @param type  更新フラグ
     * @param dto   画面情報
     * @return
     */
    R submitFn(String stuId, String type, F10305Dto dto);

    /**
     * 削除
     *
     * @param stuId
     * @param dto
     * @return
     */
    R delete(String stuId, F10305Dto dto);
}
