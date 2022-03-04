/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dto.F30421Dto;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.guard.service.F30419Service;
import jp.learningpark.modules.guard.service.F30421Service;
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>保護者知らせ画面(学研教室モード)</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/11 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/guard/F30421")
public class F30421Controller {

    /**
     * コードマスタ service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 保護者知らせ画面 service
     */
    @Autowired
    F30421Service f30421Service;
    /**
     * 保護者お知らせ閲覧状況 service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;

    /**
     * mstDeviceTokenDao
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;

    /**
     * mstGuardService
     */
    @Autowired
    MstGuardService mstGuardService;

    /**
     * F30112Service
     */
    @Autowired
    F30112Service f30112Service;

    /**
     * F30419
     */
    @Autowired
    F30419Service f30419Service;
    /**
     * 生徒基本マスタ Service
     */
    @Autowired
    MstStuService mstStuService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END
    @Value("${ans-url.token}")
    String token;
    @Autowired
    CommonService commonService;

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * @param limit
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, String url, String stuId) {
        //セッション・生徒Id
        if (StringUtils.isEmpty(stuId)){
            stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        }else{
            ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuId);
        }
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        //お知らせの件数の取得
        Integer noticeCount = f30421Service.getNoticeCount(orgId, guardId, stuId);
        //イベントの件数の取得
        Integer eventCount = f30421Service.getEventCount(guardId, stuId);
        //マナミルチャンネル件数の取得
        Integer channelCount = f30419Service.selectUnreadCount(guardId, orgId,stuId);
        //コードマスタ取得
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "LOCAL_IMG").eq("cod_value", orgId).eq("del_flg", 0));
        //お知らせデータを取得
        List<F30421Dto> f30421Dtos = f30421Service.getLists(orgId, guardId, stuId, (limit - GakkenConstant.NOTICE_PAGE_SIZE) ,GakkenConstant.NOTICE_PAGE_SIZE);
        Integer dataCount = f30421Service.selectCount(orgId, guardId, stuId);
        for (F30421Dto f30421dto : f30421Dtos) {
            Date startDate = null;
            if (f30421dto.getPubStartDt() == null) {
                startDate = new Date(f30421dto.getPubPlanStartDt().getTime());
            } else {
                startDate = new Date(f30421dto.getPubStartDt().getTime());
            }
            String startDt = DateUtils.format(startDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
            f30421dto.setStartDt(startDt);
            if (StringUtils.isEmpty(f30421dto.getTitleImgPath())) {
                //コードマスタ・画像パス
                if (mstCodDEntity != null) {
                    f30421dto.setTitleImgPath(mstCodDEntity.getCodValue2());
                } else {
                    f30421dto.setTitleImgPath("../img/logo2.png");
                }
            }
        }
        R r = R.ok();
        return r.put("f30421Dtos", f30421Dtos).put("noticeCount", noticeCount + eventCount).put("channelCount", channelCount).put("dataCount",dataCount);
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    public R sendMessage(Integer msgId){
        String guardId = ShiroUtils.getUserId();
        String stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        //guardIdでdeviceIdに問い合わせる
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        List<String> usrIds = Arrays.asList(guardId.split(","));
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        //delete  at 2021/08/17 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().and(w -> w.eq("usr_id", guardId)).eq("del_flg",0));
        //delete  at 2021/08/17 for V9.02 by NWT LiGX END
        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
        //modify at 2021/08/17 for V9.02 by NWT LiGX START
        List<String> deviceIdList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)){
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList){
                F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                //デバイスIDを取得する。
                f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                f09005DeviceDto.setUnreadcount(commonService.pushUnreadCount(ShiroUtils.getUserId()) - 1);
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("del_flg", 0).eq("stu_id", stuId));
                f09005DeviceDto.setStuId(stuId);
                f09005DeviceDto.setStuname(mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
            }
        }
        //modify at 2021/08/17 for V9.02 by NWT LiGX END
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            //メッセージの備考内容。規則：最大桁数255。
            sendMessageDto.setComment(null);
            //受信先デバイスIDの集合、必須項目。
            sendMessageDto.setDeviceList(deviceIdList);
            // メッセージに含まれる画像のリンク
            sendMessageDto.setImgUrl(null);
            //コードマスタより取得する、ブランドごとに自由に設定できる。
            sendMessageDto.setMessage(null);
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("msgId",msgId);
            map.put("msgType",GakkenConstant.sendMsgTypeUnread);
            sendMessageDto.setParams(JSON.toJSONString(map));
            //デフォルト値5にする
            sendMessageDto.setPriority(1);
            //メッセージのタイトル
            sendMessageDto.setTitle(GakkenConstant.sendTitleUnread);
            sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
            //アプリケーションを識別できる唯一秘密鍵で
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
