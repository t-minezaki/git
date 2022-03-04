/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MailUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.job.task.dto.BTGKA1001Dto;
import jp.learningpark.modules.job.task.service.BTGKA1001Service;
import jp.learningpark.modules.manager.dto.F09005SendMessageDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>イベント公開時定時メール送信日次バッチ</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/27 : wq: 新規<br />
 * @version 1.0
 */
//@Component("BTGKA1001Controller")
@RestController
@RequestMapping(value = "/common")
public class BTGKA1001Job {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;

    /**
     * バッチ処理記述書_BTGKA1001 Service
     */
    @Autowired
    private BTGKA1001Service btgka1001Service;

    /**
     * マスタコード　Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * ユーザ基本マスタ mstUsrService
     */
    @Autowired
    private MstUsrService mstUsrService;
    /**
     * mstStuService
     */
    @Autowired
    private MstStuService mstStuService;
    /**
     * デバイスTOKEN管理 mstDeviceTokenService
     */
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;
    /**
     * 通知アプリ連携API noticeApiService
     */
    @Autowired
    private NoticeApiService noticeApiService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * 通知アプリ連携API noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END
    @Autowired
    private CommonService commonService;
    @Value("${ans-url.token}")
    String token;
    @Value("${learningpark-domain.domain}")
    String domain;

    /**
     * ジョブを実行する。
     */
    @RequestMapping(value = "/BTGKA1001", method = RequestMethod.GET)
    public void execute() {
        try {
            logger.info("BTGKA1001Job START");
            // メール送信対象を取得する
            Date nowDt = DateUtils.getSysDate();
            Date beforeDt = DateUtils.addMinutes(nowDt, -30);
            List<BTGKA1001Dto> btgka1001Dtos = btgka1001Service.selectDeliverInfo(DateUtils.toTimestamp(beforeDt), DateUtils.toTimestamp(nowDt));
            // データなしの場合、バッチを終了する
            if (btgka1001Dtos.isEmpty()) {
                logger.error(MessageUtils.getMessage("MSGCOMB0023"));
            }

            String content = "";
            //当組織のイベント公開時定時送信のメール本文を取得 メール送信履歴へ登録
            //            MstCodDEntity mstCodDEntity = null;
            //            MailSendHstEntity mailSendHstEntity = new MailSendHstEntity();
            //            Map<String, Object> param = new HashMap<>();
            for (BTGKA1001Dto data : btgka1001Dtos) {
                if (StringUtils.isEmpty(data.getMailad())) {
                    continue;
                }
                // 保護者ID
                String guardId = data.getGuardId();
                // 生徒ID
                String stuId = data.getStuId();
                //プッシュメッセージ
                MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", stuId).eq("del_flg", 0));
                //ユーザー名を取得する
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
                String stuNm = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                // 組織ID
                String orgId = mstUsrEntity.getOrgId();
                //送信
                F09005SendMessageDto sendMessageDto = new F09005SendMessageDto();
                //共通部品
                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("msgId", data.getEventId());
                params1.put("msgType", Constant.sendMsgTypeMailSend);
                MstCodDEntity mstCodDEntity1 = mstCodDService.getOne(
                        new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "BLAND_MSG_DIV").eq("cod_cd", "0").eq("del_flg", 0));
                if (mstCodDEntity1 == null) {
                    //取得できない場合、ワーニングメッセージをログに出力する。
                    //message
                    sendMessageDto.setMessage("");
                    logger.error(data.getBrandCd() + "のプッシュメッセージが取得できません。");
                } else {
                    //message
                    sendMessageDto.setMessage(mstCodDEntity1.getCodValue());
                }
                //デバイスIDを取得する
                //delete  at 2021/08/17 for V9.02 by NWT LiGX START
//                MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(
//                        new QueryWrapper<MstDeviceTokenEntity>().select("device_id").eq("usr_id", guardId).eq("del_flg", 0));
                //delete  at 2021/08/17 for V9.02 by NWT LiGX START
                //add at 2021/08/17 for V9.02 by NWT LiGX START
                List<String> usrIds = Arrays.asList(guardId.split(","));
                Map<String, Object> deviceUserId = new HashMap<>();
                deviceUserId.put("userIdList",usrIds);
                List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
                //add at 2021/08/17 for V9.02 by NWT LiGX END
                //未読件数を取得する
                Integer ureadcount = commonService.pushUnreadCount(guardId);
                //modify at 2021/08/17 for V9.02 by NWT LiGX START
                List<String> deviceList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                    for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                        Map<String, String> device = new HashMap<>();
                        device.put("id", mstDeviceTokenEntity.getDeviceId().toString());
                        device.put("ureadcount", ureadcount.toString());
                        device.put("stuId", stuId);
                        device.put("stuname", stuNm);
                        deviceList.add(JSON.toJSONString(device));
                    }
                }
                //modify at 2021/08/17 for V9.02 by NWT LiGX END
                //メッセージの備考内容
                sendMessageDto.setComment("ここはcomment");
                //受信先デバイスIDの集合
                sendMessageDto.setDeviceList(deviceList);
                //メッセージに含まれる画像のリンク
                sendMessageDto.setImgUrl(data.getTitleImgPath());
                //params
                sendMessageDto.setParams(JSON.toJSONString(params1));
                //priority
                sendMessageDto.setPriority(1);
                //当日の30分前
                Long time = DateUtils.addMilliseconds(DateUtils.getSysDate(), -30 * 60 * 1000).getTime();
                //システム時間
                sendMessageDto.setSendTime(Long.toString(time));
                //title
                sendMessageDto.setTitle("予約のリマインドのご連絡");
                //token
                sendMessageDto.setToken(token);
                //modify at 2021/08/17 for V9.02 by NWT LiGX START
                if (CollectionUtils.isNotEmpty(deviceList)){
                //modify at 2021/08/17 for V9.02 by NWT LiGX END
                    //通知プッシュの起止時間と処理時間をログで記録する
                    Timestamp sTime = DateUtils.getSysTimestamp();
                    logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
                    //引渡データをもとに、通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                    R sendMessageJson = noticeApiService.sendMessage(JSON.toJSONString(sendMessageDto));
                    Timestamp eTime = DateUtils.getSysTimestamp();
                    Long cTime = eTime.getTime() - sTime.getTime();
                    logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
                    logger.info("プッシュ通知処理時間：<" + cTime + ">");
                    //返信JSON．Code　＝　「200：成功」の場合
                    if (sendMessageJson.get("code").toString().equals("200")) {
                        //メッセージ内容　＝　返信JSON．Code　＋　返信JSON．Message
                        logger.info(sendMessageJson.get("code").toString() + sendMessageJson.get("message").toString());
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        } finally {
            logger.info("END");
        }
    }
}
