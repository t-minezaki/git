/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


import jp.learningpark.framework.utils.R;

/**
 * <p>塾からの連絡通知詳細画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/11: xiong: 新規<br />
 * @version 1.0
 */
public interface F30113Service {

    /**
     *
     * @param id お知らせID
     * @return
     */
    R getNoticeNews(Integer id);
    // 2020/11/11 zhangminghao modify start
    /**
     * 保護者お知らせ閲覧状況を更新する。
     *
     * @param guardReadingId 保護者お知らせ閲覧状況ＩＤ
     */
    void noticeConfirm(Integer guardReadingId);
    // 2020/11/11 zhangminghao modify end
}
