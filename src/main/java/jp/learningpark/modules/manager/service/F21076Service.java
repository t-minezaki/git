/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F21076Dto;

import java.util.List;

/**
 * <p>面談記録結果配信設定画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : wq: 新規<br />
 * @version 1.0
 */
public interface F21076Service {

    /**
     * @param talkIds
     * @param userList
     * @param flg
     * @param questionFlg
     * @param interviewFlg
     * @return
     */
    R insert(List<Integer> talkIds, List<F21076Dto> userList, String flg, String questionFlg, String interviewFlg);
}
