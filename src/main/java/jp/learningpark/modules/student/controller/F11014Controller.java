/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;/**
 * GakkenAppApplication
 */

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.student.dto.F11014Dto;
import jp.learningpark.modules.student.service.F11014Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>生徒面談の日程設定画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/05/12 : lyh: 新規<br />
 * @version 7.0
 */
@RequestMapping("/student/F11014")
@RestController
public class F11014Controller {

    /**
     * F11014 Service
     */
    @Autowired
    F11014Service f11014Service;

    /**
     * イベント申込状況 Service
     */
    @Autowired
    GuardEventApplyStsService guardEventApplyStsService;

    /**
     * イベント日程(詳細) Service
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;

    /**
     * 生徒基本マスタ Service
     */
    @Autowired
    MstStuService mstStuService;

    /**
     * 面談記録明細 Service
     */
    @Autowired
    TalkRecordDService talkRecordDService;

    /**
     * 面談記録 Service
     */
    @Autowired
    TalkRecordHService talkRecordHService;

    /**
     * 質問面談イベント Service
     */
    @Autowired
    MstAskTalkEventService mstAskTalkEventService;

    /**
     * 生徒イベント申込状況 Service
     */
    @Autowired
    StuEventApplyStsService stuEventApplyStsService;

    /**
     * mstEventService
     */
    @Autowired
    MstEventService mstEventService;

