package jp.learningpark.modules.com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstDeviceTokenDao;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.sys.controller.SysLoginController;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import jp.learningpark.modules.sys.shiro.token.UserAuthToken;
import okhttp3.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 通知アプリ連携API
 *
 * @author NWT 楊
 */
@Service
public class NoticeApiServiceImpl<builder> implements NoticeApiService {
    /**
     * shiroサービス
     */
    @Autowired
    ShiroService shiroService;
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
     * 生徒基本マスタService
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * デバイスTOKEN管理Service
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;

    /**
     * プッシュ失敗データService
     */
    @Autowired
    PushErrDatService pushErrDatService;
    /**
     * 組織マスタ
     */
    @Autowired
    MstOrgService mstOrgService;
    /**
     * 組織マスタ
     */
    @Autowired
    MstLoginService mstLoginService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    CommonService commonService;

    @Autowired
    SysLoginController sysLoginController;
    /**
     * ユーザ基本マスタ
     */
    @Autowired
    MstUsrService mstUsrService;
    @Value("${ans-url.NOTICE_REMOTE_URL_appLoginCheck}")
    String appLoginCheck;
    @Value("${ans-url.NOTICE_REMOTE_URL_unReadGet}")
    String unReadGet;
    @Value("${ans-url.NOTICE_REMOTE_URL_sendMessage}")
    String sendMessage;
    @Value("${ans-url.NOTICE_REMOTE_URL_errorData}")
    String errorData;
    @Value("${ans-url.token}")
    String token;
    @Value("${learningpark-domain.domain}")
    String domain;

    @Autowired
    MstDeviceTokenDao mstDeviceTokenDao;
    /**
     * セッションタイムアウト時間（生徒）
     */
    @Value("${shiro.session.expireTimeStu}")
    private Integer expireTimeStu;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(50000, TimeUnit.MILLISECONDS)
            .connectTimeout(50000,TimeUnit.MILLISECONDS)
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();

    /**
     * ブランド取得API
     *
     * @param type
     * @return
     */
    @Override
    public R getBrandCd(String type, HttpServletRequest request) {
        R info = new R();
        try {
            //  NWT文　1103　要件変更
            info.put("code", 200).put("url", "/common/F40006.html?brandCd=manamiru").put("msg", "");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error(500, "その他エラー");
        }
        return info;
    }

