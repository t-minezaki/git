/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dto.F30002Dto;
import jp.learningpark.modules.guard.service.F30002Service;
import jp.learningpark.modules.guard.service.F30419Service;
import jp.learningpark.modules.guard.service.F30421Service;
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F21017Service;
import jp.learningpark.modules.student.service.F11010Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 共通処理 ServiceImplです。
 * </p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/08 : gaoli: 新規<br />
 * @version 1.0
 */
@Service
public class CommonServiceImpl implements CommonService {

    /**
     * 共通処理 DAO
     */
    @Autowired
    private CommonDao commonDao;

    /**
     * f30421Service
     */
    @Autowired
    F30421Service f30421Service;
    /**
     * f30419Service
     */
    @Autowired
    F30419Service f30419Service;
    /**
     * デバイスTOKEN管理Service
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;
    /**
     * 管理者基本マスタService
     */
    @Autowired
    MstManagerService mstManagerService;
    /**
     * メンター基本マスタService
     */
    @Autowired
    MstMentorService mstMentorService;
    /**
     * 保護者基本マスタService
     */
    @Autowired
    MstGuardService mstGuardService;
    /**
     * ユーザ基本マスタService
     */
    @Autowired
    MstUsrService mstUsrService;
    /**
     * shiroサービス
     */
    @Autowired
    ShiroService shiroService;
    /**
     * F30002_子供選択画面 Service
     */
    @Autowired
    F30002Service f30002Service;
    /**
     * 生徒基本マスタService
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * コードマスタ_明細
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * 通知アプリ連携API Service
     */
    @Autowired
    NoticeApiService noticeApiService;

    @Autowired
    F21017Service f21017Service;

    @Autowired
    F11010Service f11010Service;
    // add at 2021/9/29 for V9.02 by NWT HuangXL START
    @Autowired
    NoticeApiDao noticeApiDao;
    // add at 2021/9/29 for V9.02 by NWT HuangXL END
    /**
     * add at 2021/09/16 for V9.02 by NWT wen
     */
    @Autowired
    MailUtils mailUtils;

    @Value("${ans-url.NOTICE_REMOTE_URL_appLoginCheck}")
    String appLoginCheck;
    @Value("${ans-url.NOTICE_REMOTE_URL_unReadGet}")
    String unReadGet;
    @Value("${ans-url.token}")
    String token;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(50000, TimeUnit.MILLISECONDS)
            .connectTimeout(50000, TimeUnit.MILLISECONDS)
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>{@link CommonService#retainTopOrgList(List)}メソッドに使用して、最上位の組織を取得し、上位の組織をキャッシュします</p>
     * <p>クエリ速度を最適化するために、キャッシュを追加した後のクエリ速度はかなりのものです</p>
     */
    private final ThreadLocal<HashMap<String, MstOrgEntity>> localCacheUpperOrg = new ThreadLocal<>();

    /**
     * <p>
     * 計画図エリア情報取得処理(F10301)
     * </p>
     *
     * @param stuId    生徒ID
     * @param startYmd 週開始日
     * @param endYmd   週終了日
     * @return 計画図エリア情報
     */
    @Override
    public List<SchdlDto> getSchdlList(String stuId, Date startYmd, Date endYmd) {
        List<SchdlDto> result = getSchdlList(stuId, startYmd, endYmd, "all");
        return result;
    }

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId    生徒ID
     * @param startYmd 週開始日
     * @param endYmd   週終了日
     * @return 計画ブロック
     */
    @Override
    public List<SchdlDto> selectPlanBlockList(String stuId, Date startYmd, Date endYmd) {
        List<SchdlDto> result = selectPlanBlockList(stuId, startYmd, endYmd, "all");
        return result;
    }

    /**
     * 本組織及び上下層組織リストの取得
     *
     * @param orgId 本組織ID
     * @return
     */
    @Override
    public List<OrgAndLowerOrgIdDto> getAllOrgList(String orgId) {
        return commonDao.selectAllOrgList(orgId);
    }

    /**
     * <p>
     * 計画図エリア情報取得処理(F10301)
     * </p>
     *
     * @param stuId    生徒ID
     * @param startYmd 週開始日
     * @param endYmd   週終了日
     * @param all      全て
     * @return 計画図エリア情報
     */
    @Override
    public List<SchdlDto> getSchdlList(String stuId, Date startYmd, Date endYmd, String all) {
        List<SchdlDto> result = commonDao.selectSchdlList(stuId, startYmd, endYmd, all);
        return result;
    }

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId    生徒ID
     * @param startYmd 開始日
     * @param endYmd   終了日
     * @param all      全て
     * @return 計画ブロック
     */
    @Override
    public List<SchdlDto> selectPlanBlockList(String stuId, Date startYmd, Date endYmd, String all) {
        List<SchdlDto> result = commonDao.selectPlanBlockList(stuId, startYmd, endYmd, all);
        return result;
    }

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId    生徒ID
     * @param startYmd 開始日
     * @param endYmd   終了日
     * @param all      全て
     * @return 計画ブロック
     */
    @Override
    public List<SchdlDto> getPrintPlanBlockList(String stuId, Date startYmd, Date endYmd, String all) {
        List<SchdlDto> result = commonDao.selectPrintPlanBlockList(stuId, startYmd, endYmd, all);
        return result;
    }

    /**
     * <p>生徒固定スケジュールと個別スケジュール調整情報の取得（NWT譚）</p>
     *
     * @param id         生徒固定スケジュールID
     * @param stuId      生徒ID
     * @param startYmd   開始日
     * @param checkStart 開始時間チェック
     * @param checkEnd   終了時間チェック
     * @return 生徒固定スケジュールと個別スケジュール調整情報
     */
    @Override
    public List<SchdlDto> selectFixedBlockList(Integer id, String stuId, Date startYmd, Boolean checkStart, Boolean checkEnd) {
        List<SchdlDto> result = commonDao.selectFixedBlockList(id, stuId, startYmd, checkStart, checkEnd);
        return result;
    }

    /**
     * @param id         生徒固定スケジュールID
     * @param stuId      生徒ID
     * @param startYmd   開始日
     * @param checkStart 開始時間チェック
     * @param checkEnd   終了時間チェック
     * @return
     */
    @Override
    public List<SchdlDto> getPrintFixedBlockList(Integer id, String stuId, Date startYmd, Boolean checkStart, Boolean checkEnd) {
        return commonDao.selectPrintFixedBlockList(id, stuId, startYmd, checkStart, checkEnd);
    }

    /**
     * 変更後学年の塾学習期間IDの取得
     *
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @param schyDiv 学年区分
     * @return
     */
    @Override
    public Integer getctCrmLearnPrdIdAfterUpdateSchy(String orgId, String brandCd, String schyDiv) {
        return commonDao.seletctCrmLearnPrdIdAfterUpdateSchy(orgId, brandCd, schyDiv);
    }

    /**
     * <p>本組織及び下層組織リストの取得</p>
     *
     * @param brandCd ブランドコード
     * @param orgId   組織ID
     * @return
     */
    @Override
    public List<OrgAndLowerOrgIdDto> getThisAndLowerOrgId(String brandCd, String orgId) {
        return commonDao.selectThisAndLowerOrgId(brandCd, orgId);
    }

    /**
     * <p>本組織及び上層組織リストの取得</p>
     *
     * @param brandCd ブランドコード
     * @param orgId   組織ID
     * @return
     */
    @Override
    public List<OrgAndLowerOrgIdDto> getUpLvOrgList(String brandCd, String orgId) {
        return commonDao.getUpLvOrgList(brandCd, orgId);
    }

    /**
     * @param afterUsrId ログインID
     * @param brandCd    　ブランドコード
     * @return
     */
    @Override
    public List<String> getOrgsForChoose(String afterUsrId, String brandCd) {
        String manaFlg = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG);
        return commonDao.selectOrgsForChoose(afterUsrId, manaFlg, brandCd);
    }

