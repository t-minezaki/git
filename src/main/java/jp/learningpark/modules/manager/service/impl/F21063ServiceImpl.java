package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.manager.dao.F21063Dao;
import jp.learningpark.modules.manager.dto.F21063Dto;
import jp.learningpark.modules.manager.dto.F21063DtoIn;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21063Service;
import jp.learningpark.modules.student.dao.F11010Dao;
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
import java.util.*;

/**
 * <p>F21063_メッセージ作成画面 Controller</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/20 ： NWT)hxl ： 新規作成
 * 2020/11/11 ： NWT)文 ： 要件変更
 */
@Service
public class F21063ServiceImpl implements F21063Service {
    /**
     * F21063 ッセージ新規画面 Dao
     */
    @Autowired
    F21063Dao f21063Dao;

    /**
     * メッセージマスター Dao
     */
    @Autowired
    private MstMessageDao mstMessageDao;

    /**
     * 通知プッシュ
     */
    @Autowired
    NoticeApiService noticeApiService;

    /**
     * f11010Dao
     */
    @Autowired
    private F11010Dao f11010Dao;

    /**
     * mstDeviceTokenService dao
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
     * talkReadingStsService　dao
     */
    @Autowired
    private TalkReadingStsDao talkReadingStsDao;

    @Value("${ans-url.token}")
    String token;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>生徒IDListによるdtotListの取得</p>
     * <p>
     * 2019/06/24 hujiaxing: mod
     *
     * @param stuIdList student Id List
     * @return
     */
    @Override
    public List<F21063DtoIn> selectStuByIdList(List<String> stuIdList,Integer page,Integer limit) {
        return f21063Dao.selectStuByIdList(stuIdList,page,limit);
    }
    /**
     * <p>生徒IDListによるdtotListの总數取得</p>
     * <p>
     * 2019/06/24 hujiaxing: mod
     *
     * @param stuIdList student Id List
     * @return
     */
    @Override
    public List<F21063DtoIn> selectStuByIdListCount(List<String> stuIdList) {
        return f21063Dao.selectStuByIdListCount(stuIdList);
    }
    /**
     * 登録処理
     * <p>
     * 2019/06/24 hujiaxing: mod
     *
     * @param dto
     * @param MessageCont 　メッセージの内容
     * @param orgId 組織ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R doInsert(F21063Dto dto, String MessageCont, String orgId) {
        try {
            dto.setMessageTitle(URLDecoder.decode(dto.getMessageTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();

        //画面を元に、メッセージマスタへ登録する。
        MstMessageEntity mstMessageEntity = new MstMessageEntity();
        //組織ID
        mstMessageEntity.setOrgId(orgId);
        //面談記録ID
        mstMessageEntity.setTalkId(null);
        //メッセージタイトル
        mstMessageEntity.setMessageTitle(dto.getMessageTitle());
        //メッセージ内容
        mstMessageEntity.setMessageCont(MessageCont);
        //メッセージタップ区分
        mstMessageEntity.setMessageTypeDiv("1");
        //メッセージレベル区分
        mstMessageEntity.setMessageLevelDiv(dto.getMessageLevelDiv());
        //掲載予定開始日時
        mstMessageEntity.setPubPlanStartDt(dto.getPubPlanStartDt());
        //掲載予定終了日時
        mstMessageEntity.setPubPlanEndDt(dto.getPubPlanEndDt());
        //全体配信フラグ
        mstMessageEntity.setAllDeliverFlg(dto.getAllDeliverFlg());
        //添付ファイルパス
        mstMessageEntity.setAttachFilePath(dto.getAttachFilePath());
        //タイトル画像パス
        mstMessageEntity.setTitleImgPath(dto.getTitleImgPath());
        //作成日時
        mstMessageEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstMessageEntity.setCretUsrId(userId);
        //更新日時
        mstMessageEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstMessageEntity.setUpdUsrId(userId);
        //削除フラグ
        mstMessageEntity.setDelFlg(0);
        try {
            mstMessageDao.insert(mstMessageEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        List<String> deviceIdList = new ArrayList<>();
        //メッセージ閲覧状況の登録
        for (int i = 0; i < dto.getStuList().size(); i++) {
            TalkReadingStsEntity talkReadingStsEntity = new TalkReadingStsEntity();
            //組織ID
            talkReadingStsEntity.setOrgId(dto.getStuList().get(i).getOrgId());
            //メッセージID
            talkReadingStsEntity.setMessageId(mstMessageEntity.getId());
            //配信先ID
            talkReadingStsEntity.setDeliverId(dto.getStuList().get(i).getUsrId());
            //閲覧状況区分
            talkReadingStsEntity.setReadingStsDiv("0");
            //開封状況区分  NWT文　11/11  要件変更
            talkReadingStsEntity.setOpenedFlg("0");
            //質問事項表示フラグ
            talkReadingStsEntity.setAskDispFlg(null);
            //面談事項表示フラグ
            talkReadingStsEntity.setTalkDispFlg(null);
            //作成日時
            talkReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザー
            talkReadingStsEntity.setCretUsrId(userId);
            //更新日時
            talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザー
            talkReadingStsEntity.setUpdUsrId(userId);
            //削除
            talkReadingStsEntity.setDelFlg(0);
            try {
                talkReadingStsDao.insert(talkReadingStsEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return R.error();
            }
        }
        return R.ok().put("mstMessageEntity",mstMessageEntity);
    }

    @Override
    public R sendMessage(F21063Dto dto, MstMessageEntity mstMessageEntity){
        List<String> deviceIdList = new ArrayList<>();
        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
        SendMessageDto sendMessageDto = new SendMessageDto();
        for (int i = 0;i < dto.getStuList().size();i++) {
            //guardIdでdeviceIdに問い合わせる
            //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//            MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",dto.getStuList().get(i).getUsrId()).eq("del_flg",0));
            //delete  at 2021/08/19 for V9.02 by NWT LiGX START
            //add at 2021/08/19 for V9.02 by NWT LiGX START
            List<String> usrIds = Arrays.asList(dto.getStuList().get(i).getUsrId().split(","));
            Map<String, Object> deviceUserId = new HashMap<>();
            deviceUserId.put("userIdList",usrIds);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
            //add at 2021/08/19 for V9.02 by NWT LiGX END
            //未読件数を取得する 2020/12/01 modify yang start--
            //modify at 2021/08/19 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", mstDeviceTokenEntity.getDeviceId());
                    Integer unReadCount = f11010Dao.getUnreadCount(dto.getStuList().get(i).getUsrId());
                    map.put("unreadcount", unReadCount);
                    map.put("stuId", dto.getStuList().get(i).getUsrId());
                    map.put("stuname", dto.getStuList().get(i).getSFlnm());
                    //add deviceId to deviceIdList by forサイクル
                    deviceIdList.add(JSON.toJSONString(map));
                }
            }
            //modify at 2021/08/19 for V9.02 by NWT LiGX END
        }
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            //未読件数を取得する 2020/12/01 modify yang end--
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstMessageEntity.getTitleImgPath()==null?"":mstMessageEntity.getTitleImgPath());
            sendMessageDto.setDeviceList(deviceIdList);
            sendMessageDto.setMessage(noticeApiService.getMessageDetail("0"));
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("msgId",mstMessageEntity.getId());
            map.put("msgType", Constant.sendMsgTypeMessage);
            sendMessageDto.setParams(JSON.toJSONString(map));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(String.valueOf(mstMessageEntity.getPubPlanStartDt().getTime()));
            sendMessageDto.setTitle(mstMessageEntity.getMessageTitle());
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
