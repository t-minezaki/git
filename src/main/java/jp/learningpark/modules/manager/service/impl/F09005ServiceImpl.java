package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.manager.dao.F09005Dao;
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.F09005Dto;
import jp.learningpark.modules.manager.dto.F09005SendMessageDto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F09005Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class F09005ServiceImpl implements F09005Service {

    @Autowired
    F09005Dao f09005Dao;
    @Autowired
    EntryExitHstDao entryExitHstDao;
    @Autowired
    MstNoticeDao mstNoticeDao;
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;
    @Autowired
    GuardReadingStsDao guardReadingStsDao;
    @Autowired
    StuPointDao stuPointDao;
    /**
     * mstGuardDao
     */
    @Autowired
    MstGuardDao mstGuardDao;
    /**
     * mstUsrDao
     */
    @Autowired
    MstUsrDao mstUsrDao;
    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;
    /**
     * noticeMailSendHstDao
     */
    @Autowired
    NoticeMailSendHstDao noticeMailSendHstDao;

    /**
     * mstDeviceTokenService
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;

    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END

    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;

    /**
     * f30112Service
     */
    @Autowired
    F30112Service f30112Service;
    @Autowired
    CommonService commonService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * コードマスタ_明細Service
     */
    @Autowired
    MstCodDService mstCodDService;
    @Value("${ans-url.token}")
    String token;
    @Value("${learningpark-domain.domain}")
    String domain;

    /**
     * @param stuidlist 選択されたstuidの集合
     * @return
     */
    @Override

    public List<F09005Dto> init(List<String> stuidlist, String status) {
        return f09005Dao.init(stuidlist, status);
    }

    /**
     *
     * @param f09005DtoList 取得したf09004リストデータ
     * @param time F09004画面.時間
     * @param status F09004画面.登校/下校
     * @param pointVo
     * @return
     */

    @Override
    @Transactional
    public MstNoticeEntity save(List<F09005Dto> f09005DtoList, Date time, String status, CM0005.PointVo pointVo) {

        EntryExitHstEntity entryExitHstEntity = new EntryExitHstEntity();
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        List<String> deviceIdList = new ArrayList<>();
        List<F09005SendMessageDto> f09005SendMessageDtoList = new ArrayList<>();
        // 2020/12/2 huangxinliang modify start
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        String userId = ShiroUtils.getUserId();

        for (F09005Dto dto : f09005DtoList) {
            String orgId = ShiroUtils.getUserEntity().getOrgId();
            dto.setDx(StringUtils.equals(dto.getDx(), Constant.GO_SCHOOL) ? Constant.GO_SCHOOL_FOR_GKGC : Constant.LEAVE_SCHOOL_FOR_GKGC);
            Integer count = entryExitHstDao.selectCount(
                    new QueryWrapper<EntryExitHstEntity>().eq("org_id", orgId).eq("stu_id", dto.getStuId1()).last(
                            "and to_char(entry_dt,'yyyy-mm-dd') = '" + DateUtils.format(sysTimestamp, Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'"));
            //入退室履歴登録
            entryExitHstEntity.setOrgId(orgId);
            entryExitHstEntity.setStuId(dto.getStuId1());
            entryExitHstEntity.setEntryDt(DateUtils.toTimestamp(time));
            entryExitHstEntity.setEntryFlg(status);
            entryExitHstEntity.setEntryUserId(userId);
            entryExitHstEntity.setNoticeSts("");
            entryExitHstEntity.setCretDatime(sysTimestamp);
            entryExitHstEntity.setCretUsrId(userId);
            entryExitHstEntity.setUpdDatime(sysTimestamp);
            entryExitHstEntity.setUpdUsrId(userId);
            entryExitHstEntity.setDelFlg(0);
            try {
                entryExitHstDao.insert(entryExitHstEntity);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            //お知らせ登録
            mstNoticeEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
            mstNoticeEntity.setNoticeTitle(dto.getDx() + "のお知らせ");
            mstNoticeEntity.setNoticeCont(dto.getStuNm() + "様が" + dto.getDx() + "しました。" + "</br>" + dto.getDx() + "日時：" + DateUtils.format(time, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL));
            mstNoticeEntity.setNoticeTypeDiv("3");
            mstNoticeEntity.setNoticeLevelDiv("1");
            mstNoticeEntity.setPubPlanStartDt(DateUtils.getSysTimestamp());
            mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(mstNoticeEntity.getPubPlanStartDt(), 7)));
            mstNoticeEntity.setCretDatime(sysTimestamp);
            mstNoticeEntity.setCretUsrId(userId);
            mstNoticeEntity.setUpdDatime(sysTimestamp);
            mstNoticeEntity.setUpdUsrId(userId);
            mstNoticeEntity.setDelFlg(0);
            try {
                mstNoticeDao.insert(mstNoticeEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            if (!StringUtils.isEmpty(dto.getGuardId())) {
                //保護者お知らせ閲覧状況登録
                guardReadingStsEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
                guardReadingStsEntity.setNoticeId(mstNoticeEntity.getId());
                guardReadingStsEntity.setGuardId(dto.getGuardId());
                guardReadingStsEntity.setStuId(dto.getStuId1());
                guardReadingStsEntity.setReadingStsDiv("0");
                guardReadingStsEntity.setCretDatime(sysTimestamp);
                guardReadingStsEntity.setCretUsrId(userId);
                guardReadingStsEntity.setUpdDatime(sysTimestamp);
                guardReadingStsEntity.setUpdUsrId(userId);
                guardReadingStsEntity.setDelFlg(0);
                try {
                    guardReadingStsDao.insert(guardReadingStsEntity);

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                //お知らせ配信先登録
                mstNoticeDeliverEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
                mstNoticeDeliverEntity.setNoticeId(mstNoticeEntity.getId());
                mstNoticeDeliverEntity.setMgrFlg("");
                mstNoticeDeliverEntity.setGuardId(dto.getGuardId());
                mstNoticeDeliverEntity.setStuId(dto.getStuId1());
                mstNoticeDeliverEntity.setCretDatime(sysTimestamp);
                mstNoticeDeliverEntity.setCretUsrId(userId);
                mstNoticeDeliverEntity.setUpdDatime(sysTimestamp);
                mstNoticeDeliverEntity.setUpdUsrId(userId);
                mstNoticeDeliverEntity.setDelFlg(0);
                try {
                    mstNoticeDeliverDao.insert(mstNoticeDeliverEntity);

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
//                MstGuardEntity mstGuardEntity = mstGuardDao.selectOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", dto.getGuardId()).eq("del_flg", 0));
//                if (mstGuardEntity != null) {
//                    if (!StringUtils.isEmpty(mstGuardEntity.getMailad())) {
//                        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", dto.getGuardId()).eq("del_flg", 0));
//                        //保護者へメール送信を行う。
//                        String mailad = mstGuardEntity.getMailad();
//                        String title = dto.getDx() + "メール";
//                        String content = "";
//                        Map<String, Object> params = new HashMap<>();
//                        params.put("guardNm", mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm());
//                        params.put("stuNm", dto.getStuNm());
//                        params.put("sysDatetime", DateUtils.format(time, Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL));
//                        params.put("orgNm", dto.getOrgCd());
//                        params.put("brandCd", ShiroUtils.getBrandcd());
//                        params.put("guardId", mstUsrEntity.getAfterUsrId());
//                        params.put("goSchool", dto.getDx());
//                        boolean success = true;
//                        try {
//                            String template =
////                    "\t<p>・本文</p>\n" +
//                                    "\t${guardNm!}様\n" +
//                                            "<br>" +
//                                            "\t<p>お世話になっております。</p>\n" +
//                                            "\t<p>${orgNm!}です。</p>\n" +
//                                            "<br>" +
//                                            "\t<p>${stuNm!}様は${sysDatetime}に${goSchool}しました。</p>\n" +
//                                            "<br>" +
//                                            "\t<p>詳しい情報はマイページよりご確認ください。</p>\n" +
//                                            "<br>" +
//                                            "\t<p>ID：${guardId!}\n</p>" +
//                                            "\t<p>PASS：あなたが設定したパスワード</p>\n" +
//                                            "<br>" +
//                                            "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" +
//                                            "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
//                            content = TmplateUtils.generateString(params, template);
//                            mailUtils.sendMail(mailad, title, content);
//                        } catch (Exception e) {
//                            // 送信失敗後にエラー情報を返す
//                            logger.error(e.getMessage());
//                            success = false;
//                        }
//
//                        //お知らせメール送信履歴へ登録
//                        NoticeMailSendHstEntity noticeMailSendHstEntity = new NoticeMailSendHstEntity();
//                        //データを入力する
//                        noticeMailSendHstEntity.setOrgId(mstUsrEntity.getOrgId());
//                        noticeMailSendHstEntity.setNoticeId(mstNoticeEntity.getId());
//                        noticeMailSendHstEntity.setCtgyNm("1");
//                        noticeMailSendHstEntity.setStuId(dto.getStuId1());
//                        noticeMailSendHstEntity.setGuardId(dto.getGuardId());
//                        noticeMailSendHstEntity.setMailad(mailad);
//                        noticeMailSendHstEntity.setMailTitle(title);
//                        noticeMailSendHstEntity.setMailCnt(content);
//                        if (success) {
//                            noticeMailSendHstEntity.setStatus("0");
//                        } else {
//                            noticeMailSendHstEntity.setStatus("1");
//                        }
//                        noticeMailSendHstEntity.setCretDatime(sysTimestamp);
//                        noticeMailSendHstEntity.setCretUsrId(userId);
//                        noticeMailSendHstEntity.setUpdDatime(sysTimestamp);
//                        noticeMailSendHstEntity.setUpdUsrId(userId);
//                        noticeMailSendHstEntity.setDelFlg(0);
//                        try {
//                            noticeMailSendHstDao.insert(noticeMailSendHstEntity);
//                        } catch (Exception e) {
//                            logger.error(e.getMessage());
//                        }
//                    }
//                }
            }

            //生徒ポイント登録
            if ("0".equals(status) && count == 0) {
                // 2020/12/7 huangxinliang modify start
                CM0005.addPoint(dto.getStuId1(), orgId, pointVo, 4, userId,sysTimestamp);
                // 2020/12/7 huangxinliang modify end
            }
        }
        // 2020/12/2 huangxinliang modify end
        return mstNoticeEntity;
    }
    @Override
    public R sendMessage(List<F09005Dto> f09005DtoList,MstNoticeEntity mstNoticeEntity, Date time,String status){
        List<String> deviceIdList = new ArrayList<>();
        String goClassroomFlg = StringUtils.equals(status, "0") ? Constant.GO_CLASSROOM : Constant.LEAVE_CLASSROOM;
        String pushTitleFlg = StringUtils.equals(status, "0") ? Constant.PUSH_TITLE_GO_SCHOOL : Constant.PUSH_TITLE_LEAVE_SCHOOL;
        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
        SendMessageDto sendMessageDto = new SendMessageDto();
        for (F09005Dto dto : f09005DtoList) {
            //guardIdでdeviceIdに問い合わせる
            //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//            MstDeviceTokenEntity mstDeviceTokenEntitie = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().and(w -> w.eq("usr_id",dto.getGuardId())).eq("del_flg",0));
            //delete  at 2021/08/18 for V9.02 by NWT LiGX START
            //add at 2021/08/17 for V9.02 by NWT LiGX START

            // modify at 2021/10/11 for V9.02 by NWT wen START
            List<String> usrIds = new ArrayList<String>();
            if (StringUtils.isNotBlank(dto.getGuardId())) {
                usrIds = Arrays.asList(dto.getGuardId().split(","));
            }
            // modify at 2021/10/11 for V9.02 by NWT wen END
            Map<String, Object> deviceUserId = new HashMap<>();
            deviceUserId.put("userIdList",usrIds);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
            //add at 2021/08/17 for V9.02 by NWT LiGX END
            //未読件数を取得する 2020/12/01 modify yang start--
            Integer unreadCount = commonService.pushUnreadCount(dto.getGuardId());
            //未読件数を取得する 2020/12/01 modify yang end--
            //modify at 2021/08/17 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntitie : mstDeviceTokenEntityList) {
                    F09005DeviceDto deviceId = new F09005DeviceDto();
                    deviceId.setId(mstDeviceTokenEntitie.getDeviceId());
                    deviceId.setStuId(dto.getStuId1());
                    deviceId.setUnreadcount(unreadCount);
                    deviceId.setStuname(dto.getStuNm());
                    deviceIdList.add(JSON.toJSONString(deviceId));
                }
            }
            //modify at 2021/08/17 for V9.02 by NWT LiGX END
        }
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            sendMessageDto.setComment("代理入退登録確認comment");
            sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
            sendMessageDto.setDeviceList(deviceIdList);
            sendMessageDto.setMessage("さんが、" + DateUtils.format(time, GakkenConstant.DATE_FORMAT_HH_MM_MAIL) + "に" + goClassroomFlg);
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
        return R.ok();
    }
}
