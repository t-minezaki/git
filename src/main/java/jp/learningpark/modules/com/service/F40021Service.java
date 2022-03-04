/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>利用規約同意画面</p >
 * <p>Service</p>
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/8/7 : wang: 新規<br />
 * @version 1.0
 */
public interface F40021Service {
    /**
     * 更新項目
     * 更新
     * データを取得する
     */
    R upDateId(Boolean gidFlg, Boolean manaFlg);
    R getDate();
}
