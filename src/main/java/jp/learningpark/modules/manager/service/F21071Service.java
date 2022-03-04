/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21071Dto;

import java.util.List;

/**
 * <p>F21071_配信先既読未読状態確認一覧画面（インフォメーション）Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/06/01 : yang: 新規<br />
 * @version 7.0
 */
public interface F21071Service {
    /**
     * 既読未読集計一覧を取得する。
     * @param msgId
     * @param orgIdList
     * @param limit
     * @param page
     * @param flg
     * @return
     */
    List<F21071Dto> getGridList(Integer msgId,List<String> orgIdList,Integer limit,Integer page,boolean flg);

}
