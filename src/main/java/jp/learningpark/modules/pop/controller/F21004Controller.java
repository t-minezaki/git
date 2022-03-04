package jp.learningpark.modules.pop.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.LateAbsHstEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.entity.NoticeMailSendHstEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.LateAbsHstService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstGuardService;
import jp.learningpark.modules.common.service.MstNoticeDeliverService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.service.NoticeMailSendHstService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21017Service;
import jp.learningpark.modules.pop.service.F21004Service;
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
 * <p>
 * F21004_確認連絡画面（POP）
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/20 ： NWT)hxl ： 新規作成
 * @date 2019/11/20 10:10
 */
@RestController
@RequestMapping("/pop/F21004")
public class F21004Controller {
    /**
     * 遅刻欠席連絡履歴Service
     */
    @Autowired
    LateAbsHstService lateAbsHstService;
    /**
     * お知らせService
     */
    @Autowired
    MstNoticeService mstNoticeService;
    /**
     * お知らせメール送信履歴Service
     */
    @Autowired
    NoticeMailSendHstService noticeMailSendHstService;
    /**
     * お知らせ配信先Service
     */
    @Autowired
    MstNoticeDeliverService mstNoticeDeliverService;
    /**
     * セッションデータService
     */
    @Autowired
    MstOrgService mstOrgService;
    /**
     * guardReadingStsService
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;
    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;
    /**
     * mstGuardService
     */
    @Autowired
    MstGuardService mstGuardService;
    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * f21004Service
     */
    @Autowired
    F21004Service f21004Service;
    /**
     *
     */
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
    F21017Service f21017Service;
    @Autowired
    CommonService commonService;
    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;
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
        return f21004Service.getDetail(id);
    }

    /**
     * <p>遅刻欠席連絡履歴更新する</p>
     *
     * @param id 遅刻欠席連絡履歴.ID
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void updateState(Integer id) {
        Timestamp date = DateUtils.getSysTimestamp();
        String userId = ShiroUtils.getUserId();
        LateAbsHstEntity lateEntity = lateAbsHstService.getById(id);
        lateEntity.setCorrspdSts("1");
        lateEntity.setCorrspdDatime(date);
        lateEntity.setUpdDatime(date);
        lateEntity.setUpdUsrId(userId);
        lateAbsHstService.updateById(lateEntity);

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
        //ログインユーザーエンティティ
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //遅刻欠席連絡エンティティ
        LateAbsHstEntity lateAbsHstEntity = lateAbsHstService.getById(id);
        String stuId = lateAbsHstEntity.getStuId();
        String guardId = lateAbsHstEntity.getGuardId();
        String guardAfterId = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().select("after_usr_id").eq("usr_id", lateAbsHstEntity.getGuardId()).eq(
                "del_flg",0)).getAfterUsrId();
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
        if (!StringUtils.isEmpty(mstGuardEntity.getMailad())) {
            String mailad = mstGuardEntity.getMailad();
            String title = "連絡を確認しました";
            String content = "";
            Map<String, Object> params = new HashMap<>();
            params.put("guardNm", guardName);
            params.put("orgNm", mstOrgEntity.getOrgNm());
            params.put("brandCd", mstOrgEntity.getBrandCd());
            params.put("guardId", guardAfterId);
            boolean success = true;
//            try {
//                String template =
////                    "\t<p>・本文</p>\n" +
//                        "\t${guardNm!}様\n" +
//                        "<br>" +
//                        "<br>" +
//                        "\t<p>お世話になっております。</p>\n" +
//                        "\t<p>${orgNm!}です。</p>\n" +
//                        "<br>" +
//                        "\t<p>お知らせに新しいご案内を配信いたしましたので、</p>\n" +
//                        "\t<p>ご確認ください。</p>\n" +
//                        "<br>" +
//                        "\t<p>ID：${guardId!}\n</p>" +
//                        "\t<p>PASS：あなたが設定したパスワード</p>\n" +
//                        "<br>" +
//                        "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" +
//                        "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
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
//
//            noticeMailSendHstService.save(noticeMailSendHstEntity);
        }
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>().eq("id",lateAbsHstEntity.getNoticeId()));
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
        List<String> guardIdList = Arrays.asList(mstStuEntity.getGuardId().split(","));
        Map<String, Object> deviceGuardId = new HashMap<>();
        deviceGuardId.put("userIdList",guardIdList);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityGuardList = noticeApiDao.selectDeviceInfo(deviceGuardId);
        //add at 2021/08/19 for V9.02 by NWT LiGX END
        //modify at 2021/08/19 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityGuardList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityGuardList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", mstDeviceTokenEntity.getDeviceId());
                Integer unReadCount = commonService.pushUnreadCount(guardId);
                map.put("unreadcount", unReadCount);
                map.put("stuId", lateAbsHstEntity.getStuId());
                map.put("stuname", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                //add deviceId to deviceIdList by forサイクル
                deviceList.add(JSON.toJSONString(map));
            }
        }
        if (CollectionUtils.isNotEmpty(deviceList)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment(null);
            sendMessageDto.setImgUrl(null);
            sendMessageDto.setMessage(null);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType", Constant.sendMsgTypeUnread);
            sendMessageDto.setParams(JSON.toJSONString(params));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
            sendMessageDto.setTitle(Constant.sendTitleUnread);
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
