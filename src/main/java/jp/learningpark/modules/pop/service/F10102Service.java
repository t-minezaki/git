/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

/**
 * <p>F10102_固定活動登録画面(POP) Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/11 : wen: 新規<br />
 * @version 1.0
 */
public interface F10102Service {
    /**
     *<p>生徒固定スケジュール設定から固定ブロックを削除する。</p>
     *
     * @param id
     */
    void delBoth(Integer id);
}