    /**
     * @param eventId イベントID
     * @param tgtYmd 画面選択時間
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f11014Init(Integer eventId, String tgtYmd) {

        Date monthStartDay;
        Date monthEndDay;
        String stuId = ShiroUtils.getUserId();
        String stuName = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0)).getFlnmNm() + ' ' + mstStuService.getOne(
                new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0)).getFlnmLnm();
        // 画面選択時間が空の場合
        if (StringUtils.equals("", tgtYmd)) {
            monthStartDay = DateUtils.getMonthFirstDay(DateUtils.getSysDate());
            monthEndDay = DateUtils.getMonthEndDay(DateUtils.getSysDate());
        } else {
            // データが空でない場合
            monthStartDay = DateUtils.getMonthFirstDay(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
            monthEndDay = DateUtils.getMonthEndDay(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
        }
        DateUtils.getMonthEndDay(DateUtils.getSysDate());
        //日程予約状況を取得
        List<F11014Dto> applylist = f11014Service.getScheSts(monthStartDay, monthEndDay, eventId);
        for (F11014Dto f11014Dto : applylist) {
            f11014Dto.setTgtDay(DateUtils.format(f11014Dto.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).split("-")[2]);
        }
        if (applylist.size() != 0) {
            List<F11014Dto> applyListCopy = new ArrayList<>();
            for (int i = 0; i < applylist.size(); i++) {
                for (int j = 0; j < applylist.size(); j++) {
                    if (StringUtils.equals(applylist.get(i).getTgtDay(), applylist.get(j).getTgtDay()) && i != j) {
                        applyListCopy.add(applylist.get(i));
                        applyListCopy.add(applylist.get(j));
                        applylist.removeAll(applyListCopy);
                    }
                }
            }
            //重複しないデータ
            for (F11014Dto f11014Dto : applylist) {
                //定員＞予定済人数
                if (f11014Dto.getPersonsLimt() > f11014Dto.getPlanedMember()) {
                    f11014Dto.setFlag("0");
                } else {
                    f11014Dto.setFlag("1");
                }
            }
            //重複しデータ
            for (F11014Dto f11014Dto : applyListCopy) {
                f11014Dto.setFlag("1");
            }
            for (int i = 0; i < applyListCopy.size(); i++) {
                if (applyListCopy.get(i).getPersonsLimt() > applyListCopy.get(i).getPlanedMember()) {
                    for (int j = 0; j < applyListCopy.size(); j++) {
                        applyListCopy.get(j).setFlag("0");
                    }
                    break;
                }
            }
            applylist.addAll(applyListCopy);
            for (F11014Dto notApply : applylist) {
                //システム日時　＞＝　イベント日程(詳細)．日程予定日
                if (DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD).compareTo(DateUtils.format(notApply.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD)) > 0) {
                    notApply.setFlag("2");
                }
            }
        }
        //予約可能時間帯
        List<F11014Dto> timeList = f11014Service.getTimeLine(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS), eventId);
        List<EventSchePlanDelEntity> delEntities = eventSchePlanDelService.list(
                new QueryWrapper<EventSchePlanDelEntity>().eq("event_id", eventId).eq("sgd_plan_date",
                        DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS)).eq("del_flg", 0).orderByAsc("sgd_start_datime"));
        timeList = new ArrayList<>();
        F11014Dto proxyDto = null;
        for (EventSchePlanDelEntity eventDelEntity : delEntities) {
            proxyDto = new F11014Dto();
            proxyDto.setTimeLine(DateUtils.format(eventDelEntity.getSgdStartDatime(), GakkenConstant.DATE_FORMAT_HH_MM) + "～" + DateUtils.format(
                    eventDelEntity.getSgdEndDatime(), GakkenConstant.DATE_FORMAT_HH_MM));
            if (DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD).compareTo(DateUtils.format(eventDelEntity.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD)) <= 0) {
                timeList.add(proxyDto);
            }
        }

        // 2020/12/1 huangxinliang modify start
        MstEventEntity mstEventEntity = mstEventService.getById(eventId);
        return R.ok().put("applylist", applylist).put("timeList", timeList).put("stuName", stuName).put("refType", mstEventEntity.getRefType()).put(
                "eventTitle", mstEventEntity.getEventTitle());
        // 2020/12/1 huangxinliang modify end
    }

    /**
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/getMin", method = RequestMethod.GET)
    public R getMinDate(Integer eventId) {
        Date minDate = null;
        List<EventSchePlanDelEntity> delEntities = eventSchePlanDelService.list(
                new QueryWrapper<EventSchePlanDelEntity>().eq("event_id", eventId).eq("del_flg", 0).orderByAsc("sgd_plan_date"));
        if (delEntities.size() != 0) {
            minDate = delEntities.get(0).getSgdPlanDate();
        }

        return R.ok().put("minDate", minDate);
    }

    /**
     * @param eventId イベントID
     * @param tgtYmd 画面選択時間
     * @return
     */
    @RequestMapping(value = "/timeselect", method = RequestMethod.GET)
    public R timeSelect(Integer eventId, String tgtYmd, String timeStr) {

        String startTime = timeStr.split("～")[0];
        String endTime = timeStr.split("～")[1];
        tgtYmd = tgtYmd.split("\\(")[0];
        String selectDate = DateUtils.format(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Timestamp sgdStartTime = new Timestamp(DateUtils.parse(selectDate + " " + startTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        Timestamp sgdEndTime = new Timestamp(DateUtils.parse(selectDate + " " + endTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        //        EventSchePlanDelEntity delEntity = eventSchePlanDelService.getById(eventId);
        List<EventSchePlanDelEntity> selectTimeList = null;
        List<EventSchePlanDelEntity> copy = new ArrayList<>();

        selectTimeList = eventSchePlanDelService.list(
                new QueryWrapper<EventSchePlanDelEntity>().select("id", "sgd_plan_date", "sgd_start_datime", "persons_limt", "planed_member").eq(
                        "sgd_plan_date", DateUtils.parse(selectDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS)).eq("event_id", eventId).eq(
                        "sgd_start_datime", sgdStartTime).eq("sgd_end_datime", sgdEndTime).eq("del_flg", 0).orderByDesc("planed_member", "ref_id"));
        for (EventSchePlanDelEntity comp : selectTimeList) {
            if (comp.getPlanedMember() < comp.getPersonsLimt()) {
                copy.add(comp);
            }
        }
        selectTimeList = copy;
        EventSchePlanDelEntity selectTime = null;
        //取得できない場合
        if (selectTimeList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "イベント日程（詳細）"));
        } else {
            selectTime = selectTimeList.get(0);
        }
        return R.ok().put("selectTime", selectTime);
    }

    /**
     * @param eventId
     * @param scheDelId
     * @param startTime
     * @param checkValue
     * @param selectValue
     * @param inputArea
     * @param replyCnt
     * @param imgArea
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(Integer eventId, Integer scheDelId, String startTime,
                    String checkValue, String selectValue, String inputArea,
                    String replyCnt, String imgArea) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String stuId = ShiroUtils.getUserId();
        String guardId = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0)).getGuardId();
        TalkRecordHEntity talkRecordHEntity = talkRecordHService.getOne(
                new QueryWrapper<TalkRecordHEntity>().eq("event_id", eventId).eq("stu_id", ShiroUtils.getUserId()).eq("talk_apply_sts_div", "0").eq("del_flg",
                        0));
        Integer TalkRecordHId = null;
        if (talkRecordHEntity != null) {
            //画面．選択した予約時間帯．開始日時
            talkRecordHEntity.setTalkDatime(Timestamp.valueOf(startTime));
            //画面．備考
            talkRecordHEntity.setNoteCont(replyCnt);
            //システム日時
            talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
            talkRecordHService.update(talkRecordHEntity,
                    new QueryWrapper<TalkRecordHEntity>().eq("event_id", eventId).eq("stu_id", ShiroUtils.getUserId()).eq("talk_apply_sts_div", "0").eq(
                            "del_flg", 0));
            TalkRecordHId = talkRecordHEntity.getId();
            talkRecordDService.remove(new QueryWrapper<TalkRecordDEntity>().eq("talk_id", talkRecordHEntity.getId()));
        } else {
            //面談記録登録
            TalkRecordHEntity talkRecordHEntities = new TalkRecordHEntity();
            //セッションデータ．組織ID
            talkRecordHEntities.setOrgId(orgId);
            //引渡データ．イベントID
            talkRecordHEntities.setEventId(eventId);
            //画面．選択した予約時間帯．開始日時
            talkRecordHEntities.setTalkDatime(Timestamp.valueOf(startTime));
            //null
            talkRecordHEntities.setMentorId(null);
            //セッション．ユーザーID
            talkRecordHEntities.setStuId(stuId);
            //生徒基本マスタ．保護者ID
            talkRecordHEntities.setGuardId(guardId);
            //0：申込中
            talkRecordHEntities.setTalkApplyStsDiv("0");
            //画面．備考
            talkRecordHEntities.setNoteCont(replyCnt);
            //システム日時
            talkRecordHEntities.setCretDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            talkRecordHEntities.setCretUsrId(ShiroUtils.getUserId());
            //システム日時
            talkRecordHEntities.setUpdDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            talkRecordHEntities.setUpdUsrId(ShiroUtils.getUserId());
            //「0：有効」
            talkRecordHEntities.setDelFlg(0);
            //面談記録登録
            talkRecordHService.save(talkRecordHEntities);
            TalkRecordHId = talkRecordHEntities.getId();
        }

        //面談記録詳細登録
        List<MstAskTalkEventEntity> mstAskTalkEventEntities2 = mstAskTalkEventService.list(
                new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("del_flg", 0).eq("answer_type_div", "2").eq("item_type_div", "0").eq(
                        "del_flg", 0));
        if (!StringUtils.isEmpty(checkValue)) {
            if (mstAskTalkEventEntities2 != null) {
                String check[] = new String[checkValue.split("/").length];
                for (int i = 0; i < checkValue.split("/").length; i++) {
                    check[i] = checkValue.split("/")[i];
                    String askNum = check[i].split(":")[1];
                    MstAskTalkEventEntity mstAskTalkEventEntity = mstAskTalkEventService.getOne(
                            new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("ask_num", Integer.parseInt(askNum)).eq("item_type_div",
                                    "0").eq("del_flg", 0));
                    TalkRecordDEntity talkRecordDEntity = new TalkRecordDEntity();
                    //面談記録へ登録されたID
                    talkRecordDEntity.setTalkId(TalkRecordHId);
                    //0：質問事項
                    talkRecordDEntity.setItemTypeDiv("0");
                    //質問面談イベント．事項質問番号
                    talkRecordDEntity.setAskNum(mstAskTalkEventEntity.getAskNum());
                    //設問名
                    talkRecordDEntity.setQuestionName(mstAskTalkEventEntity.getQuestionName());
                    //質問面談イベント．設問形式区分
                    talkRecordDEntity.setAnswerTypeDiv(mstAskTalkEventEntity.getAnswerTypeDiv());
                    //質問面談イベント．選択肢(※複数回答の場合、”,”カンマで区切る)
                    talkRecordDEntity.setAnswerReltCont(check[i].split(":")[0]);
                    //システム日時
                    talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
                    //システム日時
                    talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //「0：有効」
                    talkRecordDEntity.setDelFlg(0);
                    //面談記録詳細登録
                    talkRecordDService.save(talkRecordDEntity);
                }
            }
        }
        if (!StringUtils.isEmpty(selectValue)) {
            List<MstAskTalkEventEntity> mstAskTalkEventEntities1 = mstAskTalkEventService.list(
                    new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("del_flg", 0).eq("answer_type_div", "1").eq("item_type_div", "0").eq(
                            "del_flg", 0));
            if (mstAskTalkEventEntities1 != null) {
                String select[] = new String[selectValue.split("/").length];
                for (int i = 0; i < selectValue.split("/").length; i++) {
                    select[i] = selectValue.split("/")[i];
                    String askNum = select[i].split(":")[1];
                    MstAskTalkEventEntity mstAskTalkEventEntity = mstAskTalkEventService.getOne(
                            new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("item_type_div", "0").eq("ask_num",
                                    Integer.parseInt(askNum)).eq("del_flg", 0));
                    TalkRecordDEntity talkRecordDEntity = new TalkRecordDEntity();
                    //面談記録へ登録されたID
                    talkRecordDEntity.setTalkId(TalkRecordHId);
                    //0：質問事項
                    talkRecordDEntity.setItemTypeDiv("0");
                    //質問面談イベント．事項質問番号
                    talkRecordDEntity.setAskNum(mstAskTalkEventEntity.getAskNum());
                    //設問名
                    talkRecordDEntity.setQuestionName(mstAskTalkEventEntity.getQuestionName());
                    //質問面談イベント．設問形式区分
                    talkRecordDEntity.setAnswerTypeDiv(mstAskTalkEventEntity.getAnswerTypeDiv());
                    //質問面談イベント．選択肢(※複数回答の場合、”,”カンマで区切る)
                    talkRecordDEntity.setAnswerReltCont(select[i].split(":")[0]);
                    //システム日時
                    talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
                    //システム日時
                    talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //「0：有効」
                    talkRecordDEntity.setDelFlg(0);
                    //面談記録詳細登録
                    talkRecordDService.save(talkRecordDEntity);
                }
            }
        }
        if (!StringUtils.isEmpty(inputArea)) {
            List<MstAskTalkEventEntity> mstAskTalkEventEntities0 = mstAskTalkEventService.list(
                    new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("del_flg", 0).eq("answer_type_div", "0").eq("item_type_div", "0").eq(
                            "del_flg", 0));
            if (mstAskTalkEventEntities0 != null) {
                Integer askTalkId = TalkRecordHId;
                Map<String, String> paramsMap = (Map)JSON.parse(inputArea);
                paramsMap.keySet().forEach(key->{
                    String value = paramsMap.get(key);
                    Integer askNum = Integer.parseInt(key);
                    MstAskTalkEventEntity mstAskTalkEventEntity = mstAskTalkEventService.getOne(
                            new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("ask_num", askNum).eq("item_type_div", "0").eq("del_flg", 0));
                    TalkRecordDEntity talkRecordDEntity = new TalkRecordDEntity();
                    //面談記録へ登録されたID
                    talkRecordDEntity.setTalkId(askTalkId);
                    //0：質問事項
                    talkRecordDEntity.setItemTypeDiv("0");
                    //質問面談イベント．事項質問番号
                    talkRecordDEntity.setAskNum(mstAskTalkEventEntity.getAskNum());
                    //設問名
                    talkRecordDEntity.setQuestionName(mstAskTalkEventEntity.getQuestionName());
                    //質問面談イベント．設問形式区分
                    talkRecordDEntity.setAnswerTypeDiv(mstAskTalkEventEntity.getAnswerTypeDiv());
                    //質問面談イベント．選択肢(※複数回答の場合、”,”カンマで区切る)
                    talkRecordDEntity.setAnswerReltCont(value);
                    //システム日時
                    talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
                    //システム日時
                    talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //「0：有効」
                    talkRecordDEntity.setDelFlg(0);
                    //面談記録詳細登録
                    talkRecordDService.save(talkRecordDEntity);
                });
            }
        }
        //写真
        //2020/11/12 huangxinliang modify start
        if (!StringUtils.isEmpty(imgArea)) {
            List<MstAskTalkEventEntity> mstAskTalkEventEntities0 = mstAskTalkEventService.list(
                    new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("del_flg", 0).eq("answer_type_div", "3").eq("item_type_div", "0").eq(
                            "del_flg", 0));
            if (mstAskTalkEventEntities0 != null) {
                Integer askTalkId = TalkRecordHId;
                Map<String, String> paramsMap = (Map)JSON.parse(imgArea);
                paramsMap.keySet().forEach(key->{
                    String value = paramsMap.get(key);
                    Integer askNum = Integer.parseInt(key);
                    MstAskTalkEventEntity mstAskTalkEventEntity = mstAskTalkEventService.getOne(
                            new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("ask_num", askNum).eq("item_type_div", "0").eq("del_flg", 0));
                    TalkRecordDEntity talkRecordDEntity = new TalkRecordDEntity();
                    //面談記録へ登録されたID
                    talkRecordDEntity.setTalkId(askTalkId);
                    //0：質問事項
                    talkRecordDEntity.setItemTypeDiv("0");
                    //質問面談イベント．事項質問番号
                    talkRecordDEntity.setAskNum(mstAskTalkEventEntity.getAskNum());
                    //設問名
                    talkRecordDEntity.setQuestionName(mstAskTalkEventEntity.getQuestionName());
                    //質問面談イベント．設問形式区分
                    talkRecordDEntity.setAnswerTypeDiv(mstAskTalkEventEntity.getAnswerTypeDiv());
                    //質問面談イベント．選択肢(※複数回答の場合、”,”カンマで区切る)
                    if (!value.contains(".")){
                        try {
                            talkRecordDEntity.setAnswerReltCont(FileUtils.Base64ToImage(value, eventId, askNum, stuId));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        talkRecordDEntity.setAnswerReltCont(value);
                    }
                    //システム日時
                    talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
                    //システム日時
                    talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //ログインユーザＩＤ
                    talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
                    //「0：有効」
                    talkRecordDEntity.setDelFlg(0);
                    //面談記録詳細登録
                    talkRecordDService.save(talkRecordDEntity);
                });
            }
        }
        //2020/11/12 huangxinliang modify end

        // 2020/12/2 huangxinliang modify start
        if (scheDelId != null) {
            //eventSchePlanDelCheck
            EventSchePlanDelEntity eventSchePlanDelCheck = eventSchePlanDelService.getById(scheDelId);
            //予定済人数　＋　1
            eventSchePlanDelCheck.setPlanedMember(eventSchePlanDelCheck.getPlanedMember() + 1);
            //システム日時
            eventSchePlanDelCheck.setUpdDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            eventSchePlanDelCheck.setUpdUsrId(ShiroUtils.getUserId());
            //生徒イベント申込状況更新
            eventSchePlanDelService.updateById(eventSchePlanDelCheck);
        }
        // 2020/12/2 huangxinliang modify end

        //生徒イベント申込状況更新
        StuEventApplyStsEntity stuEventApplyStsEntity = stuEventApplyStsService.getOne(
                new QueryWrapper<StuEventApplyStsEntity>().eq("event_id", eventId).eq("stu_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        EventSchePlanDelEntity delEntity = eventSchePlanDelService.getById(stuEventApplyStsEntity.getEventScheDelId());
        if (delEntity != null) {
            delEntity.setPlanedMember(delEntity.getPlanedMember() - 1);
            //システム日時
            delEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            delEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelService.updateById(delEntity);
        }
        //「1:回答済」
        stuEventApplyStsEntity.setReplyStsDiv("1");
        //システム日時
        stuEventApplyStsEntity.setReplyTime(DateUtils.getSysTimestamp());
        //イベント日程明細ID
        stuEventApplyStsEntity.setEventScheDelId(scheDelId);
        //システム日時
        stuEventApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        stuEventApplyStsEntity.setUpdUsrId(ShiroUtils.getUserId());
        stuEventApplyStsEntity.setReplyCnt(replyCnt);
        ////ログインユーザＩＤ
        stuEventApplyStsService.updateById(stuEventApplyStsEntity);
        return R.ok();
    }

    /**
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/getAskTalk", method = RequestMethod.GET)
    public R getAskTalk(Integer eventId) {
        List<F11014Dto> askTalk = f11014Service.getAskTalk(eventId);
        TalkRecordHEntity talkRecordHEntity = talkRecordHService.getOne(
                new QueryWrapper<TalkRecordHEntity>().eq("event_id", eventId).eq("stu_id", ShiroUtils.getUserId()).eq("talk_apply_sts_div", "0").eq("del_flg",
                        0));
        List<TalkRecordDEntity> talkRecordDEntities = null;
        if (talkRecordHEntity != null) {
            talkRecordDEntities = talkRecordDService.list(
                    new QueryWrapper<TalkRecordDEntity>().eq("talk_id", talkRecordHEntity.getId()).isNotNull("answer_type_div").ne("answer_type_div",
                            "").isNotNull("question_name").ne("question_name", "").eq("del_flg", 0).orderByAsc("ask_num").eq(
                            "item_type_div", "0"));
            if (talkRecordDEntities.size() != 0) {
                return R.ok().put("askTalk", askTalk).put("talkRecordH", talkRecordHEntity).put("talkRecordD", talkRecordDEntities);
            }
            return R.ok().put("askTalk", askTalk).put("talkRecordH", talkRecordHEntity);
        }
        return R.ok().put("askTalk", askTalk);
    }
}