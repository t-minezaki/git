/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.pop.dto.F40026Dto;

import java.util.List;

/**
 * <p>通知プッシュ失敗一覧画面（共通）</p >
 *
 * @author NWT : yyb <br />
 * 変更履歴 <br />
 * 2020/07/17 : yyb: 新規<br />
 * @version 7.1
 */
public interface F40026Service {
    /**
     *
     * @param msgId
     * @param messageTypeDiv
     * @return
     */
    F40026Dto getInfo(Integer msgId,String messageTypeDiv);
    /**
     *
     * @param msgId
     * @param msgTypeDiv
     * @param msgId
     * @param orgID
     * @return
     */
    List<F40026Dto> failedUserList(String msgTypeDiv,Integer msgId,String orgID,Integer limit, Integer offset);
    /**
     *
     * @param id
     * @return
     */
    void errDataUpdate(Integer id);

    /**
     *
     * @param errorIdList
     * @return
     * modify at 2021/09/17 for V9.02 UT-0029 by NWT yang
     */
    List<F40026Dto> selectDeliverInfo(List<Integer> errorIdList);
}
