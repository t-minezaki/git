package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.MstAskTalkEventService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuEventApplyStsService;
import jp.learningpark.modules.common.service.TalkRecordDService;
import jp.learningpark.modules.common.service.TalkRecordHService;
import jp.learningpark.modules.manager.dto.F08015Dto;
import jp.learningpark.modules.manager.service.F08015Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F08015_配信設定/状況確認の代理登録画面(POP) Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/19: yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/F08015")
@RestController
public class F08015Controller {
    /**
     * F08015Service
     */
    @Autowired
    F08015Service f08015Service;
    /**
     * MstEventService
     */
    @Autowired
    MstEventService mstEventService;
    /**
     * EventSchePlanDelService
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;
    /**
     * GuardEventApplyStsService
     */
    @Autowired
    GuardEventApplyStsService guardEventApplyStsService;

    /**
     * stuEventApplyStsService
     */
    @Autowired
    StuEventApplyStsService stuEventApplyStsService;

    /**
     * talkRecordDService
     */
    @Autowired
    TalkRecordDService talkRecordDService;

    /**
     * talkRecordHService
     */
    @Autowired
    TalkRecordHService talkRecordHService;
    /**
     * mstStuService
     */
    @Autowired
    MstStuService mstStuService;

    /**
     * mstAskTalkEventService
     */
    @Autowired
    MstAskTalkEventService mstAskTalkEventService;

