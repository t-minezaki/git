
package jp.learningpark.modules.guard.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30411Dao;
import jp.learningpark.modules.guard.dto.F30411Dto;
import jp.learningpark.modules.guard.service.F30411Service;
import jp.learningpark.modules.manager.dao.F21017Dao;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/18 ： NWT)hxl ： 新規作成
 * @date 2019/11/18 9:58
 */
@Service
public class F30411ServiceImpl implements F30411Service {
    /**
     * F30411Dao
     */
    @Autowired
    F30411Dao f30411Dao;

    @Autowired
    MstOrgDao mstOrgDao;
    @Autowired
    MstUsrDao mstUsrDao;
    @Autowired
    F21017Dao f21017Dao;

    @Autowired
    MstNoticeDao mstNoticeDao;
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;
    @Autowired
    GuardReadingStsDao guardReadingStsDao;
    @Autowired
    NoticeApiService noticeApiService;
    @Autowired
    LateAbsHstDao lateAbsHstDao;
    @Autowired
    MstStuDao mstStuDao;
    @Autowired
    MstDeviceTokenDao mstDeviceTokenDao;
    @Autowired
    CommonService commonService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 遅れる理由をすべて入手する
     * @return
     */
    @Override
    public List<F30411Dto> getReasons() {
        return f30411Dao.getReasons();
    }

    /**
     * 休暇タイプを取得する
     * @return
     */
    @Override
    public List<F30411Dto> getContents() {
        return f30411Dao.getContents();
    }

    /**
     * ボリュームusr_idを取得する
     */
    @Override
    public List<String> getUsrIds(String stuId,String orgId){
        return f30411Dao.getUsrIds(stuId,orgId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R submit(String type, Integer lateTime, String lateDay, String reason, String remark) {
        //タイムスタンプ
        Timestamp cretDate = DateUtils.getSysTimestamp();
//        int compare = lateDay.compareTo(DateUtils.format(cretDate, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH));
//        if (compare < 0) {
//            return R.error(MessageUtils.getMessage("MSGCOMN0024", "対象日", "システム時間-1年"));
//        }

        String tgtTime = lateDay + " " + StringUtils.split(StringUtils.defaultString(cretDate)," ")[1];
        Date dates = DateUtils.parse(tgtTime.replace("/","-"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_BARS);
        Timestamp date = DateUtils.toTimestamp(dates);

        String stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        if (StringUtils.isEmpty(stuId)) {
            return R.error();
        }
        //ログインユーザー
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        //組織ID
        String orgId = userEntity.getOrgId();
        //ログインユーザーID & 保護者ID
        String usrId = userEntity.getUsrId();
        //組織エンティティ
        MstOrgEntity mstOrgEntity = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).eq("del_flg", 0));

        //データを入力する
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        mstNoticeEntity.setOrgId(orgId);
        mstNoticeEntity.setNoticeTitle("遅刻・欠席連絡確認");
        mstNoticeEntity.setNoticeCont("遅刻・欠席連絡情報を承りました。");
        mstNoticeEntity.setNoticeTypeDiv("3");
        mstNoticeEntity.setNoticeLevelDiv("1");
        mstNoticeEntity.setPubPlanStartDt(cretDate);
        mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(cretDate, 7)));
        mstNoticeEntity.setAllDeliverFlg(null);
        mstNoticeEntity.setAttachFilePath(null);
        mstNoticeEntity.setTitleImgPath(null);
        mstNoticeEntity.setCretDatime(cretDate);
        mstNoticeEntity.setCretUsrId(usrId);
        mstNoticeEntity.setUpdDatime(cretDate);
        mstNoticeEntity.setUpdUsrId(usrId);
        mstNoticeEntity.setDelFlg(0);
        mstNoticeDao.insert(mstNoticeEntity);
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
        mstNoticeDeliverEntity.setGuardId(usrId);
        mstNoticeDeliverEntity.setCretDatime(cretDate);
        mstNoticeDeliverEntity.setCretUsrId(usrId);
        mstNoticeDeliverEntity.setUpdDatime(cretDate);
        mstNoticeDeliverEntity.setUpdUsrId(usrId);
        mstNoticeDeliverEntity.setDelFlg(0);
        mstNoticeDeliverDao.insert(mstNoticeDeliverEntity);
        //保護者お知らせ閲覧状況
        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        //データを入力する
        guardReadingStsEntity.setOrgId(orgId);
        guardReadingStsEntity.setNoticeId(noticeId);
        guardReadingStsEntity.setGuardId(usrId);
        guardReadingStsEntity.setStuId(stuId);
        guardReadingStsEntity.setReadingStsDiv("0");
        guardReadingStsEntity.setCretDatime(cretDate);
        guardReadingStsEntity.setCretUsrId(usrId);
        guardReadingStsEntity.setUpdDatime(cretDate);
        guardReadingStsEntity.setUpdUsrId(usrId);
        guardReadingStsEntity.setDelFlg(0);
        guardReadingStsDao.insert(guardReadingStsEntity);

