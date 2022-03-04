package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F08019Dto;
import jp.learningpark.modules.manager.dto.F08019TalkRecordDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>未読・未回答者送信一覧画面 Dao</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : NWT文: 新規<br />
 * 2020/11/12 : NWT文: 変更<br />
 * @version 9.0
 */
@Component
@Mapper
public interface F08019Dao {

    /**
     * <p>保護者イベント申込状況データを取得する</p>
     *
     * @param div 区分
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return List<F08019Dto>
     */
    List<F08019Dto> getGEASList(@Param("div") Integer div, @Param("orgId") String orgId, @Param("eventId") Integer eventId,@Param("limit") Integer limit,@Param("offset")Integer offset);

    /**
     * <p>生徒イベント申込状況データを取得する</p>
     *
     * @param div 区分
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return List<F08019Dto>
     */
    List<F08019Dto> getStuApplyList(@Param("div") Integer div, @Param("orgId") String orgId, @Param("eventId") Integer eventId,@Param("limit") Integer limit,@Param("offset")Integer offset);

    /**
     * <p>ウンロード用保護者イベント申込状況データを取得する</p>
     *
     * @param stuIdList 学生IDリスト
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param div 区分
     * @return List<F08019Dto>
     */
    List<F08019Dto> getDownloadInfo(
            @Param("stuIdList") List<String> stuIdList, @Param("orgId") String orgId, @Param("eventId") Integer eventId, @Param("div") Integer div);

    /**
     * <p>ウンロード用生徒イベント申込状況データを取得する</p>
     *
     * @param stuIdList 学生IDリスト
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param div 区分
     * @return List<F08019Dto>
     */
    List<F08019Dto> getStuDownloadInfo(
            @Param("stuIdList") List<String> stuIdList, @Param("orgId") String orgId, @Param("eventId") Integer eventId, @Param("div") Integer div);

    /**
     * <p>回答結果内容を取得する</p>
     *
     * @param eventId イベントID
     * @return
     */
    List<F08019TalkRecordDetails> getTalkRecordDetails(@Param("eventId") Integer eventId);
}
