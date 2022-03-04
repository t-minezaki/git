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
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.StuComplimentMstEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuComplimentMstService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.manager.dto.F21020Dto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21020Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>F21020スマホ_褒めポイント登録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/02/14 : wq: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/manager/F21020")
public class F21020Controller {

    /**
     * F21020スマホ_褒めポイント登録画面 Service
     */
    @Autowired
    F21020Service f21020Service;

    /**
     * 生徒マスタ Service
     */
    @Autowired
    MstStuService mstStuService;

    /**
     * 褒めポイント管理 Service
     */
    @Autowired
    StuComplimentMstService stuComplimentMstService;

    /**
     * お知らせマスタ Service
     */
    @Autowired
    MstNoticeService mstNoticeService;

    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;
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
     * F30112Service
     */
    @Autowired
    private F30112Service f30112Service;
    /**
     * NoticeApiService
     */
    @Autowired
    private NoticeApiService noticeApiService;
    @Autowired
    private CommonService commonService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String stuId) {
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        return R.ok().put("stuNm", mstStuEntity.getFlnmKnNm() + " " + mstStuEntity.getFlnmKnLnm());
    }

    /**
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/init2", method = RequestMethod.GET)
    public R init2(String stuId) {
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        return R.ok().put("stuNm", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
    }

    /**
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R getList(String stuId, Integer limit, Integer page) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F21020Dto> f21020Dtos = f21020Service.getInitList(orgId, stuId, limit, (page - 1) * limit);
        int total = f21020Service.getInitListCount(orgId, stuId);
        return R.ok().put("page", new PageUtils(f21020Dtos, total, limit, page));
    }

    /**
     * @param stuId セッション生徒ID
     * @param stampCd 画面選択したスタンプコート
     * @param comment 画面入力した内容
     * @return
     */
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public R sendMail(String stuId, String stampCd, String comment) {
        //NWT 楊 2021/07/28 MANAMIRU1-711 Edit Start
        //画面入力した内容デコード
        try {
            comment = URLDecoder.decode(comment,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //NWT 楊 2021/07/28 MANAMIRU1-711 Edit End
        //褒めポイント管理先登録
        StuComplimentMstEntity stuComplimentMstEntity = new StuComplimentMstEntity();
        //組織ID
        stuComplimentMstEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
        //生徒ID
        stuComplimentMstEntity.setStuId(stuId);
        //褒め日時
        stuComplimentMstEntity.setComplimentDt(DateUtils.getSysTimestamp());
        //褒めスタンプ区分
        stuComplimentMstEntity.setComplimentDiv(stampCd);
        //コメント
        stuComplimentMstEntity.setComplimentCont(comment);
        //送信フラグ
        stuComplimentMstEntity.setTransmissionFlg(null);
        //作成者
        stuComplimentMstEntity.setCreatorId(ShiroUtils.getUserId());
        //作成日時
        stuComplimentMstEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザーID
        stuComplimentMstEntity.setCretUsrId(ShiroUtils.getUserId());
        //更新日時
        stuComplimentMstEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザーID
        stuComplimentMstEntity.setUpdUsrId(ShiroUtils.getUserId());
        //削除フラグ
        stuComplimentMstEntity.setDelFlg(0);

        try {
            stuComplimentMstService.save(stuComplimentMstEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        //お知らせ登録
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        //組織ID
        mstNoticeEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
        //2020/11/13 liyuhuan modify start
        mstNoticeEntity.setNoticeTitle("教室からメッセージが届きました");
        //2020/11/13 liyuhuan modify end
        //お知らせ内容
        mstNoticeEntity.setNoticeCont(StringUtils.defaultString(stuComplimentMstEntity.getId()));
        //お知らせタップ区分
        mstNoticeEntity.setNoticeTypeDiv("7");
        //お知らせレベル区分
        mstNoticeEntity.setNoticeLevelDiv("1");
        //掲載予定開始日時
        mstNoticeEntity.setPubPlanStartDt(DateUtils.getSysTimestamp());
        //掲載予定終了日時
        mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(DateUtils.getSysDate(), 60)));
        //全体配信フラグ
        mstNoticeEntity.setAllDeliverFlg(null);
        //添付ファイルパス
        mstNoticeEntity.setAttachFilePath(null);
        //タイトル画像パス
        mstNoticeEntity.setTitleImgPath(null);
        //作成日時
        mstNoticeEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザーID
        mstNoticeEntity.setCretUsrId(ShiroUtils.getUserId());
        //更新日時
        mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザーID
        mstNoticeEntity.setUpdUsrId(ShiroUtils.getUserId());
        //削除フラグ
        mstNoticeEntity.setDelFlg(0);
        try {
            mstNoticeService.save(mstNoticeEntity);
        } catch (Exception e) {
            e.printStackTrace();
            stuComplimentMstService.removeById(stuComplimentMstEntity.getId());
            return R.error();
        }

        GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        String guardId = mstStuEntity.getGuardId();
        if (guardId == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0093"));
        }
        //組織ID
        guardReadingStsEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
        //お知らせID
        guardReadingStsEntity.setNoticeId(mstNoticeEntity.getId());
        //保護者ID
        guardReadingStsEntity.setGuardId(guardId);
        //生徒ID
        guardReadingStsEntity.setStuId(stuId);
        //閲覧状況区分
        guardReadingStsEntity.setReadingStsDiv("0");
        //作成日時
        guardReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザーID
        guardReadingStsEntity.setCretUsrId(ShiroUtils.getUserId());
        //更新日時
        guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザーID
        guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserId());
        //削除フラグ
        guardReadingStsEntity.setDelFlg(0);
        try {
            guardReadingStsService.save(guardReadingStsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            stuComplimentMstService.removeById(stuComplimentMstEntity.getId());
            mstNoticeService.removeById(mstNoticeEntity.getId());
            return R.error();
        }
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
                Map<String,Object> map = new HashMap<>();
                map.put("id",mstDeviceTokenEntity.getDeviceId());
                Integer unReadCount = commonService.pushUnreadCount(guardId);
                map.put("unreadcount",unReadCount);
                map.put("stuId",stuId);
                String stuName = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                map.put("stuname",stuName);
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
