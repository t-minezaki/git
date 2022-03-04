package jp.learningpark.modules.com.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.dao.QRCodeApiDao;
import jp.learningpark.modules.com.dao.QRCodeApiTransactionBean;
import jp.learningpark.modules.com.dto.QRCodeApiDto;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.com.service.QRCodeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dto.F09005DeviceDto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.sys.dao.SysUserDao;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * マナミルQR認識APIサービス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/18 ： NWT)hxl ： 新規作成
 */
@Service
public class QRCodeApiServiceImpl implements QRCodeApiService {
    /**
     * variousScannerTokenMstDao
     */
    @Autowired
    VariousScannerTokenMstDao variousScannerTokenMstDao;
    /**
     * mstUnitDao
     */
    @Autowired
    SysUserDao sysUserDao;
    /**
     * qrCodeApiDao
     */
    @Autowired
    QRCodeApiDao qrCodeApiDao;
    /**
     * mstVariousSetDao
     */
    @Autowired
    MstVariousSetDao mstVariousSetDao;
    /**
     * entryExitHstDao
     */
    @Autowired
    EntryExitHstDao entryExitHstDao;
    /**
     * stuPointDao
     */
    @Autowired
    StuPointDao stuPointDao;
    /**
     * mstLoginDao
     */
    @Autowired
    MstLoginDao mstLoginDao;
    /**
     * jwtUtils
     */
    @Autowired
    JwtUtils jwtUtils;

    /**
     * mstOrgDao
     */
    @Autowired
    MstOrgDao mstOrgDao;

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * noticeMailSendHstDao
     */
    @Autowired
    NoticeMailSendHstDao noticeMailSendHstDao;

    /**
     * mailUtils
     */
    @Autowired
    MailUtils mailUtils;

    /**
     * mstGuardDao
     */
    @Autowired
    MstGuardDao mstGuardDao;

    /**
     * mstUsrDao
     */
    @Autowired
    MstUsrDao mstUsrDao;

    /**
     * mstDeviceTokenDao
     */
    @Autowired
    MstDeviceTokenDao mstDeviceTokenDao;

    /**
     * noticeApiService
     */
    @Autowired
    NoticeApiService noticeApiService;

    /**
     * qrCodeApiTransactionBean
     */
    @Autowired
    QRCodeApiTransactionBean qrCodeApiTransactionBean;

    /**
     * f30112Dao
     */
    @Autowired
    F30112Dao f30112Dao;
    /**
     * commonService
     * modify NWT楊
     * 2020/11/10
     */
    @Autowired
    CommonService commonService;

