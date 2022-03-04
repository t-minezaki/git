package jp.learningpark.modules.job.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MailSendHstService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.job.entity.BTGKA1002Dto;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@Component("BTGKA1002Controller")
@RestController
@RequestMapping(value = "/common")
public class BTGKA1002Controller {

    /**
     * mailSendHstService
     */
    @Autowired
    MailSendHstService mailSendHstService;

    /**
     * mstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * mstDeviceTokenService
     */
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * 通知アプリ連携API noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END
    @Autowired
    private CommonService commonService;
    /**
     * mstUsrService
     */
    @Autowired
    private MstUsrService mstUsrService;
    /**
     * mstStuService
     */
    @Autowired
    private MstStuService mstStuService;
    /**
     * noticeApiService
     */
    @Autowired
    private NoticeApiService noticeApiService;

    /**
     * BTGKA1002Service
     */
    @Autowired
    private jp.learningpark.modules.job.service.BTGKA1002Service BTGKA1002Service;
    @Value("${ans-url.token}")
    String token;

    /**
     * logger
     */
    Logger logger = LoggerFactory.getLogger("BTGKA1002Controller");

    /**
     * ジョブを実行する。
     */
    @RequestMapping(value = "/BTGKA1002", method = RequestMethod.GET)
    public void postMail() {

        // 保護者イベント申込状況から対象者一覧を取得
        List<BTGKA1002Dto> BTGKA1002DtoList = BTGKA1002Service.getGEASList();

        // データなしの場合、バッチを終了する
        if (BTGKA1002DtoList.isEmpty()) {
            logger.error(MessageUtils.getMessage("MSGCOMN0017", "イベント"));
        }

        String params = JSON.toJSONString(BTGKA1002DtoList);
        JSONArray paramsMap = JSON.parseArray(params);

        for (Map<String, Object> param : paramsMap.toJavaList(Map.class)) {
            // 保護者ID
            String guardId = param.get("guardId").toString();
            // 生徒ID
            String stuId = param.get("stuId").toString();
            //プッシュメッセージ
            MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", stuId).eq("del_flg", 0));
            // 組織ID
            String orgId = mstUsrEntity.getOrgId();
            //ユーザー名を取得する
            MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
            String stuNm = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
            //送信
            F09005SendMessageDto sendMessageDto = new F09005SendMessageDto();
            //共通部品
            Map<String, Object> params1 = new LinkedHashMap<>();
            params1.put("msgId", param.get("id"));
            params1.put("msgType", Constant.sendMsgTypeGuardAplSts);
            MstCodDEntity mstCodDEntity1 = mstCodDService.getOne(
                    new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "BLAND_MSG_DIV").eq("cod_cd", "0").eq("del_flg", 0));
            if (mstCodDEntity1 == null) {
                //取得できない場合、ワーニングメッセージをログに出力する。
                //message
                sendMessageDto.setMessage("");
                logger.error(param.get("brandCd").toString() + "のプッシュメッセージが取得できません。");
            } else {
                //message
                sendMessageDto.setMessage(mstCodDEntity1.getCodValue());
            }
            //デバイスIDを取得する
            //delete  at 2021/08/17 for V9.02 by NWT LiGX START
//            MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(
//                    new QueryWrapper<MstDeviceTokenEntity>().select("device_id").eq("usr_id", guardId).eq("del_flg", 0));
            //delete  at 2021/08/17 for V9.02 by NWT LiGX END
            //add at 2021/08/17 for V9.02 by NWT LiGX START
            List<String> usrIds = Arrays.asList(guardId.split(","));
            Map<String, Object> deviceUserId = new HashMap<>();
            deviceUserId.put("userIdList",usrIds);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
            //add at 2021/08/17 for V9.02 by NWT LiGX END
            //未読件数を取得する
            Integer ureadcount = commonService.pushUnreadCount(guardId);

            List<String> deviceList = new ArrayList<>();
            //modify at 2021/08/17 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                    Map<String, String> device = new HashMap<>();
                    device.put("id", mstDeviceTokenEntity.getDeviceId().toString());
                    device.put("unreadcount", ureadcount.toString());
                    device.put("stuId", stuId);
                    device.put("stuname", stuNm);
                    deviceList.add(JSON.toJSONString(device));
                }
                //modify at 2021/08/17 for V9.02 by NWT LiGX END
            }
            //メッセージの備考内容
            sendMessageDto.setComment("ここはcomment");
            //受信先デバイスIDの集合
            sendMessageDto.setDeviceList(deviceList);
            //メッセージに含まれる画像のリンク
            // modify at 2021/10/14 for V9.02 by NWT wen START
            sendMessageDto.setImgUrl(param.get("titleImgPath") == null ? "" : param.get("titleImgPath").toString());
            // modify at 2021/10/14 for V9.02 by NWT wen END
            //params
            sendMessageDto.setParams(JSON.toJSONString(params1));
            //priority
            sendMessageDto.setPriority(1);
            //当日の10時
            String dat = DateUtils.format(DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS),
                    GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
            long time = DateUtils.addHours(DateUtils.parse(dat, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO), 10).getTime();
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
                    /* 2021/09/16 manamiru1-772 cuikl edit start */
                    logger.debug(sendMessageJson.get("code").toString() + sendMessageJson.get("message").toString());
                    /* 2021/09/16 manamiru1-772 cuikl edit end */
                }
            }
        }
    }
}
