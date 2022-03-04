package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dto.F08014Dto;
import jp.learningpark.modules.manager.dto.F08014StudentDto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F08014Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;


@RestController
@RequestMapping("/manager/F08014")
public class F08014Controller {

    /**
     * mstGuardService
     */
    @Autowired
    MstGuardService mstGuardService;

    /**
     * mailSendHstService
     */
    @Autowired
    MailSendHstService mailSendHstService;

    /**
     * f08014Service
     */
    @Autowired
    F08014Service f08014Service;

    /**
     * guardEventApplyStsService
     */
    @Autowired
    GuardEventApplyStsService guardEventApplyStsService;

    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * eventSchePlanDelService
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;

    /**
     * stuEventApplyStsService
     */
    @Autowired
    StuEventApplyStsService stuEventApplyStsService;

    /**
     * talkRecordHService
     */
    @Autowired
    TalkRecordHService talkRecordHService;

    /**
     * noticeApiService
     */
    @Autowired
    private NoticeApiService noticeApiService;

    /**
     * mstDeviceTokenService
     */
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;

    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END

    /**
     * F30112Dao
     */
    @Autowired
    private F30112Dao f30112Dao;

    @Autowired
    private MstUsrService mstUsrService;

    /**
     * mstStuService
     */
    @Autowired
    private MstStuService mstStuService;

    /**
     * mstEventService
     */
    @Autowired
    private MstEventService mstEventService;
    @Autowired
    private CommonService commonService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * @param id       保護者イベント申込ID
     * @param userFlag 保護者
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id, Boolean userFlag) {

        // 初期情報を取得し画面を表示する。
        F08014Dto f08014Dto = f08014Service.selectEvent(id);
        if (f08014Dto == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "予定"));
        }
        // 日程予定日フォーマット変換
        f08014Dto.setSgdStartDatimeStr(DateUtils.format(f08014Dto.getSgdStartDatime(), GakkenConstant.DATE_FORMAT_HH_MM));
        // 日程開始日時フォーマット変換
        f08014Dto.setSgdEndDatimeStr(DateUtils.format(f08014Dto.getSgdEndDatime(), GakkenConstant.DATE_FORMAT_HH_MM));
        // 日程終了日時フォーマット変換
        f08014Dto.setSgdPlanDateStr(DateUtils.format(f08014Dto.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_M_D_E_SLASH));

        List<F08014StudentDto> f08014StudentDtoList = f08014Service.selectStudent(id, userFlag);

        return R.ok().put("eventEntity", f08014Dto).put("stuList", f08014StudentDtoList);
    }

    /**
     *
     * @param guardEventApplyStsId 保護者イベント申込ID
     * @param userFlag 保護者
     * @return
     */
    @RequestMapping(value = "/updGuardEventApplySts", method = RequestMethod.GET)
    @Transactional(propagation = Propagation.REQUIRED)
    public R updGuardEventApplySts(Integer guardEventApplyStsId, Boolean userFlag) {

        // ユーザーID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        Integer eventScheDelId;
        String stuId, guardId;
        Integer eventId;
        if (userFlag) {
            GuardEventApplyStsEntity guardEventApplyStsEntity = new GuardEventApplyStsEntity();
            // 閲覧回答区分　＝　「2:キャンセル」
            guardEventApplyStsEntity.setReplyStsDiv("2");
            // 更新ユーザＩＤ　＝　ログインユーザーID
            guardEventApplyStsEntity.setUpdUsrId(userId);
            // 更新日時　＝　システム日時
            guardEventApplyStsEntity.setUpdDatime(DateUtils.toTimestamp(new Date()));
            boolean status = guardEventApplyStsService.update(guardEventApplyStsEntity, new QueryWrapper<GuardEventApplyStsEntity>().eq("id", guardEventApplyStsId));
            if (!status) {
                return R.error().put("msg", "データ更新に失敗");
            }
            GuardEventApplyStsEntity guardEventApplySts = guardEventApplyStsService.getOne(new QueryWrapper<GuardEventApplyStsEntity>().eq("id",
                    guardEventApplyStsId).eq("del_flg", 0));
            eventScheDelId = guardEventApplySts.getEventScheDelId();
            stuId = guardEventApplySts.getStuId();
            guardId = guardEventApplySts.getGuardId();
            eventId = guardEventApplySts.getEventId();
        } else {
            StuEventApplyStsEntity stuEventApplyStsEntity = new StuEventApplyStsEntity();
            // 閲覧回答区分　＝　「2:キャンセル」
            stuEventApplyStsEntity.setReplyStsDiv("2");
            // 更新ユーザＩＤ　＝　ログインユーザーID
            stuEventApplyStsEntity.setUpdUsrId(userId);
            // 更新日時　＝　システム日時
            stuEventApplyStsEntity.setUpdDatime(DateUtils.toTimestamp(new Date()));
            boolean status = stuEventApplyStsService.update(stuEventApplyStsEntity, new QueryWrapper<StuEventApplyStsEntity>().eq("id", guardEventApplyStsId));
            if (!status) {
                return R.error().put("msg", "データ更新に失敗");
            }
            StuEventApplyStsEntity stuEventApplySts = stuEventApplyStsService.getOne(new QueryWrapper<StuEventApplyStsEntity>().eq("id",
                    guardEventApplyStsId).eq("del_flg", 0));
            eventScheDelId = stuEventApplySts.getEventScheDelId();
            stuId = stuEventApplySts.getStuId();
            guardId = stuEventApplySts.getGuardId();
            eventId = stuEventApplySts.getEventId();
        }

        TalkRecordHEntity talkRecordHEntityOld = talkRecordHService.getOne(new QueryWrapper<TalkRecordHEntity>().eq("talk_apply_sts_div", "0").eq("event_id", eventId).eq("stu_id", stuId).eq("guard_id", guardId).eq("del_flg", 0).last("limit 1"));
        if (talkRecordHEntityOld != null) {
            talkRecordHEntityOld.setTalkApplyStsDiv("1");
            talkRecordHEntityOld.setUpdUsrId(ShiroUtils.getUserId());
            talkRecordHEntityOld.setUpdDatime(DateUtils.getSysTimestamp());
            talkRecordHService.updateById(talkRecordHEntityOld);
        }
        //イベント日程(詳細)
        EventSchePlanDelEntity eventSchePlanDelEntity = eventSchePlanDelService.getOne(new QueryWrapper<EventSchePlanDelEntity>().eq("id",
                eventScheDelId).eq("del_flg", 0));
        //イベント開催対象が「教室」の場合、
        if (StringUtils.equals("0", eventSchePlanDelEntity.getRefTypeDiv())) {
            //イベント日程(詳細)．予定済人数
            if (eventSchePlanDelEntity.getPlanedMember() > 0) {
                eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() - 1);
            }
            //更新ユーザＩＤ
            eventSchePlanDelEntity.setUpdUsrId(userId);
            //更新日時
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("id",
                    eventScheDelId).eq("del_flg", 0));
        }else {
            //更新日時
            eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            eventSchePlanDelEntity.setUpdUsrId(userId);
            //イベント日程(詳細)．予定済人数
            if (eventSchePlanDelEntity.getPlanedMember() > 0){
                eventSchePlanDelEntity.setPlanedMember(eventSchePlanDelEntity.getPlanedMember() - 1);
            }
            eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("id",
                    eventScheDelId).eq("del_flg", 0));
        }
        return R.ok();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getMail", method = RequestMethod.GET)
    public R getMail() {

        // 組織ID
//        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // メール内容・タイトルを取得
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2").eq("cod_key",
                "MAIL_MSG_CANCEL").eq("cod_cd", "DEFAULT").eq("del_flg", 0));

