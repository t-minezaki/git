/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;


import jp.learningpark.modules.manager.dto.F08015Dto;

import java.util.List;

/**
 * <p>F08015_配信設定/状況確認の代理登録画面(POP) Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/19: yang: 新規<br />
 * @version 1.0
 */
public interface F08015Service {
    //イベント．イベントタイトルの取得
    F08015Dto getEventSchedulePlanDel(Integer id);

    //イベン候補者取得
    List<F08015Dto> getEventList(String eventTitle, String orgId);

    //生徒候補者取得
    List<F08015Dto> getStuList(String orgId, Integer eventId, Boolean userFlag);

    //イベントの取得
    List<F08015Dto> getEventEntitylist(String eventTitle, String orgId);

    //生徒の取得
    List<F08015Dto> getStuEntityList(String orgId, String stuName);

    //先生候補者取得
    List<F08015Dto> getMentorList(String mentorName, String orgId);

    //先生の取得
    List<F08015Dto> getMentor(String displayName, Integer eventId);

    //保護者イベント申込状況を取得
    F08015Dto getDeliver(Integer geasId, Boolean userFlag);

}