    @Override
    public Integer unReadCount(String orgId, String guardId, String stuId) {
        //お知らせの件数の取得
        Integer noticeCount = f30421Service.getNoticeCount(orgId, guardId, stuId);
        //イベントの件数の取得
        Integer eventCount = f30421Service.getEventCount(guardId, stuId);
        //マナミルチャンネル件数の取得
        //        Integer channelCount = f30419Service.selectUnreadCount(guardId, orgId,stuId);
        //        Integer total = noticeCount + eventCount + channelCount;
        return noticeCount + eventCount;
    }

    @Override
    public List<MstOrgEntity> retainTopOrgList(List<MstOrgEntity> selectedOrg) {
        return retainTopOrgList(selectedOrg, ShiroUtils.getBrandcd());
    }

    @Override
    public List<MstOrgEntity> retainTopOrgList(List<MstOrgEntity> selectedOrg, String brandCd) {
        if (Strings.isNullOrEmpty(brandCd)) {
            throw new IllegalArgumentException("CommonService#retainTopOrgList " + "パラメータブランドCdを空にすることはできません");
        }
        // データが1つしかない場合は、直接返してください
        if (selectedOrg.size() == 1) {
            return selectedOrg;
        }

        // ランクで並べ替え
        Collections.sort(selectedOrg);
        localCacheUpperOrg.set(new HashMap<>(8));

        // 現在のシナリオでは、元のコレクションのみが削除されます
        // 別のコピーセットを使用してトラバースするだけで、配列の範囲外の例外を回避できます。
        List<MstOrgEntity> copyList = new ArrayList<>(selectedOrg);

        for (MstOrgEntity mstOrgEntity : copyList) {
            findUpperOrgAndDeleteLowerOrg(selectedOrg, brandCd, mstOrgEntity);
        }

        // キャッシュ内のデータをクリアします
        localCacheUpperOrg.remove();
        // 重複データを削除する、フロントエンドから渡されたパラメータは信頼できません
        // フロントエンドで選択されたデータが重複している場合は、次のように処理する必要があります。
        return selectedOrg.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 上位の組織を見つけて下位の組織を削除します
     *
     * @param orgList  選択した組織
     * @param brandCd  ブランドコード
     * @param lowerOrg 下位組織
     */
    private boolean findUpperOrgAndDeleteLowerOrg(List<MstOrgEntity> orgList, String brandCd, MstOrgEntity lowerOrg) {

        MstOrgEntity upperOrg = getUpperOrg(brandCd, lowerOrg);

        // トップ組織は存在しません、方法の終わり
        if (upperOrg == null) {
            return false;
        }

        if (orgList.contains(upperOrg)) {
            // [選択した組織]セットに上位組織がある場合は、現在の下位組織を削除してください
            orgList.remove(lowerOrg);
            return true;
        }

        // [選択した組織]セットに上位の組織がない場合は、引き続き上位の組織があるかどうかを確認してください
        boolean isExist = findUpperOrgAndDeleteLowerOrg(orgList, brandCd, upperOrg);
        if (isExist) {
            orgList.remove(lowerOrg);
        }
        return isExist;
    }

    /**
     * <p>上位組織を取得する</p>
     * <p>上位の組織がすでにキャッシュに存在する場合は、直接取り出します</p>
     */
    private MstOrgEntity getUpperOrg(String brandCd, MstOrgEntity lowerOrg) {
        MstOrgEntity upperOrg;
        HashMap<String, MstOrgEntity> cacheUpperOrg = localCacheUpperOrg.get();

        String upperOrgId = lowerOrg.getUpplevOrgId();

        if (cacheUpperOrg.containsKey(upperOrgId)) {
            upperOrg = cacheUpperOrg.get(upperOrgId);
        } else {
            // 現在の組織に基づいて上位レベルの組織情報を照会する
            upperOrg = commonDao.findUpperOrg(lowerOrg, brandCd);
            // 組織キャッシュに参加して、データベースクエリの数を減らします
            cacheUpperOrg.put(upperOrgId, upperOrg);
        }
        return upperOrg;
    }

    /**
     * デバイスTOKEN管理(2020/11/10楊)
     *
     * @param deviceToken
     * @param mstUsrEntity
     * @return
     */
    @Override
    public R updateDeviceToken(String deviceToken, MstUsrEntity mstUsrEntity, String phoneType, HttpServletRequest request) {
        R info = new R();
        //返信JSON１．Code　＝　「200: 成功」,返信JSON１．okType　＝　1  /* 1103 NWT文　要件変更 */
        info.put("code", 200);
        info.put("okType", 1);
        info.put("userId", mstUsrEntity.getUsrId());
        info.put("roleDiv", StringUtils.trim(mstUsrEntity.getRoleDiv()));
        info.put("key", "");
        if (StringUtils.isNotBlank(deviceToken) && StringUtils.isNotBlank(phoneType)) {
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = mstDeviceTokenService.list(
                    new QueryWrapper<MstDeviceTokenEntity>().eq("device_token", deviceToken));
            if (mstDeviceTokenEntityList.size() >= 1) {
                if (mstDeviceTokenEntityList.size() > 1) {
                    /* 2021/09/15 manamiru1-772 cuikl del start */
                    /* 2021/09/15 manamiru1-772 cuikl del end */
                    for (int i = 0; i < mstDeviceTokenEntityList.size(); i++) {
                        mstDeviceTokenService.removeById(mstDeviceTokenEntityList.get(i).getId());
                    }
                    MstDeviceTokenEntity dtoForInsert = new MstDeviceTokenEntity();
                    dtoForInsert.setUsrId(mstUsrEntity.getUsrId());
                    dtoForInsert.setDeviceToken(deviceToken);
                    dtoForInsert.setPhoneTypeDiv(phoneType);
                    dtoForInsert.setCretDatime(DateUtils.getSysTimestamp());
                    dtoForInsert.setCretUsrId(mstUsrEntity.getUsrId());
                    dtoForInsert.setUpdDatime(DateUtils.getSysTimestamp());
                    dtoForInsert.setUpdUsrId(mstUsrEntity.getUsrId());
                    dtoForInsert.setDelFlg(0);
                    mstDeviceTokenService.save(dtoForInsert);
                } else {
                    /* 2021/09/15 manamiru1-772 cuikl del start */
                    /* 2021/09/15 manamiru1-772 cuikl del end */
                    if (mstDeviceTokenEntityList.get(0).getDelFlg() == 1) {
                        return R.error(500, "このデバイスは無効になりました");
                    } else {
                        mstDeviceTokenEntityList.get(0).setPhoneTypeDiv(phoneType);
                        mstDeviceTokenEntityList.get(0).setUsrId(mstUsrEntity.getUsrId());
                        mstDeviceTokenEntityList.get(0).setUpdDatime(DateUtils.getSysTimestamp());
                        mstDeviceTokenEntityList.get(0).setUsrId(mstUsrEntity.getUsrId());
                        mstDeviceTokenService.update(mstDeviceTokenEntityList.get(0),
                                new QueryWrapper<MstDeviceTokenEntity>().eq("id", mstDeviceTokenEntityList.get(0).getId()));
                    }
                }
            } else {
                /* 2021/09/15 manamiru1-772 cuikl del start */
                /* 2021/09/15 manamiru1-772 cuikl del end */
                MstDeviceTokenEntity mstDeviceTokenEntity1 = new MstDeviceTokenEntity();
                //ユーザID
                mstDeviceTokenEntity1.setUsrId(mstUsrEntity.getUsrId());
                //デバイスTOKEN
                mstDeviceTokenEntity1.setDeviceToken(deviceToken);
                //端末類型区分
                mstDeviceTokenEntity1.setPhoneTypeDiv(phoneType);
                //作成日時
                mstDeviceTokenEntity1.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstDeviceTokenEntity1.setCretUsrId("sys");
                //更新日時
                mstDeviceTokenEntity1.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstDeviceTokenEntity1.setUpdUsrId("sys");
                //削除フラグ
                mstDeviceTokenEntity1.setDelFlg(0);
                //登録
                mstDeviceTokenService.save(mstDeviceTokenEntity1);
            }
            //            List<MstDeviceTokenEntity> deviceTokenEntityList = mstDeviceTokenService.list(
            //                    new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", mstUsrEntity.getUsrId()).ne("device_token", deviceToken));
            //            for (int i = 0; i < deviceTokenEntityList.size(); i++) {
            //                MstDeviceTokenEntity mstDeviceTokenEntity = new MstDeviceTokenEntity();
            //                mstDeviceTokenEntity.setUsrId("");
            //                mstDeviceTokenEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //                mstDeviceTokenEntity.setUpdUsrId(mstUsrEntity.getUsrId());
            //                mstDeviceTokenService.update(mstDeviceTokenEntity, new QueryWrapper<MstDeviceTokenEntity>().eq("id", deviceTokenEntityList.get(i).getId()));
            //            }
        } else {
            // add at 2021/11/19 for V9.02 by NWT HuangXL START
            deviceToken = Optional.ofNullable((String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_DEVICETOKEN)).orElse("");
            String userId = ShiroUtils.getUserId();
            /* 2021/09/15 manamiru1-772 cuikl del start */
            /* 2021/09/15 manamiru1-772 cuikl del end */
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList =
                    mstDeviceTokenService.list(new QueryWrapper<MstDeviceTokenEntity>().eq("device_token", deviceToken).orderByDesc("upd_datime"));
            // add at 2021/11/24 for V9.02 by NWT HuangXL START
//            logger.info("deviceToken" + deviceToken);
            // add at 2021/11/24 for V9.02 by NWT HuangXL END
            if (mstDeviceTokenEntityList.size() > 0) {
                phoneType = mstDeviceTokenEntityList.get(0).getPhoneTypeDiv();
//                deviceToken = mstDeviceTokenEntityList.get(0).getDeviceToken();
                for (MstDeviceTokenEntity dto : mstDeviceTokenEntityList) {
                    // add at 2021/11/22 for V9.02 by NWT HuangXL START
                    dto.setUsrId(mstUsrEntity.getUsrId());
                    dto.setUpdUsrId(mstUsrEntity.getUsrId());
                    dto.setUpdDatime(DateUtils.getSysTimestamp());
                    dto.setDelFlg(0);
                    // add at 2021/11/22 for V9.02 by NWT HuangXL END
                }
                mstDeviceTokenService.updateBatchById(mstDeviceTokenEntityList);
            }
            // add at 2021/11/19 for V9.02 by NWT HuangXL END
        }
        // modify at 2021/09/15 for V9.02 by NWT wen START
//        List<MstDeviceTokenEntity> deviceTokenEntityList = mstDeviceTokenService.list(
//                new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", mstUsrEntity.getUsrId()).ne("device_token", deviceToken));
//        for (int i = 0; i < deviceTokenEntityList.size(); i++) {
//            MstDeviceTokenEntity mstDeviceTokenEntity = new MstDeviceTokenEntity();
//            mstDeviceTokenEntity.setUsrId("");
//            mstDeviceTokenEntity.setUpdDatime(DateUtils.getSysTimestamp());
//            mstDeviceTokenEntity.setUpdUsrId(mstUsrEntity.getUsrId());
//            mstDeviceTokenService.update(mstDeviceTokenEntity, new QueryWrapper<MstDeviceTokenEntity>().eq("id", deviceTokenEntityList.get(i).getId()));
//        }
        // modify at 2021/09/15 for V9.02 by NWT wen END
        //取得したユーザIDとロール区分をもとに、ユーザ名を取得する
        String userName = "";
        Integer unReadCount = 0;
        switch (mstUsrEntity.getRoleDiv().trim()) {
            //「ロール区分」が「1：管理者」の場合
            case "1":
                MstManagerEntity mstManagerEntity = mstManagerService.getOne(
                        new QueryWrapper<MstManagerEntity>().eq("mgr_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //管理者基本マスタ．姓名
                userName = mstManagerEntity == null ? "" : mstManagerEntity.getFlnmNm() + " " + mstManagerEntity.getFlnmLnm();
                unReadCount = f21017Service.getUnreadCount(mstUsrEntity.getRoleDiv().trim(), mstUsrEntity.getUsrId(), mstUsrEntity.getOrgId(), DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                break;
            //「ロール区分」が「2：メンター」の場合
            case "2":
                MstMentorEntity mstMentorEntity = mstMentorService.getOne(
                        new QueryWrapper<MstMentorEntity>().eq("mentor_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //メンター基本マスタ．姓名
                userName = mstMentorEntity == null ? "" : mstMentorEntity.getFlnmNm() + " " + mstMentorEntity.getFlnmLnm();
                unReadCount = f21017Service.getUnreadCount(mstUsrEntity.getRoleDiv().trim(), mstUsrEntity.getUsrId(), mstUsrEntity.getOrgId(), DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                break;
            //「ロール区分」が「3：保護者」の場合
            case "3":
                MstGuardEntity mstGuardEntity = mstGuardService.getOne(
                        new QueryWrapper<MstGuardEntity>().eq("guard_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //保護者基本マスタ．姓名
                userName = mstGuardEntity == null ? "" : mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm();
                unReadCount = pushUnreadCount(mstUsrEntity.getUsrId());
                break;
            //「ロール区分」が「4：生徒」の場合
            case "4":
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //生徒基本マスタ．姓名
                userName = mstStuEntity == null ? "" : mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                unReadCount = f11010Service.getUnreadCount(mstUsrEntity.getUsrId());
                break;
        }
        // add at 2021/11/18 for V9.02 by NWT HuangXL START
        deleteRedundantDeviceToken(mstUsrEntity.getUsrId());
        // add at 2021/11/18 for V9.02 by NWT HuangXL END
        try {
            JSONObject node = new JSONObject();
            try {
                //端末のdeviceToken
                node.put("deviceToken", deviceToken);
                //token
                node.put("token", token);
                //デバイスの種類
                node.put("type", phoneType);
                //ユーザーCd
                node.put("userCd", mstUsrEntity.getAfterUsrId());
                //ユーザーId
                node.put("userId", mstUsrEntity.getUsrId());
                //ユーザー名
                node.put("userName", userName);
            } catch (JSONException e1) {
                logger.error(e1.getMessage());
            }
            R result = contextLoads(request, node, 0);
            //返信JSON．Code　＝　「200：成功」の場合
            if ((Integer) result.get("code") == 200) {
                logger.info("deviceId:" + result.get("deviceId").toString());
                MstDeviceTokenEntity mstDeviceTokenEntity = new MstDeviceTokenEntity();
                //返信JSON．data.deviceId
                mstDeviceTokenEntity.setDeviceId(Integer.valueOf(result.get("deviceId").toString()));
                //更新日時
                mstDeviceTokenEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstDeviceTokenEntity.setUpdUsrId("sys");
                //更新
                // modify at 2021/10/21 for V9.02 by NWT Lgx START
//                mstDeviceTokenService.update(
//                        mstDeviceTokenEntity, new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                mstDeviceTokenService.update(
                        mstDeviceTokenEntity,
                        new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", mstUsrEntity.getUsrId()).eq("device_token", deviceToken).eq("del_flg", 0));
                // modify at 2021/10/21 for V9.02 by NWT Lgx END
                //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                List<String> deviceIdList = new ArrayList<>();
                F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                //デバイスIDを取得する。
                f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                f09005DeviceDto.setUnreadcount(unReadCount);
                f09005DeviceDto.setStuId("");
                f09005DeviceDto.setStuname("");
                deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
                //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                SendMessageDto sendMessageDto = new SendMessageDto();
                //メッセージの備考内容。規則：最大桁数255。
                sendMessageDto.setComment(null);
                //受信先デバイスIDの集合、必須項目。
                sendMessageDto.setDeviceList(deviceIdList);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("msgId", null);
                map.put("msgType", Constant.sendMsgTypeUnread);
                sendMessageDto.setParams(JSON.toJSONString(map));
                // メッセージに含まれる画像のリンク
                sendMessageDto.setImgUrl(null);
                //コードマスタより取得する、ブランドごとに自由に設定できる。
                sendMessageDto.setMessage(null);
                //デフォルト値5にする
                sendMessageDto.setPriority(1);
                //メッセージのタイトル
                sendMessageDto.setTitle("unread");
                //アプリケーションを識別できる唯一秘密鍵で
                sendMessageDto.setToken(token);
                //通知プッシュの起止時間と処理時間をログで記録する
                Timestamp sTime = DateUtils.getSysTimestamp();
                logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
                noticeApiService.sendMessage(JSON.toJSONString(sendMessageDto));
                Timestamp eTime = DateUtils.getSysTimestamp();
                Long cTime = eTime.getTime() - sTime.getTime();
                logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
                logger.info("プッシュ通知処理時間：<" + cTime + ">");
            } else {
//                logger.info("NWT 通知アプリ接続失敗しました。"+ "code:"+ result.get("code") + "," + "message:"+ result.get("message"));
                info = result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return info;
    }

    @Override
    public Integer pushUnreadCount(String guardId) {
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", guardId).eq("del_flg", 0));
        Integer unreadForAll = 0;
        if (mstUsrEntity != null) {
            String tchCdFlg = "0";
            String username = StringUtils.equals("0", mstUsrEntity.getGidFlg()) ? mstUsrEntity.getAfterUsrId() : mstUsrEntity.getGidpk();
            String brandCd = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "URL_ADD_KEY").eq("del_flg", 0)).getCodValue();
            List<SysUserEntity> userEntityList = shiroService.getUserByLoginId(username, "1", brandCd, mstUsrEntity.getGidFlg(), tchCdFlg);
            /* 2021/09/15 manamiru1-772 cuikl del start */
            /* 2021/09/15 manamiru1-772 cuikl del end */
            List<String> guardIds = new ArrayList<>();
            //複数教室
            for (MstUsrEntity user : userEntityList) {
                guardIds.add(user.getUsrId());
            }
            String guards = String.join(",", guardIds);
            List<F30002Dto> stuMstEntities = f30002Service.getStudents(guards);

            for (F30002Dto stuMstEntity : stuMstEntities) {
                unreadForAll += stuMstEntity.getUnReadCount();
            }
        }
        return unreadForAll;
    }

    /**
     * tokenを取得する
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String getToken() {
        String appToken = token;
        return appToken;
    }

    /**
     * ドメイン間の要求方法
     *
     * @param request
     * @param node
     * @param flg
     * @return
     * @throws UnknownHostException
     */
    @Override
    public R contextLoads(HttpServletRequest request, JSONObject node, Integer flg) {
        R info = new R();
        URL connect;
        StringBuffer data = new StringBuffer();
        try {
            String url = appLoginCheck;
            if (flg == 1 || flg == 2) {
                url = flg == 1 ? unReadGet + "?token=" + node.get("token") + "&deviceId=" + node.get("deviceId") : unReadGet;
            }
            connect = new URL(url);
            /* 2021/09/15 manamiru1-772 cuikl del start */
            /* 2021/09/15 manamiru1-772 cuikl del end */
            if (flg == 0 || flg == 2) {
                try {
                    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(node));
                    Request request1 = new Request.Builder().url(url).post(body).build();
                    Response response = client.newCall(request1).execute();
                    String string = response.body().string();
                    /* 2021/09/15 manamiru1-772 cuikl del start */
                    /* 2021/09/15 manamiru1-772 cuikl del end */
                    Map<String, String> paramsMap = JSON.parseObject(string, Map.class);
                    if ("200".equals(String.valueOf(paramsMap.get("code")))) {
                        String deviceId = JSON.parseObject(JSON.toJSON(paramsMap.get("data")).toString(), Map.class).get("deviceId").toString();
                        info.put("deviceId", deviceId).put("code", paramsMap.get("code"));
                    } else {
//                        info.put("message", paramsMap.get("message")).put("code", paramsMap.get("code"));
                        info.put("code", 500).put("message", "NWT 通知アプリ接続失敗しました。");
                        logger.error("code:" + paramsMap.get("code") + "," + "message:" + paramsMap.get("message"));
                    }
                } catch (Exception e) {
                    info.put("code", 500).put("message", "NWT 通知アプリ接続失敗しました。");
                    logger.error(e.getMessage());
                }
            } else {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(request.getLocalAddr(), Integer.valueOf("8080")));
                HttpsURLConnection connection = (HttpsURLConnection) connect.openConnection(proxy);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    data.append(line);
                    info.put("data", data);
                }
                br.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return info;
    }

    @Override
    public String getOrgIdAdd() {
        String orgIdAdd = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ORG_ID_ADD").eq("cod_cd", "1")).getCodValue2();
        return orgIdAdd;
    }

    //2021/01/22 liyuhuan modify start
    @Override
    public String getOrgIdLower() {
        String orgIdAdd = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ORG_ID_ADD").eq("cod_cd", "1")).getCodValue();
        return orgIdAdd;
    }
//2021/01/22 liyuhuan modify end

    /**
     * @return
     */
    @Override
    public MstCodDEntity getServer() {
        return commonDao.selectServer();
    }

    /**
     * @param year
     * @return
     */
    @Override
    public List<MstHolidayEntity> selectHolidayByTgtYmd(String year) {
        return commonDao.getHolidayByTgtYmd(year);
    }

    /* 2021/02/18 4-29 add start */

    /**
     * QRコード暫定対応
     * 暫定対応
     */
    @Override
    public Integer pushUnreadCountForQR(String guardId) {
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", guardId).eq("del_flg", 0));
        String username = null;
        Integer unreadForAll = 0;
        if (mstUsrEntity != null) {
            String tchCdFlg = "0";
            if (StringUtils.isEmpty((String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD))){
                username = StringUtils.equals("0", mstUsrEntity.getGidFlg()) ? mstUsrEntity.getAfterUsrId() : mstUsrEntity.getGidpk();
            }else {
                username = (String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
                tchCdFlg = "1";
            }
            String brandCd = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "URL_ADD_KEY").eq("del_flg", 0)).getCodValue();
            List<SysUserEntity> userEntityList = shiroService.getUserByLoginId(username, "1", brandCd, mstUsrEntity.getGidFlg(), tchCdFlg);
            /* 2021/09/15 manamiru1-772 cuikl del start */
            /* 2021/09/15 manamiru1-772 cuikl del end */
            //複数教室
            for (MstUsrEntity user : userEntityList) {
                List<F30002Dto> stuMstEntities = f30002Service.getStudentsCount(user.getUsrId());
                for (F30002Dto stuMstEntity : stuMstEntities) {
                    unreadForAll += stuMstEntity.getUnReadCount();
                }
            }
        }
        return unreadForAll;
    }
    /* 2021/02/18 4-29 add end */

    /* 2021/02/20 4-29 add start */

    /**
     * @param deviceToken
     * @param mstUsrEntity
     * @param phoneType
     * @param request
     * @return
     */
    @Override
    public R updateDeviceTokenForPUSH(String deviceToken, MstUsrEntity mstUsrEntity, String phoneType, HttpServletRequest request,String userIdFake) {
        R info = new R();
        //返信JSON１．Code　＝　「200: 成功」,返信JSON１．okType　＝　1  /* 1103 NWT文　要件変更 */
        info.put("code", 200);
        info.put("okType", 1);
        // add at 2021/12/01 for V9.02 by NWT HuangXL START
        info.put("userId", userIdFake + "," + deviceToken);
        // add at 2021/12/01 for V9.02 by NWT HuangXL END
        info.put("roleDiv", StringUtils.trim(mstUsrEntity.getRoleDiv()));
        info.put("key", "");
        if (StringUtils.isNotBlank(deviceToken) && StringUtils.isNotBlank(phoneType)) {
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = mstDeviceTokenService.list(
                    new QueryWrapper<MstDeviceTokenEntity>().eq("device_token", deviceToken));
            if (mstDeviceTokenEntityList.size() >= 1) {
                if (mstDeviceTokenEntityList.size() > 1) {
                    /* 2021/09/15 manamiru1-772 cuikl del start */
                    /* 2021/09/15 manamiru1-772 cuikl del end */
                    for (int i = 0; i < mstDeviceTokenEntityList.size(); i++) {
                        mstDeviceTokenService.removeById(mstDeviceTokenEntityList.get(i).getId());
                    }
                    MstDeviceTokenEntity dtoForInsert = new MstDeviceTokenEntity();
                    dtoForInsert.setUsrId(mstUsrEntity.getUsrId());
                    dtoForInsert.setDeviceToken(deviceToken);
                    dtoForInsert.setPhoneTypeDiv(phoneType);
                    dtoForInsert.setCretDatime(DateUtils.getSysTimestamp());
                    dtoForInsert.setCretUsrId(mstUsrEntity.getUsrId());
                    dtoForInsert.setUpdDatime(DateUtils.getSysTimestamp());
                    dtoForInsert.setUpdUsrId(mstUsrEntity.getUsrId());
                    dtoForInsert.setDelFlg(0);
                    mstDeviceTokenService.save(dtoForInsert);
                } else {
				
                	/* 2021/09/15 manamiru1-772 cuikl del start */
                    /* 2021/09/15 manamiru1-772 cuikl del end */
                    // modify at 2021/9/30 for V9.02 by NWT HuangXL START
//                    if (mstDeviceTokenEntityList.get(0).getDelFlg() == 1) {
//                        return R.error(500, "このデバイスは無効になりました");
//                    } else {
                    mstDeviceTokenEntityList.get(0).setPhoneTypeDiv(phoneType);
                    mstDeviceTokenEntityList.get(0).setUsrId(mstUsrEntity.getUsrId());
                    mstDeviceTokenEntityList.get(0).setUpdDatime(DateUtils.getSysTimestamp());
                    mstDeviceTokenEntityList.get(0).setUsrId(mstUsrEntity.getUsrId());
                    mstDeviceTokenEntityList.get(0).setDelFlg(0);
                    mstDeviceTokenService.update(mstDeviceTokenEntityList.get(0),
                            new QueryWrapper<MstDeviceTokenEntity>().eq("id", mstDeviceTokenEntityList.get(0).getId()));
//                    }
                    // modify at 2021/9/30 for V9.02 by NWT HuangXL END
                }
            } else {
                /* 2021/09/15 manamiru1-772 cuikl del start */
                /* 2021/09/15 manamiru1-772 cuikl del end */
                MstDeviceTokenEntity mstDeviceTokenEntity1 = new MstDeviceTokenEntity();
                //ユーザID
                mstDeviceTokenEntity1.setUsrId(mstUsrEntity.getUsrId());
                //デバイスTOKEN
                mstDeviceTokenEntity1.setDeviceToken(deviceToken);
                //端末類型区分
                mstDeviceTokenEntity1.setPhoneTypeDiv(phoneType);
                //作成日時
                mstDeviceTokenEntity1.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstDeviceTokenEntity1.setCretUsrId("sys");
                //更新日時
                mstDeviceTokenEntity1.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstDeviceTokenEntity1.setUpdUsrId("sys");
                //削除フラグ
                mstDeviceTokenEntity1.setDelFlg(0);
                //登録
                mstDeviceTokenService.save(mstDeviceTokenEntity1);
            }
        } else {
            String userId = ShiroUtils.getUserId();
            /* 2021/09/15 manamiru1-772 cuikl del start */
            /* 2021/09/15 manamiru1-772 cuikl del end */
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = mstDeviceTokenService.list(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", userId));
            if (mstDeviceTokenEntityList.size() > 0) {
                phoneType = mstDeviceTokenEntityList.get(0).getPhoneTypeDiv();
                for (MstDeviceTokenEntity dto : mstDeviceTokenEntityList) {
                    dto.setUsrId(mstUsrEntity.getUsrId());
                    deviceToken = dto.getDeviceToken();
                }
                mstDeviceTokenService.updateBatchById(mstDeviceTokenEntityList);
            }
        }
        // modify at 2021/08/16 for V9.02 by NWT wen START
        List<MstDeviceTokenEntity> deviceTokenEntityList = mstDeviceTokenService.list(
                new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
        if (!deviceTokenEntityList.isEmpty()) {
            info.put("okType", 2);
        }
//        for (int i = 0; i < deviceTokenEntityList.size(); i++) {
//            MstDeviceTokenEntity mstDeviceTokenEntity = new MstDeviceTokenEntity();
//            mstDeviceTokenEntity.setUsrId("");
//            mstDeviceTokenEntity.setUpdDatime(DateUtils.getSysTimestamp());
//            mstDeviceTokenEntity.setUpdUsrId(mstUsrEntity.getUsrId());
//            mstDeviceTokenService.update(mstDeviceTokenEntity, new QueryWrapper<MstDeviceTokenEntity>().eq("id", deviceTokenEntityList.get(i).getId()));
//        }
        // modify at 2021/08/16 for V9.02 by NWT wen END
        // add at 2021/09/15 for V9.02 by NWT wen START
        if (!deviceTokenEntityList.isEmpty() && deviceTokenEntityList.size() >= 2) {
            String usrId = mstUsrEntity.getUsrId();
            String mailAddress = "";
            switch (StringUtils.trim(mstUsrEntity.getRoleDiv())) {
                // 管理者
                case "1":
                    MstManagerEntity manager = mstManagerService.getOne(
                            new QueryWrapper<MstManagerEntity>().select("mailad").eq("mgr_id", usrId).eq("del_flg", 0));
                    // ログイン者情報を取得できない場合、
                    if (manager == null) {
                        manager = new MstManagerEntity();
                        logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
                    }
                    mailAddress = manager.getMailad();
                    break;
                // 先生
                case "2":
                    MstMentorEntity mentor = mstMentorService.getOne(
                            new QueryWrapper<MstMentorEntity>().select("mailad").eq("mentor_id", usrId).eq("del_flg", 0));
                    // ログイン者情報を取得できない場合、
                    if (mentor == null) {
                        mentor = new MstMentorEntity();
                        logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
                    }
                    mailAddress = mentor.getMailad();
                    break;
                // 保護者
                case "3":
                    MstGuardEntity guard = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().select("mailad").eq("guard_id", usrId).eq("del_flg", 0));
                    // ログイン者情報を取得できない場合、
                    if (guard == null) {
                        guard = new MstGuardEntity();
                        logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
                    }
                    mailAddress = guard.getMailad();
                    break;
                // 生徒
                case "4":
                    MstStuEntity student = mstStuService.getOne(new QueryWrapper<MstStuEntity>().select("guard_id").eq("stu_id", usrId).eq("del_flg", 0));
                    // ログイン者情報を取得できない場合、
                    if (student == null) {
                        student = new MstStuEntity();
                        logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
                    }
                    MstGuardEntity guardMail = new MstGuardEntity();
                    // 保護者が存在する場合、
                    if (StringUtils.isNotBlank(student.getGuardId())) {
                        guardMail = mstGuardService.getOne(
                                new QueryWrapper<MstGuardEntity>().select("mailad").eq("guard_id", student.getGuardId()).eq("del_flg", 0));
                        //保護者情報が取得できない場合、
                        if (guardMail == null) {
                            guardMail = new MstGuardEntity();
                            logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
                        }
                    }
                    mailAddress = guardMail.getMailad();
                    break;
            }
            if (StringUtils.isNotBlank(mailAddress)) { // メールアドレスがある場合、メールを送信する。
                MstCodDEntity mailInfo = mstCodDService.getOne(
                        new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2").eq("cod_key", "MAIL_PUSH_ALERT_MSG").eq("del_flg", 0));
                if (mailInfo == null) {//取得できない場合、メール送信処理をスキップして、処理を継続する。
                    logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
                } else {
                    try {
                        String title = mailInfo.getCodValue2();
                        StringBuffer sb = new StringBuffer();
                        sb.append("<p style='color:#000000'>" + mailInfo.getCodValue() + "</p>");
                        mailUtils.sendMail(mailAddress, title, sb.toString());
                    } catch (Exception e) {// 配信失敗の場合、
                        return R.error(501, "メール送信が失敗しました。");
                    }
                }
            } else {//メールアドレスが設定されない場合、
                logger.warn("ユーザID：" + usrId + "へアラートメールを送信できない。");
            }
        }
        // add at 2021/09/16 for V9.02 by NWT wen END
        //取得したユーザIDとロール区分をもとに、ユーザ名を取得する
        String userName = "";
        Integer unReadCount = 0;
        switch (mstUsrEntity.getRoleDiv().trim()) {
            //「ロール区分」が「1：管理者」の場合
            case "1":
                MstManagerEntity mstManagerEntity = mstManagerService.getOne(
                        new QueryWrapper<MstManagerEntity>().eq("mgr_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //管理者基本マスタ．姓名
                userName = mstManagerEntity == null ? "" : mstManagerEntity.getFlnmNm() + " " + mstManagerEntity.getFlnmLnm();
                unReadCount = f21017Service.getUnreadCount(mstUsrEntity.getRoleDiv().trim(), mstUsrEntity.getUsrId(), mstUsrEntity.getOrgId(), DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                break;
            //「ロール区分」が「2：メンター」の場合
            case "2":
                MstMentorEntity mstMentorEntity = mstMentorService.getOne(
                        new QueryWrapper<MstMentorEntity>().eq("mentor_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //メンター基本マスタ．姓名
                userName = mstMentorEntity == null ? "" : mstMentorEntity.getFlnmNm() + " " + mstMentorEntity.getFlnmLnm();
                unReadCount = f21017Service.getUnreadCount(mstUsrEntity.getRoleDiv().trim(), mstUsrEntity.getUsrId(), mstUsrEntity.getOrgId(), DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                break;
            //「ロール区分」が「3：保護者」の場合
            case "3":
                MstGuardEntity mstGuardEntity = mstGuardService.getOne(
                        new QueryWrapper<MstGuardEntity>().eq("guard_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //保護者基本マスタ．姓名
                userName = mstGuardEntity == null ? "" : mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm();
                unReadCount = pushUnreadCountForQR(mstUsrEntity.getUsrId());
                break;
            //「ロール区分」が「4：生徒」の場合
            case "4":
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", mstUsrEntity.getUsrId()).eq("del_flg", 0));
                //生徒基本マスタ．姓名
                userName = mstStuEntity == null ? "" : mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                unReadCount = f11010Service.getUnreadCount(mstUsrEntity.getUsrId());
                break;
        }
        // add at 2021/11/18 for V9.02 by NWT HuangXL START
        deleteRedundantDeviceToken(mstUsrEntity.getUsrId());
        // add at 2021/11/18 for V9.02 by NWT HuangXL END
        try {
            JSONObject node = new JSONObject();
            try {
                //端末のdeviceToken
                node.put("deviceToken", deviceToken);
                //token
                node.put("token", token);
                //デバイスの種類
                node.put("type", phoneType);
                //ユーザーCd
                node.put("userCd", mstUsrEntity.getAfterUsrId());
                //ユーザーId
                node.put("userId", mstUsrEntity.getUsrId());
                //ユーザー名
                node.put("userName", userName);
            } catch (JSONException e1) {
                logger.error(e1.getMessage());
            }
            R result = contextLoads(request, node, 0);
            //返信JSON．Code　＝　「200：成功」の場合
            if ((Integer) result.get("code") == 200) {
                /* 2021/09/15 manamiru1-772 cuikl del start */
                /* 2021/09/15 manamiru1-772 cuikl del end */
                MstDeviceTokenEntity mstDeviceTokenEntity = new MstDeviceTokenEntity();
                //返信JSON．data.deviceId
                mstDeviceTokenEntity.setDeviceId(Integer.valueOf(result.get("deviceId").toString()));
                //更新日時
                mstDeviceTokenEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstDeviceTokenEntity.setUpdUsrId("sys");
                //更新
                // modify at 2021/08/20 for V9.02 by NWT wen START
                mstDeviceTokenService.update(
                        mstDeviceTokenEntity,
                        new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", mstUsrEntity.getUsrId()).eq("device_token", deviceToken).eq("del_flg", 0));
                // modify at 2021/08/20 for V9.02 by NWT wen END
                //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                List<String> deviceIdList = new ArrayList<>();
                F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                //デバイスIDを取得する。
                f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                f09005DeviceDto.setUnreadcount(unReadCount);
                f09005DeviceDto.setStuId("");
                f09005DeviceDto.setStuname("");
                deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
                //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                SendMessageDto sendMessageDto = new SendMessageDto();
                //メッセージの備考内容。規則：最大桁数255。
                sendMessageDto.setComment(null);
                //受信先デバイスIDの集合、必須項目。
                sendMessageDto.setDeviceList(deviceIdList);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("msgId", null);
                map.put("msgType", Constant.sendMsgTypeUnread);
                sendMessageDto.setParams(JSON.toJSONString(map));
                // メッセージに含まれる画像のリンク
                sendMessageDto.setImgUrl(null);
                //コードマスタより取得する、ブランドごとに自由に設定できる。
                sendMessageDto.setMessage(null);
                //デフォルト値5にする
                sendMessageDto.setPriority(1);
                //メッセージのタイトル
                sendMessageDto.setTitle("unread");
                //アプリケーションを識別できる唯一秘密鍵で
                sendMessageDto.setToken(token);
                //通知プッシュの起止時間と処理時間をログで記録する
                Timestamp sTime = DateUtils.getSysTimestamp();
                logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
                noticeApiService.sendMessage(JSON.toJSONString(sendMessageDto));
                Timestamp eTime = DateUtils.getSysTimestamp();
                Long cTime = eTime.getTime() - sTime.getTime();
                logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
                logger.info("プッシュ通知処理時間：<" + cTime + ">");
            } else {
                info = result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return info;
    }
    /* 2021/02/20 4-29 add end */

    // add at 2021/9/29 for V9.02 by NWT HuangXL START
    @Override
    public void deleteRedundantDeviceToken(String usrId) {
        noticeApiDao.updateDelFlgByUsrId(usrId);
    }
    // add at 2021/9/29 for V9.02 by NWT HuangXL END
}