    @Autowired
    ShiroService shiroService;
    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ans-url.token}")
    String token;
    @Value("${learningpark-domain.domain}")
    String domain;

    private static final AtomicLong interval = new AtomicLong(5 * 60);
    // 2020/11/24 huangxinliang modify start
    private static final Logger log = LoggerFactory.getLogger(QRCodeApiServiceImpl.class);
    // 2020/11/24 huangxinliang modify end
    private String testFlag = "0";
    // 2021/1/4 huangxinliang modify start
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    // 2021/1/4 huangxinliang modify end
    @Override
    public String getTestFlag() {
        return this.testFlag;
    }

    /**
     * mstUsrService
     * NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit
     */
    @Autowired
    MstUsrService mstUsrService;
    /**
     * mstManagerService
     * NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit
     */
    @Autowired
    MstManagerService mstManagerService;

    @Override
    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }

    /**
     * ログイン
     *
     * @param orgId 組織ID
     * @param loginId 入退室アプリログイン用ID
     * @param password 入退室アプリログイン用パスワード
     * @return  結果
     */
    @Override
    // 2021/09/22 manamiru1-772 cuikl del
    public R login(String orgId, String loginId, String password) {
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        CM0003.LoginCheckResultDto loginCheckResultDto;
        //GID認証処理
        try {
            //ログインIDのGID認証処理
            loginCheckResultDto = CM0003.loginCheck(loginId, password);
        } catch (LockedAccountException e) {
            return R.error().put("message", MessageUtils.getMessage("MSGCOMN0002")).put("token", "");
        }
        R loginCheck = CM0006.loginCheck(loginCheckResultDto, loginId, GakkenConstant.LOGIN_TYPE_KBN.QR_CODE_LOGIN.getValue(), "/QRATAPI");
        if ((Integer)loginCheck.get("code") != 0){
            return R.error().put("message", "ログインユーザはデータベースに存在しません").put("token", "");
        }
        SysUserEntity sysUserEntity;
        //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　Start
        List<SysUserEntity> sysUserEntities = new ArrayList<>();
        String gidFlg = loginCheckResultDto.getGidCheckFlag();
        Integer count = 0;
        String tchCdFlg = "0";
        String tchCd = loginCheckResultDto.getTchCd();
        //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　Start
        String loginCheckId = loginId;
        //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　End
        if (StringUtils.equals("1",gidFlg)){
            String gidpk = loginCheckResultDto.getGidpk();
            if (StringUtils.isNotBlank(gidpk)){
                //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　Start
                count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("gidpk",gidpk).eq("org_id",orgId).eq("del_flg",0));
                //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　Start
                loginCheckId = count > 0 ? gidpk:loginId;
                //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　End
            }
            if (StringUtils.isNotBlank(tchCd) && count == 0){
                count = shiroService.tchCdCount(tchCd,orgId).size();
                //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　End
                //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　Start
                loginCheckId = count > 0 ? tchCd:loginId;
                //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　End
                tchCdFlg = count > 0 ?"1":"0";
            }
        }
        //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　Start
        sysUserEntities = qrCodeApiDao.selectUserByLoginIdAndOrgId(loginCheckId, "0", orgId, gidFlg, tchCdFlg,getTestFlag());
        if (sysUserEntities.size() == 0){
            return R.error().put("message", "ログインユーザはデータベースに存在しません").put("token", "");
        }
        CM0006.insertMstLoginData("0", null, loginCheckId, "/QRATAPI", GakkenConstant.LOGIN_TYPE_KBN.QR_CODE_LOGIN.getValue(), null);
        //NWT　楊　2021/07/19　MANAMIRU1-703　Edit　End
        sysUserEntity = sysUserEntities.get(0);
        //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　End
        //自動でき作成されたのTOKENへの組織別独一性を判断を行う
        String token = getToken(StringUtils.defaultString(sysUserEntity.getId()), orgId);
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_CSRF_TOKEN, token);
        VariousScannerTokenMstEntity variousScannerTokenMstEntity = new VariousScannerTokenMstEntity();
        variousScannerTokenMstEntity.setOrgId(sysUserEntity.getOrgId());
        variousScannerTokenMstEntity.setUserId(loginId);
        variousScannerTokenMstEntity.setLoginType(Constant.QR_LOGIN);
        variousScannerTokenMstEntity.setOrgToken(token);
        variousScannerTokenMstEntity.setEntryDt(sysTimestamp);
        variousScannerTokenMstEntity.setCretDatime(sysTimestamp);
        variousScannerTokenMstEntity.setCretUsrId(loginId);
        variousScannerTokenMstEntity.setUpdDatime(sysTimestamp);
        variousScannerTokenMstEntity.setUpdUsrId(loginId);
        variousScannerTokenMstEntity.setDelFlg(0);
        variousScannerTokenMstDao.insert(variousScannerTokenMstEntity);
        //該当組織の塾名を組織マスタから抽出して、appに転送する
        String orgNm = sysUserEntity.getOrgNm();
        R r = R.ok();
        r.put("code", 200);
        r.put("message", orgNm);
        r.put("token", token);
        return r;
    }

    /**
     * 更新トークン
     *
     * @param orgId 組織ID
     * @param loginId 入退室アプリログイン用ID
     * @param token トークン
     * @return 結果
     */
    @Override
    public R tokenRefresh(String orgId, String loginId, String token) {
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //自動できに転送されたのoldtokenの再送信時間を判断する
        VariousScannerTokenMstEntity oldVariousScannerTokenMstEntity = variousScannerTokenMstDao.selectOne(
                new QueryWrapper<VariousScannerTokenMstEntity>().eq("user_id", loginId).eq("org_token", token).eq("del_flg", 0));
        //トークンテーブルにデータがありません
        if (oldVariousScannerTokenMstEntity == null) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        Claims tokenObject;
        tokenObject = jwtUtils.getClaimByToken(token);
        //token.bodyは空です
        if (tokenObject == null) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        //トークンの有効期限が切れました
        if (jwtUtils.isTokenExpired(tokenObject.getExpiration())) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        String id = tokenObject.getSubject();
        String newToken = jwtUtils.generateToken(id, interval.get());
        //flash内容登録/更新及びAPP返信
        //入退室スキャンTOKEN管理．該当組織のTOKEN　＝　'qrlogin'
        if (StringUtils.equals(Constant.QR_LOGIN, oldVariousScannerTokenMstEntity.getLoginType())) {
            VariousScannerTokenMstEntity variousScannerTokenMstEntity = new VariousScannerTokenMstEntity();
            variousScannerTokenMstEntity.setOrgId(oldVariousScannerTokenMstEntity.getOrgId());
            variousScannerTokenMstEntity.setUserId(loginId);
            variousScannerTokenMstEntity.setLoginType(Constant.TOKEN_REFRESH);
            variousScannerTokenMstEntity.setOrgToken(newToken);
            variousScannerTokenMstEntity.setEntryDt(sysTimestamp);
            variousScannerTokenMstEntity.setCretDatime(sysTimestamp);
            variousScannerTokenMstEntity.setCretUsrId(loginId);
            variousScannerTokenMstEntity.setUpdDatime(sysTimestamp);
            variousScannerTokenMstEntity.setUpdUsrId(loginId);
            variousScannerTokenMstEntity.setDelFlg(0);
            variousScannerTokenMstDao.insert(variousScannerTokenMstEntity);
            //以前のトークンの無効化
            oldVariousScannerTokenMstEntity.setDelFlg(1);
            variousScannerTokenMstDao.updateById(oldVariousScannerTokenMstEntity);
        } else if (StringUtils.equals(Constant.TOKEN_REFRESH, oldVariousScannerTokenMstEntity.getLoginType())) {
            oldVariousScannerTokenMstEntity.setOrgToken(newToken);
            oldVariousScannerTokenMstEntity.setEntryDt(sysTimestamp);
            oldVariousScannerTokenMstEntity.setUpdDatime(sysTimestamp);
            oldVariousScannerTokenMstEntity.setUpdUsrId(loginId);
            oldVariousScannerTokenMstEntity.setDelFlg(0);
            variousScannerTokenMstDao.updateById(oldVariousScannerTokenMstEntity);
        }
        R r = R.ok();
        r.put("code", 200);
        r.put("message", "");
        r.put("token", newToken);
        return r;
    }

    /**
     * 生徒登校または下校
     *
     * @param orgId 組織ID
     * @param loginId 入退室アプリログイン用ID
     * @param studentId 生徒ID
     * @param token トークン
     * @return 結果
     */
    @Override
    public R studentLogin(String orgId, String loginId, String studentId, String token) {
        VariousScannerTokenMstEntity tokenMstEntity = variousScannerTokenMstDao.selectOne(
                new QueryWrapper<VariousScannerTokenMstEntity>().eq("user_id", loginId).eq("org_token", token).eq("del_flg", 0));
        if (tokenMstEntity == null) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        Claims tokenObject;
        tokenObject = jwtUtils.getClaimByToken(token);
        //token.bodyは空です
        if (tokenObject == null) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        //トークンの有効期限が切れました
        if (jwtUtils.isTokenExpired(tokenObject.getExpiration())) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        //該当生徒の存在チェック
        //NWT　楊　2021/09/15　MANAMIRU1-782　Edit　Start
        QRCodeApiDto mstStuEntity = qrCodeApiDao.selectOne(studentId);
        //NWT　楊　2021/09/15　MANAMIRU1-782　Edit　End
        //検索されたの結果 IS NULL
        if (mstStuEntity == null) {
            return R.error().put("message", "エラーメッセージ").put("token", "");
        }
        CM0005.PointVo pointVo = CM0005.getPointVo(mstStuEntity.getOrgId());
        //該当生徒の状態チェック
        VariousScannerTokenMstEntity oldVariousScannerTokenMstEntity = variousScannerTokenMstDao.selectOne(
                new QueryWrapper<VariousScannerTokenMstEntity>().eq("login_type", Constant.STUDENT_LOGIN).eq("user_id", mstStuEntity.getStuId()).orderByDesc(
                        "upd_datime").last("limit 1"));
        //検索されたの結果はエンプティの場合
        if (oldVariousScannerTokenMstEntity == null) {
            VariousScannerTokenMstEntity variousScannerTokenMstEntity = new VariousScannerTokenMstEntity();
            variousScannerTokenMstEntity.setOrgId(mstStuEntity.getOrgId());
            variousScannerTokenMstEntity.setUserId(mstStuEntity.getStuId());
            variousScannerTokenMstEntity.setLoginType(Constant.STUDENT_LOGIN);
            variousScannerTokenMstEntity.setOrgToken(token);
            variousScannerTokenMstEntity.setEntryDt(sysTimestamp);
            variousScannerTokenMstEntity.setCretDatime(sysTimestamp);
            variousScannerTokenMstEntity.setCretUsrId(loginId);
            variousScannerTokenMstEntity.setUpdDatime(sysTimestamp);
            variousScannerTokenMstEntity.setUpdUsrId(loginId);
            variousScannerTokenMstEntity.setDelFlg(0);
            variousScannerTokenMstDao.insert(variousScannerTokenMstEntity);
        } else {
            // 2020/12/2 huangxinliang modify start
            //登録時間は再認識可能の間隔内の場合　
            if ((getDifference(oldVariousScannerTokenMstEntity.getEntryDt(), sysTimestamp, 1) < pointVo.getReRecoTime())) {
                return new R().put("code", 900).put("message", "既に登録されています。時間を置いてかざしてください。").put("info", "");
            }
            // 2020/12/2 huangxinliang modify end
            //登録時間は再認識可能の間隔外の場合　
            oldVariousScannerTokenMstEntity.setOrgId(mstStuEntity.getOrgId());
            oldVariousScannerTokenMstEntity.setUserId(mstStuEntity.getStuId());
            oldVariousScannerTokenMstEntity.setLoginType(Constant.STUDENT_LOGIN);
            oldVariousScannerTokenMstEntity.setOrgToken(token);
            oldVariousScannerTokenMstEntity.setEntryDt(sysTimestamp);
            oldVariousScannerTokenMstEntity.setUpdDatime(sysTimestamp);
            oldVariousScannerTokenMstEntity.setUpdUsrId(loginId);
            oldVariousScannerTokenMstEntity.setDelFlg(0);
            variousScannerTokenMstDao.updateById(oldVariousScannerTokenMstEntity);
        }
        //ポイント付与及び登校下校判断を行
        Integer count = entryExitHstDao.selectCount(
                new QueryWrapper<EntryExitHstEntity>().eq("org_id", mstStuEntity.getOrgId()).eq("stu_id", mstStuEntity.getStuId()).last(
                        "and to_char(entry_dt,'yyyy-mm-dd') = '" + DateUtils.format(sysTimestamp, Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'"));
        //該当生徒は登校と判断する
        //登校下校判断を行う
        String goSchoolFlag;
        String pushTitleFlg;
        String goClassroomFlg;
        if (count % 2 == 0) {
            goSchoolFlag = Constant.GO_SCHOOL_FOR_GKGC;
            pushTitleFlg = Constant.PUSH_TITLE_GO_SCHOOL;
            goClassroomFlg = Constant.GO_CLASSROOM;
            // 2020/12/2 huangxinliang modify start
            if (count == 0){
                //自動できポイント付与を行う
                // 2020/12/7 huangxinliang modify start
                CM0005.addPoint(mstStuEntity.getStuId(), mstStuEntity.getOrgId(), pointVo, 4, loginId,sysTimestamp);
                // 2020/12/7 huangxinliang modify end
            }
            // 2020/12/2 huangxinliang modify end
        } else {
            goSchoolFlag = Constant.LEAVE_SCHOOL_FOR_GKGC;
            pushTitleFlg = Constant.PUSH_TITLE_LEAVE_SCHOOL;
            goClassroomFlg = Constant.LEAVE_CLASSROOM;
            //該当生徒は下校と判断する
            EntryExitHstEntity entryExitHstEntity = entryExitHstDao.selectOne(
                    new QueryWrapper<EntryExitHstEntity>().eq("org_id", mstStuEntity.getOrgId()).eq("stu_id", mstStuEntity.getStuId()).orderByDesc("id").last(
                            "limit 1"));
            //入退室履歴．登録フラグ　＝　「0：登校」　&&　入退室履歴．登録日時！＝システム時間（年月日まで）
            if (StringUtils.equals(entryExitHstEntity.getEntryFlg(), "0") && !(StringUtils.equals(
                    DateUtils.format(entryExitHstEntity.getEntryDt(), Constant.DATE_FORMAT_YYYY_MM_DD_BARS),
                    DateUtils.format(sysTimestamp, Constant.DATE_FORMAT_YYYY_MM_DD_BARS)))) {
                return R.error().put("message", "エラーメッセージ").put("info", "");
            }
            if (StringUtils.equals(entryExitHstEntity.getEntryFlg(), "1")) {
                return R.error().put("message", "エラーメッセージ").put("info", "");
            }
        }
        //入退室履歴に登録を行う
        EntryExitHstEntity entryExitHstEntity = new EntryExitHstEntity();
        //入退室履歴登録
        entryExitHstEntity.setOrgId(mstStuEntity.getOrgId());
        entryExitHstEntity.setStuId(mstStuEntity.getStuId());
        entryExitHstEntity.setEntryDt(sysTimestamp);
        entryExitHstEntity.setEntryFlg(StringUtils.equals(goSchoolFlag, Constant.GO_SCHOOL_FOR_GKGC) ? "0" : "1");
        entryExitHstEntity.setEntryUserId(null);
        entryExitHstEntity.setNoticeSts("0");
        entryExitHstEntity.setCretDatime(sysTimestamp);
        entryExitHstEntity.setCretUsrId(loginId);
        entryExitHstEntity.setUpdDatime(sysTimestamp);
        entryExitHstEntity.setUpdUsrId(loginId);
        entryExitHstEntity.setDelFlg(0);
        entryExitHstDao.insert(entryExitHstEntity);
        // 2021/01/04 huangxinliang modify start
        threadPoolTaskExecutor.execute(() -> {
            //お知らせ送信を行う
            // 2020/11/30 huangxinliang modify start
            Boolean sendFlag = false;
            // 2020/11/30 huangxinliang modify end
            if (!StringUtils.isEmpty(mstStuEntity.getGuardId())) {
                R info = sendNotice(orgId, loginId, studentId, sysTimestamp, mstStuEntity, goSchoolFlag);
                sendFlag = (Boolean) info.get("sendFlg");
                //2020/11/23 huangxinliang modify start
                Integer noticeId = (Integer) info.get("noticeId");
                sendMessage(mstStuEntity,noticeId,pushTitleFlg,goClassroomFlg,sysTimestamp);
            }
            //入退室履歴更新
            if(sendFlag){
                EntryExitHstEntity exitHstEntity = new EntryExitHstEntity();
                exitHstEntity.setId(entryExitHstEntity.getId());
                exitHstEntity.setNoticeSts("1");
                entryExitHstDao.updateById(exitHstEntity);
            }
        });
        if (StringUtils.equals("0", getTestFlag())) {
            //検証
            R r = R.ok();
            r.put("code", 200);
            r.put("message", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
            r.put("info", StringUtils.equals(goSchoolFlag, Constant.GO_SCHOOL_FOR_GKGC) ? "0" : "1");
            //生年月日は空ではないと判断します。modify yang 2011/11/27 start--
            r.put("birthday", mstStuEntity.getBirthd()== null?"":DateUtils.format(mstStuEntity.getBirthd(), Constant.DATE_FORMAT_YYYYMMDD));
            //生年月日は空ではないと判断します。modify yang 2011/11/27 end--
            // 2020/12/1 huangxinliang modify start
            r.put("grade", Integer.parseInt(mstStuEntity.getCodCd()));
            // 2020/12/1 huangxinliang modify end
            return r;
        } else {
            //本番
            R r = R.ok();
            r.put("code", 200);
            r.put("message", mstStuEntity.getFlnmNm() + mstStuEntity.getFlnmLnm());
            r.put("info", StringUtils.equals(goSchoolFlag, Constant.GO_SCHOOL_FOR_GKGC) ? "0" : "1");
            return r;
        }
        // 2020/11/24 huangxinliang modify end
    }

    public R sendNotice(String orgId, String loginId, String studentId, Timestamp sysTimestamp, QRCodeApiDto mstStuEntity, String goSchoolFlag) {
        String brandCode;
        if (StringUtils.equals("0", getTestFlag())) {
            //検証
            MstOrgEntity orgEntity = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId).last("limit 1"));
            if (orgEntity == null) {
                return R.error().put("sendFlg",false);
            }
            brandCode = orgEntity.getBrandCd();
        } else {
            //本番
            brandCode = orgId;
        }
        goSchoolFlag = StringUtils.equals(goSchoolFlag, Constant.GO_SCHOOL_FOR_GKGC) ? Constant.GO_SCHOOL_FOR_GKGC : Constant.LEAVE_SCHOOL_FOR_GKGC;
        //2020/11/23 huangxinliang modify start
        Integer noticeId;
        //トランザクションセーブポイントを設定する
        try {
            noticeId = qrCodeApiTransactionBean.sendNotice(loginId, sysTimestamp, mstStuEntity, goSchoolFlag);
        } catch (Exception e) {
            log.error("通知に失敗しました。");
            return R.error().put("sendFlg",false);
        }
        //2020/11/23 huangxinliang modify end
        //メールを送る
//        MstGuardEntity mstGuardEntity = mstGuardDao.selectOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", mstStuEntity.getGuardId()));
//        if (!StringUtils.isEmpty(mstGuardEntity.getMailad())) {
//            MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", mstStuEntity.getGuardId()));
//            String mailad = mstGuardEntity.getMailad();
//            String title = goSchoolFlag + "メール";
//            String content = "";
//            Map<String, Object> params = new HashMap<>();
//            params.put("guardNm", mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm());
//            params.put("stuNm", mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
//            params.put("sysDatetime", DateUtils.format(sysTimestamp, Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL));
//            params.put("orgNm", mstStuEntity.getOrgNm());
//            params.put("brandCd", brandCode);
//            params.put("guardId", mstUsrEntity.getAfterUsrId());
//            params.put("goSchool", goSchoolFlag);
//            boolean success = true;
////            try {
////                String template =
////                        //                    "\t<p>・本文</p>\n" +
////                                "\t${guardNm!}様\n" +
////                                "<br>" +
////                                "\t<p>お世話になっております。</p>\n" +
////                                "\t<p>${orgNm!}です。</p>\n" +
////                                "<br>" +
////                                "\t<p>${stuNm!}様は${sysDatetime}に${goSchool}しました。</p>\n" +
////                                "<br>" +
////                                "\t<p>詳しい情報はマイページよりご確認ください。</p>\n" +
////                                "<br>" +
////                                "\t<p>ID：${guardId!}\n</p>" +
////                                "\t<p>PASS：あなたが設定したパスワード</p>\n" +
////                                "<br>" +
////                                "\t<p>※このメールは送信専用アドレスから配信されています。</p>\n" +
////                                "\t<p>ご返信いただいてもお答えできませんのでご了承ください。</p>\n";
////                content = TmplateUtils.generateString(params, template);
////                mailUtils.sendMail(mailad, title, content);
////            } catch (Exception e) {
////                // 送信失敗後にエラー情報を返す
////                log.error("メールの送信に失敗しました。");
////                success = false;
////            }
//            // お知らせメール送信履歴へ登録する。
////            NoticeMailSendHstEntity noticeMailSendHstEntity = new NoticeMailSendHstEntity();
////            //データを入力する
////            noticeMailSendHstEntity.setOrgId(mstStuEntity.getOrgId());
////            noticeMailSendHstEntity.setNoticeId(noticeId);
////            noticeMailSendHstEntity.setCtgyNm("1");
////            noticeMailSendHstEntity.setStuId(mstStuEntity.getStuId());
////            noticeMailSendHstEntity.setGuardId(mstStuEntity.getGuardId());
////            noticeMailSendHstEntity.setMailad(mailad);
////            noticeMailSendHstEntity.setMailTitle(title);
////            noticeMailSendHstEntity.setMailCnt(content);
////            if (success) {
////                noticeMailSendHstEntity.setStatus("0");
////            } else {
////                noticeMailSendHstEntity.setStatus("1");
////            }
////            noticeMailSendHstEntity.setCretDatime(sysTimestamp);
////            noticeMailSendHstEntity.setCretUsrId(loginId);
////            noticeMailSendHstEntity.setUpdDatime(sysTimestamp);
////            noticeMailSendHstEntity.setUpdUsrId(loginId);
////            noticeMailSendHstEntity.setDelFlg(0);
////            noticeMailSendHstDao.insert(noticeMailSendHstEntity);
////        }
        return R.ok().put("noticeId",noticeId).put("sendFlg",true);
    }

    /**
     * 2回の間のタイムスタンプ計算機能
     *
     * @param beginDate     開始時間
     * @param endDate       終了時間
     * @param f 時差の形式は0：秒、1：分、2時間、3日です。
     * @return long 秒
     */
    public static long getDifference(Date beginDate, Date endDate, int f) {
        long result = 0;
        if (beginDate == null || endDate == null) {
            return 0;
        }
        try {
            // 日付を差し引いて、日付の差X（単位：ミリ秒）を取得します。
            long millisecond = endDate.getTime() - beginDate.getTime();
            /*
              Math.abs((int)(millisecond/1000)); 絶対値1秒= 1000ミリ秒
              millisecond/1000 --> 秒 millisecond/1000*60 - > 分
              millisecond/(1000*60*60) -- > 時間 millisecond/(1000*60*60*24) -->
              日
              */
            switch (f) {
                case 0:
                    // second
                    return (millisecond / 1000);
                case 1:
                    // minute
                    return (millisecond / (1000 * 60));
                case 2:
                    // hour
                    return (millisecond / (1000 * 60 * 60));
                case 3:
                    // day
                    return (millisecond / (1000 * 60 * 60 * 24));
                default:
                    return -1;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * トークンを取得
     *
     * @param id データベースのユーザーID
     * @param orgId 組織のブランドコード
     * @return トークンの取得
     */
    private String getToken(String id, String orgId) {
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("del_flg", 0).eq("cod_key", "REFSH_TOKEN_TIME"));
        if (mstCodDEntity != null) {
            interval.getAndSet(Long.parseLong(mstCodDEntity.getCodValue()) * 60);
        }
        String token = jwtUtils.generateToken(id, interval.get());
        VariousScannerTokenMstEntity variousScannerTokenMstEntity = variousScannerTokenMstDao.selectOne(
                new QueryWrapper<VariousScannerTokenMstEntity>().eq("org_id", orgId).eq("org_token", token));
        if (variousScannerTokenMstEntity != null) {
            token = getToken(id, orgId);
        }
        return token;
    }

    /**
     *
     * @param mstStuEntity
     * @param noticeId
     */
    private void sendMessage(MstStuEntity mstStuEntity,Integer noticeId,String pushTitleFlg,String goClassroomFlg,Timestamp sysTimestamp){
        //guardIdでdeviceIdに問い合わせる
        //delete  at 2021/08/17 for V9.02 by NWT LiGX START
//        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenDao.selectOne(
//                new QueryWrapper<MstDeviceTokenEntity>().and(w->w.eq("usr_id", mstStuEntity.getGuardId())).eq("del_flg",0));
        //delete  at 2021/08/17 for V9.02 by NWT LiGX END
        //add at 2021/08/17 for V9.02 by NWT LiGX START
        Map<String, Object> deviceUserId = new HashMap<>();
        List<String> usrIds = Arrays.asList( mstStuEntity.getGuardId().split(","));
        deviceUserId.put("userIdList",usrIds);
        List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
        //add at 2021/08/17 for V9.02 by NWT LiGX END
        String pushDate = new SimpleDateFormat(GakkenConstant.DATE_FORMAT_HH_MM_MAIL).format(sysTimestamp);
        //modify at 2021/08/17 for V9.02 by NWT LiGX START
        if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)){
            List<String> deviceIdList = new ArrayList<>();
            for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList){
                F09005DeviceDto f09005DeviceDto = new F09005DeviceDto();
                //デバイスIDを取得する。
                f09005DeviceDto.setId(mstDeviceTokenEntity.getDeviceId());
                //未読件数を取得する
                /* 2021/02/18 4-29 start */
                //Integer noticeUnreadCount = commonService.pushUnreadCount(mstStuEntity.getGuardId());
                Integer noticeUnreadCount = commonService.pushUnreadCountForQR(mstStuEntity.getGuardId());
                /* 2021/02/18 4-29 end */
                f09005DeviceDto.setUnreadcount(noticeUnreadCount);
                f09005DeviceDto.setStuId(mstStuEntity.getStuId());
                f09005DeviceDto.setStuname(mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm());
                deviceIdList.add(JSON.toJSONString(f09005DeviceDto));
            }
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("QR入退登録確認comment");
            sendMessageDto.setImgUrl("");
            sendMessageDto.setDeviceList(deviceIdList);
            sendMessageDto.setMessage("さんが、" + pushDate + "に" + goClassroomFlg);
            Map<String, Object> map = new LinkedHashMap<>(4);
            map.put("msgId", noticeId == null ? -1 : noticeId);
            map.put("msgType", Constant.sendMsgTypeQRCodeApi);
            sendMessageDto.setParams(JSON.toJSONString(map));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(DateUtils.getSysTimestamp().getTime()));
            sendMessageDto.setTitle(pushTitleFlg);
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
        //modify at 2021/08/17 for V9.02 by NWT LiGX END
    }
}