//        if (mstCodDEntity == null) {
//            return R.error().put("msg",MessageUtils.getMessage("MSGCOMB0017", "メール内容・タイトル"));
//        }
        return R.ok().put("mailEntity", mstCodDEntity);
    }

    /**
     *
     * @param params メールパラメータ
     * @param geasId イベントID
     * @return
     */
    @RequestMapping(value = "/postMail", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public R postMail(String params, Integer geasId) {

        // ユーザーID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        Map<String, Object> paramsMap = (Map<String, Object>) JSON.parse(params);

        GuardEventApplyStsEntity guardEventApplyStsEntity = guardEventApplyStsService.getOne(new QueryWrapper<GuardEventApplyStsEntity>().eq("id", geasId).eq("del_flg", 0));

        if (guardEventApplyStsEntity == null) {
            return R.error().put("msg",MessageUtils.getMessage("MSGCOMB0017", "保護者イベント申込状況"));
        }
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",guardEventApplyStsEntity.getGuardId()));
        int eventId = guardEventApplyStsEntity.getEventId();

        paramsMap.put("orgNm", ShiroUtils.getUserEntity().getOrgNm());
        paramsMap.put("stuId", guardEventApplyStsEntity.getStuId());
        paramsMap.put("guardId", guardEventApplyStsEntity.getGuardId());
        paramsMap.put("guardAfterId", mstUsrEntity.getAfterUsrId());

        MstGuardEntity guardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", guardEventApplyStsEntity.getGuardId()).eq("del_flg", 0));

        if (guardEntity == null) {
            return R.error().put("msg",MessageUtils.getMessage("MSGCOMB0017", "保護者"));
        }

        paramsMap.put("guardNm", guardEntity.getFlnmNm() + guardEntity.getFlnmLnm());
        paramsMap.put("email", guardEntity.getMailad());

        // メール内容・タイトルを取得
//        MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2").eq("cod_key", "MAIL_MSG_CANCEL").eq("cod_cd", "DEFAULT").eq("del_flg", 0));
//
//        if (mstCodDEntity == null) {
//            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2").eq("cod_key", "MAIL_MSG_CANCEL").eq("cod_cd", "DEFAULT").eq("del_flg", 0));
//        }
        String mailTitle = noticeApiService.getMessageDetail("0");
        if(mailTitle==null){
            return R.error().put("NullMessage",ShiroUtils.getBrandcd()+"のプッシュメッセージが取得できません。");
        }
//        String mailTitle = mstCodDEntity.getCodValue();

        paramsMap.put("mailTitle", mailTitle);
        // メール内容を取得し、リアルタイム送信を行う
        R r = f08014Service.postEmail(paramsMap);
        // メール送信履歴へ登録する。
        MailSendHstEntity mailSendHstEntity = new MailSendHstEntity();
        // 組織ID
        mailSendHstEntity.setOrgId(orgId);
        // イベントID
        mailSendHstEntity.setEventId(eventId);
        // カテゴリ
        mailSendHstEntity.setCtgyNm("3");
        // 生徒ID
        mailSendHstEntity.setStuId((String) paramsMap.get("stuId"));
        // 保護者ID
        mailSendHstEntity.setGuardId((String) paramsMap.get("guardId"));
        // メールアドレス
        mailSendHstEntity.setMailad((String) paramsMap.get("email"));
        // メールタイトル
        mailSendHstEntity.setMailTitle((String) paramsMap.get("mailTitle"));
        // メール内容
        mailSendHstEntity.setMailCnt((String) r.get("mailcnt"));
        // ステータス
        mailSendHstEntity.setStatus((String) r.get("status"));
        // 作成日時
        mailSendHstEntity.setCretDatime(DateUtils.toTimestamp(new Date()));
        // 作成ユーザＩＤ
        mailSendHstEntity.setCretUsrId(userId);
        // 更新日時
        mailSendHstEntity.setUpdDatime(DateUtils.toTimestamp(new Date()));
        // 更新ユーザＩＤ
        mailSendHstEntity.setUpdUsrId(userId);

        mailSendHstService.save(mailSendHstEntity);

        MstEventEntity mstEventEntity=mstEventService.getOne(new QueryWrapper<MstEventEntity>().select("*").eq("id",eventId));

        mstEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
        List<String> deviceIdList=new ArrayList<>();
        // 通知プッシュ： 受信先デバイスIDの集合、必須項目
        //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity=mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",paramsMap.get("guardId")).eq("del_flg",0));
        //delete  at 2021/08/18 for V9.02 by NWT LiGX EMD
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        String guardId =  paramsMap.get("guardId").toString();
        List<String> usrIds = Arrays.asList(guardId.split(","));
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",paramsMap.get("stuId")).eq("del_flg", 0));
        //modify at 2021/08/17 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",mstDeviceTokenEntity.getDeviceId());
                Integer UnReadCount = commonService.pushUnreadCount((String) paramsMap.get("guardId"));
                map.put("unreadcount",UnReadCount);
                map.put("stuId",paramsMap.get("stuId"));
                map.put("stuname",mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                deviceIdList.add(JSON.toJSONString(map));
            }
        }
        //modify at 2021/08/17 for V9.02 by NWT LiGX END
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            //未読件数を取得する 2020/12/01 modify yang end--
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstEventEntity.getTitleImgPath()==null?"":mstEventEntity.getTitleImgPath());
            sendMessageDto.setDeviceList(deviceIdList);
            sendMessageDto.setMessage(mailTitle);
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("msgId",mstEventEntity.getId());
            map.put("msgType",Constant.sendMsgTypeGuardEventApplySts);
            sendMessageDto.setParams(JSON.toJSONString(map));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(String.valueOf(mstEventEntity.getPubStartDt().getTime()));
            sendMessageDto.setTitle(mstEventEntity.getEventTitle());
            sendMessageDto.setToken(commonService.getToken());
            //通知プッシュの起止時間と処理時間をログで記録する
            Timestamp sTime = DateUtils.getSysTimestamp();
            logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
            noticeApiService.sendMessage(JSON.toJSONString(sendMessageDto));
            Timestamp eTime = DateUtils.getSysTimestamp();
            Long cTime = eTime.getTime() - sTime.getTime();
            logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
            logger.info("プッシュ通知処理時間：<" + cTime + ">");
        }
        return R.ok().put("r",r);
    }
}
