/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30405Dto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>保護者面談の日程設定画面 Dao</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/16 : wq: 新規<br />
 * @version 1.0
 */
public interface F30405Dao {

    /**
     * @param monthStartDay 月開始日
     * @param monthEndDay 月終了日
     * @param eventId イベントID
     * @return
     */
    List<F30405Dto> selectScheSts(@Param("monthStartDay") Date monthStartDay, @Param("monthEndDay") Date monthEndDay, @Param("eventId") Integer eventId);

    /**
     * @param applyId 保護者イベント申込ID
     * @return
     */
    F30405Dto selectReplyCnt(Integer applyId);

    /**
     * @param tgtYmd 選択日
     * @param eventId イベントID
     * @return
     */
    List<F30405Dto> selectTimeLine(@Param("tgtYmd") Date tgtYmd, @Param("eventId") Integer eventId);

    /**
     * @param eventId
     * @return
     */
    List<F30405Dto> getAskTalk(Integer eventId);
}
