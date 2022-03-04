/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.guard.dao.F30405Dao;
import jp.learningpark.modules.guard.dto.F30405Dto;
import jp.learningpark.modules.guard.service.F30405Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>保護者面談の日程設定画面 ServiceImpl</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/16 : wq: 新規<br />
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class F30405ServiceImpl implements F30405Service {

    /**
     * 保護者面談の日程設定画面 Dao
     */
    @Autowired
    F30405Dao f30405Dao;

    /**
     * 保護者イベント申込状況 Dao
     */
    @Autowired
    GuardEventApplyStsDao guardEventApplyStsDao;

    /**
     * イベント日程(詳細) Dao
     */
    @Autowired
    EventSchePlanDelDao eventSchePlanDelDao;

    /**
     * 面談記録 Dao
     */
    @Autowired
    TalkRecordHDao talkRecordHDao;

    /**
     * 面談記録詳細 Dao
     */
    @Autowired
    TalkRecordDDao talkRecordDDao;

    /**
     * mstEventDao
     */
    @Autowired
    MstEventDao mstEventDao;

    /**
     * @param monthStartDay 月開始日
     * @param monthEndDay 月終了日
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F30405Dto> getScheSts(Date monthStartDay, Date monthEndDay, Integer eventId) {
        return f30405Dao.selectScheSts(monthStartDay, monthEndDay, eventId);
    }

    /**
     * @param applyId 保護者イベント申込ID
     * @return
     */
    @Override
    public F30405Dto getReplyCnt(Integer applyId) {
        return f30405Dao.selectReplyCnt(applyId);
    }

    /**
     * @param tgtYmd 選択日
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F30405Dto> getTimeLine(Date tgtYmd, Integer eventId) {
        return f30405Dao.selectTimeLine(tgtYmd, eventId);
    }

    /**
     * @param eventId
     * @return
     */
    @Override
    public List<F30405Dto> selectAskTalk(Integer eventId) {
        return f30405Dao.getAskTalk(eventId);
    }

    @Override
    public R getInitData(Integer eventId, Integer applyId, String tgtYmd, Boolean firstFlag) {
        Date monthStartDay = null;
        Date monthEndDay = null;
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
        List<F30405Dto> applylist = f30405Dao.selectScheSts(monthStartDay, monthEndDay, eventId);
        for (int i = 0; i < applylist.size(); i++) {
            applylist.get(i).setTgtDay(DateUtils.format(applylist.get(i).getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).split("-")[2]);
        }
        if (applylist.size() != 0) {
            // modify at 2022/2/10 for V9.02 by NWT HuangXL START
//            List<F30405Dto> applyListCopy = new ArrayList<>();
            Set<F30405Dto> applySetCopy = new HashSet<>();
            for (int i = 0; i < applylist.size(); i++) {
                for (int j = 0; j < applylist.size(); j++) {
                    if (StringUtils.equals(applylist.get(i).getTgtDay(), applylist.get(j).getTgtDay()) && i != j) {
                        applySetCopy.add(applylist.get(i));
                        applySetCopy.add(applylist.get(j));
//                        applyListCopy.add(applylist.get(i));
//                        applyListCopy.add(applylist.get(j));
//                        applylist.removeAll(applyListCopy);
                    }
                }
                applylist.removeAll(applySetCopy);
            }
            List<F30405Dto> applyListCopy = new ArrayList<>(applySetCopy);
            // modify at 2022/2/10 for V9.02 by NWT HuangXL END
            //重複しないデータ
            for (int i = 0; i < applylist.size(); i++) {
                //定員＞予定済人数
                if (applylist.get(i).getPersonsLimt() > applylist.get(i).getPlanedMember()) {
                    applylist.get(i).setFlag("0");
                } else {
                    applylist.get(i).setFlag("1");
                }
            }
            //重複しデータ
            for (int i = 0; i < applyListCopy.size(); i++) {
                applyListCopy.get(i).setFlag("1");
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
            for (F30405Dto notApply : applylist) {
                //システム日時　＞＝　イベント日程(詳細)．日程予定日
                if (DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD).compareTo(DateUtils.format(notApply.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD)) > 0) {
                    notApply.setFlag("2");
                }
            }
        }
        //質問回答内容を取得
        F30405Dto f30405Dto = f30405Dao.selectReplyCnt(applyId);
        //予約可能時間帯
        List<F30405Dto> timeList = f30405Dao.selectTimeLine(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS), eventId);
        //該当保護者イベント申込状況を取得する。
        GuardEventApplyStsEntity applyStsEntity = guardEventApplyStsDao.selectById(applyId);
        //該当イベント日程(詳細)を取得する。
        EventSchePlanDelEntity delEntity = eventSchePlanDelDao.selectById(applyStsEntity.getEventScheDelId());
        if (StringUtils.equals("1", applyStsEntity.getProxyFlg())) {
            List<EventSchePlanDelEntity> delEntities = eventSchePlanDelDao.selectList(
                    new QueryWrapper<EventSchePlanDelEntity>().eq("event_sche_id", delEntity.getEventScheId()).eq("ref_id", delEntity.getRefId()).eq(
                            "sgd_plan_date", DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS)).eq("del_flg", 0).orderByAsc(
                            "sgd_start_datime"));
            timeList = new ArrayList<>();
            F30405Dto proxyDto = null;
            for (EventSchePlanDelEntity eventDelEntity : delEntities) {
                proxyDto = new F30405Dto();
                proxyDto.setTimeLine(DateUtils.format(eventDelEntity.getSgdStartDatime(), GakkenConstant.DATE_FORMAT_HH_MM) + "～" + DateUtils.format(
                        eventDelEntity.getSgdEndDatime(), GakkenConstant.DATE_FORMAT_HH_MM));
                if (DateUtils.getSysDate().compareTo(eventDelEntity.getSgdPlanDate()) < 0) {
                    timeList.add(proxyDto);
                }
            }
        } else {
            List<F30405Dto> timeListnew = new ArrayList<>();
            for (F30405Dto dto : timeList) {
                if (DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD).compareTo(DateUtils.format(dto.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD)) <= 0) {
                    timeListnew.add(dto);
                }
            }
            timeList = timeListnew;
            if (timeList.size() != 0) {
                for (F30405Dto time : timeList) {
                    time.setTimeLine(
                            DateUtils.format(time.getSgdStartDatime(), GakkenConstant.DATE_FORMAT_HH_MM) + "～" + DateUtils.format(time.getSgdEndDatime(),
                                    GakkenConstant.DATE_FORMAT_HH_MM));
                }
            }
        }
        //質問面談イベントに質問データを取得する。
        List<F30405Dto> askItems = f30405Dao.getAskTalk(eventId);
        //面談記録
        TalkRecordHEntity talkRecordHEntity = talkRecordHDao.selectOne(
                new QueryWrapper<TalkRecordHEntity>().eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("event_id", eventId).eq("stu_id",
                        ShiroUtils.getSessionAttribute("stuId")).eq("talk_apply_sts_div", "0").eq("del_flg", 0));
        List<TalkRecordDEntity> talkRecordDEntities = new ArrayList<>();
        //面談記録詳細
        if (talkRecordHEntity != null) {
            talkRecordDEntities = talkRecordDDao.selectList(
                    new QueryWrapper<TalkRecordDEntity>().eq("talk_id", talkRecordHEntity.getId()).eq("del_flg", 0).eq("item_type_div", "0").eq("del_flg",
                            0).orderByAsc("ask_num"));
        }
        MstEventEntity mstEventEntity = mstEventDao.selectById(eventId);
        // 2021/1/13 huangxinliang modify start
        R r = R.ok().put("applylist", applylist).put("f30405Dto", f30405Dto).put("timeList", timeList).put("askItems", askItems).put("talkRecordHEntity",
                talkRecordHEntity).put("talkRecordDEntities", talkRecordDEntities).put("eventTitle", mstEventEntity.getEventTitle());
        if (firstFlag != null && firstFlag) {
            r.put("refType", mstEventEntity.getRefType());
        }
        return r;
        // 2021/1/13 huangxinliang modify end
    }

    /**
     * @param eventId イベントID
     * @return
     */
    @Override
    public R getMinDate(Integer eventId) {
        Date minDate = null;
        Date sysDateFormat = DateUtils.parse(DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS),
                GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        List<EventSchePlanDelEntity> delEntities = eventSchePlanDelDao.selectList(
                new QueryWrapper<EventSchePlanDelEntity>().eq("event_id", eventId).gt("sgd_plan_date", sysDateFormat).eq("del_flg", 0).orderByAsc(
                        "sgd_plan_date"));
        if (delEntities.size() != 0) {
            minDate = delEntities.get(0).getSgdPlanDate();
        }

        return R.ok().put("minDate", minDate);
    }

    /**
     * @param eventId イベントID
     * @param tgtYmd 対象年月
     * @param timeStr 時間文字
     * @return
     */
    @Override
    public R timeSelect(Integer eventId, String tgtYmd, String timeStr) {
        String startTime = timeStr.split("～")[0];
        String endTime = timeStr.split("～")[1];
        tgtYmd = tgtYmd.split("\\(")[0];
        String selectDate = DateUtils.format(DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Timestamp sgdStartTime = new Timestamp(DateUtils.parse(selectDate + " " + startTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        Timestamp sgdEndTime = new Timestamp(DateUtils.parse(selectDate + " " + endTime + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        List<EventSchePlanDelEntity> selectTimeList = null;
        List<EventSchePlanDelEntity> copy = new ArrayList<>();
        selectTimeList = eventSchePlanDelDao.selectList(
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
     * @param f30405Dto
     * @param resultList
     * @return
     */
    @Override
    public R updateDB(F30405Dto f30405Dto, String resultList) {
        //面談情報
        List<F30405Dto> result = JSON.parseObject(StringUtils.defaultString(resultList), new TypeReference<List<F30405Dto>>() {
        });

        EventSchePlanDelEntity eventSchePlanDelCheck = eventSchePlanDelDao.selectById(f30405Dto.getScheDelId());
        if (eventSchePlanDelCheck != null && eventSchePlanDelCheck.getPersonsLimt() <= eventSchePlanDelCheck.getPlanedMember()) {
            return R.error(MessageUtils.getMessage("MSGCOMN0111", "日程"));
        }

        //イベント申込状況更新
        GuardEventApplyStsEntity applyEntity = guardEventApplyStsDao.selectById(f30405Dto.getApplyId());
        if (applyEntity.getEventScheDelId() != null) {
            EventSchePlanDelEntity changeEntity = eventSchePlanDelDao.selectById(applyEntity.getEventScheDelId());
            if (changeEntity.getPlanedMember() > 0) {
                changeEntity.setPlanedMember(changeEntity.getPlanedMember() - 1);
                changeEntity.setUpdUsrId(ShiroUtils.getUserId());
                changeEntity.setUpdDatime(DateUtils.getSysTimestamp());
                eventSchePlanDelDao.updateById(changeEntity);
            }
        }
        //質問回答
        applyEntity.setReplyCnt(f30405Dto.getNoteCont());
        //閲覧回答区分
        applyEntity.setReplyStsDiv("1");
        //回答日時
        applyEntity.setReplyTime(DateUtils.getSysTimestamp());
        //イベント日程(明細)ID
        applyEntity.setEventScheDelId(f30405Dto.getScheDelId());
        //更新日時
        applyEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        applyEntity.setUpdUsrId(ShiroUtils.getUserId());
        guardEventApplyStsDao.updateById(applyEntity);

        if (applyEntity.getEventScheDelId() != null) {
            //イベント日程（詳細)更新
            EventSchePlanDelEntity scheDelEntity = eventSchePlanDelDao.selectById(applyEntity.getEventScheDelId());
            //予定済人数
            scheDelEntity.setPlanedMember(scheDelEntity.getPlanedMember() + 1);
            //更新日時
            scheDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            scheDelEntity.setUpdUsrId(ShiroUtils.getUserId());
            eventSchePlanDelDao.updateById(scheDelEntity);
        }

        TalkRecordDEntity talkRecordDEntity = null;
        TalkRecordHEntity talkRecordHEntity = null;
        //編集の場合、面談詳細データを削除する。
        if (f30405Dto.getTalkId() != null) {
            talkRecordHEntity = talkRecordHDao.selectById(f30405Dto.getTalkId());
            //面談日時
            talkRecordHEntity.setTalkDatime(f30405Dto.getSgdStartDatime());
            //備考
            talkRecordHEntity.setNoteCont(f30405Dto.getNoteCont());
            //更新日時
            talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザーID
            talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
            try {
                talkRecordHDao.updateById(talkRecordHEntity);
            } catch (Exception e) {
                return R.error("面談記録の更新に失敗しました");
            }
            talkRecordDDao.delete(new QueryWrapper<TalkRecordDEntity>().eq("talk_id", f30405Dto.getTalkId()).eq("item_type_div", "0"));
        } else {
            talkRecordHEntity = new TalkRecordHEntity();
            //セッションデータ．組織ID
            talkRecordHEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
            //引渡データ．イベントID
            talkRecordHEntity.setEventId(f30405Dto.getEventId());
            //画面．選択した予約時間帯．開始日時
            talkRecordHEntity.setTalkDatime(f30405Dto.getSgdStartDatime());
            //null
            talkRecordHEntity.setMentorId(null);
            //セッション．ユーザーID
            talkRecordHEntity.setStuId((String)ShiroUtils.getSessionAttribute("stuId"));
            //生徒基本マスタ．保護者ID
            talkRecordHEntity.setGuardId(ShiroUtils.getUserId());
            //0：申込中
            talkRecordHEntity.setTalkApplyStsDiv("0");
            //画面．備考
            talkRecordHEntity.setNoteCont(f30405Dto.getNoteCont());
            //システム日時
            talkRecordHEntity.setCretDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            talkRecordHEntity.setCretUsrId(ShiroUtils.getUserId());
            //システム日時
            talkRecordHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //ログインユーザＩＤ
            talkRecordHEntity.setUpdUsrId(ShiroUtils.getUserId());
            //「0：有効」
            talkRecordHEntity.setDelFlg(0);
            try {
                talkRecordHDao.insert(talkRecordHEntity);
            } catch (Exception e) {
                return R.error("面談記録の追加に失敗しました");
            }
        }
        String stuId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
        for (F30405Dto dto : result) {
            talkRecordDEntity = new TalkRecordDEntity();
            //面談記録ID
            talkRecordDEntity.setTalkId(f30405Dto.getTalkId() == null ? talkRecordHEntity.getId() : f30405Dto.getTalkId());
            //事項類型区分
            talkRecordDEntity.setItemTypeDiv("0");
            //質問番号
            talkRecordDEntity.setAskNum(dto.getAskNum());
            //回答形式区分
            talkRecordDEntity.setAnswerTypeDiv(dto.getAnswerTypeDiv());
            //回答結果内容
            //2020/11/12 huangxinliang modify start
            if (StringUtils.equals("3", dto.getAnswerTypeDiv()) && !dto.getContent().contains(".")){
                try {
                    talkRecordDEntity.setAnswerReltCont(FileUtils.Base64ToImage(dto.getContent(), f30405Dto.getEventId(), dto.getAskNum(), stuId));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                talkRecordDEntity.setAnswerReltCont(dto.getContent());
            }
            //2020/11/12 huangxinliang modify end
            //設問名
            talkRecordDEntity.setQuestionName(dto.getQuestionName());
            //作成ユーザＩＤ
            talkRecordDEntity.setCretUsrId(ShiroUtils.getUserId());
            //作成日時
            talkRecordDEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新日時
            talkRecordDEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            talkRecordDEntity.setUpdUsrId(ShiroUtils.getUserId());
            //削除フラグ
            talkRecordDEntity.setDelFlg(0);
            try {
                talkRecordDDao.insert(talkRecordDEntity);
            } catch (Exception e) {
                return R.error("データベースの操作に失敗しました。");
            }
        }
        return R.ok();
    }
}
