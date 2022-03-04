/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00061Dto;

/**
 * <p>F00061_メンター生徒関係検索一覧 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/18 : gong: 新規<br />
 * @version 1.0
 */
public interface F00061Service {
    /**
     * 生徒メンター関係一覧のリストの情報
     *
     * @param dto   　画面．検索条件
     * @param limit 　画面表示の数
     * @param page  　現在のページ
     * @return
     */
    R getList(F00061Dto dto, Integer limit, Integer page);
}
