package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.MailUtils;
import jp.learningpark.modules.manager.dao.F08019Dao;
import jp.learningpark.modules.manager.dto.F08019Dto;
import jp.learningpark.modules.manager.dto.F08019TalkRecordDetails;
import jp.learningpark.modules.manager.service.F08019Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>未読・未回答者送信一覧画面 ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : NWT文: 新規<br />
 * 2020/11/12 : NWT文: 変更<br />
 * @version 9.0
 */
@Service
public class F08019ServiceImpl implements F08019Service {

    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;

    /**
     * f08019Dao
     */
    @Autowired
    private F08019Dao f08019Dao;

    /**
     *
     */
    @Value("${learningpark-domain.domain}")
    String domain;

    /**
     * @param div 区分
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return List<F08019Dto>
     */
    @Override
    public List<F08019Dto> getGeasList(Integer div, String orgId, Integer eventId, Integer limit,Integer offset) {
        return f08019Dao.getGEASList(div, orgId, eventId,limit,offset);
    }

    /**
     * @param div 区分
     * @param orgId 組織ID
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F08019Dto> selectStuApplyList(Integer div, String orgId, Integer eventId, Integer limit,Integer offset) {
        return f08019Dao.getStuApplyList(div, orgId, eventId,limit,offset);
    }

    /**
     * @param stuIdList 学生IDリスト
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param div 区分
     * @return List<F08019Dto>
     */
    @Override
    public List<F08019Dto> getDownloadInfo(List<String> stuIdList, String orgId, Integer eventId, Integer div) {
        return f08019Dao.getDownloadInfo(stuIdList, orgId, eventId, div);
    }

    /**
     * @param stuIdList 学生IDリスト
     * @param orgId 組織ID
     * @param eventId イベントID
     * @param div 区分
     * @return
     */
    @Override
    public List<F08019Dto> selectStuDownloadInfo(List<String> stuIdList, String orgId, Integer eventId, Integer div) {
        return f08019Dao.getStuDownloadInfo(stuIdList, orgId, eventId, div);
    }

    /**
     * 回答結果内容を取得する
     *
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F08019TalkRecordDetails> getTalkRecordDetails(Integer eventId) {
        return f08019Dao.getTalkRecordDetails(eventId);
    }

    ;
}
