package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11014Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * GakkenAppApplication
 *
 * @author lyh
 */
@Mapper
public interface F11014Dao {
    /**
     * @param monthStartDay 月開始日
     * @param monthEndDay 月終了日
     * @param eventId イベントID
     * @return
     */
    List<F11014Dto> selectScheSts(@Param("monthStartDay") Date monthStartDay, @Param("monthEndDay") Date monthEndDay, @Param("eventId") Integer eventId);

    /**
     * @param applyId 保護者イベント申込ID
     * @return
     */
    F11014Dto selectReplyCnt(Integer applyId);

    /**
     * @param tgtYmd 選択日
     * @param eventId イベントID
     * @return
     */
    List<F11014Dto> selectTimeLine(@Param("tgtYmd") Date tgtYmd, @Param("eventId") Integer eventId);

    /**
     * @param eventId
     * @return
     */
    List<F11014Dto> getAskTalk(@Param("eventId") Integer eventId);
}
