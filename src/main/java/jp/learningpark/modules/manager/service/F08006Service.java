/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;


import jp.learningpark.modules.manager.dto.F08006Dto;

import java.util.List;

/**
 * <p>F08006_イベント日程時間設定画面(POP) Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/01: yang: 新規<br />
 * @version 1.0
 */
public interface F08006Service {

    /**
     * 候補者リストを取得
     *
     * @param mentorName
     * @param orgId
     * @return
     */
    List<F08006Dto> getMentorList(String mentorName, String orgId);

    /**
     * ユーザー存在チェック
     *
     * @param orgId
     * @param mentorId
     * @return
     */
    F08006Dto getuserEntity(String orgId, String mentorId);

}
