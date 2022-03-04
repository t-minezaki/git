/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.dto.F30405Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>保護者面談の日程設定画面 Service</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/16 : wq: 新規<br />
 * @version 1.0
 */
public interface F30405Service {

    /**
     * @param monthStartDay 月開始日
     * @param monthEndDay 月終了日
     * @param eventId イベントID
     * @return
     */
    List<F30405Dto> getScheSts(Date monthStartDay, Date monthEndDay, Integer eventId);

    /**
     * @param applyId 保護者イベント申込ID
     * @return
     */
    F30405Dto getReplyCnt(Integer applyId);

    /**
     * @param tgtYmd 選択日
     * @param eventId イベントID
     * @return
     */
    List<F30405Dto> getTimeLine(Date tgtYmd, Integer eventId);

    /**
     * @param eventId 　イベントID
     * @return
     */
    List<F30405Dto> selectAskTalk(Integer eventId);

    /**
     * @param eventId イベントID
     * @param applyId 　保護者イベント申込状況ID
     * @param tgtYmd 　対象年月
     * @return
     */
    R getInitData(Integer eventId, Integer applyId, String tgtYmd, Boolean firstFlag);

    /**
     * @param eventId イベントID
     * @return
     */
    R getMinDate(Integer eventId);

    /**
     * @param eventId イベントID
     * @param tgtYmd 対象年月
     * @param timeStr 時間文字
     * @return
     */
    R timeSelect(Integer eventId, String tgtYmd, String timeStr);

    /**
     * @param f30405Dto
     * @param resultList
     * @return
     */
    R updateDB(F30405Dto f30405Dto, String resultList);
}