        //データを入力する
        LateAbsHstEntity lateAbsHstEntity = new LateAbsHstEntity();
        lateAbsHstEntity.setOrgId(orgId);
        lateAbsHstEntity.setGuardId(usrId);
        lateAbsHstEntity.setStuId(stuId);
        lateAbsHstEntity.setAbsSts(type);
//        lateAbsHstEntity.setLateTm(lateTime);
        lateAbsHstEntity.setTgtYmd(date);
        lateAbsHstEntity.setNoticeId(noticeId);
        lateAbsHstEntity.setCorrspdSts("0");
        List<F30411Dto> reasons = f30411Dao.getReasons();
        lateAbsHstEntity.setAbsReason(reason);
        lateAbsHstEntity.setBikou(remark);
        lateAbsHstEntity.setCretDatime(cretDate);
        lateAbsHstEntity.setCretUsrId(usrId);
        lateAbsHstEntity.setUpdDatime(cretDate);
        lateAbsHstEntity.setUpdUsrId(usrId);
        lateAbsHstEntity.setDelFlg(0);
//        MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("guard_id",mstNoticeDeliverEntity.getGuardId()));
        String message = noticeApiService.getMessageDetail("1");
        try{
            lateAbsHstDao.insert(lateAbsHstEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.ok().put("notice",mstNoticeEntity).put("message",message).put("stuId",stuId);
    }

    /**
     *
     * @param stuId
     * @param mstNoticeEntity
     * @return
     */
    @Override
    public R sendMessage(String stuId,MstNoticeEntity mstNoticeEntity){
        List<String> deviceIdList = new ArrayList<>();
        MstStuEntity mstStuEntity =  mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("stu_id",stuId));
        //delete  at 2021/08/17 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntityGuard =  mstDeviceTokenDao.selectOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",mstStuEntity.getGuardId()).eq("del_flg",0));
        //delete  at 2021/08/17 for V9.02 by NWT LiGX END
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        List<String> deviceUsrIds = Arrays.asList(mstStuEntity.getGuardId().split(","));
        Map<String, Object> deviceId = new HashMap<>();
        deviceId.put("userIdList",deviceUsrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityGuard = noticeApiDao.selectDeviceInfo(deviceId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        List<String> deviceListGuard = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityGuard)){
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityGuard){
                Map<String,Object> map = new HashMap<>();
                map.put("id",mstDeviceTokenEntity.getDeviceId());
                //ログイン当日（YYYYMMDD）
                Integer unReadCount = commonService.pushUnreadCount(mstStuEntity.getGuardId());
                map.put("unreadcount",unReadCount);
                map.put("stuId",stuId);
                map.put("stuname",mstStuEntity.getFlnmNm()+" "+mstStuEntity.getFlnmLnm());
                //add deviceId to deviceIdList by forサイクル
                deviceListGuard.add(JSON.toJSONString(map));
            }
        }
        if (CollectionUtils.isNotEmpty(deviceListGuard)){
            String message = noticeApiService.getMessageDetail("1");
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
            sendMessageDto.setMessage(message);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType", Constant.sendMsgTypeLateAbs);
            sendMessageDto.setParams(JSON.toJSONString(params));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(mstNoticeEntity.getPubPlanStartDt().getTime()));
            sendMessageDto.setTitle(mstNoticeEntity.getNoticeTitle());
            sendMessageDto.setToken(commonService.getToken());
            sendMessageDto.setDeviceList(deviceListGuard);
            //通知プッシュの起止時間と処理時間をログで記録する
            Timestamp sTime = DateUtils.getSysTimestamp();
            logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
            noticeApiService.sendMessage(JSON.toJSONString(sendMessageDto));
            Timestamp eTime = DateUtils.getSysTimestamp();
            Long cTime = eTime.getTime() - sTime.getTime();
            logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
            logger.info("プッシュ通知処理時間：<" + cTime + ">");
        }
        List<String> usrIds = f30411Dao.getUsrIds(stuId, ShiroUtils.getUserEntity().getOrgId());
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        Map<String, Object> deviceUserId = new HashMap<>();
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        //delete  at 2021/08/17 for V9.02 by NWT LiGX START
//        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = mstDeviceTokenDao.selectList(new QueryWrapper<MstDeviceTokenEntity>().in("usr_id",usrIds).eq("del_flg",0));
        //delete  at 2021/08/17 for V9.02 by NWT LiGX END
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)){
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList){
                Map<String,Object> map = new HashMap<>();
                map.put("id",mstDeviceTokenEntity.getDeviceId());
                MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",mstDeviceTokenEntity.getUsrId()).eq("del_flg",0));
                //ログイン当日（YYYYMMDD）
                String passDate = DateUtils.format(DateUtils.addMonths(DateUtils.getSysDate(),-1), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                Integer unReadCount = f21017Dao.getUnreadCount(mstUsrEntity.getRoleDiv().trim(),mstUsrEntity.getUsrId(),mstUsrEntity.getOrgId(),passDate);
                map.put("unreadcount",unReadCount);
                map.put("stuId",stuId);
                map.put("stuname",mstStuEntity.getFlnmNm()+" "+mstStuEntity.getFlnmLnm());
                //add deviceId to deviceIdList by forサイクル
                deviceIdList.add(JSON.toJSONString(map));
            }
        }
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            String message = noticeApiService.getMessageDetail("1");
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
            sendMessageDto.setMessage(message);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType", Constant.sendMsgTypeLateAbs);
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
        return R.ok().put("deviceIdList",deviceIdList).put("deviceListGuard",deviceListGuard);
    }

}
