package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F08019Dto;
import jp.learningpark.modules.manager.dto.F08019TalkRecordDetails;

import java.util.List;

/**
 * <p>未読・未回答者送信一覧画面 Service</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : NWT文: 新規<br />
 * 2020/11/12 : NWT文: 変更<br />
 * @version 9.0
 */
public interface F08019Service {

    /**
     * <p>保護者イベント申込状況データを取得する</p>
     *
     * @param div 区分
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return Result<List>
     */
    List<F08019Dto> getGeasList(Integer div, String orgId, Integer eventId, Integer limit,Integer offset);

    /**
     * <p>生徒イベント申込状況データを取得する</p>
     *
     * @param div 区分
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return Result<List < F08019Dto>>
     */
    List<F08019Dto> selectStuApplyList(Integer div, String orgId, Integer eventId, Integer limit,Integer offset);

    /**
     * <p>ウンロード用保護者イベント申込状況データを取得する</p>
     *
     * @param stuIdList 学生IDリスト
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param div 区分
     * @return Result<List < F08019Dto>>
     */
    List<F08019Dto> getDownloadInfo(List<String> stuIdList, String orgId, Integer eventId, Integer div);

    /**
     * <p>ウンロード用生徒イベント申込状況データを取得する</p>
     *
     * @param stuIdList 学生IDリスト
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param div 区分
     * @return Result<List   <   F08019Dto>>
     */
    List<F08019Dto> selectStuDownloadInfo(List<String> stuIdList, String orgId, Integer eventId, Integer div);

    /**
     * <p>回答結果内容を取得する</p>
     *
     * @param eventId イベントID
     * @return Result<List   <   F08019TalkRecordDetails>>
     */
    List<F08019TalkRecordDetails> getTalkRecordDetails(Integer eventId);
}
