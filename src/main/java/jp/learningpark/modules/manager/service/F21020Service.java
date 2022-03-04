/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21020Dto;

import java.util.List;

/**
 * <p>F21020スマホ_褒めポイント登録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/14 : wq: 新規<br />
 * @version 1.0
 */
public interface F21020Service {

    /**
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @param limit
     * @param page
     * @return
     */
    List<F21020Dto> getInitList(String orgId, String stuId, Integer limit, Integer page);

    /**
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    Integer getInitListCount(String orgId, String stuId);
}