    /**
     * 初期表示
     *
     * @param pageDiv   引渡データ．画面区分
     * @param clickDate 引渡データ．クリック日時の日付
     * @param clickTime 引渡データ．クリック日時の時間
     * @param geasId    引渡データ．保護者イベント申込ID
     * @return
     */
    //    F08015Dto f08015Dto = new F08015Dto();
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String pageDiv, Integer id, String clickDate, String clickTime, Integer geasId, String stuName, String eventTitle, String mentorName, Boolean userFlag) {
        R info = new R();
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // イベント検索
        List<F08015Dto> eventList = f08015Service.getEventList(orgId, eventTitle);
        //先生検索
        List<F08015Dto> mentorList = f08015Service.getMentorList(mentorName, orgId);
        info.put("eventList", eventList).put("mentorList", mentorList);
        //画面区分が「1：新規」の場合
        if (StringUtils.equals(pageDiv, "1")) {
            R.ok();
        }
        //画面区分が「2：コマ非表示」の場合、
        else if (StringUtils.equals(pageDiv, "2")) {
            //イベント日程（明細）IDを取得
            F08015Dto f08015Dto = f08015Service.getEventSchedulePlanDel(id);
            //取得できない場合
            if (f08015Dto == null) {
                //画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "日程詳細"));
            }
            //生徒検索
            List<F08015Dto> stuList = f08015Service.getStuList(orgId, f08015Dto.getEventId(), userFlag);
            //イベント．イベントタイトル
            MstEventEntity mstEventEntity = mstEventService.getOne(new QueryWrapper<MstEventEntity>().eq("id", f08015Dto.getEventId()).eq("del_flg", 0));
            info.put("eventTitle", mstEventEntity.getEventTitle()).put("f08015Dto", f08015Dto).put("stuList",stuList);
        }
        //画面区分が「3:先生変更」の場合
        else if (StringUtils.equals(pageDiv, "3")) {
            //保護者イベント申込状況を取得
            F08015Dto f08015Dto = f08015Service.getDeliver(geasId, userFlag);
            //取得できない場合
            if (f08015Dto == null) {
                //画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "保護者イベント申込状況"));
            }
            info.put("f08015Dto", f08015Dto);
        }
        return info;
    }

    @RequestMapping(value = "/getStuList", method = RequestMethod.GET)
    public R getStuList(Integer eventId, Boolean userFlag) {
        R info = new R();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        if (userFlag == null) {
            int count = guardEventApplyStsService.count(new QueryWrapper<GuardEventApplyStsEntity>().eq("del_flg", 0).eq("event_id", eventId));
            userFlag = count > 0;
        }
        //生徒検索
        List<F08015Dto> stuList = f08015Service.getStuList(orgId, eventId, userFlag);
        info.put("stuList", stuList);
        return info;
    }

    /**
     * 代理登録ボダン押下
     * @param pageDiv 画面区分
     * @param eventTitle イベント名
     * @param stuName 生徒名
     * @param displayName 先生名
     * @param planDate 画面．予約日
     * @param startTime 画面．開始時間
     * @param endTime 画面．終了時間
     * @return
     */

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public R log(String pageDiv, String eventTitle, String stuName, String displayName, String planDate, String startTime, String endTime, Integer eventId, String stuId, Integer geasId, Integer eventScheId, String refId, Boolean userFlag) {
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        if (StringUtils.equals(pageDiv, "1")) {
            //イベント存在チェック
            List<F08015Dto> eventEntityList = f08015Service.getEventEntitylist(orgId, eventTitle);
            if (eventEntityList.isEmpty()) {
                return R.error(MessageUtils.getMessage("MSGCOMN0009", "イベントタイトル", "イベントマスタ"));
            }
        }
        if (StringUtils.equals(pageDiv, "1") || StringUtils.equals(pageDiv,"2")){
            //生徒存在チェック
            List<F08015Dto> stuEntity = f08015Service.getStuEntityList(orgId, stuName);
            if (stuEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0009", "生徒名", "生徒基本マスタ"));
            }
            /* 2021/1/13 UT-120 cuikailin add start */
            TalkRecordHEntity old = talkRecordHService.getOne(new QueryWrapper<TalkRecordHEntity>().eq("stu_id", stuId).eq("event_id", eventId).eq("del_flg", 0).last("limit 1"));
            if (old != null) {
                if (StringUtils.equals("0", old.getTalkApplyStsDiv()) || StringUtils.equals("1", old.getTalkApplyStsDiv())) {
                    talkRecordDService.remove(new QueryWrapper<TalkRecordDEntity>().eq("talk_id", old.getId()).eq("item_type_div", "0").eq("del_flg", 0));
                    talkRecordHService.removeById(old.getId());
                } else if (StringUtils.equals("2", old.getTalkApplyStsDiv()) || StringUtils.equals("3", old.getTalkApplyStsDiv())) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0159"));
                }
            }
            /* 2021/1/13 UT-120 cuikailin add end */
        }
        //イベント日程（明細）IDを取得
        //先生・教室のイベント日程存在チェック
        List<F08015Dto> mentorList = f08015Service.getMentor(displayName, eventId);
        if (mentorList.isEmpty()) {
            return R.error(MessageUtils.getMessage("MSGCOMN0009", "先生名、また教室", "イベント日程"));
        }
        //イベント日程(詳細)の予約可否チェック
        EventSchePlanDelEntity eventSchePlanDelEntity = null;
        if (StringUtils.equals(pageDiv, "1")){
            eventSchePlanDelEntity = eventSchePlanDelService.getOne(new QueryWrapper<EventSchePlanDelEntity>().
                    select("id", "persons_limt", "planed_member", "sgd_start_datime").eq("event_id", eventId).eq("ref_id", refId)
                    .eq("sgd_plan_date", DateUtils.parse(planDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS))
                    .eq("sgd_start_datime", DateUtils.toTimestamp(DateUtils.parse(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO)))
                    .eq("sgd_end_datime", DateUtils.toTimestamp(DateUtils.parse(endTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))).eq("del_flg", 0));
            //取得できない場合
            if (eventSchePlanDelEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0059", "登録時間とイベント日程（明細）の設定時間"));
            }
            //取得結果のイベント日程(詳細)．定員　＝　イベント日程(詳細)．予定済人数の場合
            if (eventSchePlanDelEntity.getPersonsLimt().equals(eventSchePlanDelEntity.getPlanedMember())) {
                return R.error(MessageUtils.getMessage("MSGCOMN0115", "イベント日程"));
            }
            eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() + 1);
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("id", eventSchePlanDelEntity.getId()).eq("del_flg", 0));
        } else if (StringUtils.equals(pageDiv, "2")) {
            eventSchePlanDelEntity = eventSchePlanDelService.getOne(new QueryWrapper<EventSchePlanDelEntity>().
                    select("id", "persons_limt", "planed_member", "sgd_start_datime").eq("event_id", eventId).eq("ref_id", refId)
                    .eq("sgd_plan_date", DateUtils.parse(planDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS))
                    .eq("sgd_start_datime", DateUtils.toTimestamp(DateUtils.parse(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO)))
                    .eq("sgd_end_datime", DateUtils.toTimestamp(DateUtils.parse(endTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))).eq("del_flg", 0));
            if (eventSchePlanDelEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0059", "登録時間とイベント日程（明細）の設定時間"));
            }
            if (eventSchePlanDelEntity.getPlanedMember() >= eventSchePlanDelEntity.getPersonsLimt()) {
                return R.error(MessageUtils.getMessage("MSGCOMN0115", "イベント日程"));
            }
            //取得結果のイベント日程(詳細)．定員　＝　イベント日程(詳細)．予定済人数の場合
            if (eventSchePlanDelEntity.getPersonsLimt() == eventSchePlanDelEntity.getPlanedMember()) {
                return R.error(MessageUtils.getMessage("MSGCOMN0115", "イベント日程"));
            }
            eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() + 1);
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("id", eventSchePlanDelEntity.getId()).eq("del_flg", 0));
        }
        if (userFlag == null) {
            int count = guardEventApplyStsService.count(new QueryWrapper<GuardEventApplyStsEntity>().eq("del_flg", 0).eq("event_id", eventId));
            userFlag = count > 0;
        }
        //引渡データ．画面区分が「１：新規」「2：コマ非表示」の場合、
        if (StringUtils.equals(pageDiv, "1") || StringUtils.equals(pageDiv, "2")) {
            if (userFlag) {
                GuardEventApplyStsEntity guardEventApplyStsEntity = guardEventApplyStsService.getOne(new QueryWrapper<GuardEventApplyStsEntity>().eq("stu_id",
                        stuId).eq("event_id", eventId).eq("del_flg", 0));
                if (guardEventApplyStsEntity.getEventScheDelId() != null && StringUtils.equals(pageDiv, "1") && !StringUtils.equals(guardEventApplyStsEntity.getReplyStsDiv(), "2")) {
                    EventSchePlanDelEntity delEntity = eventSchePlanDelService.getById(guardEventApplyStsEntity.getEventScheDelId());
                    //予定済人数
                    delEntity.setPlanedMember(delEntity.getPlanedMember() - 1);
                    //更新日時
                    delEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //更新ユーザＩＤ
                    delEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    eventSchePlanDelService.updateById(delEntity);
                }
                if (guardEventApplyStsEntity.getEventScheDelId() != null && StringUtils.equals(pageDiv, "2") && !StringUtils.equals(guardEventApplyStsEntity.getReplyStsDiv(), "2")) {
                    EventSchePlanDelEntity delEntity = eventSchePlanDelService.getById(guardEventApplyStsEntity.getEventScheDelId());
                    //予定済人数
                    delEntity.setPlanedMember(delEntity.getPlanedMember() - 1);
                    //更新日時
                    delEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //更新ユーザＩＤ
                    delEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    eventSchePlanDelService.updateById(delEntity);
                }
                //イベント日程(明細)ID
                guardEventApplyStsEntity.setEventScheDelId(eventSchePlanDelEntity.getId());
                guardEventApplyStsEntity.setReadingStsDiv("1");
                guardEventApplyStsEntity.setReplyStsDiv("1");
                guardEventApplyStsEntity.setReadTime(DateUtils.getSysTimestamp());
                guardEventApplyStsEntity.setReplyTime(DateUtils.getSysTimestamp());
                //代理登録フラグ
                guardEventApplyStsEntity.setProxyFlg("1");
                //更新日時
                guardEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                guardEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
                guardEventApplyStsService.update(guardEventApplyStsEntity, new QueryWrapper<GuardEventApplyStsEntity>().eq("id", guardEventApplyStsEntity.getId()));
            } else {
                StuEventApplyStsEntity stuEventApplyStsEntity = stuEventApplyStsService.getOne(new QueryWrapper<StuEventApplyStsEntity>().eq("stu_id",
                        stuId).eq("event_id", eventId).eq("del_flg", 0));
                if (stuEventApplyStsEntity.getEventScheDelId() != null && StringUtils.equals(pageDiv, "1") && !StringUtils.equals(stuEventApplyStsEntity.getReplyStsDiv(), "2")) {
                    EventSchePlanDelEntity delEntity = eventSchePlanDelService.getById(stuEventApplyStsEntity.getEventScheDelId());
                    //予定済人数
                    delEntity.setPlanedMember(delEntity.getPlanedMember() - 1);
                    //更新日時
                    delEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //更新ユーザＩＤ
                    delEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    eventSchePlanDelService.updateById(delEntity);
                }
                if (stuEventApplyStsEntity.getEventScheDelId() != null && StringUtils.equals(pageDiv, "2") && !StringUtils.equals(stuEventApplyStsEntity.getReplyStsDiv(), "2")) {
                    EventSchePlanDelEntity delEntity = eventSchePlanDelService.getById(stuEventApplyStsEntity.getEventScheDelId());
                    //予定済人数
                    delEntity.setPlanedMember(delEntity.getPlanedMember() - 1);
                    //更新日時
                    delEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //更新ユーザＩＤ
                    delEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    eventSchePlanDelService.updateById(delEntity);
                }
                //イベント日程(明細)ID
                stuEventApplyStsEntity.setEventScheDelId(eventSchePlanDelEntity.getId());
                stuEventApplyStsEntity.setReadingStsDiv("1");
                stuEventApplyStsEntity.setReplyStsDiv("1");
                stuEventApplyStsEntity.setReadTime(DateUtils.getSysTimestamp());
                stuEventApplyStsEntity.setReplyTime(DateUtils.getSysTimestamp());
                //代理登録フラグ
                stuEventApplyStsEntity.setProxyFlg("1");
                //更新日時
                stuEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                stuEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
                stuEventApplyStsService.update(stuEventApplyStsEntity, new QueryWrapper<StuEventApplyStsEntity>().eq("id", stuEventApplyStsEntity.getId()));
            }
        }
        //引渡データ．画面区分が「3:先生変更」
        if (StringUtils.equals(pageDiv, "3")){
            EventSchePlanDelEntity eventSchePlanDelEntityAfter = eventSchePlanDelService.getOne(new QueryWrapper<EventSchePlanDelEntity>().
                    select("id", "persons_limt", "planed_member").eq("event_id", eventId).eq("ref_id",refId)
                    .eq("sgd_plan_date", DateUtils.parse(planDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS))
                    .eq("sgd_start_datime", DateUtils.toTimestamp(DateUtils.parse(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO)))
                    .eq("sgd_end_datime", DateUtils.toTimestamp(DateUtils.parse(endTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))).eq("del_flg", 0));
            if (eventSchePlanDelEntityAfter == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0059", "登録時間とイベント日程（明細）の設定時間"));
            }
            if (eventSchePlanDelEntityAfter.getPlanedMember() >= eventSchePlanDelEntityAfter.getPersonsLimt()) {
                return R.error(MessageUtils.getMessage("MSGCOMN0115", "イベント日程"));
            }
            eventSchePlanDelEntityAfter.setPlanedMember(eventSchePlanDelEntityAfter.getPlanedMember() + 1);
            eventSchePlanDelEntityAfter.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelEntityAfter.setUpdDatime(DateUtils.getSysTimestamp());
            eventSchePlanDelService.update(eventSchePlanDelEntityAfter, new QueryWrapper<EventSchePlanDelEntity>().eq("id", eventSchePlanDelEntityAfter.getId()));

            Integer espdId;
            if (userFlag) {
                GuardEventApplyStsEntity guardEventApplyStsEntity = guardEventApplyStsService.getById(geasId);
                espdId = guardEventApplyStsEntity.getEventScheDelId();
                //イベント日程(明細)ID
                guardEventApplyStsEntity.setEventScheDelId(eventSchePlanDelEntityAfter.getId());
                guardEventApplyStsEntity.setReadingStsDiv("1");
                guardEventApplyStsEntity.setReplyStsDiv("1");
                //代理登録フラグ
                guardEventApplyStsEntity.setProxyFlg("1");
                //更新日時
                guardEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                guardEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
                guardEventApplyStsService.update(guardEventApplyStsEntity, new QueryWrapper<GuardEventApplyStsEntity>().eq("id", guardEventApplyStsEntity.getId()));
            } else {
                StuEventApplyStsEntity stuEventApplyStsEntity = stuEventApplyStsService.getById(geasId);
                espdId = stuEventApplyStsEntity.getEventScheDelId();
                //イベント日程(明細)ID
                stuEventApplyStsEntity.setEventScheDelId(eventSchePlanDelEntityAfter.getId());
                stuEventApplyStsEntity.setReadingStsDiv("1");
                stuEventApplyStsEntity.setReplyStsDiv("1");
                //代理登録フラグ
                stuEventApplyStsEntity.setProxyFlg("1");
                //更新日時
                stuEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                stuEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
                stuEventApplyStsService.update(stuEventApplyStsEntity, new QueryWrapper<StuEventApplyStsEntity>().eq("id", stuEventApplyStsEntity.getId()));
            }
            eventSchePlanDelEntity = eventSchePlanDelService.getById(espdId);
            //取得できない場合
            if (eventSchePlanDelEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0059", "登録時間とイベント日程（明細）の設定時間"));
            }
            eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() - 1);
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("id", eventSchePlanDelEntity.getId()));
        }

        if (StringUtils.equals(pageDiv, "1") || StringUtils.equals(pageDiv, "2")) {
            MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).last("limit 1"));
            /* 2021/1/13 UT-120 cuikailin modify start */
