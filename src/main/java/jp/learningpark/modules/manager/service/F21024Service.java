/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21024Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>出欠席連絡一覧（スマホ）</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/25 : zpa: 新規<br />
 * @version 1.0
 */
public interface F21024Service{
    List<F21024Dto> select(String userId, String orgId, String corrspdSts, String tgtYmd, String roleDiv, Integer limit, Integer offset);

    Integer getCount(String userId, String orgId, String corrspdSts, String tgtYmd, String roleDiv, Integer limit, Integer offset);
}
