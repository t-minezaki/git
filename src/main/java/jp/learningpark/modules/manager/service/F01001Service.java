/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F01001Dto;

import java.util.List;

/**
 * <p>F01001_塾時期検索一覧画面 Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/05 : xiong: 新規<br />
 * @version 1.0
 */
public interface F01001Service {
    /**
     * <p>F01001_塾時期検索一覧画面 Service</p >
     *
     * @author NWT : xiong <br />
     * 変更履歴 <br />
     * 2019/01/05 : xiong: 新規<br />
     * @version 1.0
     */

    /**
     * 塾時期検索一覧画面
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param pageSize
     * @param limit
     * @return
     */
    List<F01001Dto> getUpplevInfomation(String orgId, String brandCd, Integer pageSize, Integer limit);

    /**
     * 塾時期検索一覧画面
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @return
     */
    Integer getTotalCount(String orgId, String brandCd);

    /**
     * 対応する組織を組織マスタから論理削除処理行う。
     * @param id
     * @return
     */
    R deleteById(Integer id);

}
