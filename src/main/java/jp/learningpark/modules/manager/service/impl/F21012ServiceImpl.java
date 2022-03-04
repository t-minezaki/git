package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dao.F21012Dao;
import jp.learningpark.modules.manager.dto.F21012Dto;
import jp.learningpark.modules.manager.service.F21012Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 学習連絡確認Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/29 ： NWT)hxl ： 新規作成
 * @date 2019/11/29 17:51
 */
@Service
public class F21012ServiceImpl implements F21012Service {
    /**
     * f21012Dao
     */
    @Autowired
    F21012Dao f21012Dao;
    /**
     *保護者指導報告書閲覧状況Dao
     */
    @Autowired
    GuidReprApplyStsDao guidReprApplyStsDao;
    /**
     *指導報告書配信管理Dao
     */
    @Autowired
    GuidReprDeliverDao guidReprDeliverDao;
    /**
     *お知らせDao
     */
    @Autowired
    MstNoticeDao mstNoticeDao;
    /**
     *お知らせ配信先Dao
     */
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;
    /**
     *保護者お知らせ閲覧状況Dao
     */
    @Autowired
    GuardReadingStsDao guardReadingStsDao;
    /**
     * 採番マスタ Dao
     */
    @Autowired
    MstMaxIdDao mstMaxIdDao;
    /**
     * mstStuDao
     */
    @Autowired
    MstStuDao mstStuDao;
    /**
     * mstOrgDao
     */
    @Autowired
    MstOrgDao mstOrgDao;
    /**
     * mstDeviceTokenDao
     */
    @Autowired
    MstDeviceTokenDao mstDeviceTokenDao;

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
    @Autowired
    CommonService commonService;
    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;
    @Value("${ans-url.token}")
    String token;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 編集または作成指導報告書配信管理
     *
     * @param list          生徒ID
     * @param orgId         組織ID
     * @param guidDeliverCD 指導報告書配信コード
     * @param date          出席簿対象日
     * @param startDate     公開開始日時
     * @param endDate       公開終了日時
     * @param statusCD      指導報告書ステータス区分
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveOrUpdate(List<F21012Dto> list, String orgId, String guidDeliverCD, Date date, Date startDate, Date endDate, String statusCD,String noticeLevelDiv) {
        String userId = ShiroUtils.getUserId();
        //システム日時
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        List<F21012Dto> stuIdAndGuiRepId = new ArrayList<>();
        if (!(list == null || list.size() == 0)) {
            //指導報告書明細、指導報告書ヘーダ、出席簿ヘーダから対象者リスト中の送信対象は指導報告書存在チェック
            stuIdAndGuiRepId = f21012Dao.getStuIdAndGuiRepId(date, orgId, list);
            //対象者のIDあるだが対応する指導報告書コードがない場合
            Set<String> originSet = new HashSet<>();
            list.forEach(v -> {
                originSet.add(v.getStuId());
            });
            Set<String> nowSet = new HashSet<>();
            stuIdAndGuiRepId.forEach(v -> {
                nowSet.add(v.getStuId());
            });
            if (!nowSet.containsAll(originSet)){
                return R.ok().put("stuIdList", new ArrayList<>(nowSet));
            }
        }
        //前画面に転送しての指導報告書マスタコードはエンプティの場合（新規モード）
        if (StringUtils.isEmpty(guidDeliverCD)){
            guidDeliverCD = getMaxId(orgId, userId, sysTimestamp);
            //指導報告書配信管理新規
            GuidReprDeliverEntity guidReprDeliverEntity = new GuidReprDeliverEntity();
            guidReprDeliverEntity.setGuidReprDeliverCd(guidDeliverCD);
            guidReprDeliverEntity.setOrgId(orgId);
            guidReprDeliverEntity.setPubStartDt(DateUtils.toTimestamp(startDate));
            guidReprDeliverEntity.setPubEndDt(DateUtils.toTimestamp(endDate));
            guidReprDeliverEntity.setEventStsDiv(statusCD);
            guidReprDeliverEntity.setTgtYmd(date);
            guidReprDeliverEntity.setCretDatime(sysTimestamp);
            guidReprDeliverEntity.setCretUsrId(userId);
            guidReprDeliverEntity.setUpdDatime(sysTimestamp);
            guidReprDeliverEntity.setUpdUsrId(userId);
            guidReprDeliverEntity.setDelFlg(0);
            guidReprDeliverDao.insert(guidReprDeliverEntity);
                //保護者指導報告書閲覧状況送信対象新規
                stuIdAndGuiRepId.forEach(v -> {
                    createGuidReprApplySts(guidReprDeliverEntity.getGuidReprDeliverCd(), v.getGuiRepId(), orgId, v.getStuId(), getGuardId(v.getStuId()), sysTimestamp, userId);
                });
        }else{//前画面に転送しての指導報告書マスタコードはエンプティ以外の場合（編集モード）
            //指導報告書配信管理更新
            GuidReprDeliverEntity guidReprDeliverEntity = guidReprDeliverDao.selectOne(new QueryWrapper<GuidReprDeliverEntity>().eq("guid_repr_deliver_cd", guidDeliverCD).eq("org_id", orgId).eq("del_flg",0).orderByDesc("cret_datime").last("limit 1"));
            if (guidReprDeliverEntity != null) {
                guidReprDeliverEntity.setTgtYmd(date);
                guidReprDeliverEntity.setEventStsDiv(statusCD);
                guidReprDeliverEntity.setPubStartDt(DateUtils.toTimestamp(startDate));
                guidReprDeliverEntity.setPubEndDt(DateUtils.toTimestamp(endDate));
                guidReprDeliverEntity.setUpdDatime(sysTimestamp);
                guidReprDeliverEntity.setUpdUsrId(userId);
                guidReprDeliverEntity.setDelFlg(0);
                guidReprDeliverDao.updateById(guidReprDeliverEntity);
            }else {
                return R.error(MessageUtils.getMessage("MSGCOMB0023"));
            }
            //指導報告書 マスタ管理、保護者指導報告書申請状況から該当報告
            List<F21012Dto> guidApplyList = f21012Dao.getGuidApplyByDeliverId(guidDeliverCD, orgId);
            //編集状態リスト内生徒を減らす
            if (guidApplyList.size() > stuIdAndGuiRepId.size()){
                //F21012Dtoリスト -> Map
                Map<String, Integer> stuGuidReprMap = new HashMap<>();
                stuIdAndGuiRepId.forEach(v -> {
                    stuGuidReprMap.put(v.getStuId(), v.getGuiRepId());
                });
                guidApplyList.forEach(v -> {
                    if (stuGuidReprMap.containsKey(v.getStuId())){
                        updateGuidReprApplySts(v.getGuidReprApplyStsId(), getGuardId(v.getStuId()), sysTimestamp, userId, 0);
                    }else {//保護者指導報告書閲覧状況送信減らす対象更新
                        updateGuidReprApplySts(v.getGuidReprApplyStsId(), getGuardId(v.getStuId()), sysTimestamp, userId, 1);
                    }
                });
            }else if (guidApplyList.size() == stuIdAndGuiRepId.size()){
                //編集状態リスト内生徒数は変更しない
                guidApplyList.forEach(v -> {
                    updateGuidReprApplySts(v.getGuidReprApplyStsId(), getGuardId(v.getStuId()), sysTimestamp, userId, 0);
                });
            }else {//編集状態リスト内生徒数は増えた
                final String DeliverCD = guidDeliverCD;
                //F21012Dtoリスト -> Map
                Map<String, Integer> stuGuidApplyMap = new HashMap<>();
                guidApplyList.forEach(v -> {
                    stuGuidApplyMap.put(v.getStuId(), v.getGuidReprApplyStsId());
                });
                stuIdAndGuiRepId.forEach(v -> {
                    if (stuGuidApplyMap.containsKey(v.getStuId())) {
                        //対象者リスト中対象者をＤＢに更新する
                        updateGuidReprApplySts(stuGuidApplyMap.get(v.getStuId()), getGuardId(v.getStuId()), sysTimestamp, userId, 0);
                    }else {
                        //対象者リスト．生徒ID - 保護者指導報告書申込状況．生徒ID残すの部分は新規処理と行う
                        createGuidReprApplySts(DeliverCD, v.getGuiRepId(), orgId, v.getStuId(), getGuardId(v.getStuId()), sysTimestamp, userId);
                    }
                });
            }
        }
        List<String> sendDataList = new ArrayList<>();
        //ステータス状態は「公開」場合
        if (StringUtils.equals(statusCD, "0")){
            final String DeliverCD = guidDeliverCD;
            List<String> deviceList = new ArrayList<>();
            //お知らせ登録
            MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
            stuIdAndGuiRepId.forEach(v -> {
                String guardId = getGuardId(v.getStuId());
                mstNoticeEntity.setOrgId(orgId);
                mstNoticeEntity.setNoticeTitle((StringUtils.isEmpty(v.getCodValue()) ? "" : v.getCodValue()) + "指導報告書 " + DateUtils.format(date, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                mstNoticeEntity.setNoticeCont(v.getGuiRepId().toString()+","+DeliverCD);
                mstNoticeEntity.setNoticeTypeDiv("4");
                mstNoticeEntity.setNoticeLevelDiv(noticeLevelDiv);
                mstNoticeEntity.setPubPlanStartDt(sysTimestamp);
                mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.addDays(sysTimestamp, 7)));
                mstNoticeEntity.setAllDeliverFlg(null);
                mstNoticeEntity.setAttachFilePath(null);
                mstNoticeEntity.setTitleImgPath(null);
                mstNoticeEntity.setCretDatime(sysTimestamp);
                mstNoticeEntity.setCretUsrId(userId);
                mstNoticeEntity.setUpdDatime(sysTimestamp);
                mstNoticeEntity.setUpdUsrId(userId);
                mstNoticeEntity.setDelFlg(0);
                mstNoticeDao.insert(mstNoticeEntity);
                //お知らせ.ID
                Integer noticeId = mstNoticeEntity.getId();
                MstOrgEntity mstOrgEntity = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).eq("del_flg", 0));
                //管理フラグ
                String mgrFlg = mstOrgEntity.getMgrFlg();
                //お知らせ配信先登録
                MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
                //データを入力する
                mstNoticeDeliverEntity.setOrgId(orgId);
                mstNoticeDeliverEntity.setNoticeId(noticeId);
                mstNoticeDeliverEntity.setMgrFlg(mgrFlg);
                mstNoticeDeliverEntity.setStuId(v.getStuId());
                mstNoticeDeliverEntity.setGuardId(guardId);
                mstNoticeDeliverEntity.setCretDatime(sysTimestamp);
                mstNoticeDeliverEntity.setCretUsrId(userId);
                mstNoticeDeliverEntity.setUpdDatime(sysTimestamp);
                mstNoticeDeliverEntity.setUpdUsrId(userId);
                mstNoticeDeliverEntity.setDelFlg(0);
                mstNoticeDeliverDao.insert(mstNoticeDeliverEntity);
                //保護者お知らせ閲覧状況
                GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
                //データを入力する
                guardReadingStsEntity.setOrgId(orgId);
                guardReadingStsEntity.setNoticeId(noticeId);
                guardReadingStsEntity.setGuardId(guardId);
                guardReadingStsEntity.setStuId(v.getStuId());
                guardReadingStsEntity.setReadingStsDiv("0");
                guardReadingStsEntity.setCretDatime(sysTimestamp);
                guardReadingStsEntity.setCretUsrId(userId);
                guardReadingStsEntity.setUpdDatime(sysTimestamp);
                guardReadingStsEntity.setUpdUsrId(userId);
                guardReadingStsEntity.setDelFlg(0);
                guardReadingStsDao.insert(guardReadingStsEntity);
//               for (F21012Dto f21012Dto : list){
                 MstStuEntity mstStuEntity = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("stu_id",v.getStuId()));
                //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//                   MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenDao.selectOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",mstStuEntity.getGuardId()));
                //delete  at 2021/08/18 for V9.02 by NWT LiGX START
                //add at 2021/08/18 for V9.02 by NWT LiGX START
                List<String> usrIds = Arrays.asList(mstStuEntity.getGuardId().split(","));
                Map<String, Object> deviceUserId = new HashMap<>();
                deviceUserId.put("userIdList",usrIds);
                List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
                //add at 2021/08/18 for V9.02 by NWT LiGX END
                //modify at 2021/08/18 for V9.02 by NWT LiGX START
                if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                    for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", mstDeviceTokenEntity.getDeviceId());
                        //未読件数を取得する 2020/12/01 modify yang start--
                        Integer unReadCount = commonService.pushUnreadCount(mstStuEntity.getGuardId());
                        //未読件数を取得する 2020/12/01 modify yang end--
                        map.put("unreadcount", unReadCount);
                        map.put("stuId", mstStuEntity.getStuId());
                        map.put("stuname", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                        //add deviceId to deviceIdList by forサイクル
                        deviceList.add(JSON.toJSONString(map));
                    }
                }
                //modify at 2021/08/18 for V9.02 by NWT LiGX END
//               }
            });
            if (CollectionUtils.isNotEmpty(deviceList)){
                String message = noticeApiService.getMessageDetail("0");
                Map<String,Object> params = new LinkedHashMap<>();
                params.put("msgId",mstNoticeEntity.getId());
                params.put("msgType",Constant.sendMsgTypeGuidRepr);
                Map<String,Object> sendData = new HashMap<>();
                sendData.put("comment","ここはcomment");
                sendData.put("deviceList",deviceList);
                sendData.put("imgUrl",mstNoticeEntity.getTitleImgPath()==null?"":mstNoticeEntity.getTitleImgPath());
                sendData.put("message",message);
                sendData.put("params",JSON.toJSONString(params));
                sendData.put("priority",1);
                sendData.put("sendTime",mstNoticeEntity.getPubPlanStartDt().getTime());
                sendData.put("title",mstNoticeEntity.getNoticeTitle());
                sendData.put("token",commonService.getToken());
                //通知プッシュの起止時間と処理時間をログで記録する
                Timestamp sTime = DateUtils.getSysTimestamp();
                logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
                noticeApiService.sendMessage(JSON.toJSONString(sendData));
                Timestamp eTime = DateUtils.getSysTimestamp();
                Long cTime = eTime.getTime() - sTime.getTime();
                logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
                logger.info("プッシュ通知処理時間：<" + cTime + ">");
            }
        }
        return R.ok();
    }

    /**
     * 組織別で連番採番
     * @param orgId 組織ID
     * @param UsrId ユーザーID
     * @return
     */
    private synchronized String getMaxId(String orgId, String UsrId, Timestamp sysTimestamp){
        Integer maxId;
        MstMaxIdEntity mstMaxIdEntity = mstMaxIdDao.selectOne(new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "s"));
        if ((mstMaxIdEntity != null) && (mstMaxIdEntity.getMaxId() != null)){
            maxId = (Integer) mstMaxIdEntity.getMaxId();
            mstMaxIdEntity.setMaxId(maxId + 1);
            mstMaxIdEntity.setUpdDatime(sysTimestamp);
            mstMaxIdEntity.setUpdUsrId(UsrId);
            mstMaxIdDao.updateById(mstMaxIdEntity);
        }else {
            maxId = 0;
            mstMaxIdEntity = new MstMaxIdEntity();
            mstMaxIdEntity.setOrgId(orgId);
            mstMaxIdEntity.setMaxId(1);
            mstMaxIdEntity.setTypeDiv("s");
            mstMaxIdEntity.setCretDatime(sysTimestamp);
            mstMaxIdEntity.setCretUsrId(UsrId);
            mstMaxIdEntity.setUpdDatime(sysTimestamp);
            mstMaxIdEntity.setUpdUsrId(UsrId);
            mstMaxIdEntity.setDelFlg(0);
            mstMaxIdDao.insert(mstMaxIdEntity);
        }
        return "s" + (maxId + 1);
    }

    /**
     * 保護者IDを取得する
     * @param stuId 生徒ID
     * @return
     */
    private String getGuardId(String stuId){
        MstStuEntity mstStuEntity = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId));
        return mstStuEntity.getGuardId();
    }

    /**
     * 保護者指導報告書閲覧状況送信対象新規
     * @param guidReprDeliverCD 指導報告書配信管理の指導報告書配信コード
     * @param guidReprId        指導報告書ID
     * @param orgId             組織ID
     * @param stuId             生徒ID
     * @param guardId           保護者ID
     * @param sysTimestamp      システム日時
     * @param userId            ユーザーID
     */
    private void createGuidReprApplySts(String guidReprDeliverCD, Integer guidReprId
            , String orgId, String stuId, String guardId, Timestamp sysTimestamp, String userId){
        GuidReprApplyStsEntity guidReprApplyStsEntity = new GuidReprApplyStsEntity();
        guidReprApplyStsEntity.setGuidReprDeliverCd(guidReprDeliverCD);
        guidReprApplyStsEntity.setGuidReprId(guidReprId);
        guidReprApplyStsEntity.setOrgId(orgId);
        guidReprApplyStsEntity.setStuId(stuId);
        guidReprApplyStsEntity.setGuardId(getGuardId(stuId));
        guidReprApplyStsEntity.setReadingStsDiv("0");
        guidReprApplyStsEntity.setCretDatime(sysTimestamp);
        guidReprApplyStsEntity.setCretUsrId(userId);
        guidReprApplyStsEntity.setUpdDatime(sysTimestamp);
        guidReprApplyStsEntity.setUpdUsrId(userId);
        guidReprApplyStsEntity.setDelFlg(0);
        guidReprApplyStsDao.insert(guidReprApplyStsEntity);
    }

    /**
     * 保護者指導報告書閲覧状況送信対象更新
     * @param id            保護者指導報告書閲覧状況ID
     * @param guardId       保護者ID
     * @param sysTimestamp  システム日時
     * @param userId        ユーザーID
     * @param delFlag       削除フラグ
     */
    private void updateGuidReprApplySts(Integer id, String guardId, Timestamp sysTimestamp, String userId, Integer delFlag){
        GuidReprApplyStsEntity guidReprApplyStsEntity = guidReprApplyStsDao.selectById(id);
        //データを更新する
        guidReprApplyStsEntity.setGuardId(guardId);
        guidReprApplyStsEntity.setUpdDatime(sysTimestamp);
        guidReprApplyStsEntity.setUpdUsrId(userId);
        guidReprApplyStsEntity.setDelFlg(delFlag);
        guidReprApplyStsDao.updateById(guidReprApplyStsEntity);
    }
}
