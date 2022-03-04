/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F00053Dto;

import java.util.List;

/**
 * <p>F00053_生徒グループ関係検索一覧画面 Service</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/18 : tan: 新規<br />
 * @version 1.0
 */
public interface F00053Service {

    /**
     * 初期化
     *
     * @param orgId    session 組織ＩＤ
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    List<F00053Dto> getGroupList(String orgId,String groupName, Integer limit, Integer page);

    Integer getTotalCount(String orgId,String groupName);

}