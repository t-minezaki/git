/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
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
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.F09005SendMessageDto;
import jp.learningpark.modules.manager.dto.F21026Dto;
import jp.learningpark.modules.manager.service.F21026Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>F21017_マスホ_生徒一覧画面_V6.0</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/21 : yang: 新規<br />
 * @version 6.0
 */
@RestController
@RequestMapping(value = "/manager/F21026")
public class F21026Controller {

    @Autowired
    MstVariousSetService mstVariousSetService;
    @Autowired
    F21026Service f21026Service;
    @Autowired
    EntryExitHstService entryExitHstService;
    @Autowired
    MstNoticeService mstNoticeService;
    @Autowired
    MstNoticeDeliverService mstNoticeDeliverService;
    @Autowired
    GuardReadingStsService guardReadingStsService;
    @Autowired
    StuPointService stuPointService;
    @Autowired
    MstGuardService mstGuardService;
    @Autowired
    MailUtils mailUtils;
    @Autowired
    MstUsrService mstUsrService;
    @Autowired
    NoticeMailSendHstService noticeMailSendHstService;
    @Autowired
    MstOrgService mstOrgService;
    @Value("${learningpark-domain.domain}")
    String domain;
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END
    @Autowired
    CommonService commonService;
    @Autowired
    NoticeApiService noticeApiService;
    @Value("${ans-url.token}")
    String token;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String stuId) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //本組織で指定した登校自動ポイントを取得する
        MstVariousSetEntity mstVariousSetEntity = mstVariousSetService.getOne(new QueryWrapper<MstVariousSetEntity>().eq("org_id", orgId).eq("del_flg", 0));
        //前画面から引き渡し生徒IDを元に、、ユーザ基本マスタ、生徒基本マスタ、コートマスタから生徒情報を習得し
        F21026Dto f21026Dto = f21026Service.getStuInfo(orgId, stuId, date);
        return R.ok().put("stu", f21026Dto).put("mstVariousSetEntity",mstVariousSetEntity);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(String stuId,String flg,String date,String guardId,String stuName,Integer point,Integer goSchPoint) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String userId = ShiroUtils.getUserId();
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        String pushDate = DateUtils.format(DateUtils.parse(date, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), GakkenConstant.DATE_FORMAT_HH_MM_MAIL);
        //登録日時
        Timestamp time = DateUtils.toTimestamp(DateUtils.parse(date, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        //登校が可否チェック
        Integer loginCount = f21026Service.login(stuId, date.substring(0, 10));
        //guardIdでdeviceIdに問い合わせる
        List<String> deviceIdList = new ArrayList<>();
        F09005SendMessageDto sendMessageDto = new F09005SendMessageDto();
        if (StringUtils.equals(flg, "0")) {
            if (loginCount % 2 != 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0127", "入室", "退室"));
            }
        } else {
            if (loginCount % 2 == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0127", "入室", "退室"));
            }
        }
        // 2020/12/4 huangxinliang modify start
        if (loginCount == 0 && StringUtils.equals(flg, "0")){
            // 2020/12/7 huangxinliang modify start
            CM0005.PointVo pointVo = CM0005.getPointVo(orgId);
            CM0005.addPoint(stuId, orgId, pointVo, 4, userId,sysTimestamp);
            // 2020/12/7 huangxinliang modify end
        }
        // 2020/12/4 huangxinliang modify end
        String goShoolFlg = StringUtils.equals(flg, "0") ? Constant.GO_SCHOOL_FOR_GKGC : Constant.LEAVE_SCHOOL_FOR_GKGC;
        String goClassroomFlg = StringUtils.equals(flg, "0") ? Constant.GO_CLASSROOM : Constant.LEAVE_CLASSROOM;
        String pushTitleFlg = StringUtils.equals(flg, "0") ? Constant.PUSH_TITLE_GO_SCHOOL : Constant.PUSH_TITLE_LEAVE_SCHOOL;
        //入退室履歴へ登録、
        EntryExitHstEntity entryExitHstEntity = new EntryExitHstEntity();
        //組織ID
        entryExitHstEntity.setOrgId(orgId);
        //生徒ID
        entryExitHstEntity.setStuId(stuId);
        //登録日時
        entryExitHstEntity.setEntryDt(time);
        //登録フラグ
        entryExitHstEntity.setEntryFlg(flg);
        //登録ユーザID
        entryExitHstEntity.setEntryUserId(userId);
        //通知ステータス
        entryExitHstEntity.setNoticeSts("");
        //作成日時
        entryExitHstEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        entryExitHstEntity.setCretUsrId(userId);
        //作成日時
        entryExitHstEntity.setUpdDatime(sysTimestamp);
        //作成ユーザＩＤ
        entryExitHstEntity.setUpdUsrId(userId);
        //削除フラグ
        entryExitHstEntity.setDelFlg(0);
        entryExitHstService.save(entryExitHstEntity);
        //お知らせへ登録
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        //組織ID
        mstNoticeEntity.setOrgId(orgId);
        //お知らせタイトル
        mstNoticeEntity.setNoticeTitle(goShoolFlg + "のお知らせ");
        date = DateUtils.format(DateUtils.parse(date, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL);
        //お知らせ内容
        mstNoticeEntity.setNoticeCont(stuName + "様が" + goShoolFlg + "しました。" + "</br>" + goShoolFlg + "日時：" + date);
        //お知らせタップ区分
        mstNoticeEntity.setNoticeTypeDiv("3");
        //お知らせレベル区分
        mstNoticeEntity.setNoticeLevelDiv("1");
        //掲載予定開始日時
        mstNoticeEntity.setPubPlanStartDt(sysTimestamp);
        //掲載予定終了日時
        mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(new Date(sysTimestamp.getTime()),7)));
        //全体配信フラグ
        mstNoticeEntity.setAllDeliverFlg(null);
        //タイトル画像パス
        mstNoticeEntity.setAttachFilePath(null);
        //作成日時
        mstNoticeEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        mstNoticeEntity.setCretUsrId(userId);
        //作成日時
        mstNoticeEntity.setUpdDatime(sysTimestamp);
        //作成ユーザＩＤ
        mstNoticeEntity.setUpdUsrId(userId);
        //削除フラグ
        mstNoticeEntity.setDelFlg(0);
        mstNoticeService.save(mstNoticeEntity);
        //お知らせ配信先へ登録
        MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
        //組織ID
        mstNoticeDeliverEntity.setOrgId(orgId);
        //お知らせID
        mstNoticeDeliverEntity.setNoticeId(mstNoticeEntity.getId());
        //管理フラグ
        mstNoticeDeliverEntity.setMgrFlg(null);
        //保護者ID
        mstNoticeDeliverEntity.setGuardId(guardId);
        //生徒ID
        mstNoticeDeliverEntity.setStuId(stuId);
        //作成日時
        mstNoticeDeliverEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        mstNoticeDeliverEntity.setCretUsrId(userId);
        //作成日時
        mstNoticeDeliverEntity.setUpdDatime(sysTimestamp);
        //作成ユーザＩＤ
        mstNoticeDeliverEntity.setUpdUsrId(userId);
        //削除フラグ
        mstNoticeDeliverEntity.setDelFlg(0);
        mstNoticeDeliverService.save(mstNoticeDeliverEntity);
        //保護者お知らせ閲覧状況へ登録
        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        //組織ID
        guardReadingStsEntity.setOrgId(orgId);
        //お知らせID
        guardReadingStsEntity.setNoticeId(mstNoticeEntity.getId());
        //保護者ID
        guardReadingStsEntity.setGuardId(guardId);
        //生徒ID
        guardReadingStsEntity.setStuId(stuId);
        //閲覧状況区分
        guardReadingStsEntity.setReadingStsDiv("0");
        //作成日時
        guardReadingStsEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        guardReadingStsEntity.setCretUsrId(userId);
        //作成日時
        guardReadingStsEntity.setUpdDatime(sysTimestamp);
        //作成ユーザＩＤ
        guardReadingStsEntity.setUpdUsrId(userId);
        //削除フラグ
        guardReadingStsEntity.setDelFlg(0);
        guardReadingStsService.save(guardReadingStsEntity);

        if (!StringUtils.isEmpty(guardId)) {
            MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", guardId).eq("del_flg", 0));
            if (mstGuardEntity != null) {
//                if (!StringUtils.isEmpty(mstGuardEntity.getMailad())) {
//                    MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", guardId).eq("del_flg", 0));
//                    MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", mstUsrEntity.getOrgId()).eq("del_flg", 0));
//                    //保護者へメール送信を行う。
//                    String mailad = mstGuardEntity.getMailad();
//                    String title = goShoolFlg + "メール";
//                    String content = "";
//                    Map<String, Object> params = new HashMap<>();
//                    params.put("guardNm", mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm());
//                    params.put("stuNm", stuName);
//                    params.put("sysDatetime", DateUtils.format(time, Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL));
//                    params.put("orgNm", mstOrgEntity.getOrgNm());
//                    params.put("brandCd", ShiroUtils.getBrandcd());
//                    params.put("guardId", mstUsrEntity.getAfterUsrId());
//                    params.put("goSchool", goShoolFlg);
//                    boolean success = true;
//                    try {
//                        String template =
////                    "\t<p>・本文</p>\n" +
//                                "\t${guardNm!}様\n" +
//                                "<br>" +
//                                "\t<p>お世話になっております。</p>\n" +
//                                "\t<p>${orgNm!}です。</p>\n" +
//                                "<br>" +
//                                "\t<p>${stuNm!}様は${sysDatetime}に${goSchool}しました。</p>\n" +
//                                "<br>" +
//                                "\t<p>詳しい情報はマイページよりご確認ください。</p>\n" +
//                                "<br>" +
//                                "\t<p>ID：${guardId!}\n</p>" +
//                                "\t<p>PASS：あなたが設定したパスワード</p>\n" +
//                                "<br>" +
//                                "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" +
//                                "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
//                        content = TmplateUtils.generateString(params, template);
//                        mailUtils.sendMail(mailad, title, content);
//                    } catch (Exception e) {
//                        // 送信失敗後にエラー情報を返す
//                        e.printStackTrace();
//                        success = false;
//                    }

//                    //お知らせメール送信履歴へ登録
//                    NoticeMailSendHstEntity noticeMailSendHstEntity = new NoticeMailSendHstEntity();
//                    //データを入力する
//                    noticeMailSendHstEntity.setOrgId(mstUsrEntity.getOrgId());
//                    noticeMailSendHstEntity.setNoticeId(mstNoticeEntity.getId());
//                    noticeMailSendHstEntity.setCtgyNm("1");
//                    noticeMailSendHstEntity.setStuId(stuId);
//                    noticeMailSendHstEntity.setGuardId(guardId);
//                    noticeMailSendHstEntity.setMailad(mailad);
//                    noticeMailSendHstEntity.setMailTitle(title);
//                    noticeMailSendHstEntity.setMailCnt(content);
//                    if (success) {
//                        noticeMailSendHstEntity.setStatus("0");
//                    } else {
//                        noticeMailSendHstEntity.setStatus("1");
//                    }
//                    noticeMailSendHstEntity.setCretDatime(sysTimestamp);
//                    noticeMailSendHstEntity.setCretUsrId(userId);
//                    noticeMailSendHstEntity.setUpdDatime(sysTimestamp);
//                    noticeMailSendHstEntity.setUpdUsrId(userId);
//                    noticeMailSendHstEntity.setDelFlg(0);
//                  }
                try {
//                        noticeMailSendHstService.save(noticeMailSendHstEntity);
                    //管理者が連絡確認の確認連絡クリック 2020/11/12 modify --yang start
                    //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//                    MstDeviceTokenEntity mstDeviceTokenEntitie = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().and(w -> w.eq("usr_id",guardId)).eq("del_flg",0));
                    //delete  at 2021/08/18 for V9.02 by NWT LiGX END
                    //add at 2021/08/17 for V9.02 by NWT LiGX START
                    List<String> usrIds = Arrays.asList(guardId.split(","));
                    Map<String, Object> deviceUserId = new HashMap<>();
                    deviceUserId.put("userIdList",usrIds);
                    List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
                    //add at 2021/08/17 for V9.02 by NWT LiGX END
                    /* 2021/03/17 manamiru4-50 start */
                    Integer unreadCount = commonService.pushUnreadCountForQR(guardId);
                    /* 2021/03/17 manamiru4-50 end */
                    //modify at 2021/08/17 for V9.02 by NWT LiGX START
                    if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                        for (MstDeviceTokenEntity mstDeviceTokenEntitie : mstDeviceTokenEntityList) {
                            F09005DeviceDto deviceId = new F09005DeviceDto();
                            deviceId.setId(mstDeviceTokenEntitie.getDeviceId());
                            deviceId.setUnreadcount(unreadCount);
                            deviceId.setStuname(stuName);
                            deviceId.setStuId(stuId);
                            deviceIdList.add(JSON.toJSONString(deviceId));
                        }
                    }
                    //modify at 2021/08/17 for V9.02 by NWT LiGX END
                    if (CollectionUtils.isNotEmpty(deviceIdList)){
                        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                        sendMessageDto.setComment("代理入退登録確認comment");
                        sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
                        sendMessageDto.setDeviceList(deviceIdList);
                        sendMessageDto.setMessage("さんが、" + pushDate + "に" + goClassroomFlg );
                        Map<String,Object> map = new LinkedHashMap<>();
                        map.put("msgId",mstNoticeEntity.getId());
                        map.put("msgType",Constant.sendMsgTypeEntryExitHst);
                        sendMessageDto.setParams(JSON.toJSONString(map));
                        sendMessageDto.setPriority(1);
                        sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
                        sendMessageDto.setTitle(pushTitleFlg);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return R.ok();
    }
}
