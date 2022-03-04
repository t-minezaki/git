package jp.learningpark.modules.pop.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.guard.dao.F30411Dao;
import jp.learningpark.modules.guard.dto.F30411Dto;
import jp.learningpark.modules.guard.service.F30411Service;
import jp.learningpark.modules.manager.dao.F21017Dao;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.pop.dto.F21016SchyDto;
import jp.learningpark.modules.pop.service.F21016Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 出席簿用代理登録
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/21 ： NWT)hxl ： 新規作成
 * @date 2019/11/21 10:15
 */
@RestController
@RequestMapping("/pop/F21016")
public class F21016Controller extends Throwable {
    /**
     * lateAbsHstService
     */
    @Autowired
    LateAbsHstService lateAbsHstService;
    /**
     * お知らせService
     */
    @Autowired
    MstNoticeService mstNoticeService;
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
     * mstStuService
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * f30411Service
     */
    @Autowired
    F30411Service f30411Service;
    /**
     * f21016Service
     */
    @Autowired
    F21016Service f21016Service;

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

    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * mstGuardService
     */
    @Autowired
    MstGuardService mstGuardService;

    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;
    @Autowired
    CommonService commonService;
    /**
     * F30411Dao
     */
    @Autowired
    F30411Dao f30411Dao;
    /**
     * mstUsrService
     */
    @Autowired
    MstUsrService mstUsrService;
    /**
     * mstUsrService
     */
    @Autowired
    F21017Dao f21017Dao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        //学年区分
        List<F21016SchyDto> schys = f21016Service.getSchy();
        //休暇タイプ
        List<F30411Dto> contents = f30411Service.getContents();
        //休暇理由
        List<F30411Dto> reasons = f30411Service.getReasons();
        return R.ok().put("reasons", reasons).put("schys", schys).put("contents", contents);
    }

    /**
     * ファジークエリ保護者
     *
     * @param guardName 画面．検索条件．姓名
     * @return
     */
    @RequestMapping(value = "/getGuardList", method = RequestMethod.GET)
    public R guardInfo(String guardName) {
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        String orgId = userEntity.getOrgId();
        return f21016Service.getGuardList(orgId, guardName);
    }

    /**
     * ファジークエリ生徒
     *
     * @param schy    学年
     * @param stuName 画面．検索条件．姓名
     * @return
     */
    @RequestMapping(value = "/getStudentList", method = RequestMethod.GET)
    public R studentInfo(String schy, String stuName) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return f21016Service.getStudentList(orgId, schy, stuName);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public R submit(String stuId, String type, Integer lateTime
            , String lateDay, String reason, String remark) {
        //タイムスタンプ
        Timestamp cretDate = DateUtils.getSysTimestamp();
        int compare = lateDay.compareTo(DateUtils.format(cretDate, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        if (compare < 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0024", "対象日", "システム時間-1年"));
        }

        String tgtTime = lateDay + " " + StringUtils.split(StringUtils.defaultString(cretDate)," ")[1];
        Date dates = DateUtils.parse(tgtTime.replace("/","-"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        Timestamp date = DateUtils.toTimestamp(dates);
        //ログインユーザー
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //組織ID
        String orgId = userEntity.getOrgId();
        //ログインユーザーID
        String usrId = userEntity.getUsrId();
        //学生エンティティ
        MstStuEntity stu = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId));
        //保護者ID
        if (StringUtils.isEmpty(stu.getGuardId())){
            return R.error(MessageUtils.getMessage("MSGCOMN0151"));
        }
        String guardId = stu.getGuardId();
        //組織エンティティ
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).eq("del_flg", 0));
//        Timestamp date = DateUtils.toTimestamp(DateUtils.parse(lateDay, Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM));

        //データを入力する
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        mstNoticeEntity.setOrgId(orgId);
        mstNoticeEntity.setNoticeTitle("遅刻・欠席連絡確認");
        mstNoticeEntity.setNoticeCont("ご連絡ありがとうございます。既に確認しました。");
        mstNoticeEntity.setNoticeTypeDiv("3");
        mstNoticeEntity.setNoticeLevelDiv("1");
        mstNoticeEntity.setPubPlanStartDt(cretDate);
        mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(cretDate, 7)));
        mstNoticeEntity.setCretDatime(cretDate);
        mstNoticeEntity.setCretUsrId(usrId);
        mstNoticeEntity.setUpdDatime(cretDate);
        mstNoticeEntity.setUpdUsrId(usrId);
        mstNoticeEntity.setDelFlg(0);
        mstNoticeService.save(mstNoticeEntity);
        //お知らせ.ID
        Integer noticeId = mstNoticeEntity.getId();

        MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
        //管理フラグ
        String mgrFlg = mstOrgEntity.getMgrFlg();
        //データを入力する
        mstNoticeDeliverEntity.setOrgId(orgId);
        mstNoticeDeliverEntity.setNoticeId(noticeId);
        mstNoticeDeliverEntity.setMgrFlg(mgrFlg);
        mstNoticeDeliverEntity.setStuId(stuId);
        mstNoticeDeliverEntity.setGuardId(guardId);
        mstNoticeDeliverEntity.setCretDatime(cretDate);
        mstNoticeDeliverEntity.setCretUsrId(usrId);
        mstNoticeDeliverEntity.setUpdDatime(cretDate);
        mstNoticeDeliverEntity.setUpdUsrId(usrId);
        mstNoticeDeliverEntity.setDelFlg(0);
        mstNoticeDeliverService.save(mstNoticeDeliverEntity);
        //保護者お知らせ閲覧状況
        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        //データを入力する
        guardReadingStsEntity.setOrgId(orgId);
        guardReadingStsEntity.setNoticeId(noticeId);
        guardReadingStsEntity.setGuardId(guardId);
        guardReadingStsEntity.setStuId(stuId);
        guardReadingStsEntity.setReadingStsDiv("0");
        guardReadingStsEntity.setCretDatime(cretDate);
        guardReadingStsEntity.setCretUsrId(usrId);
        guardReadingStsEntity.setUpdDatime(cretDate);
        guardReadingStsEntity.setUpdUsrId(usrId);
        guardReadingStsEntity.setDelFlg(0);
        guardReadingStsService.save(guardReadingStsEntity);

        LateAbsHstEntity lateAbsHstEntity = new LateAbsHstEntity();
        //データを入力する
        lateAbsHstEntity.setOrgId(orgId);
        lateAbsHstEntity.setGuardId(guardId);
        lateAbsHstEntity.setStuId(stuId);
        lateAbsHstEntity.setAbsSts(type);
        lateAbsHstEntity.setTgtYmd(date);
        lateAbsHstEntity.setNoticeId(noticeId);
        lateAbsHstEntity.setCorrspdSts("0");
         lateAbsHstEntity.setAbsReason(reason);
        lateAbsHstEntity.setBikou(remark);
        lateAbsHstEntity.setCretDatime(cretDate);
        lateAbsHstEntity.setCretUsrId(usrId);
        lateAbsHstEntity.setUpdDatime(cretDate);
        lateAbsHstEntity.setUpdUsrId(usrId);
        lateAbsHstEntity.setDelFlg(0);
        if (lateAbsHstService.save(lateAbsHstEntity)) {
            String message = noticeApiService.getMessageDetail("1");
            List<String> deviceIdList = new ArrayList<>();
            MstStuEntity mstStuEntity =  mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id",stuId));
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
            //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//            MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().in("usr_id",mstStuEntity.getGuardId()));
            //delete  at 2021/08/19 for V9.02 by NWT LiGX END
            //add at 2021/08/19 for V9.02 by NWT LiGX START
            List<String> userIdList = Arrays.asList(mstStuEntity.getGuardId().split(","));
            Map<String, Object> deviceUserId = new HashMap<>();
            deviceUserId.put("userIdList",userIdList);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
            //add at 2021/08/19 for V9.02 by NWT LiGX END
            //modify at 2021/08/19 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", mstDeviceTokenEntity.getDeviceId());
                    //未読件数を取得する 2020/12/01 modify yang start--
                    Integer unReadCount = commonService.pushUnreadCount(mstStuEntity.getGuardId());
                    //未読件数を取得する 2020/12/01 modify yang end--
                    map.put("unreadcount", unReadCount);
                    map.put("stuId", stuId);
                    map.put("stuname", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                    //add deviceId to deviceIdList by forサイクル
                    deviceIdList.add(JSON.toJSONString(map));
                }
            }
            if (CollectionUtils.isNotEmpty(deviceIdList)){
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
            List<String> deviceIdManaList = new ArrayList<>();
            List<String> usrIds = f30411Dao.getUsrIds(stuId, ShiroUtils.getUserEntity().getOrgId());
            //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = mstDeviceTokenService.list(new QueryWrapper<MstDeviceTokenEntity>().in("usr_id",usrIds).eq("del_flg",0));
            //delete  at 2021/08/19 for V9.02 by NWT LiGX END
            //add at 2021/08/19 for V9.02 by NWT LiGX START
            Map<String, Object> eviceIdManaId = new HashMap<>();
            eviceIdManaId.put("userIdList",usrIds);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityManaList = noticeApiDao.selectDeviceInfo(eviceIdManaId);
            //add at 2021/08/19 for V9.02 by NWT LiGX END
            //modify at 2021/08/19 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityManaList)){
                for (MstDeviceTokenEntity dto : mstDeviceTokenEntityManaList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",dto.getDeviceId());
                    MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",dto.getUsrId()).eq("del_flg",0));
                    Integer unReadCount = f21017Dao.getUnreadCount(mstUsrEntity.getRoleDiv().trim(),mstUsrEntity.getUsrId(),mstUsrEntity.getOrgId(),DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                    map.put("unreadcount",unReadCount);
                    map.put("stuId",stuId);
                    map.put("stuname",mstStuEntity.getFlnmNm()+" "+mstStuEntity.getFlnmLnm());
                    //add deviceId to deviceIdList by forサイクル
                    deviceIdManaList.add(JSON.toJSONString(map));
                }
            }
            //modify at 2021/08/19 for V9.02 by NWT LiGX END
            if (CollectionUtils.isNotEmpty(deviceIdManaList)){
                sendMessageDto.setDeviceList(deviceIdManaList);
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
        } else {
            return R.error();
        }
    }
}
