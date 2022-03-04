/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

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
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21017Service;
import jp.learningpark.modules.pop.service.F21025Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>スマホ_連絡確認画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/27 : zpa: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F21025")
public class F21025Controller {
    @Autowired
    F21025Service f21025Service;
    @Autowired
    LateAbsHstService lateAbsHstService;
    @Autowired
    MstOrgService mstOrgService;
    @Autowired
    GuardReadingStsService guardReadingStsService;
    @Autowired
    MstGuardService mstGuardService;
    @Autowired
    MailUtils mailUtils;
    @Autowired
    NoticeMailSendHstService noticeMailSendHstService;
    @Autowired
    MstUsrService mstUsrService;
    /**
     * mstStuService
     */
    @Autowired
    MstStuService mstStuService;
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
     * f30112Dao
     */
    @Autowired
    F30112Dao f30112Dao;
    @Autowired
    CommonService commonService;
    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;
    /**
     * f21017Service
     */
    @Autowired
    F21017Service f21017Service;
    /**
     * mstNoticeService
     */
    @Autowired
    MstNoticeService mstNoticeService;
    @Value("${learningpark-domain.domain}")
    String domain;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>IDで詳細な遅刻欠席連絡情報を取得</p>
     *
     * @param id 遅刻欠席連絡履歴.ID
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public R getDetail(Integer id) {
        return f21025Service.getDetail(id);
    }
    /**
     * <p>遅刻欠席連絡履歴更新する</p>
     *
     * @param id 遅刻欠席連絡履歴.ID
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public R updateState(Integer id) {
        LateAbsHstEntity lash = new LateAbsHstEntity();
        String updt = "";
        try{
            lash = lateAbsHstService.getOne(new QueryWrapper<LateAbsHstEntity>().eq("id",id).eq("del_flg",0));
            updt = DateUtils.format(lash.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        }catch (Exception e){
            e.printStackTrace();
        }
        //システム日時
        Timestamp date = DateUtils.getSysTimestamp();
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        LateAbsHstEntity lateEntity = lateAbsHstService.getById(id);
        //排他チェク
        if (!StringUtils.equals(updt, DateUtils.format(lateEntity.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        lateEntity.setCorrspdSts("1");
        lateEntity.setUpdDatime(date);
        lateEntity.setUpdUsrId(userId);
        lateAbsHstService.updateById(lateEntity);
        return R.ok();
    }
    /**
     * <p>お知らせとお知らせ配信先に记录增加</p>
     *
     * @param id        遅刻欠席連絡ID
     * @param guardName 保護者名
     * @return
     */
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public R notice(Integer id, String guardName) {
        LateAbsHstEntity lateAbsHstEntity = lateAbsHstService.getById(id);
        String stuId = lateAbsHstEntity.getStuId();
        String guardId = lateAbsHstEntity.getGuardId();
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //ログインユーザーID
        String usrId = userEntity.getUsrId();
        //ログインユーザーの組織ID
        String orgId = userEntity.getOrgId();
        //組織エンティティ
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).eq("del_flg", 0));
        //システム時間
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //お知らせ.ID
        Integer noticeId = lateAbsHstEntity.getNoticeId();
        //保護者お知らせ閲覧状況更新する
        GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getOne(new QueryWrapper<GuardReadingStsEntity>().eq("notice_id", noticeId));
        guardReadingStsEntity.setReadingStsDiv("1");
        guardReadingStsEntity.setUpdUsrId(usrId);
        guardReadingStsEntity.setUpdDatime(sysTimestamp);
        guardReadingStsService.saveOrUpdate(guardReadingStsEntity);
        //メールを送る
        MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", guardId));
        //メール空でない判断
//        if (!StringUtils.isEmpty(mstGuardEntity.getMailad())) {
//            String guardAfterId = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().select("after_usr_id").eq("usr_id", lateAbsHstEntity.getGuardId()).eq(
//                    "del_flg", 0)).getAfterUsrId();
//            String mailad = mstGuardEntity.getMailad();
//            String title = "連絡を確認しました";
//            String content = "";
//            Map<String, Object> params = new HashMap<>();
//            params.put("guardNm", guardName);
//            params.put("orgNm", mstOrgEntity.getOrgNm());
//            params.put("brandCd", mstOrgEntity.getBrandCd());
//            params.put("guardId", guardAfterId);
//            boolean success = true;
//            try {
//                String template =
//                        "\t${guardNm!}様\n" +
//                                "<br>" +
//                                "<br>" +
//                                "\t<p>お世話になっております。</p>\n" +
//                                "\t<p>${orgNm!}です。</p>\n" +
//                                "<br>" +
//                                "\t<p>お知らせに新しいご案内を配信いたしましたので、</p>\n" +
//                                "\t<p>ご確認ください。</p>\n" +
//                                "<br>" +
//                                "\t<p>ID：${guardId!}\n</p>" +
//                                "\t<p>PASS：あなたが設定したパスワード</p>\n" +
//                                "<br>" +
//                                "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" +
//                                "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
//                content = TmplateUtils.generateString(params, template);
//                mailUtils.sendMail(mailad, title, content);
//            } catch (Exception e) {
//                // 送信失敗後にエラー情報を返す
//                e.printStackTrace();
//                success = false;
//            }
            // お知らせメール送信履歴へ登録する。
