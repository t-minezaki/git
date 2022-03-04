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
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F21024Dto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21017Service;
import jp.learningpark.modules.manager.service.F21024Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>出欠席連絡一覧（スマホ）</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/25 : zpa: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F21024")
public class F21024Controller {
    @Autowired
    F21024Service f21024Service;
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;
    @Autowired
    MstUsrService mstUsrService;
    @Autowired
    F21017Service f21017Service;
    @Autowired
    CommonService commonService;
    @Autowired
    NoticeApiService noticeApiService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param limit
     * @param page
     * @param corrspdSts
     * @return
     */
    @RequestMapping(value = "/init" , method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String corrspdSts){
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        //組織IDの取得
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザのロール
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        //ログイン当日（YYYYMMDD）
        String date = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        // クエリーデータセットを取得する
        List<F21024Dto> f21024DtoList = f21024Service.select(userId,orgId,corrspdSts,date,roleDiv,GakkenConstant.NOTICE_PAGE_SIZE,(limit - GakkenConstant.NOTICE_PAGE_SIZE));
        Integer count = f21024Service.getCount(userId,orgId,corrspdSts,date,roleDiv,null,null);
        //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity =  mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",userId).eq("del_flg",0));
        //delete  at 2021/08/18 for V9.02 by NWT LiGX END
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        List<String> usrIds = Arrays.asList(userId.split(","));
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        List<String> deviceListMana = new ArrayList<>();
        //modify at 2021/08/17 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", mstDeviceTokenEntity.getDeviceId());
                MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", mstDeviceTokenEntity.getUsrId()).eq("del_flg", 0));
                Integer unReadCount = f21017Service.getUnreadCount(mstUsrEntity.getRoleDiv().trim(), mstUsrEntity.getUsrId(), mstUsrEntity.getOrgId(), DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                map.put("unreadcount", unReadCount);
                map.put("stuId", "");
                map.put("stuname", "");
                //add deviceId to deviceIdList by forサイクル
                deviceListMana.add(JSON.toJSONString(map));
            }
        }
        //modify at 2021/08/17 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(deviceListMana)){
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment(null);
            sendMessageDto.setImgUrl(null);
            sendMessageDto.setMessage(null);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",null);
            params.put("msgType", Constant.sendMsgTypeUnread);
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
        return R.ok().put("page", new PageUtils(f21024DtoList,count,limit, page));
    }
}