    /**
     * 通知アプリのログインAPI
     * <p>
     * update 1103 NWT文　要件変更
     *
     * @param loginId 送信JSON１．loginId
     * @param password マナミルパスワード
     * @param deviceToken アプリ端末のdeviceToken
     * @param phoneType 端末のタイプ
     * @param request
     * @return
     */
    @Override
    public R appLoginCheck(String loginId, String password, String deviceToken, String phoneType, HttpServletRequest request, HttpServletResponse response) {
        R info = new R();
        info.remove("msg");
        // add at 2021/12/01 for V9.02 by NWT HuangXL START
        if (StringUtils.isEmpty(deviceToken)){
            return R.error(401, GakkenConstant.DEVICE_TOKEN_NULL_MSG).put("okType", 0);
        }
        // add at 2021/12/01 for V9.02 by NWT HuangXL END
        logger.info("deviceToken" + deviceToken);
        String brandCode = "";
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_DEVICETOKEN, deviceToken);
        /* 2021/09/15 manamiru1-772 cuikl del start */
        /* 2021/09/15 manamiru1-772 cuikl del end */
        ShiroUtils.setSessionAttribute("phone_type", phoneType);
        CM0003.LoginCheckResultDto loginCheckResultDto;
        String loginUrl = request.getRequestURL().toString();
        try {
            //ログインIDのGID認証処理
            loginCheckResultDto = CM0003.loginCheck(loginId, password);
            // 2021/1/5 huangxinliang modify start
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GID_FLG, loginCheckResultDto.getGidCheckFlag());
            // 2021/1/5 huangxinliang modify end
        } catch (LockedAccountException e) {
            logger.error(e.getMessage());
            return R.error(500, null).put("okType", 0);
        }
        R loginCheck = CM0006.loginCheck(loginCheckResultDto, loginId, "2", loginUrl);
        if ((Integer)loginCheck.get("code") != 0){
            // 2021/2/20 liguangxin modify start
            return R.error(401, null).put("okType", 0);
            // 2021/2/20 liguangxin modify end
        }
        if (StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getLoginCheckFlag())) {
            if (StringUtils.equals(loginCheckResultDto.getGidCheckFlag(), GakkenConstant.OK_FLAG)){
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_MAIL_ADDRESS, loginCheckResultDto.getMailad());
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GIDPK, loginCheckResultDto.getGakkenIDshort().getGakkenID().getGidpk());
                if (!StringUtils.isEmpty(loginCheckResultDto.getTchCd())){
                    ShiroUtils.setSessionAttribute(GakkenConstant.TCH_CD, loginCheckResultDto.getTchCd());
                }
            }
        }
        String gidFlg = ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG).toString();
        //NWT　楊　2021/06/30　MANAMIRU1-699 --> 727　Edit　Start
        String gidpk = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
        String tchCd = (String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
        String tchCdFlg = "0";
        Integer count = 0;
        if (StringUtils.equals("1",gidFlg)){
            if (StringUtils.isNotBlank(gidpk)){
                count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("gidpk",gidpk).eq("del_flg",0));
                loginId = count > 0 ? gidpk:loginId;
            }if (StringUtils.isNotBlank(tchCd) && count == 0){
                count = mstManagerService.count(new QueryWrapper<MstManagerEntity>().eq("tch_cd",tchCd).eq("del_flg",0));
                loginId = count > 0 ? tchCd:loginId;
                tchCdFlg = count > 0 ?"1":"0";
            }
        }
        //NWT　楊　2021/06/30　MANAMIRU1-699 --> 727　Edit　End
        /* 1103 NWT文　要件変更 */
        List<SysUserEntity> userEntityList = shiroService.getUserByLoginId(loginId, "1", null, gidFlg, tchCdFlg);
        SysUserEntity mstUsrEntity = null;
        if (userEntityList.size() == 0) {
            return R.error(500, null).put("okType", 0);
        } else if (userEntityList.size() >= 1) {
            // 塾時期チェック
            if (StringUtils.equals(userEntityList.get(0).getRoleDiv().trim(), "4")) {
                if ((StringUtils.equals(userEntityList.get(0).getBrandCd(),commonService.getOrgIdLower()))) {
                    return R.error(500,"お子さまのアカウントとなりますので、保護者さま用のアカウントにてログインしてください");
                }
                if (!sysLoginController.hasLearnPrd(userEntityList.get(0).getUsrId())) {
                    return  R.error(500, MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                }
            }
            // 2020/12/11 huangxinliang modify start
            ShiroUtils.setSessionAttribute(GakkenConstant.MAX_ERROR_COUNT, userEntityList.get(0).getErrorCount());
            // 2020/12/11 huangxinliang modify end
            /* 2021/09/15 manamiru1-772 cuikl del start */
            /* 2021/09/15 manamiru1-772 cuikl del end */
            mstUsrEntity = userEntityList.get(0);
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_PASSWORD, password);
            brandCode = mstUsrEntity.getBrandCd();
            R r = loginUser(mstUsrEntity, response);
            if (!StringUtils.equals("0", StringUtils.defaultString(r.get("code")))) {
                //返信JSON１．Code　＝　「500：失敗」,返信JSON１．okType　＝　0
                info = R.error(500, null).put("okType", 0);
            } else {
                //1.3　コードマスタ明細からURL中の付きキーを取得する。
                ShiroUtils.setSessionAttribute(GakkenConstant.MANA_FLAG, "1");
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, brandCode);
                // 画面入力したログインIDをセッションに格納する
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID, mstUsrEntity.getAfterUsrId());
                if (StringUtils.isNotBlank(deviceToken)){
                    //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
                    if (StringUtils.equals("1",gidFlg)){
                        if (StringUtils.isNotBlank(tchCd)){
                            count = mstManagerService.count(new QueryWrapper<MstManagerEntity>().eq("tch_cd",tchCd).eq("del_flg",0));
                        }
                        if (StringUtils.isNotBlank(gidpk)  && count == 0){
                            count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("gidpk",gidpk).eq("del_flg",0));
                        }
                    }else {
                        count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("after_usr_id",loginId).eq("del_flg",0));
                    }
                    if (count == 1){
                    //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End
                        String userId = userEntityList.get(0).getUsrId();
                        //NWT　楊　2021/08/24　MANAMIRU1-675　Edit　Start
                        userId = getUserId(userId,gidFlg,tchCdFlg);
                        //NWT　楊　2021/08/24　MANAMIRU1-675　Edit　End
                        /* 2021/02/20 4-29 start */
                        return commonService.updateDeviceTokenForPUSH(deviceToken,mstUsrEntity,phoneType,request,userId);
                    }else {
                        //NWT　楊　2021/08/24　MANAMIRU1-675　Edit　Start
                        loginId = getUserId(loginId,gidFlg,tchCdFlg);
                        //NWT　楊　2021/08/24　MANAMIRU1-675　Edit　ENd
                        info = commonService.updateDeviceTokenForPUSH(deviceToken,mstUsrEntity,phoneType,request,loginId);
                        /* 2021/02/20 4-29 end */
                        if ((Integer) info.get("code") == 200){
                            info.put("userId", "GID-" + loginId).put("roleDiv", "").put("key", "");
                            // add at 2021/11/19 for V9.02 by NWT HuangXL START
                            info.put("userId", info.get("userId") + "," + deviceToken);
                            // add at 2021/11/19 for V9.02 by NWT HuangXL END
                        }else {
                            return R.error(500,info.get("message").toString());
                        }
                    }

                }else {
                    //NWT　楊　2021/08/02　MANAMIRU1-675　Edit　Start
                    info.put("msg","アプリ端末のdeviceToken is null");
                    logger.info("msg:"+"アプリ端末のdeviceToken is null");
                    if (userEntityList.size() == 1){
                        String userId = mstUsrEntity.getUsrId();
                        userId = getUserId(userId,gidFlg,tchCdFlg);
                        info.put("code", 200);
                        info.put("okType", 1);
                        info.put("userId", userId);
                        info.put("roleDiv", StringUtils.trim(mstUsrEntity.getRoleDiv()));
                        info.put("key", "");
                    }else {
                        loginId = getUserId(loginId,gidFlg,tchCdFlg);
                        //NWT　楊　2021/08/02　MANAMIRU1-675　Edit　End
                        info.put("code", 200).put("okType", 1).put("userId", "GID-" + loginId).put("roleDiv", "").put("key", "");
                    }
                }
            }
        }
        info.remove("msg");
        /* 2021/09/15 manamiru1-772 cuikl del start */
        /* 2021/09/15 manamiru1-772 cuikl del end */
        return info;
    }

    /**
     * コードマスタ_明細Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * メッセージの備考内容の取得
     *
     * @return
     */
    @Override
    public String getMessageDetail(String codCd) {
        //コードマスタ_明細．コード値（プッシュメッセージ）
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "BLAND_MSG_DIV").eq("cod_cd", codCd).eq("del_flg", 0));
        //メッセージ内容
        String msgContent = mstCodDEntity.getCodValue();
        return msgContent;
    }

    /**
     * 未読/既読件数取得Api
     *
     * @param request
     * @param token 端末のdeviceToken
     * @param flg
     * @return
     */
    @Override
    public R unReadGet(HttpServletRequest request, String token, Integer flg) {
        R info = new R();
        //デバイスTOKEN管理からデバイスIDを取得
        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(
                new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        JSONObject node = new JSONObject();
        try {
            //token
            node.put("token", token);
            //取得したデバイスID
            node.put("deviceId", mstDeviceTokenEntity.getDeviceId());
            //既読データの取得
            if (flg == 2) {
                node.put("targetAppId", "3");
            }
            String returnData = contextLoads(request, node, flg).toString();
            Integer code = (Integer)JSON.parseObject(returnData, Map.class).get("code");
            //未読データの取得
            if (flg == 1) {
                Integer count = 0;
                if (code == 200) {
                    String data = JSON.parseObject(returnData).get("data").toString();
                    String record = JSON.parseObject(data).get("record").toString();
                    JSONArray array = (JSONArray)JSON.parse(record);
                    if (array.size() > 0) {
                        count = (Integer)((JSONObject)array.get(0)).get("unreadCount");
                    }
                    info.put("count", count);
                }
            } else {
                info.put("message", "");
            }
            info.put("code", code);
        } catch (JSONException e) {
            logger.error(e.getMessage());
        } catch (UnknownHostException e1) {
            logger.error(e1.getMessage());
        }
        return info;
    }

    /**
     * メッセージ送信
     *
     * @param data 送信data(JSON)
     * @return
     */
    @Override
    public R sendMessage(String data) {
        R info = new R();
        if (StringUtils.isNotBlank(data)){
            //ソースデータ取得
            String splitData = data;
            //ソースデータタイプ変換
            JSONArray sendMaps = (JSONArray) JSON.parseObject(data, Map.class).get("deviceList");
            if (sendMaps != null && sendMaps.size() > 0){
                //ソースデータ長
                Integer size = sendMaps.size();
                List<String> sendList = sendMaps.toJavaList(String.class);
                //グループ長
                Integer groupSize = GakkenConstant.SEND_GROUP_SIZE;
                int num = ( size + groupSize - 1 )/groupSize ;
                for (int i = 0; i < num; i++) {
                    int fromIndex = i * groupSize;
                    int toIndex = (i+1) * groupSize < size ? ( i+1 ) * groupSize : size;
                    JSONObject jo = JSONObject.parseObject(splitData);
                    jo.put("deviceList", sendList.subList(fromIndex,toIndex));
                    try {
                        RequestBody body = RequestBody.create(mediaType, jo.toJSONString());
                        Request request = new Request.Builder().url(sendMessage).post(body).build();
                        Response response = client.newCall(request).execute();
                        String string = response.body().string();
                        Map<String, String> paramsMap = JSON.parseObject(string, Map.class);
                        if (paramsMap.get("code") == "200"){
                            logger.info(paramsMap.get("code") + ":" + paramsMap.get("message"));
                        }
                        info.put("code", paramsMap.get("code")).put("message", paramsMap.get("message"));
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        info.put("code", 500).put("message", "システムエラー");
                    }
                }
            }
        }
        return info;
    }

    /**
     * 送信失敗データの取得(errorDataGet)
     *
     * @param startDateTime 送信時間区間の開始時間
     * @param endDateTime 送信時間区間の終了時間
     * @return
     */
    @Override
    public R getErrorData(Long startDateTime, Long endDateTime) {
        R info = new R();
        String url = errorData + "?token=" + URLEncoder.encode(commonService.getToken()) + "&startDateTime=" + startDateTime + "&endDateTime=" + endDateTime;
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            Map<String, String> paramsMap = JSON.parseObject(string, Map.class);
            info.put("code", paramsMap.get("code")).put("message", paramsMap.get("message")).put("data", paramsMap.get("data"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return info;
    }

    /**
     * プッシュ失敗データに登録
     *
     * @param result
     * @return
     */
    @Override
    public R insert(String result) {
        List<PushErrDatEntity> pushErrDatEntities = JSON.parseArray(result, PushErrDatEntity.class);
        for (PushErrDatEntity dto : pushErrDatEntities) {
            PushErrDatEntity pushErrDatEntity = pushErrDatService.getOne(new QueryWrapper<PushErrDatEntity>().eq("send_id",dto.getSendId()).eq("del_flg",0));
            if (pushErrDatEntity == null){
                MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().select("org_id").eq("usr_id", dto.getStuId()).eq("del_flg", 0));
                MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(
                        new QueryWrapper<MstDeviceTokenEntity>().eq("device_id", dto.getDeviceId()).eq("del_flg", 0));
                if (mstDeviceTokenEntity != null && mstUsrEntity != null) {
                    //配送先ID
                    dto.setDeliverId(mstDeviceTokenEntity.getUsrId());
                    //組織ID
                    dto.setOrgId(mstUsrEntity.getOrgId());
                    //作成日時
                    dto.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    dto.setCretUsrId(ShiroUtils.getUserId());
                    //更新日時
                    dto.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    dto.setUpdUsrId(ShiroUtils.getUserId());
                    //削除フラグ
                    dto.setDelFlg(0);
                    //登録
                    pushErrDatService.save(dto);
                }
            }
        }
        return R.ok();
    }

    /**
     * ログアウト（logout）(ログアウトAPIを改修 )
     *
     * @param loginId
     * 2020/11/11 modify yang
     * @return
     */
    @Override
    public R logout(String loginId, HttpServletRequest request) {
        if (StringUtils.isNotBlank(ShiroUtils.getUserId())){
            MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id",ShiroUtils.getUserId()).eq("del_flg",0));
            if (mstDeviceTokenEntity != null){
                //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                List<String> deviceIdList = new ArrayList<>();
                F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                //デバイスIDを取得する。
                f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                f09005DeviceDto.setUnreadcount(0);
                f09005DeviceDto.setStuId("");
                f09005DeviceDto.setStuname("");
                deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
                //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
                SendMessageDto sendMessageDto = new SendMessageDto();
                //メッセージの備考内容。規則：最大桁数255。
                sendMessageDto.setComment(null);
                //受信先デバイスIDの集合、必須項目。
                sendMessageDto.setDeviceList(deviceIdList);
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("msgId",null);
                map.put("msgType","999");
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
                sendMessage(JSON.toJSONString(sendMessageDto));
                Timestamp eTime = DateUtils.getSysTimestamp();
                Long cTime = eTime.getTime() - sTime.getTime();
                logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
                logger.info("プッシュ通知処理時間：<" + cTime + ">");
            }else {
                logger.info("ユーザとデバイスがバインディングされていません。");
            }

        }
        R r = new R();
        ShiroUtils.logout();
        r.remove("msg");
        return r.ok().put("code", 200).put("msg", "").put("loginUrl", "");
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
    public R contextLoads(HttpServletRequest request, JSONObject node, Integer flg) throws UnknownHostException {
        R info = new R();
        URL connect;
        StringBuffer data = new StringBuffer();
        try {
            String url = appLoginCheck;
            if (flg == 1 || flg == 2) {
                url = flg == 1 ? unReadGet + "?token=" + node.get("token") + "&deviceId=" + node.get("deviceId") : unReadGet;
            }
            connect = new URL(url);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(request.getLocalAddr(), Integer.valueOf("8080")));
            HttpURLConnection connection = (HttpURLConnection)connect.openConnection(proxy);
            if (flg == 0 || flg == 2) {
                try {
                    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(node));
                    Request request1 = new Request.Builder().url(url).post(body).build();
                    Response response = client.newCall(request1).execute();
                    String string = response.body().string();
                    Map<String, String> paramsMap = JSON.parseObject(string, Map.class);
                    String deviceId = JSON.parseObject(JSON.toJSON(paramsMap.get("data")).toString(), Map.class).get("deviceId").toString();
                    info.put("deviceId", deviceId).put("code", paramsMap.get("code"));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    data.append(line);
                    info.put("data", data);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return info;
    }

    private R updateLockState(String loginId) {
        Integer maxErrorCount = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.MAX_ERROR_COUNT);
        if (maxErrorCount != null && maxErrorCount < 100) {
            shiroService.updateErrorCount(loginId, (maxErrorCount + 1));
        }
        if (maxErrorCount != null && maxErrorCount == 99) {
            shiroService.updateLockFlg(loginId);
        }
        return R.error(MessageUtils.getMessage("MSGCOMN0001"));
    }

    /**
     * login user to shiro
     *
     * @param sysUserEntity userData
     * @return
     */
    private R loginUser(SysUserEntity sysUserEntity, HttpServletResponse response) {
        //「Subject」を取得
        Subject subject = ShiroUtils.getSubject();
        //ユーザー名/パスワード認証トークンを作成する
        UserAuthToken token = null;
        //セッションでパスワードを取得する
        String password = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_PASSWORD);
        Cookie cookieEquipment = new Cookie("equipment", "phone");
        Cookie cookieKey = new Cookie("key", "PUSHAPI");
        cookieEquipment.setPath("/");
        cookieKey.setPath("/");
        response.addCookie(cookieEquipment);
        response.addCookie(cookieKey);

        String loginUrl = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_LOGIN_URL);
        token = new UserAuthToken(StringUtils.defaultString(sysUserEntity.getId()), password, Constant.LOGIN_TYPE_GAKKEN, false, null);
        if (StringUtils.defaultString(GakkenConstant.ROLE_DIV.STUDENT.getValue()).equals(StringUtils.trim(sysUserEntity.getRoleDiv()))) {
            ShiroUtils.getSubject().getSession().setTimeout(expireTimeStu.longValue() * 60 * 1000);
        }
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "2", null);
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "2", null);
            return updateLockState(sysUserEntity.getAfterUsrId());
        } catch (LockedAccountException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "2", null);
            return R.error(MessageUtils.getMessage("MSGCOMN0002")).put("lockFlg", "1");
        } catch (AuthenticationException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "2", null);
            return updateLockState(sysUserEntity.getAfterUsrId());
        }

        String csrfToken = jwtUtils.generateToken(sysUserEntity.getId());
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_CSRF_TOKEN, csrfToken);

        // ブランドコードをセッションに格納する
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, sysUserEntity.getBrandCd());

        CM0006.insertMstLoginData("0", null, sysUserEntity.getAfterUsrId(), loginUrl, "2", sysUserEntity.getOrgId());

        return R.ok();
    }
    private String getUserId(String userId,String gidFlg,String tchCd){
        if (StringUtils.equals("0",gidFlg)){
            userId = userId + ",0";
        }else{
            if (StringUtils.equals("0",tchCd)){
                userId = userId + ",1";
            }else {
                userId = userId + ",2";
            }
        }
        return userId;
    }
}
