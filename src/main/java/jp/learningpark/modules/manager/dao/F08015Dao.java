/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08015Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F08015_配信設定/状況確認の代理登録画面(POP) Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/19 : yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F08015Dao {
    //イベント．イベントタイトルの取得
    F08015Dto getEventSchedulePlanDel(@Param("id") Integer id);

    //イベン候補者取得
    List<F08015Dto> getEventList(@Param("eventTitle") String eventTitle, @Param("orgId") String orgId);

    //生徒候補者取得
    List<F08015Dto> getStuList(@Param("orgId") String orgId, @Param("eventId") Integer eventId, @Param("userFlag") Boolean userFlag);

    //イベントの取得
    List<F08015Dto> getEventEntity(@Param("eventTitle") String eventTitle, @Param("orgId") String orgId);

    //生徒の取得
    List<F08015Dto> getStuEntity(@Param("orgId") String orgId, @Param("stuName") String stuName);

    //先生候補者取得
    List<F08015Dto> getMentorList(@Param("mentorName") String mentorName, @Param("orgId") String orgId);

    //先生の取得
    List<F08015Dto> getMentor(@Param("displayName") String displayName, @Param("eventId") Integer eventId);

    //保護者イベント申込状況を取得
    F08015Dto getDeliver(@Param("geasId") Integer geasId, @Param("userFlag") Boolean userFlag);

}
