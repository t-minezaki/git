/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;


import jp.learningpark.modules.manager.dto.F21009Dto;

import java.util.List;

/**
 * <p>F21009_出席簿指導内容情報画面Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/26 : yang: 新規<br />
 * @version 5.0
 */
public interface F21009Service {
    /**
     * 指導報告書情報を画面に表示
     * @param attendBookCd
     * @param orgId
     * @return
     */
    List<F21009Dto> getGrdData(String attendBookCd,String orgId,String absDiv);

    /**
     * 出席簿明細から出席簿情報を取得
     * @param attendBookCd
     * @param orgId
     * @return
     */
    List<F21009Dto> getAbhData(String attendBookCd,String orgId,String absDiv);
}