//            TalkRecordHEntity old = talkRecordHService.getOne(new QueryWrapper<TalkRecordHEntity>().eq("stu_id", stuId).eq("event_id", eventId).eq("del_flg", 0).last("limit 1"));
//            if (old != null) {
//                if (StringUtils.equals("0", old.getTalkApplyStsDiv()) || StringUtils.equals("1", old.getTalkApplyStsDiv())) {
//                    talkRecordDService.remove(new QueryWrapper<TalkRecordDEntity>().eq("talk_id", old.getId()).eq("item_type_div", "0").eq("del_flg", 0));
//                    talkRecordHService.removeById(old.getId());
//                } else if (StringUtils.equals("2", old.getTalkApplyStsDiv()) || StringUtils.equals("3", old.getTalkApplyStsDiv())) {
//                    return R.error(MessageUtils.getMessage("MSGCOMN0159"));
//                }
//            }
            /* 2021/1/13 UT-120 cuikailin modify end */
            //0629追加面谈データ作成
            TalkRecordHEntity talkRecordHEntity = new TalkRecordHEntity();
            //組織ID
            talkRecordHEntity.setOrgId(orgId);
            //イベントID
            talkRecordHEntity.setEventId(eventId);
            //面談日時
            if (StringUtils.equals(pageDiv, "1")) {
                talkRecordHEntity.setTalkDatime(DateUtils.toTimestamp(DateUtils.parse(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO)));
            } else if (StringUtils.equals(pageDiv, "2")) {
                talkRecordHEntity.setTalkDatime(eventSchePlanDelEntity.getSgdStartDatime());
            }
            //先生ID
            talkRecordHEntity.setMentorId(null);
            //生徒ID
            talkRecordHEntity.setStuId(stuId);
            //保護者ID
            talkRecordHEntity.setGuardId(mstStuEntity.getGuardId());
            //面談申込状態区分
            talkRecordHEntity.setTalkApplyStsDiv("0");
            //作成日時
            talkRecordHEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            talkRecordHEntity.setCretUsrId(ShiroUtils.getUserId());
            //更新日時
            talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
            //削除フラグ
            talkRecordHEntity.setDelFlg(0);
            talkRecordHService.save(talkRecordHEntity);

            //面談記録へ登録されたID
            Integer talkRecordHEntityId = talkRecordHEntity.getId();
            //面談記録詳細登録を行う
            List<MstAskTalkEventEntity> mstAskTalkEventEntityList = mstAskTalkEventService.list(new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("item_type_div", "0").eq("use_div", "1").eq("del_flg", 0));
            if (mstAskTalkEventEntityList.size() != 0) {
                for (MstAskTalkEventEntity mstAskTalkEventEntity : mstAskTalkEventEntityList) {
                    if (!StringUtils.isEmpty(mstAskTalkEventEntity.getQuestionName())) {
                        TalkRecordDEntity talkRecordDEntity = new TalkRecordDEntity();
                        //面談記録ID
                        talkRecordDEntity.setTalkId(talkRecordHEntityId);
                        //事項類型区分
                        talkRecordDEntity.setItemTypeDiv("0");
                        //设问名
                        talkRecordDEntity.setQuestionName(mstAskTalkEventEntity.getQuestionName());
                        //質問番号
                        talkRecordDEntity.setAskNum(mstAskTalkEventEntity.getAskNum());
                        //回答形式区分
                        talkRecordDEntity.setAnswerTypeDiv(mstAskTalkEventEntity.getAnswerTypeDiv());
                        //回答結果内容
                        talkRecordDEntity.setAnswerReltCont(null);
                        //作成日時
                        talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //作成ユーザＩＤ
                        talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
                        //更新日時
                        talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
                        //削除フラグ
                        talkRecordDEntity.setDelFlg(0);
                        talkRecordDService.save(talkRecordDEntity);
                    }
                }
            }
        }
        return R.ok();
    }

    /**
     *
     * @param id
     *  @param flg コマ非表示ボダン押下 または コマ表示ボダン押下
     * @return
     */
    @RequestMapping(value = "/unit",method = RequestMethod.GET)
    public R unit(Integer id,Integer flg){
        EventSchePlanDelEntity eventSchePlanDelEntity = eventSchePlanDelService.getById(id);
        //コマ非表示ボダン押下の場合、
        if (flg == 1) {
            //取消フラグ 「1:取消」
            eventSchePlanDelEntity.setCancelFlg("1");
            //更新日時
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelService.update(eventSchePlanDelEntity,new QueryWrapper<EventSchePlanDelEntity>().eq("id",id));
        }
        //コマ表示ボダン押下の場合
        else if (flg == 0) {
            //取消フラグ 「0:未取消」
            eventSchePlanDelEntity.setCancelFlg("0");
            //更新日時
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            eventSchePlanDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelService.update(eventSchePlanDelEntity,new QueryWrapper<EventSchePlanDelEntity>().eq("id",id));
        }
        return R.ok();
    }

}
