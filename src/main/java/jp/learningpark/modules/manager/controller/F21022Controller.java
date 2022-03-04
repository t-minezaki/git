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
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.StuComplimentMstEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuComplimentMstService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.manager.dto.F21022Dto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21022Service;
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
 * <p>ほめポイント登録画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/12 : lyh: 新規<br />
 * @version 6.0
 */
@RestController
@RequestMapping("/manager/F21022")
public class F21022Controller {
    /**
     * f21022Service
     */
    @Autowired
    F21022Service f21022Service;
    /**
     * コードマスタ_明細Service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * お知らせService
     */
    @Autowired
    MstNoticeService mstNoticeService;
    /**
     * 褒めポイント管理Service
     */
    @Autowired
    StuComplimentMstService stuComplimentMstService;
    /**
     * 保護者お知らせ閲覧状況Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;
    /**
     * 生徒基本マスタService
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * デバイスTOKEN管理 Service
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
     * 通知アプリ連携API Service
     */
    @Autowired
    NoticeApiService noticeApiService;
    @Autowired
    CommonService commonService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * F30112Service
     */
    @Autowired
    private F30112Service f30112Service;

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer limit,Integer page,String stuId){
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //システム日付
        Date date = new Date();
        String guardId = "";
        //システム日付-60
        Date startDate = DateUtils.addDays(date, -60);
        String complimentDt = DateUtils.format(startDate, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        //褒めポイント管理、お知らせ、保護者お知らせ閲覧状況、コートマスターから、情報を取得し、画面で表示する。
        List<F21022Dto> F21022List = f21022Service.init(orgId,complimentDt,stuId, limit, (page - 1) * limit);
        Integer count = f21022Service.getCount(orgId,complimentDt,stuId);
        if (stuId != null){
            MstStuEntity student = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",stuId).eq("del_flg",0));
            //画面．保護者ID
            guardId= student.getGuardId();
        }
//        if (F21022List.size() ==0){
//            return R.error(MessageUtils.getMessage("MSGCOMN0017", ""));
//        }
        return R.ok().put("page", new PageUtils(F21022List,count,limit,page)).put("guardId",guardId);
    }

    @RequestMapping(value = "/submit",method = RequestMethod.GET)
    public R submit(String stuId,String complimentDiv,String content){
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        MstStuEntity student = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",stuId).eq("del_flg",0));
        //画面．保護者ID
        String guardId = student.getGuardId();
        //システム日付
        Date date = new Date();
        //掲載予定開始日時＋60
        Date startDate = DateUtils.addDays(date, +60);
        String complimentDt = DateUtils.format(startDate, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(complimentDt);
        //褒めポイント管理先登録
        StuComplimentMstEntity stuComplimentMstEntity = new StuComplimentMstEntity();
        //セッションデータ．組織ID
        stuComplimentMstEntity.setOrgId(orgId);
        //セッションデータ．生徒ID
        stuComplimentMstEntity.setStuId(stuId);
        //システム日時
        stuComplimentMstEntity.setComplimentDt(DateUtils.getSysTimestamp());
        //画面．スタンプコート
        stuComplimentMstEntity.setComplimentDiv(complimentDiv);
        //画面．入力した内容
        stuComplimentMstEntity.setComplimentCont(content);
        //NULL
        stuComplimentMstEntity.setTransmissionFlg(null);
        //作成者
        stuComplimentMstEntity.setCreatorId(userId);
        //システム日時
        stuComplimentMstEntity.setCretDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        stuComplimentMstEntity.setCretUsrId(userId);
        //システム日時
        stuComplimentMstEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        stuComplimentMstEntity.setUpdUsrId(userId);
        //「0：有効」
        stuComplimentMstEntity.setDelFlg(0);
        stuComplimentMstService.save(stuComplimentMstEntity);
        Integer stuComplimentId = stuComplimentMstEntity.getId();
        //お知らせ登録
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        //セッションデータ．組織ID
        mstNoticeEntity.setOrgId(orgId);
        //ほめポイント
        //2020/11/13 liyuhuan modify start
        mstNoticeEntity.setNoticeTitle("教室からメッセージが届きました");
        //2020/11/13 liyuhuan modify end
        //褒めポイント管理連番で採番した．ID
        mstNoticeEntity.setNoticeCont(stuComplimentId.toString());
        //「7:褒めポイント」
        mstNoticeEntity.setNoticeTypeDiv("7");
        //1：普通
        mstNoticeEntity.setNoticeLevelDiv("1");
        //システム日付
        mstNoticeEntity.setPubPlanStartDt(DateUtils.getSysTimestamp());
        //掲載予定開始日時＋60
        mstNoticeEntity.setPubPlanEndDt(ts);
        //NULL
        mstNoticeEntity.setAllDeliverFlg(null);
        //NULL
        mstNoticeEntity.setAttachFilePath(null);
        //NULL
        mstNoticeEntity.setTitleImgPath(null);
        //システム日時
        mstNoticeEntity.setCretDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        mstNoticeEntity.setCretUsrId(userId);
        //システム日時
        mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        mstNoticeEntity.setUpdUsrId(userId);
        //「0：有効」
        mstNoticeEntity.setDelFlg(0);
        mstNoticeService.save(mstNoticeEntity);
        Integer mstNoticeId = mstNoticeEntity.getId();
        //保護者お知らせ閲覧状況登録登録
        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        //セッションデータ．組織ID
        guardReadingStsEntity.setOrgId(orgId);
        //DBセット「お知らせ登録.ID」
        guardReadingStsEntity.setNoticeId(mstNoticeId);
        //画面．保護者ID
        guardReadingStsEntity.setGuardId(guardId);
        //POP画面から戻り値、生徒ID
        guardReadingStsEntity.setStuId(stuId);
        //「0:未読
        guardReadingStsEntity.setReadingStsDiv("0");
        //システム日時
        guardReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        guardReadingStsEntity.setCretUsrId(userId);
        //システム日時
        guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //ログインユーザＩＤ
        guardReadingStsEntity.setUpdUsrId(userId);
        //「0：有効」
        guardReadingStsEntity.setDelFlg(0);
        guardReadingStsService.save(guardReadingStsEntity);
        // 通知プッシュ： 受信先デバイスIDの集合、必須項目
        List<String> deviceIdList = new ArrayList<>();
        //get deviceId by guard id(after id)
        //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",guardId).eq("del_flg",0));
        //delete  at 2021/08/18 for V9.02 by NWT LiGX END
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        List<String> usrIds = Arrays.asList(guardId.split(","));
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        //modify at 2021/08/17 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", mstDeviceTokenEntity.getDeviceId());
                Integer unReadCount = commonService.pushUnreadCount(guardId);
                map.put("unreadcount", unReadCount);
                map.put("stuId", stuId);
                String stuName = student.getFlnmNm() + " " + student.getFlnmLnm();
                map.put("stuname", stuName);
                //add deviceId to deviceIdList by forサイクル
                deviceIdList.add(JSON.toJSONString(map));
            }
        }
        //modify at 2021/08/17 for V9.02 by NWT LiGX END
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            //通知プッシュ;マナミル側の各関連機能のところでメッセージを送信する時、該当共通部品を利用する。
            String message = noticeApiService.getMessageDetail("0");
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
            sendMessageDto.setMessage(message);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType", Constant.sendMsgTypestuCompliment);
            sendMessageDto.setParams(JSON.toJSONString(params));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(mstNoticeEntity.getPubPlanStartDt().getTime()));
            sendMessageDto.setTitle(mstNoticeEntity.getNoticeTitle());
            sendMessageDto.setToken(commonService.getToken());
            sendMessageDto.setDeviceList(deviceIdList);
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