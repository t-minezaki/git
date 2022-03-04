package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstDeviceTokenDao;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.StuEventApplyStsService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dao.F08010Dao;
import jp.learningpark.modules.manager.dto.F08010Dto;
import jp.learningpark.modules.manager.dto.F08012Dto;
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.F09005SendMessageDto;
import jp.learningpark.modules.manager.service.F08010Service;
import jp.learningpark.modules.student.service.F11010Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

@RestController
@RequestMapping(value = {"/manager/F08010"})
public class F08010Controller {

    /**
     * MstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;
    @Autowired
    NoticeApiService noticeApiService;
    /**
     * MstGrpService
     */
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * 生徒マスタ　Service
     */
    @Autowired
    private MstStuService mstStuService;
    /**
     * mstDeviceTokenDao
     */
    @Autowired
    F30112Dao f30112Dao;

    @Autowired
    MstDeviceTokenDao mstDeviceTokenDao;
    /**
     * F08010Service
     */
    @Autowired
    private F08010Service f08010Service;
    /**
     * MstEventService
     */
    @Autowired
    private MstEventService mstEventService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END
    /**
     * GuardEventApplyStsService
     */
    @Autowired
    private GuardEventApplyStsService guardEventApplyStsService;

    @Autowired
    private StuEventApplyStsService stuEventApplyStsService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private F08010Dao f08010Dao;
    @Autowired
    private F11010Service f11010Service;
    @Value("${ans-url.token}")
    String token;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>初期表示</p>
     * eventId イベントID
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId) {

        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        // 公開有無
        List<MstCodDEntity> eventStsDivList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "EVENT_STS_DIV").eq("del_flg", 0));
        if (eventStsDivList.size() <= 0) {

            return R.error(MessageUtils.getMessage("MSGCOMN0017", "公開有無"));
        }
        /* NWT崔 manmiru1-726 2021/07/07 edit start */
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        // 画面．追加対象一覧を表示
        List<F08010Dto> guardApplyList = f08010Service.selectGuardAndStudent(eventId, orgIdList, true);

        List<F08010Dto> studentApplyList = f08010Service.selectGuardAndStudent(eventId, orgIdList, false);
        /* NWT崔 manmiru1-726 2021/07/07 edit end */

        // イベント詳細情報を表示
        MstEventEntity mstEventEntity = mstEventService.getOne(
                new QueryWrapper<MstEventEntity>().select("id", "org_id, pub_start_dt", "pub_end_dt", "apply_start_dt", "apply_end_dt", "chg_limt_days",
                        "noitce_flg", "remand_flg", "event_sts_div").eq("id", eventId).eq("del_flg", 0));
        if (mstEventEntity == null) {

            return R.error(MessageUtils.getMessage("MSGCOMN0017", "配信者"));
        }
        Integer deliverTargetDiv = studentApplyList.size() == 0 ? 0 : 1;
        Integer readCount = 0;
        if (deliverTargetDiv == 0) {
            readCount = guardEventApplyStsService.count(
                    new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", eventId).eq("reading_sts_div", "1").eq("del_flg", 0));
        } else {
            readCount = stuEventApplyStsService.count(
                    new QueryWrapper<StuEventApplyStsEntity>().eq("event_id", eventId).eq("reading_sts_div", "1").eq("del_flg", 0));
        }
        boolean activeFlg = true;
        if (readCount > 0) {
            activeFlg = false;
        }
        guardApplyList.addAll(studentApplyList);
        String replyFlg = "0";
        for (F08010Dto f08010Dto : guardApplyList) {
            if (StringUtils.equals("1", f08010Dto.getReplyFlg())) {
                replyFlg = "1";
                break;
            }
        }
        return R.ok().put("guardAndStudentList", guardApplyList).put("eventStsDivList", eventStsDivList).put("eventEntity", mstEventEntity).put(
                "orgId", orgId).put("deliverTargetDiv", deliverTargetDiv).put("activeFlg", activeFlg).put("replyFlg", replyFlg);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public R page(Integer limit, Integer page, String selectData) {
        if (StringUtils.isEmpty(selectData)) {
            return R.ok();
        } else {
            //            List<F08012Dto> data = (List<F08012Dto>) JSON.parse(selectData);
            List<F08012Dto> data = JSON.parseArray(selectData, F08012Dto.class);
            for (int i = 0; i < data.size(); i++) {
                String codValue = mstCodDService.getOne(
                        new QueryWrapper<MstCodDEntity>().eq("cod_key", "SCHY_DIV").eq("cod_cd", StringUtils.trim(data.get(i).getSchyDiv())).eq("del_flg",
                                0)).getCodValue();
                data.get(i).setSchyDiv(codValue);
            }
            //            for (F08012Dto dto: data) {
            //                String codValue = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key","SCHY_DIV").eq("cod_cd",dto.getSchyDiv()).eq("del_flg"
            //                        ,0)).getCodValue();
            //                dto.setSchyDiv(codValue);
            //            }
            if (data.size() == 0) {
                return R.ok();
            }
            return R.ok().put("page", new PageUtils(data, data.size(), limit, page));
        }
    }

    /**
     * <p>保存</p>
     *
     * @param eventParams イベント データを保存する
     * @param guardParams 保護者  データを保存する
     * @param eventId イベントID
     * @param orgId 組織ID
     * @return R 画面情報
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public R save(String eventParams, String guardParams, Integer eventId, String orgId, String guardParamsAll) {

        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // JSON解析
        Map<String, String> eventParamsMap = (Map)JSON.parse(eventParams);
        String reSentFlg = eventParamsMap.get("reSentFlg");
        // チェック
        if ((eventParamsMap.get("pubEndDt").isEmpty() && !eventParamsMap.get("pubStartDt").isEmpty()) || (!eventParamsMap.get(
                "pubEndDt").isEmpty() && eventParamsMap.get("pubStartDt").isEmpty())) {
            return R.error(MessageUtils.getMessage("MSGCOMN0039", "公開開始日時", "公開終了日時"));
        }

        if ((!eventParamsMap.get("pubEndDt").isEmpty() && !eventParamsMap.get("pubStartDt").isEmpty() && DateUtils.parse(
                eventParamsMap.get("pubStartDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM).after(
                DateUtils.parse(eventParamsMap.get("pubEndDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0048", "公開開始日時", "公開終了日時"));
        }

        if ((eventParamsMap.get("applyEndDt").isEmpty() && !eventParamsMap.get("applyStartDt").isEmpty()) || (!eventParamsMap.get(
                "applyEndDt").isEmpty() && eventParamsMap.get("applyStartDt").isEmpty())) {
            return R.error(MessageUtils.getMessage("MSGCOMN0039", "申込可能開始日時", "申込可能終了日時"));
        }

        if ((!eventParamsMap.get("applyEndDt").isEmpty() && !eventParamsMap.get("applyStartDt").isEmpty() && DateUtils.parse(
                eventParamsMap.get("applyStartDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM).after(
                DateUtils.parse(eventParamsMap.get("applyEndDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0048", "申込可能開始日時", "申込可能終了日時"));
        }

        if (!StringUtils.isEmpty(eventParamsMap.get("chgLimtDays")) && Integer.valueOf(eventParamsMap.get("chgLimtDays")) > 30) {
            return R.error(MessageUtils.getMessage("MSGCOMD0014", "変更可能期間", "0", "30"));
        }

        if (eventParamsMap.get("eventStsDiv").equals("0")) {
            if (eventParamsMap.get("pubEndDt").isEmpty() || eventParamsMap.get("pubStartDt").isEmpty() || eventParamsMap.get(
                    "applyEndDt").isEmpty() || eventParamsMap.get("applyStartDt").isEmpty() || eventParamsMap.get("chgLimtDays").isEmpty()) {

                return R.error().put("msg", MessageUtils.getMessage("MSGCOMD0001", "\"公開開始日時、公開終了日時、申込可能開始日時、申込可能終了日時、変更可能期間\""));
            }
        }

        // 配信したイベントが予約された場合
        //        Integer guardEventApplyStsCount = 0;
        //        guardEventApplyStsCount = f08010Service.getGeasCount(eventId);
        //        if (guardEventApplyStsCount > 0) {
        //            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0121", "該当イベント"));
        //        }
        final String sentFlg = "1";
        MstEventEntity mstEventEntity = new MstEventEntity();
        /* NWT崔 2021/07/06 manmiru1-726 edit start */
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        List<String> orgIdStrList = new ArrayList<>();
        for (OrgAndLowerOrgIdDto dto: orgIdList){
            orgIdStrList.add(dto.getOrgId());
        }
        if (!sentFlg.equals(reSentFlg)) {
            /* NWT崔 manmiru1-726 2021/07/08 edit start */
            //　保護者先配信する
            if (Integer.parseInt(eventParamsMap.get("deliverTargetDiv")) == 0) {
                /*削除操作 */
                f08010Dao.deleteData(eventId, orgIdStrList, true);
            }else {
                f08010Dao.deleteData(eventId, orgIdStrList, false);
            }
            /* NWT崔 manmiru1-726 2021/07/08 edit end */
        /* NWT崔 manmiru1-726 2021/07/06 edit end */
            stuEventApplyStsService.remove(
                    new QueryWrapper<StuEventApplyStsEntity>().eq("event_id", eventId).eq("org_id", orgId).eq("reply_sts_div", "0").eq("del_flg", 0));

            mstEventEntity.setEventStsDiv(String.valueOf(eventParamsMap.get("eventStsDiv")));

            if (eventParamsMap.get("pubStartDt") != null && !StringUtils.isEmpty(eventParamsMap.get("pubStartDt"))) {
                // 公開開始日時
                mstEventEntity.setPubStartDt(
                        DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("pubStartDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 公開開始日時
                mstEventEntity.setPubStartDt(null);
            }
            if (eventParamsMap.get("pubEndDt") != null && !StringUtils.isEmpty(eventParamsMap.get("pubEndDt"))) {
                // 公開終了日時
                mstEventEntity.setPubEndDt(DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("pubEndDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 公開終了日時
                mstEventEntity.setPubEndDt(null);
            }
            if (eventParamsMap.get("applyStartDt") != null && !StringUtils.isEmpty(eventParamsMap.get("applyStartDt"))) {
                // 申込み可能開始日時
                mstEventEntity.setApplyStartDt(
                        DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("applyStartDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 申込み可能開始日時
                mstEventEntity.setApplyStartDt(null);
            }
            if (eventParamsMap.get("applyEndDt") != null && !StringUtils.isEmpty(eventParamsMap.get("applyEndDt"))) {
                // 申込み可能終了日時
                mstEventEntity.setApplyEndDt(
                        DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("applyEndDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 申込み可能終了日時
                mstEventEntity.setApplyEndDt(null);
            }

            // 通知有無フラグを設定する
            mstEventEntity.setNoitceFlg(eventParamsMap.get("noitceFlg"));
            // リマインド通知有無フラグを設定する
            mstEventEntity.setRemandFlg(eventParamsMap.get("remandFlg"));

            // 変更可能期間を設定する
            mstEventEntity.setChgLimtDays(StringUtils.isEmpty(eventParamsMap.get("chgLimtDays")) ? null : Integer.valueOf(eventParamsMap.get("chgLimtDays")));
            // 更新日時を設定する
            mstEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤを設定する
            mstEventEntity.setUpdUsrId(userId);

            mstEventEntity.setOrgId(orgId);

            mstEventEntity.setId(eventId);
            // イベントへ更新する。
            f08010Service.updateEvent(mstEventEntity);

        }
        if (sentFlg.equals(reSentFlg)) {
            if (eventParamsMap.get("pubStartDt") != null && !StringUtils.isEmpty(eventParamsMap.get("pubStartDt"))) {
                // 公開開始日時
                mstEventEntity.setPubStartDt(
                        DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("pubStartDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 公開開始日時
                mstEventEntity.setPubStartDt(null);
            }
            if (eventParamsMap.get("pubEndDt") != null && !StringUtils.isEmpty(eventParamsMap.get("pubEndDt"))) {
                // 公開終了日時
                mstEventEntity.setPubEndDt(DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("pubEndDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 公開終了日時
                mstEventEntity.setPubEndDt(null);
            }
            if (eventParamsMap.get("applyStartDt") != null && !StringUtils.isEmpty(eventParamsMap.get("applyStartDt"))) {
                // 申込み可能開始日時
                mstEventEntity.setApplyStartDt(
                        DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("applyStartDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 申込み可能開始日時
                mstEventEntity.setApplyStartDt(null);
            }
            if (eventParamsMap.get("applyEndDt") != null && !StringUtils.isEmpty(eventParamsMap.get("applyEndDt"))) {
                // 申込み可能終了日時
                mstEventEntity.setApplyEndDt(
                        DateUtils.toTimestamp(DateUtils.parse(eventParamsMap.get("applyEndDt"), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            } else {
                // 申込み可能終了日時
                mstEventEntity.setApplyEndDt(null);
            }
            mstEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstEventEntity.setId(eventId);
            // イベントへ更新する。
            mstEventService.updateById(mstEventEntity);
        }
        F09005SendMessageDto sendMessageDto = new F09005SendMessageDto();
        String title = mstEventService.getById(eventId).getEventTitle();
        if (!sentFlg.equals(reSentFlg)) {
            sendMessageDto.setTitle(title);
        }
        List<String> deviceIdList = new ArrayList<>();
        // 画面からのユーザデータ
        Map<String, String> guardParamsMap = (Map)JSON.parse(guardParams);
        List<String> stuIdList = JSON.parseArray(guardParamsMap.get("stuId"), String.class);
        List<String> guardIdList = JSON.parseArray(guardParamsMap.get("guardId"), String.class);
        /* NWT崔 manmiru1-726 2021/07/07 add start */
        List<String> orgList = JSON.parseArray(guardParamsMap.get("orgId"), String.class);
        /* NWT崔 manmiru1-726 2021/07/07 add end */

        // データベースを挿入するユーザ
        List<GuardEventApplyStsEntity> guardList = null;
        List<StuEventApplyStsEntity> studentList = null;
        //　保護者先配信する
        if (Integer.parseInt(eventParamsMap.get("deliverTargetDiv")) == 0) {
            if (!sentFlg.equals(reSentFlg)) {
                GuardEventApplyStsEntity guardEventApplyStsEntity = new GuardEventApplyStsEntity();
                // 作成日時を設定する
                guardEventApplyStsEntity.setCretDatime(new Timestamp(System.currentTimeMillis()));
                // 作成ユーザＩＤを設定する
                guardEventApplyStsEntity.setCretUsrId(userId);
                // 更新日時を設定する
                guardEventApplyStsEntity.setUpdDatime(new Timestamp(System.currentTimeMillis()));
                // 更新ユーザＩＤを設定する
                guardEventApplyStsEntity.setUpdUsrId(userId);
                // イベントIDを設定する
                guardEventApplyStsEntity.setEventId(eventId);
                /* NWT崔　manmiru1-726 2021/07/07 delete start  */
                /* NWT崔　manmiru1-726 2021/07/07 delete end  */
                // 閲覧状況区分を設定する
                guardEventApplyStsEntity.setReadingStsDiv("0");
                // 閲覧回答区分を設定する
                guardEventApplyStsEntity.setReplyStsDiv("0");
                // 削除フラグを設定する
                guardEventApplyStsEntity.setDelFlg(0);
                for (int i = 0; i < stuIdList.size(); i++) {
                    /* NWT崔 manmiru1-726 2021/07/07 edit start */
                    guardList = guardEventApplyStsService.list(
                            new QueryWrapper<GuardEventApplyStsEntity>().select("guard_id", "stu_id").in("org_id", orgIdStrList).eq("event_id", eventId).eq("reply_sts_div","1").eq("guard_id",guardIdList.get(i)).eq("stu_id",stuIdList.get(i)).eq("del_flg", 0));
                    /* NWT崔 manmiru1-726 2021/07/07 edit end */
                    if (guardList.size() > 0){
                        continue;
                    }
                    guardEventApplyStsEntity.setStuId(stuIdList.get(i));
                    guardEventApplyStsEntity.setGuardId(guardIdList.get(i));
                    /* NWT崔 manmiru1-726 2021/07/07 add start */
                    // 組織IDを設定する
                    guardEventApplyStsEntity.setOrgId(orgList.get(i));
                    /* NWT崔 manmiru1-726 2021/07/07 add end */
                    // 保護者イベント申込状況データへ登録する
                    boolean status = guardEventApplyStsService.save(guardEventApplyStsEntity);
                    if (!status) {
                        return R.error();
                    }
                }
            } else {
                List<GuardEventApplyStsEntity> list = new ArrayList<>();
                GuardEventApplyStsEntity entity;
                for (int i = 0; i < stuIdList.size(); i++) {
                    entity = new GuardEventApplyStsEntity();
                    entity.setOrgId(orgId);
                    entity.setEventId(eventId);
                    entity.setStuId(stuIdList.get(i));
                    entity.setGuardId(guardIdList.get(i));
                    entity.setUpdUsrId(ShiroUtils.getUserId());
                    list.add(entity);
                }
                f08010Service.resendDataUpdate(list, "guard");
            }
            Map<String, String> guardParamsAllMap = (Map)JSON.parse(guardParamsAll);
            List<String> stuIdAllList = JSON.parseArray(guardParamsAllMap.get("stuIdAll"), String.class);
            for (int i = 0; i < stuIdAllList.size(); i++) {
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuIdAllList.get(i)).eq("del_flg", 0));
                String guardId = mstStuEntity.getGuardId();
                //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//                MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenDao.selectOne(
//                        new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", guardId).last("limit 1"));
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
                        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                        F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                        //デバイスIDを取得する。
                        f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                        //未読件数を取得する
                        Integer noticeUnreadCount = commonService.pushUnreadCount(guardId);
                        f09005DeviceDto.setUnreadcount(noticeUnreadCount);
                        f09005DeviceDto.setStuId(stuIdAllList.get(i));
                        f09005DeviceDto.setStuname(mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                        deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
                    }
                }
                //modify at 2021/08/17 for V9.02 by NWT LiGX END
            }
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            newCommonData(sendMessageDto, deviceIdList, eventId);
        } else {
            //　生徒先配信する
            StuEventApplyStsEntity stuEventApplyStsEntity = new StuEventApplyStsEntity();
            if (!sentFlg.equals(reSentFlg)) {
                // 作成日時を設定する
                stuEventApplyStsEntity.setCretDatime(new Timestamp(System.currentTimeMillis()));
                // 作成ユーザＩＤを設定する
                stuEventApplyStsEntity.setCretUsrId(userId);
                // 更新日時を設定する
                stuEventApplyStsEntity.setUpdDatime(new Timestamp(System.currentTimeMillis()));
                // 更新ユーザＩＤを設定する
                stuEventApplyStsEntity.setUpdUsrId(userId);
                // イベントIDを設定する
                stuEventApplyStsEntity.setEventId(eventId);
                /* NWT崔　manmiru1-726 2021/07/07 delete start  */
                /* NWT崔　manmiru1-726 2021/07/07 delete end  */
                // 閲覧状況区分を設定する
                stuEventApplyStsEntity.setReadingStsDiv("0");
                // 閲覧回答区分を設定する
                stuEventApplyStsEntity.setReplyStsDiv("0");
                // 削除フラグを設定する
                stuEventApplyStsEntity.setDelFlg(0);
                for (int i = 0; i < stuIdList.size(); i++) {
                    /* NWT崔 manmiru1-726 2021/07/07 edit start */
                    studentList = stuEventApplyStsService.list(new QueryWrapper<StuEventApplyStsEntity>().select("guard_id", "stu_id").in("org_id", orgIdStrList).eq("event_id", eventId).eq("reply_sts_div", "1").eq("guard_id", guardIdList.get(i)).eq("stu_id", stuIdList.get(i)).eq("del_flg", 0));
                    /* NWT崔 manmiru1-726 2021/07/07 edit end */
                    if (studentList.size() > 0) {
                        continue;
                    }
                    stuEventApplyStsEntity.setStuId(stuIdList.get(i));
                    stuEventApplyStsEntity.setGuardId(guardIdList.get(i));
                    /* NWT崔 manmiru1-726 2021/07/07 add start */
                    // 組織IDを設定する
                    stuEventApplyStsEntity.setOrgId(orgList.get(i));
                    /* NWT崔 manmiru1-726 2021/07/07 add end */
                    // 保護者イベント申込状況データへ登録する
                    boolean status = stuEventApplyStsService.save(stuEventApplyStsEntity);
                    if (!status) {
                        return R.error();
                    }
                }
            } else {
                List<GuardEventApplyStsEntity> list = new ArrayList<>();
                GuardEventApplyStsEntity entity;
                for (int i = 0; i < stuIdList.size(); i++) {
                    entity = new GuardEventApplyStsEntity();
                    entity.setOrgId(orgId);
                    entity.setEventId(eventId);
                    entity.setStuId(stuIdList.get(i));
                    entity.setGuardId(guardIdList.get(i));
                    entity.setUpdUsrId(ShiroUtils.getUserId());
                    list.add(entity);
                }
                f08010Service.resendDataUpdate(list, "student");
            }
            Map<String, String> guardParamsAllMap = (Map)JSON.parse(guardParamsAll);
            List<String> stuIdAllList = JSON.parseArray(guardParamsAllMap.get("stuIdAll"), String.class);
            for (int i = 0; i < stuIdAllList.size(); i++) {
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuIdAllList.get(i)).eq("del_flg", 0));
                //delete  at 2021/08/18 for V9.02 by NWT LiGX START
//                MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenDao.selectOne(
//                        new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", stuIdAllList.get(i)).last("limit 1"));
                ///delete  at 2021/08/18 for V9.02 by NWT LiGX END
                //add at 2021/08/17 for V9.02 by NWT LiGX START
                List<String> usrIds = Arrays.asList(stuIdAllList.get(i).split(","));
                Map<String, Object> deviceUserId = new HashMap<>();
                deviceUserId.put("userIdList",usrIds);
                List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
                //add at 2021/08/17 for V9.02 by NWT LiGX END
                //modify at 2021/08/17 for V9.02 by NWT LiGX START
                if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)){
                    for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList){
                        //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                        F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                        //デバイスIDを取得する。
                        f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                        //未読件数を取得する
                        Integer noticeUnreadCount = f11010Service.getUnreadCount(stuIdAllList.get(i));
                        f09005DeviceDto.setUnreadcount(noticeUnreadCount);
                        f09005DeviceDto.setStuId(stuIdAllList.get(i));
                        f09005DeviceDto.setStuname(mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                        deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
                    }
                }
                //modify at 2021/08/17 for V9.02 by NWT LiGX END
            }
            newCommonData(sendMessageDto, deviceIdList, eventId);
        }
        return R.ok();
    }

    private void newCommonData(F09005SendMessageDto sendMessageDto, List<String> deviceIdList, Integer eventId) {
        sendMessageDto.setComment("ここはcomment");
        sendMessageDto.setImgUrl(null);
        sendMessageDto.setDeviceList(deviceIdList);
        sendMessageDto.setMessage(noticeApiService.getMessageDetail("0"));
        Map<String, Object> map = new LinkedHashMap<>();
        //            メッセージ区分　＝　「3：イベント管理の配信設定」
        map.put("msgId", eventId);
        map.put("msgType", Constant.sendMsgTypeEvent);
        sendMessageDto.setParams(JSON.toJSONString(map));
        sendMessageDto.setPriority(1);
        sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
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

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public R check(Integer eventId) {

        String orgId = ShiroUtils.getUserEntity().getOrgId();
        Integer guardEventApplyStsCount = guardEventApplyStsService.count(
                new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", eventId).ne("org_id", orgId).eq("del_flg", 0));

        return R.ok().put("guardEventApplyStsCount", guardEventApplyStsCount);
    }

}
