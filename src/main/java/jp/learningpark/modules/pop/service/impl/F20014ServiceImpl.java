/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.pop.dao.F20014Dao;
import jp.learningpark.modules.pop.dto.F20014Dto;
import jp.learningpark.modules.pop.service.F20014Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>F20014_積み残し設定画面(POP) ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/07 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F20014ServiceImpl implements F20014Service {
    /**
     * F20014_積み残し設定画面 Dao
     */
    @Autowired
    private F20014Dao f20014Dao;

    /**
     * <p>※区分（0：未計画）の場合、生徒タームプラン設定のから取得する）</p>
     *
     * @param id
     * @return
     */
    @Override
    public F20014Dto getInitInfoByTermId(Integer id) {
        return f20014Dao.selectInitInfoByTermId(id);
    }

    /**
     * <p>区分（1：計画済）の場合、生徒ウィークリー計画実績設定から取得する）S1</p>
     *
     * @param id
     * @return
     */
    @Override
    public F20014Dto getInitInfoByWeeklyId(Integer id) {
        return f20014Dao.selectInitInfoByWeeklyId(id);
    }

    /**
     * <p>区分（1：計画済）の場合、生徒ウィークリー計画実績設定から取得する）</p>
     *
     * @param id
     * @return
     */
    @Override
    public F20014Dto getInitInfoByWeeklyIdNotInS1(Integer id) {
        return f20014Dao.selectInitInfoByWeeklyIdNotInS1(id);
    }
}
