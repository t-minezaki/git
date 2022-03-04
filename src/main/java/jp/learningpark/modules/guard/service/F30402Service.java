/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


import jp.learningpark.framework.utils.R;

/**
 * <p>塾からのイベント情報詳細画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/30: hujiaxing: 新規<br />
 * @version 1.0
 */
public interface F30402Service {

    /**
     * 初期化  ページ
     * @param id イベントID
     * @return
     */
    R getEventNews(Integer id);
}