//            NoticeMailSendHstEntity noticeMailSendHstEntity = new NoticeMailSendHstEntity();
//            //データを入力する
//            noticeMailSendHstEntity.setOrgId(orgId);
//            noticeMailSendHstEntity.setNoticeId(noticeId);
//            noticeMailSendHstEntity.setCtgyNm("4");
//            noticeMailSendHstEntity.setStuId(stuId);
//            noticeMailSendHstEntity.setGuardId(guardId);
//            noticeMailSendHstEntity.setMailad(mailad);
//            noticeMailSendHstEntity.setMailTitle(title);
//            noticeMailSendHstEntity.setMailCnt(content);
//            if (success) {
//                noticeMailSendHstEntity.setStatus("0");
//            } else {
//                noticeMailSendHstEntity.setStatus("1");
//            }
//            noticeMailSendHstEntity.setCretDatime(sysTimestamp);
//            noticeMailSendHstEntity.setCretUsrId(usrId);
//            noticeMailSendHstEntity.setUpdDatime(sysTimestamp);
//            noticeMailSendHstEntity.setUpdUsrId(usrId);
//            noticeMailSendHstEntity.setDelFlg(0);
//            noticeMailSendHstService.save(noticeMailSendHstEntity);
//        }
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>().eq("id",lateAbsHstEntity.getNoticeId()));
        String message = noticeApiService.getMessageDetail("0");
        MstStuEntity mstStuEntity =  mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",lateAbsHstEntity.getStuId()));
        //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntityMana =  mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",usrId).eq("del_flg",0));
        //delete  at 2021/08/19 for V9.02 by NWT LiGX END
        //add at 2021/08/19 for V9.02 by NWT LiGX START
        List<String> usrIds = Arrays.asList(usrId.split(","));
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/19 for V9.02 by NWT LiGX END
        List<String> deviceListMana = new ArrayList<>();
        //modify at 2021/08/19 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntityMana : mstDeviceTokenEntityList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", mstDeviceTokenEntityMana.getDeviceId());
                MstUsrEntity mstUsrEntity = mstUsrService.getOne(
                        new QueryWrapper<MstUsrEntity>().eq("usr_id", mstDeviceTokenEntityMana.getUsrId()).eq("del_flg", 0));
                Integer unReadCount = f21017Service.getUnreadCount(mstUsrEntity.getRoleDiv().trim(), mstUsrEntity.getUsrId(), mstUsrEntity.getOrgId(),
                        DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                map.put("unreadcount", unReadCount);
                map.put("stuId", lateAbsHstEntity.getStuId());
                map.put("stuname", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                //add deviceId to deviceIdList by forサイクル
                deviceListMana.add(JSON.toJSONString(map));
            }
        }
        //modify at 2021/08/19 for V9.02 by NWT LiGX END
        if (CollectionUtils.isNotEmpty(deviceListMana)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment(null);
            sendMessageDto.setImgUrl(null);
            sendMessageDto.setMessage(null);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType",Constant.sendMsgTypeUnread);
            sendMessageDto.setDeviceList(deviceListMana);
            sendMessageDto.setParams(JSON.toJSONString(params));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
            sendMessageDto.setTitle(Constant.sendTitleUnread);
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
        List<String> deviceList = new ArrayList<>();
        //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",mstStuEntity.getGuardId()).eq("del_flg",0));
        //delete  at 2021/08/19 for V9.02 by NWT LiGX END
        //add at 2021/08/19 for V9.02 by NWT LiGX START
        List<String> guardIds = Arrays.asList(mstStuEntity.getGuardId().split(","));
        Map<String, Object> deviceGuardId = new HashMap<>();
        deviceGuardId.put("userIdList",guardIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityGuardList = noticeApiDao.selectDeviceInfo(deviceGuardId);
        //add at 2021/08/19 for V9.02 by NWT LiGX END
        //modify at 2021/08/19 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityGuardList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityGuardList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", mstDeviceTokenEntity.getDeviceId());
                //未読件数を取得する 2020/12/01 modify yang start--
                Integer unReadCount = commonService.pushUnreadCount(guardId);
                //未読件数を取得する 2020/12/01 modify yang end--
                map.put("unreadcount", unReadCount);
                map.put("stuId", lateAbsHstEntity.getStuId());
                map.put("stuname", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                //add deviceId to deviceIdList by forサイクル
                deviceList.add(JSON.toJSONString(map));
            }
        }
        //modify at 2021/08/19 for V9.02 by NWT LiGX ENDs
        if (CollectionUtils.isNotEmpty(deviceList)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
            sendMessageDto.setMessage(message);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType",Constant.sendMsgTypeLateAbs);
            sendMessageDto.setParams(JSON.toJSONString(params));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(mstNoticeEntity.getPubPlanStartDt().getTime()));
            sendMessageDto.setTitle(mstNoticeEntity.getNoticeTitle());
            sendMessageDto.setToken(commonService.getToken());
            sendMessageDto.setDeviceList(deviceList);
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
