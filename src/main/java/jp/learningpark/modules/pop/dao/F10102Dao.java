/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>F10102_固定活動登録画面(POP) Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/11 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10102Dao {
    /**
     *<p>生徒固定スケジュール設定から固定ブロックを削除する。</p>
     *
     * @param id
     */
    void fixdschdlDel(Integer id);
}
