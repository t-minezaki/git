/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.pop.dto.F20014Dto;

/**
 * <p>F20014_積み残し設定画面(POP) Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/07 : gong: 新規<br />
 * @version 1.0
 */
public interface F20014Service {
    /**
     * <p>※区分（0：未計画）の場合、生徒タームプラン設定のから取得する）</p>
     *
     * @param id
     * @return
     */
    F20014Dto getInitInfoByTermId(Integer id);

    /**
     * <p>区分（1：計画済）の場合、生徒ウィークリー計画実績設定から取得する）</p>
     *
     * @param id
     * @return
     */
    F20014Dto getInitInfoByWeeklyId(Integer id);

    /**
     * <p>区分（1：計画済）の場合、生徒ウィークリー計画実績設定から取得する）</p>
     *
     * @param id
     * @return
     */
    F20014Dto getInitInfoByWeeklyIdNotInS1(Integer id);
}
