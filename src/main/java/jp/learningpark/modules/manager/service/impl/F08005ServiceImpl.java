/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.EventSchePlanDelDao;
import jp.learningpark.modules.common.dao.EventScheduleDao;
import jp.learningpark.modules.common.dao.GuardEventApplyStsDao;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstEventDao;
import jp.learningpark.modules.common.dao.MstOrgDao;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dao.F08005Dao;
import jp.learningpark.modules.manager.dto.F08005Dto;
import jp.learningpark.modules.manager.dto.F08005ScheduleDto;
import jp.learningpark.modules.manager.service.F08005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F08005 イベント管理日程の設定画面 ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/31 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F08005ServiceImpl implements F08005Service {

    /**
     * イベント管理日程の設定画面 Dao
     */
    @Autowired
    F08005Dao f08005Dao;

    /**
     * イベントマスタ Dao
     */
    @Autowired
    MstEventDao mstEventDao;

    /**
     * コードマスタ明細 Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * 組織マスタ Dao
     */
    @Autowired
    MstOrgDao mstOrgDao;

    /**
     * イベント日程 Dao
     */
    @Autowired
    EventScheduleDao eventScheduleDao;

    /**
     * イベント日程(詳細) Dao
     */
    @Autowired
    EventSchePlanDelDao eventSchePlanDelDao;

    /**
     * 保護者イベント申込状況 Dao
     */
    @Autowired
    GuardEventApplyStsDao guardEventApplyStsDao;

    /**
     * @param eventId イベントID
     * @param userName ユーザー名
     * @return
     */
    @Override
    public R getInitData(Integer eventId, String userName) {
        //イベントエンティティ取得
        MstEventEntity mstEventEntity = mstEventDao.selectById(eventId);
        //カラーリスト
        List<MstCodDEntity> colorList = mstCodDDao.selectList(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "COLOR_BASE").eq("del_flg", 0).orderByAsc("sort"));
        R r = R.ok();
        r.put("colorList", colorList).put("mstEventEntity", mstEventEntity);
        //本組織
        MstOrgEntity mstOrgEntity = null;
        //ユーザーチェックリスト
        List<F08005Dto> userCheckList = null;
        //ユーザー対応の色を表示される
        if (StringUtils.equals("0", mstEventEntity.getRefType())) {
            mstOrgEntity = mstOrgDao.selectOne(
                    new QueryWrapper<MstOrgEntity>().select("org_id", "org_nm").eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0));
            r.put("mstOrgEntity", mstOrgEntity);
        } else {
            userCheckList = f08005Dao.selectUserCheckList(ShiroUtils.getUserEntity().getOrgId(), userName);
            r.put("userCheckList", userCheckList);
        }
        Date minScheDate = null;
        if (StringUtils.equals("0", mstEventEntity.getRefType())) {
            List<EventScheduleEntity> getMinDate = eventScheduleDao.selectList(
                    new QueryWrapper<EventScheduleEntity>().select("sgd_plan_date").eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("event_id",
                            eventId).eq("ref_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0).orderByAsc("sgd_plan_date"));
            if (getMinDate.size() != 0) {
                minScheDate = getMinDate.get(0).getSgdPlanDate();
            }
        } else {
            minScheDate = f08005Dao.selectMentorMinScheduleDate(ShiroUtils.getUserEntity().getOrgId(), eventId);
        }
        return r.put("minScheDate", minScheDate);
    }

    /**
     * @param eventId 　イベントID
     * @param tgtYmd 　対象年月日
     * @return
     */
    public R getEventScheduleData(Integer eventId, String tgtYmd) {
        R r = R.ok();
        //イベントエンティティ取得
        MstEventEntity mstEventEntity = mstEventDao.selectById(eventId);
        //今週の月曜日
        Date thisMonday = DateUtils.getMonday(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD));
        //今週の日曜日
        Date thisSunday = DateUtils.getSunday(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYYMMDD));
        //日程スケジュール情報
        List<F08005ScheduleDto> eventScheduleEntityList = null;
        if (StringUtils.equals("0", mstEventEntity.getRefType())) {
            eventScheduleEntityList = f08005Dao.selectScheduleInfo(ShiroUtils.getUserEntity().getOrgNm(), ShiroUtils.getUserEntity().getOrgId(), eventId,
                    thisMonday, thisSunday);
        } else {
            eventScheduleEntityList = f08005Dao.selectMentorScheduleInfo(ShiroUtils.getUserEntity().getOrgId(), eventId, thisMonday, thisSunday);
        }

        //カラーリスト
        List<MstCodDEntity> colorList = mstCodDDao.selectList(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "COLOR_BASE").eq("del_flg", 0).orderByAsc("sort"));
        //本組織
        MstOrgEntity mstOrgEntity = null;
        //ユーザーチェックリスト
        List<F08005Dto> userCheckList = null;
        //ユーザー対応の色を表示される
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.equals("0", mstEventEntity.getRefType())) {
            mstOrgEntity = mstOrgDao.selectOne(
                    new QueryWrapper<MstOrgEntity>().select("org_id", "org_nm").eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0));
            map.put(mstOrgEntity.getOrgId(), colorList.get(0).getCodValue());
            r.put("mstOrgEntity", mstOrgEntity);
        } else {
            userCheckList = f08005Dao.selectUserCheckList(ShiroUtils.getUserEntity().getOrgId(), null);
            if (userCheckList.size() > colorList.size()) {
                int loop = userCheckList.size() / colorList.size();
                for (int i = 0; i < userCheckList.size(); i++) {
                    if (i > colorList.size() - 1) {
                        //                        i = 0;
                        map.put(userCheckList.get(i).getusrId(), colorList.get(i - loop * colorList.size()).getCodValue());
                    } else {
                        map.put(userCheckList.get(i).getusrId(), colorList.get(i).getCodValue());
                    }
                }
            } else {
                for (int i = 0; i < userCheckList.size(); i++) {
                    map.put(userCheckList.get(i).getusrId(), colorList.get(i).getCodValue());
                }
            }
            r.put("userCheckList", userCheckList);
        }
        //日程スケジュール情報が未取得の場合
        if (eventScheduleEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "日程スケジュール情報")).put("map", map);
        }
        return R.ok().put("eventScheduleEntityList", eventScheduleEntityList).put("map", map);
    }

    /**
     * @param entity
     * @return
     */
    public R updateDB(F08005ScheduleDto entity) {
        //エンティティ取得
        EventScheduleEntity eventScheduleEntity = eventScheduleDao.selectById(entity.getId());
        MstEventEntity eventEntity = mstEventDao.selectById(eventScheduleEntity.getEventId());
        List<GuardEventApplyStsEntity> applyStsEntity = guardEventApplyStsDao.selectList(
                new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", eventScheduleEntity.getEventId()).eq("reply_sts_div", "1").eq("del_flg", 0));
        //取得条件が取得できた場合、スケジュールの移動不可
        if (applyStsEntity.size() != 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0121", "該当イベント"));
        }
        //イベント日程(詳細)リストを取得
        List<EventSchePlanDelEntity> eventSchePlanDelEntities = eventSchePlanDelDao.selectList(
                new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", eventScheduleEntity.getId()).eq("del_flg", 0));
        //フォーマット
        Timestamp timeStart = DateUtils.toTimestamp(DateUtils.parse(entity.getStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO));
        Timestamp timeEnd = DateUtils.toTimestamp(DateUtils.parse(entity.getEndTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO));

        //スケジュールの日程を移動可否チェック
        Date dateCheck = DateUtils.parse(entity.getStartTime().split(" ")[0], GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Timestamp timeStartCheck = DateUtils.toTimestamp(DateUtils.parse(entity.getStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO));
        Timestamp timeEndCheck = DateUtils.toTimestamp(DateUtils.parse(entity.getEndTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO));
        List<EventScheduleEntity> entityCheck = f08005Dao.selectScheduleForCheck(eventScheduleEntity.getEventId(), eventScheduleEntity.getRefId(), dateCheck,
                timeStartCheck, timeEndCheck);
        for (EventScheduleEntity eventsche : entityCheck) {
            if (!eventsche.getId().equals(entity.getId())) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
            }
        }
        if (entityCheck.size() > 1) {
            return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
        }
        if (eventScheduleEntity != null) {
            //日程予定日
            eventScheduleEntity.setSgdPlanDate(DateUtils.parse(entity.getStartTime().split(" ")[0], GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
            //日程開始日時を更新
            eventScheduleEntity.setSgdStartDatime(timeStart);
            //日程終了日時を更新
            eventScheduleEntity.setSgdEndDatime(timeEnd);
            //更新日時
            eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            eventScheduleEntity.setUpdUsrId(ShiroUtils.getUserId());
            //データベースで更新
            eventScheduleDao.updateById(eventScheduleEntity);
        }
        for (int i = 0; i < eventSchePlanDelEntities.size(); i++) {
            //日程予定日
            eventSchePlanDelEntities.get(i).setSgdPlanDate(DateUtils.parse(entity.getStartTime().split(" ")[0], GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
            //日程開始日時を更新
            eventSchePlanDelEntities.get(i).setSgdStartDatime(DateUtils.toTimestamp(
                    DateUtils.addMinutes(DateUtils.parse(entity.getStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO), eventEntity.getUnitTime() * i)));
            //日程終了日時を更新
            if (i == eventSchePlanDelEntities.size() - 1) {
                eventSchePlanDelEntities.get(i).setSgdEndDatime(timeEnd);
            } else {
                eventSchePlanDelEntities.get(i).setSgdEndDatime(DateUtils.toTimestamp(
                        DateUtils.addMinutes(DateUtils.parse(entity.getStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO),
                                eventEntity.getUnitTime() * (i + 1))));
            }
            //更新日時
            eventSchePlanDelEntities.get(i).setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            eventSchePlanDelEntities.get(i).setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelDao.updateById(eventSchePlanDelEntities.get(i));
        }
        return R.ok().put("eventScheduleEntity", eventScheduleEntity);
    }
}
