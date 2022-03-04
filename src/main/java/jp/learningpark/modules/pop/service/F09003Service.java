/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.pop.dto.F09003Dto;

import java.util.List;
import java.util.Map;

/**
 * <p>F09003_対象者選択画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/28 : yang: 新規<br />
 * @version 5.0
 */

public interface F09003Service {
    /**
     * 検索
     * @param orgId
     * @param paramsMap
     * @return
     */
    List<F09003Dto> getStuList(String orgId,Map<String, String> paramsMap,String userId,String roleDiv);
}
