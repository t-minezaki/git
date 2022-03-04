/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.student.dto.F10101Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>F10101固定スケジュール表示・編集画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/13 : gong: 新規<br />
 * @version 1.0
 */
public interface F10101Service {
    /**
     * <p>スケジュールエリア情報で表示する(F10101)</p>
     *
     * @param stuId     生徒ID
     * @param startDate 対象週開始日
     * @param endDate   対象週終了日
     * @return
     */
    List<SchdlDto> getSchdlListOfF10101(Integer id,String stuId, Date startDate,boolean checkStart, boolean checkEnd);

    /**
     * <p>固定ブロックエリア情報の取得処理</p>
     *
     * @return ブロックエリア情報
     */
    List<F10101Dto> getBlock();
}
