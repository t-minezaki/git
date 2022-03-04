/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>スマホ_連絡確認画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/27 : zpa: 新規<br />
 * @version 1.0
 */
public interface F21025Service {
    /**
     * IDで詳細な遅刻欠席連絡情報を取得
     *
     * @param id 遅刻欠席連絡履歴.ID
     * @return
     */
    R getDetail(Integer id);
}
