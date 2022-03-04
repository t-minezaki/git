package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11014Dto;

import java.util.Date;
import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 */
public interface F11014Service {
    /**
     * @param monthStartDay 月開始日
     * @param monthEndDay 月終了日
     * @param eventId イベントID
     * @return
     */
    List<F11014Dto> getScheSts(Date monthStartDay, Date monthEndDay, Integer eventId);

    /**
     * @param applyId 保護者イベント申込ID
     * @return
     */
    F11014Dto getReplyCnt(Integer applyId);

    /**
     * @param tgtYmd 選択日
     * @param eventId イベントID
     * @return
     */
    List<F11014Dto> getTimeLine(Date tgtYmd, Integer eventId);

    /**
     * @param eventId
     * @return
     */
    List<F11014Dto> getAskTalk(Integer eventId);
}
