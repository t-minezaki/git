package jp.learningpark.modules.pop.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.pop.dto.F40026Dto;
import jp.learningpark.modules.pop.service.F40026Service;
import jp.learningpark.modules.student.dao.F11010Dao;
import org.apache.commons.collections4.CollectionUtils;
import org.opensaml.xml.signature.Q;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>通知プッシュ失敗一覧画面（共通）</p >
 *
 * @author NWT : yyb <br />
 * 変更履歴 <br />
 * 2020/07/17 : yyb: 新規<br />
 * @version 7.1
 */
@RequestMapping("/manager/F40026")
@RestController
public class F40026Controller {
    /*
     *通知プッシュ失敗一覧画面（共通)Service
     */
    @Autowired
    private F40026Service f40026Service;
    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;
    /**
     * 機器Service
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
     * 通知Service
     */
    @Autowired
    private NoticeApiService noticeApiService;

    /**
     * F30112Dao
     */
    @Autowired
    private F30112Dao f30112Dao;

    /**
     * F11010Dao
     */
    @Autowired
    private F11010Dao f11010Dao;
    /**
     * 生徒 Service
     */
    @Autowired
    private MstStuService mstStuService;

    /**
     * プッシュ失敗データService
     */
    @Autowired
    private PushErrDatService pushErrDatService;
    /**
     * 用户 Service
     */
    @Autowired
    private MstUsrService mstUsrService;

    @Autowired
    private CommonService commonService;

    @Value("${ans-url.token}")
    String token;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value="/init",method = RequestMethod.GET)
    public R init(Integer msgId,String messageTypeDiv){
        String orgNm = "";
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id",ShiroUtils.getUserEntity().getOrgId())));
        if (org != null) {
            orgNm = org.getOrgNm();
        }
        //メッセージ情報を得る
        F40026Dto info = f40026Service.getInfo(msgId,messageTypeDiv);
        return R.ok().put("info",info).put("orgId", ShiroUtils.getUserEntity().getOrgId()).put("orgNm", orgNm).put("messageTypeDiv",messageTypeDiv);
    }

    @RequestMapping(value="/getFailedUserList",method = RequestMethod.GET)
    public R getFailedUserList(String msgTypeDiv,Integer msgId,String orgId,Integer limit, Integer page)
    {
        Integer offset = (page-1)*limit;
        //送信失敗者リストを取得する
        Integer count = f40026Service.failedUserList(msgTypeDiv, msgId, StringUtils.isNotBlank(orgId)?orgId:ShiroUtils.getUserEntity().getOrgId(),null,null).size();
        List<F40026Dto> failedUserList = f40026Service.failedUserList(msgTypeDiv, msgId, StringUtils.isNotBlank(orgId)?orgId:ShiroUtils.getUserEntity().getOrgId(),limit,offset);
        F40026Dto f40026Dto = new F40026Dto();
        f40026Dto.setRoleDiv("3 ");
        return R.ok().put("page", new PageUtils(failedUserList,count, limit, page));
    }

    @RequestMapping(value="/sendMessage",method = RequestMethod.GET)
    // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang START
    public R deliver(Integer msgId,String imgUrl,Long sendTime,String title,String messageTypeDiv,String pushErrDatIdList){
        List<Integer> errorIdList = (List<Integer>) JSON.parse(pushErrDatIdList);
        List<F40026Dto> deliverIdList = f40026Service.selectDeliverInfo(errorIdList);
        List<String> deviceList = new ArrayList<>();
        for (F40026Dto dto:deliverIdList) {
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = mstDeviceTokenService.list(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",
                    dto.getDeliverId()).eq("device_id",dto.getDeviceId()).eq("del_flg",0));
            //add at 2021/08/19 for V9.02 by NWT LiGX END
            //modify at 2021/08/19 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", mstDeviceTokenEntity.getDeviceId());
                    //unreadCount
                    map.put("unreadcount", StringUtils.equals("3",dto.getRoleDiv())?commonService.pushUnreadCount(dto.getDeliverId()):f11010Dao.getUnreadCount(dto.getDeliverId()));
                    map.put("stuId", dto.getStuId());
                    //stuname
                    map.put("stuname",dto.getStuNm());
                    deviceList.add(JSON.toJSONString(map));
                }
            }
        }
        // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang END
        if (CollectionUtils.isNotEmpty(deviceList)){
            //メッセージ内容の取得
            String message = noticeApiService.getMessageDetail("0");
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(imgUrl);
            sendMessageDto.setDeviceList(deviceList);
            sendMessageDto.setMessage(message);
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("msgId",msgId);
            map.put("msgType",messageTypeDiv);
            sendMessageDto.setParams(JSON.toJSONString(map));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(sendTime));
            sendMessageDto.setTitle(title);
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
        for (int i=0;i<errorIdList.size();i++)
        {
            // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang START
            f40026Service.errDataUpdate(errorIdList.get(i));
            // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang END
        }
        return R.ok();
    }

}
