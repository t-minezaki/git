package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.EventSchePlanDelDao;
import jp.learningpark.modules.common.dao.EventScheduleDao;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.EventScheduleService;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstMentorService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.manager.dto.F08006Dto;
import jp.learningpark.modules.manager.service.F08006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>F08006_イベント日程時間設定画面(POP) Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/01: yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/F08006")
@RestController
public class F08006Controller {
    /**
     * イベントスケジュール service
     */
    @Autowired
    EventScheduleService eventScheduleService;

    /**
     * イベントマスタ service
     */
    @Autowired
    MstEventService mstEventService;

    /**
     * ユーザーマスタ service
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * イベント日程時間設定画面 service
     */
    @Autowired
    F08006Service f08006Service;

    /**
     * イベント日程(詳細) service
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;

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
     * メンターマスタ service
     */
    @Autowired
    MstMentorService mstMentorService;

    /**
     * 保護者イベント申込状況 service
     */
    @Autowired
    GuardEventApplyStsService guardEventApplyStsService;

    /**
     * <p>初期表示</p>
     *
     * @param scheduleId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer scheduleId, String mentorId) {
        R info = R.ok();
        String applySts = scheduleId == null ? "" : "0";
        //引渡データ．日程IDがある場合、
        if (scheduleId != null) {
            //前画面から引渡したクリック日時、日程IDより、下記条件で、イベント日程より、データを取得し、画面初期表示する
            EventScheduleEntity eventScheduleEntity = eventScheduleService.getOne(
                    new QueryWrapper<EventScheduleEntity>().eq("id", scheduleId).eq("del_flg", 0));

            //取得したイベントは予約が入った場合
            List<GuardEventApplyStsEntity> guardEventApplyStsList = guardEventApplyStsService.list(
                    new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", eventScheduleEntity.getEventId()).eq("reply_sts_div", "1").eq("del_flg", 0));
            if (guardEventApplyStsList.size() != 0) {
                applySts = "1";
            }
            MstMentorEntity mstMentorEntity = mstMentorService.getOne(new QueryWrapper<MstMentorEntity>().eq("mentor_id", mentorId).eq("del_flg", 0));
            return R.ok().put("eventScheduleEntity", eventScheduleEntity).put("mstMentorEntity", mstMentorEntity).put("applySts", applySts);
        }
        info.put("applySts", applySts);
        //return
        return info;
    }

    /**
     * 新規（個別）
     *
     * @param username
     * @param startTimedate
     * @param SgdStartDatime
     * @param SgdEndDatime
     * @param eventId
     * @param scheduleId
     * @param refType
     * @param personlimit
     * @return
     */
    @RequestMapping(value = "/saveSingle", method = RequestMethod.POST)
    public R saveSingle(String mentorId, String username, String startTimedate, String selectDate, String SgdStartDatime, String SgdEndDatime, Integer eventId, Integer scheduleId, String refType, Integer personlimit) throws UnsupportedEncodingException {
        //前画面から引渡したイベントIDより、1コマ時間を取得し
        MstEventEntity mstEventEntity = mstEventService.getOne(new QueryWrapper<MstEventEntity>().eq("id", eventId).eq("del_flg", 0));
        Date startTime = null;
        Date sgdstartDatime = null;
        Date sgdendDatime = null;
        EventScheduleEntity eventScheduleEntity = null;
        EventSchePlanDelEntity eventSchePlanDelEntity = null;
        if (!StringUtils.isEmpty(startTimedate)) {
            //日程予定日を取得する
            startTime = DateUtils.parse(URLDecoder.decode(startTimedate, "UTF-8").substring(0, 10), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            //日程開始日時を取得する
            sgdstartDatime = DateUtils.parse(URLDecoder.decode(startTimedate, "UTF-8").substring(0, 10) + " " + SgdStartDatime,
                    GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            //日程終了日時を取得する
            sgdendDatime = DateUtils.parse(URLDecoder.decode(startTimedate, "UTF-8").substring(0, 10) + " " + SgdEndDatime,
                    GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        } else {
            //日程予定日を取得する
            startTime = DateUtils.parse(URLDecoder.decode(selectDate, "UTF-8").replace("-", "/").substring(0, 10), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
            //日程開始日時を取得する
            sgdstartDatime = DateUtils.parse(
                    URLDecoder.decode(selectDate, "UTF-8").substring(0, 10) + " " + SgdStartDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            //日程終了日時を取得する
            sgdendDatime = DateUtils.parse(
                    URLDecoder.decode(selectDate, "UTF-8").substring(0, 10) + " " + SgdEndDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        }
        Calendar date = Calendar.getInstance();
        date.setTime(sgdendDatime);
        Long end = date.getTimeInMillis();
        date.setTime(sgdstartDatime);
        Long start = date.getTimeInMillis();
        //回数目を取得する
        Integer times = (new Double(Math.ceil((double)((end - start) / (1000 * 60)) / (double)mstEventEntity.getUnitTime()))).intValue();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザーID0
        String userId = ShiroUtils.getUserEntity().getUsrId();
        String displayNm = StringUtils.equals("", username) ? ShiroUtils.getUserEntity().getOrgNm() : username;
        //ユーザー存在チェック
        F08006Dto mstUsrEntity = f08006Service.getuserEntity(orgId, mentorId);
        //画面ステータスが「新規」の場合
        if (scheduleId == null) {
            //セッションデータ．関連タイプが「1：メンター」時、
            if (StringUtils.equals("1", refType)) {
                //ユーザー名未入力時、
                if (StringUtils.equals("", username)) {
                    //エラーメッセージ
                    return R.error(MessageUtils.getMessage("MSGCOMD0001", "ユーザー名"));
                }
                //取得できない場合
                if (mstUsrEntity == null) {
                    //エラーメッセージ
                    return R.error(MessageUtils.getMessage("MSGCOMN0009", "ユーザー名", "ユーザーマスタ"));
                }
                List<EventScheduleEntity> checkList = eventScheduleService.list(
                        new QueryWrapper<EventScheduleEntity>().eq("event_id", eventId).eq("ref_id", mstUsrEntity.getUsrId()).eq("sgd_plan_date", startTime).lt(
                                "sgd_start_datime", sgdendDatime).gt("sgd_end_datime", sgdstartDatime).eq("del_flg", 0));
                if (checkList.size() > 0) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
                }
                //イベント日程へ登録
                eventScheduleEntity = new EventScheduleEntity();
                //イベントID
                eventScheduleEntity.setEventId(eventId);
                //組織ID
                eventScheduleEntity.setOrgId(orgId);
                //関連ID
                eventScheduleEntity.setRefId(mstUsrEntity.getUsrId());
                //関連タイプ
                eventScheduleEntity.setRefType(refType);
                //日程予定日
                eventScheduleEntity.setSgdPlanDate(startTime);
                //日程開始日時
                eventScheduleEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                //日程終了日時
                eventScheduleEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                //定員
                eventScheduleEntity.setPersonsLimt(1);
                //1コマ時間
                eventScheduleEntity.setUnitTime(mstEventEntity.getUnitTime());
                //作成日時
                eventScheduleEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                eventScheduleEntity.setCretUsrId(userId);
                //更新日時
                eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                eventScheduleEntity.setUpdUsrId(userId);
                //削除フラグ
                eventScheduleEntity.setDelFlg(0);
                //データベースにインポート
                eventScheduleDao.insert(eventScheduleEntity);
                //回数目循環挿入データ
                for (int i = 0; i < times; i++) {
                    //イベント日程（詳細）へ登録
                    eventSchePlanDelEntity = new EventSchePlanDelEntity();
                    //イベントID
                    eventSchePlanDelEntity.setEventId(eventId);
                    //イベント日程ID
                    eventSchePlanDelEntity.setEventScheId(eventScheduleEntity.getId());
                    //関連ID
                    eventSchePlanDelEntity.setRefId(mstUsrEntity.getUsrId());
                    //関連タイプ
                    eventSchePlanDelEntity.setRefTypeDiv(refType);
                    //日程予定日
                    eventSchePlanDelEntity.setSgdPlanDate(startTime);
                    //日程開始日時
                    eventSchePlanDelEntity.setSgdStartDatime(DateUtils.toTimestamp(DateUtils.addMinutes(sgdstartDatime, mstEventEntity.getUnitTime() * i)));
                    //日程終了日時
                    if (i == times - 1) {
                        eventSchePlanDelEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                    } else {
                        eventSchePlanDelEntity.setSgdEndDatime(
                                DateUtils.toTimestamp(DateUtils.addMinutes(sgdstartDatime, mstEventEntity.getUnitTime() * (i + 1))));
                    }
                    //定員
                    eventSchePlanDelEntity.setPersonsLimt(1);
                    //予定済人数
                    eventSchePlanDelEntity.setPlanedMember(0);
                    //取消フラグ
                    eventSchePlanDelEntity.setCancelFlg("0");
                    //作成日時
                    eventSchePlanDelEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    eventSchePlanDelEntity.setCretUsrId(userId);
                    //更新日時
                    eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    eventSchePlanDelEntity.setUpdUsrId(userId);
                    //削除フラグ
                    eventSchePlanDelEntity.setDelFlg(0);
                    //データベースにインポート
                    eventSchePlanDelDao.insert(eventSchePlanDelEntity);
                }
            }
            //セッションデータ．関連タイプが「0：教室」時、
            else if (StringUtils.equals("0", refType)) {
                List<EventScheduleEntity> checkList = eventScheduleService.list(
                        new QueryWrapper<EventScheduleEntity>().eq("event_id", eventId).eq("ref_id", orgId).eq("sgd_plan_date", startTime).lt(
                                "sgd_start_datime", sgdendDatime).gt("sgd_end_datime", sgdstartDatime).eq("del_flg", 0));
                if (checkList.size() > 0) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
                }
                //イベント日程へ登録
                eventScheduleEntity = new EventScheduleEntity();
                //イベントID
                eventScheduleEntity.setEventId(eventId);
                //組織ID
                eventScheduleEntity.setOrgId(orgId);
                //関連ID
                eventScheduleEntity.setRefId(orgId);
                //関連タイプ
                eventScheduleEntity.setRefType(refType);
                //日程予定日
                eventScheduleEntity.setSgdPlanDate(startTime);
                //日程開始日時
                eventScheduleEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                //日程終了日時
                eventScheduleEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                //定員
                eventScheduleEntity.setPersonsLimt(personlimit);
                //1コマ時間
                eventScheduleEntity.setUnitTime(mstEventEntity.getUnitTime());
                //作成日時
                eventScheduleEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                eventScheduleEntity.setCretUsrId(userId);
                //更新日時
                eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                eventScheduleEntity.setUpdUsrId(userId);
                //削除フラグ
                eventScheduleEntity.setDelFlg(0);
                //データベースにインポート
                eventScheduleDao.insert(eventScheduleEntity);

                //イベント日程（詳細）へ登録
                eventSchePlanDelEntity = new EventSchePlanDelEntity();
                //イベントID
                eventSchePlanDelEntity.setEventId(eventId);
                //イベント日程ID
                eventSchePlanDelEntity.setEventScheId(eventScheduleEntity.getId());
                //関連ID
                eventSchePlanDelEntity.setRefId(orgId);
                //関連タイプ
                eventSchePlanDelEntity.setRefTypeDiv(refType);
                //日程予定日
                eventSchePlanDelEntity.setSgdPlanDate(startTime);
                //日程開始日時
                eventSchePlanDelEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                //日程終了日時
                eventSchePlanDelEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                //定員
                eventSchePlanDelEntity.setPersonsLimt(personlimit);
                //予定済人数
                eventSchePlanDelEntity.setPlanedMember(0);
                //取消フラグ
                eventSchePlanDelEntity.setCancelFlg("0");
                //作成日時
                eventSchePlanDelEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                eventSchePlanDelEntity.setCretUsrId(userId);
                //更新日時
                eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                eventSchePlanDelEntity.setUpdUsrId(userId);
                //削除フラグ
                eventSchePlanDelEntity.setDelFlg(0);
                //データベースにインポート
                eventSchePlanDelDao.insert(eventSchePlanDelEntity);
            }
        }
        //画面ステータスが「変更」の場合
        if (scheduleId != null) {
            /* 2021/1/13 UT-122 cuikailin add start */
            //保護者申し込み済みで削除できないチェック
            EventSchePlanDelEntity eventSchePlanDelEntity1 = eventSchePlanDelService.getOne(
                    new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", scheduleId).gt("planed_member", 0).eq("del_flg", 0));
            //取得できた場合
            if (eventSchePlanDelEntity1 != null) {
                //エラーメッセージ
                return R.error(MessageUtils.getMessage("MSGCOMN0111", "保護者"));
            }
            /* 2021/1/13 UT-122 cuikailin add end */
            eventScheduleEntity = eventScheduleService.getById(scheduleId);
            Timestamp startDateTime = new Timestamp(DateUtils.parse(
                    DateUtils.format(eventScheduleEntity.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS) + " " + SgdStartDatime + ":00",
                    GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
            Timestamp endDateTime = new Timestamp(DateUtils.parse(
                    DateUtils.format(eventScheduleEntity.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS) + " " + SgdEndDatime + ":00",
                    GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
            if (StringUtils.equals("1", refType)) {
                //ユーザー名未入力時、
                if (StringUtils.equals("", username)) {
                    //エラーメッセージ
                    return R.error(MessageUtils.getMessage("MSGCOMD0001", "ユーザー名"));
                }
                List<EventScheduleEntity> checkList = eventScheduleService.list(
                        new QueryWrapper<EventScheduleEntity>().eq("event_id", eventId).eq("ref_id", eventScheduleEntity.getRefId()).eq("sgd_plan_date",
                                eventScheduleEntity.getSgdPlanDate()).lt("sgd_start_datime", sgdendDatime).gt(
                                "sgd_end_datime", sgdstartDatime).eq("del_flg", 0));
                for (EventScheduleEntity checkEntity : checkList) {
                    if (!checkEntity.getId().equals(scheduleId)) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
                    }
                }

                //日程開始日時
                eventScheduleEntity.setSgdStartDatime(startDateTime);
                //日程終了日時
                eventScheduleEntity.setSgdEndDatime(endDateTime);
                //関連ID
                eventScheduleEntity.setRefId(mstUsrEntity.getUsrId());
                //
                eventScheduleEntity.setUpdUsrId(ShiroUtils.getUserId());
                //
                eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
                eventScheduleService.updateById(eventScheduleEntity);

                //イベント日程（詳細）から論理削除する
                eventSchePlanDelService.remove(new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", scheduleId).eq("del_flg", 0));

                for (int i = 0; i < times; i++) {
                    //イベント日程（詳細）へ登録
                    eventSchePlanDelEntity = new EventSchePlanDelEntity();
                    //イベントID
                    eventSchePlanDelEntity.setEventId(eventId);
                    //イベント日程ID
                    eventSchePlanDelEntity.setEventScheId(eventScheduleEntity.getId());
                    //関連ID
                    eventSchePlanDelEntity.setRefId(mstUsrEntity.getUsrId());
                    //関連タイプ
                    eventSchePlanDelEntity.setRefTypeDiv(refType);
                    //日程予定日
                    eventSchePlanDelEntity.setSgdPlanDate(startTime);
                    //日程開始日時
                    eventSchePlanDelEntity.setSgdStartDatime(DateUtils.toTimestamp(DateUtils.addMinutes(sgdstartDatime, mstEventEntity.getUnitTime() * i)));
                    //日程終了日時
                    if (i == times - 1) {
                        eventSchePlanDelEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                    } else {
                        eventSchePlanDelEntity.setSgdEndDatime(
                                DateUtils.toTimestamp(DateUtils.addMinutes(sgdstartDatime, mstEventEntity.getUnitTime() * (i + 1))));
                    }
                    //定員
                    eventSchePlanDelEntity.setPersonsLimt(1);
                    //予定済人数
                    eventSchePlanDelEntity.setPlanedMember(0);
                    //取消フラグ
                    eventSchePlanDelEntity.setCancelFlg("0");
                    //作成日時
                    eventSchePlanDelEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    eventSchePlanDelEntity.setCretUsrId(userId);
                    //更新日時
                    eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    eventSchePlanDelEntity.setUpdUsrId(userId);
                    //削除フラグ
                    eventSchePlanDelEntity.setDelFlg(0);
                    //データベースにインポート
                    eventSchePlanDelDao.insert(eventSchePlanDelEntity);
                }
            }
            if (StringUtils.equals(refType, "0")) {
                List<EventScheduleEntity> checkList = eventScheduleService.list(
                        new QueryWrapper<EventScheduleEntity>().eq("event_id", eventId).eq("ref_id", eventScheduleEntity.getRefId()).eq("sgd_plan_date",
                                eventScheduleEntity.getSgdPlanDate()).lt("sgd_start_datime", sgdendDatime).gt(
                                "sgd_end_datime", sgdstartDatime).eq("del_flg", 0));
                for (EventScheduleEntity checkEntity : checkList) {
                    if (!checkEntity.getId().equals(scheduleId)) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
                    }
                }
                //イベント日程（詳細）へ登録
                //
                eventScheduleEntity.setPersonsLimt(personlimit);
                //日程開始日時
                eventScheduleEntity.setSgdStartDatime(startDateTime);
                //日程終了日時
                eventScheduleEntity.setSgdEndDatime(endDateTime);

                //更新日時
                eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                eventScheduleEntity.setUpdUsrId(userId);
                //データベースの更新
                eventScheduleService.update(eventScheduleEntity, new QueryWrapper<EventScheduleEntity>().eq("id", scheduleId).eq("del_flg", 0));

                //イベント日程（詳細）から論理削除する
                eventSchePlanDelService.remove(new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", scheduleId).eq("del_flg", 0));

                eventSchePlanDelEntity = new EventSchePlanDelEntity();
                //イベントID
                eventSchePlanDelEntity.setEventId(eventId);
                //イベント日程ID
                eventSchePlanDelEntity.setEventScheId(eventScheduleEntity.getId());
                //関連ID
                eventSchePlanDelEntity.setRefId(orgId);
                //関連タイプ
                eventSchePlanDelEntity.setRefTypeDiv(refType);
                //日程予定日
                eventSchePlanDelEntity.setSgdPlanDate(startTime);
                //日程開始日時
                eventSchePlanDelEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                //日程終了日時
                eventSchePlanDelEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                //定員
                eventSchePlanDelEntity.setPersonsLimt(personlimit);
                //予定済人数
                eventSchePlanDelEntity.setPlanedMember(0);
                //取消フラグ
                eventSchePlanDelEntity.setCancelFlg("0");
                //作成日時
                eventSchePlanDelEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                eventSchePlanDelEntity.setCretUsrId(userId);
                //更新日時
                eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                eventSchePlanDelEntity.setUpdUsrId(userId);
                //削除フラグ
                eventSchePlanDelEntity.setDelFlg(0);
                //データベースにインポート
                eventSchePlanDelDao.insert(eventSchePlanDelEntity);
            }

        }
        return R.ok().put("eventScheduleEntity", eventScheduleEntity).put("displayNm", displayNm);
    }

    /**
     * 一括新規
     *
     * @param weekdayList
     * @param SgdStartDatime
     * @param SgdEndDatime
     * @param eventId
     * @param personlimit
     * @param refType
     * @return
     */
    @RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
    public R saveGroup(@RequestParam("weekdayList")
                               List<String> weekdayList, String SgdStartDatime, String SgdEndDatime, Integer eventId, Integer personlimit, String refType, String mentorId, String username) {
        //画面．月曜日選択から画面．日曜日選択までのいずれかを選択すること。
        if (weekdayList.size() == 0) {
            //エラーメッセージ
            return R.error(MessageUtils.getMessage("MSGCOMN0050", "曜日"));
        }
        //前画面から引渡したイベントIDより、1コマ時間を取得し
        MstEventEntity mstEventEntity = mstEventService.getOne(new QueryWrapper<MstEventEntity>().eq("id", eventId).eq("del_flg", 0));
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザーID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        EventScheduleEntity eventScheduleEntity = null;
        EventSchePlanDelEntity eventSchePlanDelEntity = null;
        for (int i = 0; i < weekdayList.size(); i++) {
            //日程開始日時を取得する
            Date sgdstartDatime = DateUtils.parse(weekdayList.get(i) + " " + SgdStartDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            //日程終了日時を取得する
            Date sgdendDatime = DateUtils.parse(weekdayList.get(i) + " " + SgdEndDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            if (StringUtils.equals("1", refType)) {
                //ユーザー存在チェック
                F08006Dto mstUsrEntity = f08006Service.getuserEntity(orgId, mentorId);
                //取得できない場合
                if (mstUsrEntity == null) {
                    //エラーメッセージ
                    return R.error(MessageUtils.getMessage("MSGCOMN0009", "ユーザー名", "ユーザーマスタ"));
                }
                List<EventScheduleEntity> checkList = eventScheduleService.list(
                        new QueryWrapper<EventScheduleEntity>().eq("event_id", eventId).eq("ref_id", mstUsrEntity.getUsrId()).eq("sgd_plan_date",
                                DateUtils.parse(weekdayList.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)).lt(
                                "sgd_start_datime", sgdendDatime).gt("sgd_end_datime", sgdstartDatime).eq("del_flg", 0));
                if (checkList.size() > 0) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
                }
            } else {
                List<EventScheduleEntity> checkList = eventScheduleService.list(
                        new QueryWrapper<EventScheduleEntity>().eq("event_id", eventId).eq("ref_id", orgId).eq("sgd_plan_date",
                                DateUtils.parse(weekdayList.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)).lt(
                                "sgd_start_datime", sgdendDatime).gt("sgd_end_datime", sgdstartDatime).eq("del_flg", 0));
                if (checkList.size() > 0) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0097", "時間"));
                }
            }
        }
        //「一括登録」の場合
        for (int i = 0; i < weekdayList.size(); i++) {
            //日程開始日時を取得する
            Date checkStartDate = DateUtils.parse(weekdayList.get(i) + " " + SgdStartDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            Date nowDate = DateUtils.getSysDate();
            if (checkStartDate.compareTo(nowDate) > 0) {
                //日程開始日時を取得する
                Date sgdstartDatime = DateUtils.parse(weekdayList.get(i) + " " + SgdStartDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                //日程終了日時を取得する
                Date sgdendDatime = DateUtils.parse(weekdayList.get(i) + " " + SgdEndDatime, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                Calendar date = Calendar.getInstance();
                date.setTime(sgdendDatime);
                Long end = date.getTimeInMillis();
                date.setTime(sgdstartDatime);
                Long start = date.getTimeInMillis();
                //回数目を取得する
                Integer times = (new Double(Math.ceil((double)((end - start) / (1000 * 60)) / (double)mstEventEntity.getUnitTime()))).intValue();
                //セッションデータ．関連タイプが「1：メンター」時、
                if (StringUtils.equals("1", refType)) {
                    //ユーザー名未入力時、
                    if (StringUtils.equals("", username)) {
                        //エラーメッセージ
                        return R.error(MessageUtils.getMessage("MSGCOMD0001", "ユーザー名"));
                    }
                    //ユーザー存在チェック
                    F08006Dto mstUsrEntity = f08006Service.getuserEntity(orgId, mentorId);
                    //取得できない場合
                    if (mstUsrEntity == null) {
                        //エラーメッセージ
                        return R.error(MessageUtils.getMessage("MSGCOMN0009", "ユーザー名", "ユーザーマスタ"));
                    }
                    //イベント日程へ登録
                    eventScheduleEntity = new EventScheduleEntity();
                    //イベントID
                    eventScheduleEntity.setEventId(eventId);
                    //組織ID
                    eventScheduleEntity.setOrgId(orgId);
                    //関連ID
                    eventScheduleEntity.setRefId(mstUsrEntity.getUsrId());
                    //関連タイプ
                    eventScheduleEntity.setRefType(refType);
                    //日程予定日
                    eventScheduleEntity.setSgdPlanDate(DateUtils.parse(weekdayList.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //日程開始日時
                    eventScheduleEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                    //日程終了日時
                    eventScheduleEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                    //定員
                    eventScheduleEntity.setPersonsLimt(1);
                    //1コマ時間
                    eventScheduleEntity.setUnitTime(mstEventEntity.getUnitTime());
                    //作成日時
                    eventScheduleEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    eventScheduleEntity.setCretUsrId(userId);
                    //更新日時
                    eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    eventScheduleEntity.setUpdUsrId(userId);
                    //削除フラグ
                    eventScheduleEntity.setDelFlg(0);
                    //データベースにインポート
                    eventScheduleDao.insert(eventScheduleEntity);
                    //回数目循環挿入データ
                    for (int j = 0; j < times; j++) {
                        //イベント日程（詳細）へ登録
                        eventSchePlanDelEntity = new EventSchePlanDelEntity();
                        //イベントID
                        eventSchePlanDelEntity.setEventId(eventId);
                        //イベント日程ID
                        eventSchePlanDelEntity.setEventScheId(eventScheduleEntity.getId());
                        //関連ID
                        eventSchePlanDelEntity.setRefId(mstUsrEntity.getUsrId());
                        //関連タイプ
                        eventSchePlanDelEntity.setRefTypeDiv(refType);
                        //日程予定日
                        eventSchePlanDelEntity.setSgdPlanDate(DateUtils.parse(weekdayList.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                        //日程開始日時
                        eventSchePlanDelEntity.setSgdStartDatime(DateUtils.toTimestamp(DateUtils.addMinutes(sgdstartDatime, mstEventEntity.getUnitTime() * j)));
                        if (j == times - 1) {
                            //日程終了日時
                            eventSchePlanDelEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                        } else {
                            //日程終了日時
                            eventSchePlanDelEntity.setSgdEndDatime(
                                    DateUtils.toTimestamp(DateUtils.addMinutes(sgdstartDatime, mstEventEntity.getUnitTime() * (j + 1))));
                        }
                        //定員
                        eventSchePlanDelEntity.setPersonsLimt(1);
                        //予定済人数
                        eventSchePlanDelEntity.setPlanedMember(0);
                        //取消フラグ
                        eventSchePlanDelEntity.setCancelFlg("0");
                        //作成日時
                        eventSchePlanDelEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //作成ユーザＩＤ
                        eventSchePlanDelEntity.setCretUsrId(userId);
                        //更新日時
                        eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        eventSchePlanDelEntity.setUpdUsrId(userId);
                        //削除フラグ
                        eventSchePlanDelEntity.setDelFlg(0);
                        //データベースにインポート
                        eventSchePlanDelDao.insert(eventSchePlanDelEntity);
                    }
                } else if (StringUtils.equals("0", refType)) { //セッションデータ．関連タイプが「0：教室」時、
                    //定員未入力時
                    if (personlimit == null) {
                        //エラーメッセージ
                        return R.error(MessageUtils.getMessage("MSGCOMD0001", "定員"));
                    }
                    //イベント日程へ登録
                    eventScheduleEntity = new EventScheduleEntity();
                    //イベントID
                    eventScheduleEntity.setEventId(eventId);
                    //組織ID
                    eventScheduleEntity.setOrgId(orgId);
                    //関連ID
                    eventScheduleEntity.setRefId(orgId);
                    //関連タイプ
                    eventScheduleEntity.setRefType(refType);
                    //日程予定日
                    eventScheduleEntity.setSgdPlanDate(DateUtils.parse(weekdayList.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //日程開始日時
                    eventScheduleEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                    //日程終了日時
                    eventScheduleEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                    //定員
                    eventScheduleEntity.setPersonsLimt(personlimit);
                    //1コマ時間
                    eventScheduleEntity.setUnitTime(mstEventEntity.getUnitTime());
                    //作成日時
                    eventScheduleEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    eventScheduleEntity.setCretUsrId(userId);
                    //更新日時
                    eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    eventScheduleEntity.setUpdUsrId(userId);
                    //削除フラグ
                    eventScheduleEntity.setDelFlg(0);
                    //データベースにインポート
                    eventScheduleDao.insert(eventScheduleEntity);

                    //イベント日程（詳細）へ登録
                    eventSchePlanDelEntity = new EventSchePlanDelEntity();
                    //イベントID
                    eventSchePlanDelEntity.setEventId(eventId);
                    //イベント日程ID
                    eventSchePlanDelEntity.setEventScheId(eventScheduleEntity.getId());
                    //関連ID
                    eventSchePlanDelEntity.setRefId(orgId);
                    //関連タイプ
                    eventSchePlanDelEntity.setRefTypeDiv(refType);
                    //日程予定日
                    eventSchePlanDelEntity.setSgdPlanDate(DateUtils.parse(weekdayList.get(i), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    //日程開始日時
                    eventSchePlanDelEntity.setSgdStartDatime(DateUtils.toTimestamp(sgdstartDatime));
                    //日程終了日時
                    eventSchePlanDelEntity.setSgdEndDatime(DateUtils.toTimestamp(sgdendDatime));
                    //定員
                    eventSchePlanDelEntity.setPersonsLimt(personlimit);
                    //予定済人数
                    eventSchePlanDelEntity.setPlanedMember(0);
                    //取消フラグ
                    eventSchePlanDelEntity.setCancelFlg("0");
                    //作成日時
                    eventSchePlanDelEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    eventSchePlanDelEntity.setCretUsrId(userId);
                    //更新日時
                    eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    eventSchePlanDelEntity.setUpdUsrId(userId);
                    //削除フラグ
                    eventSchePlanDelEntity.setDelFlg(0);
                    //データベースにインポート
                    eventSchePlanDelDao.insert(eventSchePlanDelEntity);
                }
            }
        }
        return R.ok().put("eventScheduleEntity", eventScheduleEntity).put("displayNm", ShiroUtils.getUserEntity().getOrgNm());
    }

    /**
     * 候補者リストを取得
     *
     * @param mentorname
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public R submit(String mentorname) {
        //ユーザー名未入力時
        if (mentorname.equals("")) {
            return R.ok();
        }
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザー検索の場合、
        List<F08006Dto> mstMentorEntityList = f08006Service.getMentorList(mentorname, orgId);
        return R.ok().put("mstMentorEntityList", mstMentorEntityList);

    }

    /**
     * 削除
     *
     * @param scheduleId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R getSchedule(Integer scheduleId) {
        //ユーザーID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //保護者申し込み済みで削除できないチェック
        EventSchePlanDelEntity eventSchePlanDelEntity = eventSchePlanDelService.getOne(
                new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", scheduleId).gt("planed_member", 0).eq("del_flg", 0));
        //取得できた場合
        if (eventSchePlanDelEntity != null) {
            //エラーメッセージ
            return R.error(MessageUtils.getMessage("MSGCOMN0111", "保護者"));
        }
        //下記条件で、イベント日程から論理削除する。
        EventScheduleEntity eventScheduleEntity = eventScheduleService.getOne(new QueryWrapper<EventScheduleEntity>().eq("id", scheduleId).eq("del_flg", 0));
        //イベント日程．削除フラグ
        eventScheduleEntity.setDelFlg(1);
        //イベント日程．更新日時
        eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //イベント日程．更新ユーザＩＤ
        eventScheduleEntity.setUpdUsrId(userId);
        //データベースの更新
        eventScheduleService.update(eventScheduleEntity, new QueryWrapper<EventScheduleEntity>().eq("id", scheduleId).eq("del_flg", 0));
        //下記条件で、イベント日程（詳細）から論理削除する。
        List<EventSchePlanDelEntity> eventSchePlanDel = eventSchePlanDelService.list(
                new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", scheduleId).eq("del_flg", 0));
        for (int i = 0; i < eventSchePlanDel.size(); i++) {
            //イベント日程(詳細)．削除フラグ
            eventSchePlanDel.get(i).setDelFlg(1);
            //イベント日程(詳細)．更新日時
            eventSchePlanDel.get(i).setUpdDatime(DateUtils.getSysTimestamp());
            //イベント日程(詳細)．更新ユーザＩＤ
            eventSchePlanDel.get(i).setUpdUsrId(userId);
            //データベースの更新
            eventSchePlanDelService.updateBatchById(eventSchePlanDel);
        }

        return R.ok().put("eventScheduleEntity", eventScheduleEntity);
    }
}
