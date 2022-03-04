/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F07003Dto;

import java.util.List;

/**
 * <p>F70003_教科メンテナンス一覧画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/04/02: wen: 新規<br />
 * @version 1.0
 */
public interface F07003Service {

    /**
     * @param subjtCd 教科CD
     * @param subjtNm 教科名
     * @param commonType ブロックタイプ（共通）
     * @param reviewType ブロックタイプ（復習系のみ）
     * @param startRow 開始位置
     * @return コードマスタ情報記録数
     */
    List<F07003Dto> getMstCodDList(String subjtCd, String subjtNm, String commonType, String reviewType, Integer startRow);

    /**
     * <p>コードマスタ情報記録数取得</p>
     *
     * @param subjtCd 教科CD
     * @param subjtNm 教科名
     * @param commonType ブロックタイプ（共通）
     * @param reviewType ブロックタイプ（復習系のみ）
     * @return コードマスタ情報リスト
     */
    Integer gettMstCodDListCount(String subjtCd, String subjtNm, String commonType, String reviewType);

    /**
     * <p>削除</p>
     *
     * @param subjtCd 教科CD
     * @param updateTm 更新時間
     * @return
     */
    R delete(String subjtCd, String updateTm);
}
