/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


import jp.learningpark.framework.utils.R;

/**
 * <p>塾ニュース詳細表示画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
public interface F30111Service {

    /**
     *
     * @param id お知らせID
     * @param url 当画面URL
     * @return R
     */
    R getNoticeNews(Integer id ,String url);
}
