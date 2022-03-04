/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

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
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.student.dao.F11020Dao;
import jp.learningpark.modules.student.dto.F11010Dto;
import jp.learningpark.modules.student.dto.F11011Dto;
import jp.learningpark.modules.student.dto.F11013Dto;
import jp.learningpark.modules.student.dto.F11020Dto;
import jp.learningpark.modules.student.service.F11010Service;
import jp.learningpark.modules.student.service.F11011Service;
import jp.learningpark.modules.student.service.F11013Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * <p>スマホ_メッセージ一覧</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/29 : zpa: 新規<br />
 * @version 7.0
 */
@RestController
@RequestMapping("/student/F11010")
public class F11010Controller {
    /**
     * mstCodDService;
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * f11010Service
     */
    @Autowired
    F11010Service f11010Service;

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
    MstStuService mstStuService;
    @Autowired
    CommonService commonService;
    @Autowired
    NoticeApiService noticeApiService;
    @Autowired
    F11011Service f11011Service;

    @Autowired
    F11013Service f11013Service;

    @Autowired
    F11020Dao f11020Dao;
    @Autowired
    TalkReadingStsService talkReadingStsService;
    @Autowired
    StuEventApplyStsService stuEventApplyStsService;
    @Value("${ans-url.token}")
    String token;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer limit, Integer page){
        //セッション・生徒Id
        String stuId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //コードマスタ取得
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "LOCAL_IMG").eq("cod_value", orgId).eq("del_flg", 0));
        //塾の連絡通知を取得し
        List<F11010Dto> f11010Dtos = f11010Service.getList(stuId, GakkenConstant.NOTICE_PAGE_SIZE, (limit - GakkenConstant.NOTICE_PAGE_SIZE));
        if(f11010Dtos != null && f11010Dtos.size() != 0){
            for(F11010Dto f11010Dto : f11010Dtos){
                if (StringUtils.isEmpty(f11010Dto.getTitleImgPath())) {
                    //コードマスタ・画像パス
                    if (mstCodDEntity != null) {
                        f11010Dto.setTitleImgPath(mstCodDEntity.getCodValue2());
                    } else {
                        f11010Dto.setTitleImgPath("../img/logo2.png");
                    }
                }
            }
        }
        Integer count = f11010Service.getCount(stuId);
        Integer numCount = f11010Service.getUnreadCount(stuId);
        return R.ok().put("f11010Dtos",f11010Dtos).put("numCount",numCount).put("count", count);
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    public R sendMessage(String typeDiv,Integer id){
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //セッション・生徒Id
        String stuId = ShiroUtils.getUserId();
        if (StringUtils.equals(typeDiv,"1")){
            F11011Dto f11011Dto = f11011Service.init(id);
            if(f11011Dto != null){
                if (f11011Dto.getReadingStsDiv().equals("0")) {
                    TalkReadingStsEntity talkReadingStsEntity = talkReadingStsService.getById(f11011Dto.getId());
                    talkReadingStsEntity.setReadingStsDiv("1");
                    talkReadingStsEntity.setUpdDatime(sysTimestamp);
                    talkReadingStsEntity.setUpdUsrId(stuId);
                    talkReadingStsService.updateById(talkReadingStsEntity);
                }
            }
        }else if (StringUtils.equals(typeDiv,"4")||StringUtils.equals(typeDiv,"5")){
            List<F11020Dto> askAndTalkList = f11020Dao.getAskAndTalk(id, stuId, stuId);
            if (askAndTalkList.size() > 0 && StringUtils.equals(askAndTalkList.get(0).getReadingStsDiv(), "0")) {
                TalkReadingStsEntity talkReadingStsEntity = talkReadingStsService.getOne(
                        new QueryWrapper<TalkReadingStsEntity>().eq("message_id", id).eq("deliver_id", stuId).eq("del_flg", 0));
                if (talkReadingStsEntity != null && StringUtils.equals(talkReadingStsEntity.getReadingStsDiv(), "0")) {
                    talkReadingStsEntity.setReadingStsDiv("1");
                    talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    talkReadingStsEntity.setUpdUsrId(stuId);
                    talkReadingStsService.updateById(talkReadingStsEntity);
                }
            }
        }else {
            F11013Dto f11013Dto = f11013Service.init(id);
            if(f11013Dto != null){
                //「閲覧状況区分」が「未読」の場合、閲覧状況の更新を行う
                StuEventApplyStsEntity dto = stuEventApplyStsService.getOne(new QueryWrapper<StuEventApplyStsEntity>().eq("id", f11013Dto.getId()).eq("del_flg",0));
                dto.setReadingStsDiv("1");
                dto.setReadTime(DateUtils.getSysTimestamp());
                dto.setUpdDatime(DateUtils.getSysTimestamp());
                dto.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
                stuEventApplyStsService.saveOrUpdate(dto);
            }
        }
        //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",stuId).eq("del_flg",0));
        //delete  at 2021/08/19 for V9.02 by NWT LiGX END
        //add at 2021/08/19 for V9.02 by NWT LiGX START
        List<String> usrIds = Arrays.asList(stuId.split(","));
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/19 for V9.02 by NWT LiGX END
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",stuId).eq("del_flg",0));
        List<String> deviceIdList = new ArrayList<>();
        //modify at 2021/08/19 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                F09005DeviceDto deviceId = new F09005DeviceDto();
                deviceId.setStuId(stuId);
                deviceId.setStuname(mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                deviceId.setId(mstDeviceTokenEntity.getDeviceId());
                deviceId.setUnreadcount(f11010Service.getUnreadCount(stuId));
                deviceIdList.add(JSON.toJSONString(deviceId));
            }
        }
        //modify at 2021/08/19 for V9.02 by NWT LiGX END
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment(null);
            sendMessageDto.setDeviceList(deviceIdList);
            // メッセージに含まれる画像のリンク
            sendMessageDto.setImgUrl(null);
            //コードマスタより取得する、ブランドごとに自由に設定できる。
            sendMessageDto.setMessage(null);
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("msgId",id);
            map.put("msgType",GakkenConstant.sendMsgTypeUnread);
            sendMessageDto.setParams(JSON.toJSONString(map));
            //デフォルト値5にする
            sendMessageDto.setPriority(1);
            //メッセージのタイトル
            sendMessageDto.setTitle(Constant.sendTitleUnread);
            sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
            //アプリケーションを識別できる唯一秘密鍵で
            sendMessageDto.setToken(token);
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