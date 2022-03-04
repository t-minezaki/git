/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.manager.dao.F08013Dao;
import jp.learningpark.modules.manager.dto.F08013Dto;
import jp.learningpark.modules.manager.dto.F08013GuardApplyDto;
import jp.learningpark.modules.manager.service.F08013Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>配信設定状況確認カレンダー表示画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/12 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F08013ServiceImpl implements F08013Service {
    /**
     * イベント管理日程の設定画面 Dao
     */
    @Autowired
    F08013Dao f08013Dao;

    /**
     * @param orgId 組織ID
     * @param inputNm 画面．ユーザ検索エリア．ユーザー名
     * @return
     */
    @Override
    public List<F08013Dto> getUserCheckList(String orgId, String inputNm) {
        return f08013Dao.selectUserCheckList(orgId, inputNm);
    }

    /**
     * @param orgId 組織ID
     * @param startDate 対象週開始日
     * @param endDate 対象週終了日
     * @return
     */
    @Override
    public List<F08013GuardApplyDto> getScheduleInfo(String orgId, Date startDate, Date endDate) {
        return f08013Dao.selectScheduleInfo(orgId, startDate, endDate);
    }

    /**
     * @param orgId 組織ID
     * @param eventTitle イベントタイトル
     * @return
     */
    @Override
    public List<MstEventEntity> getEventInfo(String orgId, String eventTitle) {
        return f08013Dao.selectEventInfo(orgId, eventTitle);
    }

    /**
     * @param orgId 組織ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return
     */
    @Override
    public List<F08013GuardApplyDto> getEventScheDelInfo(String orgId, Date startDate, Date endDate) {
        return f08013Dao.selectEventScheDelInfo(orgId, startDate, endDate);
    }

    /**
     * @param eventScheDelId イベント日程(明細)ID
     * @return
     */
    @Override
    public F08013GuardApplyDto getGuardInfo(Integer eventScheDelId) {
        return f08013Dao.selectGuardInfo(eventScheDelId);
    }

    /**
     * @param eventScheDelId イベント日程(明細)ID
     * @return
     */
    @Override
    public F08013GuardApplyDto getStudentInfo(Integer eventScheDelId) {
        return f08013Dao.selectStudentInfo(eventScheDelId);
    }
}
