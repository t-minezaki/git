/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>面談記録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/25 : wq: 新規<br />
 * @version 1.0
 */
public interface F21075Service {

    /**
     * @param talkId
     * @param eventId
     * @return
     */
    R getInitData(Integer talkId, Integer eventId);

    /**
     * @param talkId
     * @param flg
     * @param resultList
     * @param isProxy
     * @return
     */
    R updateDB(Integer talkId, String flg, String resultList, boolean isProxy);
}
