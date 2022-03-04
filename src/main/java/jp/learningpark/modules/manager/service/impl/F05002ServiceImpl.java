/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstNoticeDeliverService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dao.F05002Dao;
import jp.learningpark.modules.manager.dto.*;
import jp.learningpark.modules.manager.service.F05002Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F05002 知らせ新規画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/24 : hujiaxing : mod<br />
 * @version 1.0
 */
@Service
@Transactional
public class F05002ServiceImpl implements F05002Service {
    /**
     * F05002 知らせ新規画面 Dao
     */
    @Autowired
    F05002Dao f05002Dao;
    /**
     * 通知プッシュ
     */
    @Autowired
    NoticeApiService noticeApiService;

    /**
     *　デバイストーケン　Service
     */
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;

    /**
     * お知らせマスター Dao
     */
    @Autowired
    private MstNoticeDao mstNoticeDao;

    /**
     * お知らせ配信先マスター  Service
     */
    @Autowired
    private MstNoticeDeliverService mstNoticeDeliverService;

    /**
     * 保護者お知らせ閲覧状況　Service
     */
    @Autowired
    private GuardReadingStsService guardReadingStsService;

    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * デバイストーケン noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END

    /**
     * 共通処理　Service
     */
    @Autowired
    private CommonService commonService;

    @Value("${ans-url.token}")
    String token;

    @Autowired
    MstStuService mstStuService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * <p>該当組織対応する生徒、保護者リストを取得する</p>
     *
     * @param stuIdList student id List
     * @return
     */
    @Override
    public List<F05002Dto> getStuAndGuardList(List<String> stuIdList) {
        return f05002Dao.selectStuAndGuardList(stuIdList);
    }

    /**
     * <p>生徒IDListによるdtotListの取得</p>
     * <p>
     * 2019/06/24 hujiaxing: mod
     *
     * @param stuIdList student Id List
     * @return
     */
    @Override
    public List<F05002DtoIn> selectStuByIdList(List<String> stuIdList) {
        return f05002Dao.selectStuByIdList(stuIdList);
    }

