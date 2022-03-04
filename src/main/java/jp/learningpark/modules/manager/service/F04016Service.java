/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F04016Dto;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（マナミルチャンネル）Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/10 : yang: 新規<br />
 * @version 6.0
 */
public interface F04016Service {
    /**
     * 保護者お知らせ閲覧状況より、取得する
     * @param noticeId
     * @param flg
     * @param limit
     * @param page
     * @return
     */
    List<F04016Dto> init(Integer noticeId, String flg,String orgId, Integer limit, Integer page);

    /**
     * 件数の取得
     * @param noticeId
     * @param flg
     * @return
     */
    Integer getTotalCount(Integer noticeId,String flg,String orgId);
}