    /**
     * 登録処理
     * 2019/06/24 hujiaxing: mod
     *
     * @param dto
     * @param noticeCont 　ニュースの内容
     * @param orgId 組織ID
     *
     * update NWT文　2020/12/1　一括挿入ロジックを変更
     * @return
     */
    @Override
    public MstNoticeEntity doInsert(F05002Dto dto, String noticeCont, String orgId) throws UnsupportedOperationException {
        try {
            dto.setNoticeTitle(URLDecoder.decode(dto.getNoticeTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();

        //画面を元に、お知らせマスタへ登録する。
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        //組織ID
        mstNoticeEntity.setOrgId(orgId);
        //お知らせタイトル
        mstNoticeEntity.setNoticeTitle(dto.getNoticeTitle());
        //お知らせ内容
        mstNoticeEntity.setNoticeCont(noticeCont);
        //お知らせタップ区分
        mstNoticeEntity.setNoticeTypeDiv("2");
        //お知らせレベル区分
        mstNoticeEntity.setNoticeLevelDiv(dto.getNoticeLevelDiv());
        //掲載予定開始日時
        mstNoticeEntity.setPubPlanStartDt(dto.getPubPlanStartDt());
        //掲載予定終了日時
        mstNoticeEntity.setPubPlanEndDt(dto.getPubPlanEndDt());
        //全体配信フラグ
        mstNoticeEntity.setAllDeliverFlg(dto.getAllDeliverFlg());
        //添付ファイルパス
        mstNoticeEntity.setAttachFilePath(dto.getAttachFilePath());
        //タイトル画像パス
        mstNoticeEntity.setTitleImgPath(dto.getTitleImgPath());
        //作成日時
        mstNoticeEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstNoticeEntity.setCretUsrId(userId);
        //更新日時
        mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstNoticeEntity.setUpdUsrId(userId);
        //削除フラグ
        mstNoticeEntity.setDelFlg(0);
        try {
            mstNoticeDao.insert(mstNoticeEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        List<MstNoticeDeliverEntity> deliverEntities = new ArrayList<>();
        List<GuardReadingStsEntity> readingStsEntities = new ArrayList<>();
        for (int i = 0; i < dto.getStuList().size(); i++) {
            if (dto.getStuList().get(i).getGuardId() != null && !dto.getStuList().get(i).getGuardId().isEmpty()) {
                MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
                String currentOrgId = dto.getStuList().get(i).getOrgId();//2019/06/24 hujiaxing： modify
                //組織ID
                mstNoticeDeliverEntity.setOrgId(currentOrgId);
                //お知らせID
                mstNoticeDeliverEntity.setNoticeId(mstNoticeEntity.getId());
                //生徒ID
                mstNoticeDeliverEntity.setStuId(dto.getStuList().get(i).getStuId());
                //保護者ID
                //2019/06/24 hujiaxing： modify
                mstNoticeDeliverEntity.setGuardId(dto.getStuList().get(i).getGuardId());
                //管理フラグ
                mstNoticeDeliverEntity.setMgrFlg(null);
                //作成日時
                mstNoticeDeliverEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザー
                mstNoticeDeliverEntity.setCretUsrId(userId);
                //更新日時
                mstNoticeDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザー
                mstNoticeDeliverEntity.setUpdUsrId(userId);
                //削除フラグ
                mstNoticeDeliverEntity.setDelFlg(0);
                deliverEntities.add(mstNoticeDeliverEntity);

                GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
                //組織ID
                guardReadingStsEntity.setOrgId(dto.getStuList().get(i).getOrgId());
                //お知らせID
                guardReadingStsEntity.setNoticeId(mstNoticeEntity.getId());
                //2019/06/24 hujiaxing： modify
                guardReadingStsEntity.setGuardId(dto.getStuList().get(i).getGuardId());
                //生徒ID
                guardReadingStsEntity.setStuId(dto.getStuList().get(i).getStuId());
                //閲覧状況区分
                guardReadingStsEntity.setReadingStsDiv("0");
                //作成日時
                guardReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザー
                guardReadingStsEntity.setCretUsrId(userId);
                //更新日時
                guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザー
                guardReadingStsEntity.setUpdUsrId(userId);
                //削除
                guardReadingStsEntity.setDelFlg(0);
                readingStsEntities.add(guardReadingStsEntity);
            }
        }
        try {
            //お知らせ配信先一括挿入
            mstNoticeDeliverService.saveBatch(deliverEntities);
            //保護者お知らせ閲覧状況一括挿入
            guardReadingStsService.saveBatch(readingStsEntities);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //put->メッセージ送信（messageSend）
        return mstNoticeEntity;
    }
    @Override
    public R sendMessage(pushMsgReceives receiver, MstNoticeEntity mstNoticeEntity){
        List<String> deviceIdList = new ArrayList<>();
        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
        SendMessageDto sendMessageDto = new SendMessageDto();
        //guardIdでdeviceIdに問い合わせる
        for (int i = 0;i < receiver.getStuId().size();i++){
            //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//            MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",receiver.getReceiverId().get(i)).eq("del_flg",0));
            //delete  at 2021/08/18 for V9.02 by NWT LiGX END
            //add at 2021/08/17 for V9.02 by NWT LiGX START
            List<String> usrIds = Arrays.asList(receiver.getReceiverId().get(i).split(","));
            Map<String, Object> deviceUserId = new HashMap<>();
            deviceUserId.put("userIdList",usrIds);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
            //add at 2021/08/17 for V9.02 by NWT LiGX END
            //modify at 2021/08/17 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                    F09005DeviceDto deviceId = new F09005DeviceDto();
                    deviceId.setId(mstDeviceTokenEntity.getDeviceId());
                    deviceId.setStuId(receiver.getStuId().get(i));
                    //未読件数を取得する
                    Integer unreadCount = commonService.pushUnreadCount(mstDeviceTokenEntity.getUsrId());
                    deviceId.setUnreadcount(unreadCount);
                    MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", receiver.getStuId().get(i)).eq("del_flg", 0));
                    String stuName = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                    deviceId.setStuname(stuName);
                    deviceIdList.add(JSON.toJSONString(deviceId));
                }
            }
        }
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            sendMessageDto.setComment(receiver.getComment());
            sendMessageDto.setImgUrl(mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
            sendMessageDto.setDeviceList(deviceIdList);
            sendMessageDto.setMessage(noticeApiService.getMessageDetail(receiver.getMessageCode()));
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("msgId",mstNoticeEntity.getId());
            map.put("msgType",receiver.getMsgType());
            sendMessageDto.setParams(JSON.toJSONString(map));
            sendMessageDto.setPriority(receiver.getPriority());
            sendMessageDto.setSendTime(Long.toString(receiver.getSendTime()));
            sendMessageDto.setTitle(mstNoticeEntity.getNoticeTitle());
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
