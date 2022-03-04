/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.annotation.LoginLog;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.security.saml.SamlConstants;
import jp.learningpark.framework.security.saml.SamlService;
import jp.learningpark.framework.utils.CM0003;
import jp.learningpark.framework.utils.CM0005;
import jp.learningpark.framework.utils.CM0006;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.HttpContextUtils;
import jp.learningpark.framework.utils.JwtUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.SpringContextUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.GakkenConstant.EQUIPMENT_DIV;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstLoginEntity;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstCrmschLearnPrdService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.common.service.MstGuardService;
import jp.learningpark.modules.common.service.MstLoginService;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.common.service.MstMentorService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dto.F30002Dto;
import jp.learningpark.modules.guard.service.F30002Service;
import jp.learningpark.modules.sys.dao.SysUserOnlineDao;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import jp.learningpark.modules.sys.service.SysCaptchaService;
import jp.learningpark.modules.sys.shiro.session.OnlineSession;
import jp.learningpark.modules.sys.shiro.token.UserAuthToken;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jp.learningpark.modules.common.GakkenConstant.EQUIPMENT_DIV.PC;
import static jp.learningpark.modules.common.GakkenConstant.EQUIPMENT_DIV.PHONE;
import static jp.learningpark.modules.common.GakkenConstant.EQUIPMENT_DIV.TABLET;

/**
 * ログイン
 *
 * @author chenshun
 */
@Controller
public class SysLoginController extends AbstractController {

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private MstUsrService mstUsrService;

    @Autowired
    private MstMentorService mstMentorService;

    @Autowired
    private MstGuardService mstGuardService;

    @Autowired
    private MstOrgService mstOrgService;

    @Autowired
    private MstStuService mstStuService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * gakkenIdAPI
     */
    @Autowired
    GakkenIdAPI gakkenIdAPI;

    /**
     * f30002Service
     */
    @Autowired
    F30002Service f30002Service;

    /**
     * 塾学習期間 Service
     */
    @Autowired
    private MstCrmschLearnPrdService mstCrmschLearnPrdService;
    /**
     * デバイスTOKEN管理Service
     */
    @Autowired
    MstDeviceTokenService mstDeviceTokenService;

    /**
     * mstLoginService
     */
    @Autowired
    MstLoginService mstLoginService;

    /**
     * mstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * sysLoginController
     */
    @Autowired
    SysLoginController sysLoginController;

    @Autowired
    MstManagerService mstManagerService;
    @Autowired
    CommonService commonService;

    Logger logger = LoggerFactory.getLogger(SysLoginController.class);

    /**
     * 固定文字列
     */
    @Value("${md5.secret}")
    private String secret;
    
    /** registrationId */
    @Value("${saml2.regId}")
    private String regId;

    /** IdP エンティティID */
    @Value("${saml2.idpEntityId}")
    private String idpEntityId;

    /** IdP SSOエンドポイント */
    @Value("${saml2.idpSSOLocation}")
    private String idpSSOLocation;

    /** SPの秘密鍵 */
    @Value("${saml2.spPrivateKey}")
    private String spPrivateKey;

    /** SPの共通鍵 */
    @Value("${saml2.spCertificate}")
    private String spCertificate;

    /** IDPの共通鍵 */
    @Value("${saml2.idpCertificate}")
    private String idpCertificate;

    /** 認証失敗URL */
    @Value("${saml2.failureUrl}")
    private String failureUrl;

    @Autowired
    private SamlService service;

    /**
     * セッションタイムアウト時間（生徒）
     */
    @Value("${shiro.session.expireTimeStu}")
    private Integer expireTimeStu;
    //NWT　楊　2021/07/22　MANAMIRU1-737　Delete　Start
    //NWT　楊　2021/07/22　MANAMIRU1-737　Delete　Start

//    @GetMapping("captcha.jpg")
//    @LoginLog
//    public void captcha(HttpServletResponse response, String uuid) throws ServletException, IOException {
//        response.setHeader("Cache-Control", "no-store, no-cache");
//        response.setContentType("image/jpeg");
//
//        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
//
//        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(image, "jpg", out);
//        IOUtils.closeQuietly(out);
//    }

    /**
     * ログイン
     *
     * @throws Exception
     */
    @PostMapping("/sys/login")
    @ResponseBody
    @LoginLog
    public Map<String, Object> login(String username, String password, String brandCd, String loginUrl, String equipment, HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            //            if (1 == 1){
            //                throw new Exception("ログイン中にテスト用例外が発生しました。");
            //            }
            //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　Start
            ShiroUtils.setSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG,true);
            //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　End
            if (StringUtils.isEmpty(loginUrl)) {
                loginUrl = StringUtils.defaultString(request.getRequestURL());
                if (StringUtils.indexOf(username, "GID-") >= 0) {
                    username = username.split("-")[1];
                    password = StringUtils.defaultString(ShiroUtils.getSessionAttribute("GIDPWD"));
                    brandCd = "manamiru";
                } else {
                    username = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID);
                    password = StringUtils.defaultString(ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_PASSWORD));
                    brandCd = StringUtils.defaultString(ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD));
                }
            }

            Cookie cookieEquipment = new Cookie("equipment", equipment);
            cookieEquipment.setPath("/");
            response.addCookie(cookieEquipment);

            //1.3　コードマスタ明細からURL中の付きキーを取得する。
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "URL_ADD_KEY").eq("del_flg", 0));
            if (mstCodDEntity == null) {
                logger.error("--------table mst_cod_d data miss--------");
            }
            String manaFlag = StringUtils.equals(mstCodDEntity.getCodValue(), brandCd) ? "1" : "0";
            ShiroUtils.setSessionAttribute(GakkenConstant.MANA_FLAG, manaFlag);
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, brandCd);

            CM0003.LoginCheckResultDto loginCheckResultDto;
            try {
                //ログインIDのGID認証処理
                loginCheckResultDto = CM0003.loginCheck(username, password);
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GID_FLG, loginCheckResultDto.getGidCheckFlag());
            } catch (LockedAccountException e) {
                return R.error(MessageUtils.getMessage("MSGCOMN0002")).put("lockFlg", "1");
            }
            R loginCheck = CM0006.loginCheck(loginCheckResultDto, username, "1", loginUrl);
            if ((Integer)loginCheck.get("code") != 0){
                return loginCheck;
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

            //今回のログインした端末類型を取得する。
            switch (equipment) {
                case "otherEquip":
                    //PCの場合、端末区分＝「1：PC」
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, PC);
                    break;
                case "tablet":
                    //タブレットの場合、端末区分＝「2：タブレット」
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, TABLET);
                    break;
                case "phone":
                    //スマホの場合、端末区分＝「3：スマホ」
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, GakkenConstant.EQUIPMENT_DIV.PHONE);
                    break;
                default:
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, PC);
            }

            //ログインURL
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_LOGIN_URL, loginUrl);
            // 画面入力したログインIDをセッションに格納する
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID, username);
            // ユーザログインPW
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_PASSWORD, password);
            String gidFlg = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG);
            //NWT　楊　2021/06/30　MANAMIRU1-699 --> 727　Edit　Start
            String gidpk = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
            String tchCd = (String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
            String tchCdFlg = "0";
            Integer count = 0;
            if (StringUtils.equals("1",gidFlg)){
                if (StringUtils.isNotBlank(gidpk)){
                    count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("gidpk",gidpk).eq("del_flg",0));
                    username = count > 0 ? gidpk:username;
                }if (StringUtils.isNotBlank(tchCd) && count == 0){
                    count = mstManagerService.count(new QueryWrapper<MstManagerEntity>().eq("tch_cd",tchCd).eq("del_flg",0));
                    if (count > 0){
                        //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　Start
                        boolean allTchCdUsrsFirstLoginFlg = shiroService.selectFirstLoginUsrByTchcd(tchCd) == 0 ? true:false;
                        //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　End
                        ShiroUtils.setSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG,allTchCdUsrsFirstLoginFlg);
                        username = tchCd;
                        tchCdFlg = "1";
                        //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　End
                    }
                }
            }
            //NWT　楊　2021/06/30　MANAMIRU1-699 --> 727　Edit　End
            // ユーザーの登録IDとブランドコード
            List<SysUserEntity> userEntityList = shiroService.getUserByLoginId(username, manaFlag, brandCd, gidFlg, tchCdFlg);

            if (userEntityList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0001"));
            } else {
                Integer maxErrorCount = userEntityList.stream().max(Comparator.comparing(SysUserEntity::getErrorCount)).get().getErrorCount();
                ShiroUtils.setSessionAttribute(GakkenConstant.MAX_ERROR_COUNT, maxErrorCount);
                if (!StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getLoginCheckFlag())){
                    return updateLockState(userEntityList.get(0).getAfterUsrId());
                }
                String roleDiv = userEntityList.get(0).getRoleDiv().trim();
                //2021/01/22 liyuhuan modify start
                if (StringUtils.equals(roleDiv, "4")){
                    // 学生ログインgkgc
                    if (StringUtils.equals(brandCd,commonService.getOrgIdLower())||(userEntityList.size()==1&&StringUtils.equals(userEntityList.get(0).getBrandCd(),commonService.getOrgIdLower()))) {
                        return R.error("お子さまのアカウントとなりますので、保護者さま用のアカウントにてログインしてください");
                    }
                }
                //2021/01/22 liyuhuan modify end

                String strUrl = null;

                if (StringUtils.equals(roleDiv, "3")) {
                    List<F30002Dto> stuMstEntities = new ArrayList<>();
                    List<String> guardIds = new ArrayList<>();
                    //複数教室
                    for (MstUsrEntity user : userEntityList) {
                        guardIds.add(user.getUsrId());
                    }
                    String guards = String.join(",", guardIds);
                    stuMstEntities = f30002Service.getStudents(guards);
                    if (stuMstEntities.size() == 0) {
                        //         子供個数が0個の場合
                        return R.error(MessageUtils.getMessage("MSGCOMN0102"));
                    } else if (stuMstEntities.size() > 1) {
                        R r = sysLoginController.loginUser(userEntityList.get(0));
                        if ((int)r.get("code") != 0) {
                            return r;
                        }
                        return sysLoginController.afterSessionLogin(response, userEntityList.get(0));
                    } else {
                        // 塾時期チェック
                        if (hasLearnPrd(stuMstEntities.get(0).getStuId())) {
                            //学生のリストをトラバースする
                            for (SysUserEntity sysUserEntity : userEntityList) {
                                //組織IDが等しいと判断する場合
                                if (sysUserEntity.getOrgId().equals(stuMstEntities.get(0).getOrgId())) {
//                                    等しい、このプロテクターを交換してください
                                    R r1 = sysLoginController.loginUser(sysUserEntity);
                                    if ((int)r1.get("code") != 0) {
                                        return r1;
                                    }
                                    return sysLoginController.afterSessionLogin(response, sysUserEntity);
                                }
                            }
                        } else {
                            return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                        }
                    }
                } else {
                    //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
                    if (StringUtils.equals("1",gidFlg)){
                        if (StringUtils.isNotBlank(tchCd)){
                            //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　Start
                            boolean allTchCdUsrsFirstLoginFlg = shiroService.selectFirstLoginUsrByTchcd(tchCd) == 0 ? true:false;
                            //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　End
                            ShiroUtils.setSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG,allTchCdUsrsFirstLoginFlg);
                            count = mstManagerService.count(new QueryWrapper<MstManagerEntity>().eq("tch_cd",tchCd).eq("del_flg",0));
                        }
                        if (StringUtils.isNotBlank(gidpk)  && count == 0){
                            count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("gidpk",gidpk).eq("del_flg",0));
                        }
                    }else {
                        count = mstUsrService.count(new QueryWrapper<MstUsrEntity>().eq("after_usr_id",username).eq("del_flg",0));
                    }
                    if (count == 1) {
                        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End

                        R r = sysLoginController.loginUser(userEntityList.get(0));

                        if ((int)r.get("code") != 0) {
                            return r;
                        }
                        // 塾時期チェック
                        if (StringUtils.equals(userEntityList.get(0).getRoleDiv().trim(), "4")) {
                            if (!hasLearnPrd(userEntityList.get(0).getUsrId())) {
                                return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                            }
                        }
                        return sysLoginController.afterSessionLogin(response, userEntityList.get(0));
                    //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
                    } else if (count > 1) {
                    //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End
                        //ブランドコードをセッションに保存
                        Cookie cookie = new Cookie("brandcd", (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD));
                        cookie.setPath("/");
                        //            cookie.setHttpOnly(true);
                        cookie.setMaxAge(-1);
                        response.addCookie(cookie);
                        //ログインユーザーが複数組織に存在する場合、
                        EQUIPMENT_DIV equipment_div = (EQUIPMENT_DIV)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV);
                        int equipDiv = equipment_div == null ? 0 : equipment_div.getValue();
                        if (equipDiv == PC.getValue()) {
                            strUrl = "/common/F40013.html";
                        } else if (equipDiv == TABLET.getValue()) {
                            strUrl = "/common/F40014.html";
                        } else if (equipDiv == PHONE.getValue() && (Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.MANAGER.getValue() || Integer.parseInt(
                                roleDiv) == GakkenConstant.ROLE_DIV.MENTOR.getValue())) {
                            strUrl = "/common/F40015.html";
                        } else if (equipDiv == PHONE.getValue() && Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.STUDENT.getValue()) {
                            strUrl = "/common/F40016.html";
                        }
                    }
                }
                return R.ok().put("url", strUrl);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
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
     * <p>キャラクター登録最初のアドレス</p>
     *
     * @return
     */
    @LoginLog
    public R getUrl(SysUserEntity user) {
        EQUIPMENT_DIV equipment_div = (EQUIPMENT_DIV)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV);
        int equipment = equipment_div == null ? 0 : equipment_div.getValue();
        String brandCd = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);
//        SysUserEntity user = ShiroUtils.getUserEntity();
        String strUrl = null;

        String username = "";

        // ユーザーロール区分
        String roleDiv = user.getRoleDiv().trim();

        switch (roleDiv) {
            case "0":
                // システム管理者
                strUrl = "/sys/index.html";
                break;
            case "1":
                // 管理者
                strUrl = "/manager/F00001.html";
                if (equipment != 1 && StringUtils.equals(brandCd,commonService.getOrgIdAdd())) {
                    strUrl = "/manager/F21017.html";
                }
                break;
            case "2":
                // メンター
                strUrl = "/manager/F00001.html";
                // メンター基本マスタを元に、メンター姓名を取得する。
                MstMentorEntity mstMentorEntity = mstMentorService.getOne(new QueryWrapper<MstMentorEntity>().and(w->w.eq("mentor_id", user.getUsrId())));
                if (mstMentorEntity != null) {
                    String mentorNm = mstMentorEntity.getFlnmNm() + " " + mstMentorEntity.getFlnmLnm();
                    username = mentorNm;
                    ShiroUtils.setSessionAttribute(GakkenConstant.MENTOR_NM, mentorNm);
                }
                if (equipment != 1 && StringUtils.equals(brandCd,commonService.getOrgIdAdd())) {
                    strUrl = "/manager/F21017.html";
                }
                break;
            case "3":
                List<F30002Dto> stuMstEntities = new ArrayList<>();
                // 保護者
                //複数教室
                List<MstUsrEntity> users = mstUsrService.list(
                        new QueryWrapper<MstUsrEntity>().select("usr_id", "org_id").eq("after_usr_id", user.getAfterUsrId()).eq("del_flg",
                                0));
                List<String> guardIds = new ArrayList<>();
                //複数教室
                for (MstUsrEntity mstUsrEntity : users) {
                    guardIds.add(mstUsrEntity.getUsrId());
                }
                String guards = String.join(",", guardIds);
                stuMstEntities = f30002Service.getStudents(guards);
                if (stuMstEntities.size() == 0) {
                    // 子供個数flg
                    return R.ok().put("strUrl","countFlg");
                } else if (stuMstEntities.size() == 1) {
                    //ログイン後最初に表示されるページが「ホーム」に修正して下さい GKGC組織判断 2020/12/11 modify yang --start
                    // １個子供
                    strUrl = brandCd.toUpperCase().equals(commonService.getOrgIdAdd()) ? "/guard/F30421.html" : "/guard/F30112.html";
                    //ログイン後最初に表示されるページが「ホーム」に修正して下さい GKGC組織判断 2020/12/11 modify yang --start
                    //保護者の情報
                    MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("guard_id", user.getUsrId()).eq("del_flg", 0));
                    if (mstGuardEntity != null) {
                        username = mstGuardEntity.getFlnmNm() + " " + mstGuardEntity.getFlnmLnm();
                    }
                    // 塾時期チェック
                    if (!hasLearnPrd(stuMstEntities.get(0).getStuId())) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                    }
                    // 2021/1/6 huangxinliang modify start
//                    String familyCd = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_FAMILY_CD);
//                    String stuId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
//                    if (StringUtils.isNotBlank(familyCd) && StringUtils.isNotBlank(stuId)){
//                        CM0003.updateMstStuByStuId(familyCd, ShiroUtils.getUserEntity().getAfterUsrId(), stuId);
//                    }
                    // 2021/1/6 huangxinliang modify end
                } else {
                    strUrl = "/guard/F30002.html";
                }
                break;
            case "4":
                // 生徒
                strUrl = "/student/F10301.html";
                // 生徒基本マスタを元に、生徒姓名を取得する。
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().and(w->w.eq("stu_id", user.getUsrId())));
                if (mstStuEntity != null) {
                    String stuNm = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                    username = stuNm;
                    ShiroUtils.setSessionAttribute(GakkenConstant.STU_NM, stuNm);
                }

                if (equipment == 3) {
                    strUrl = "/student/F11008.html";
                }
                // 塾時期チェック
                if (!hasLearnPrd(user.getUsrId())) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                }
                break;
            default:
                // //初回登録
                strUrl = "/student/F10301.html";
        }
        // ユーザログイン
        Extensions exts = new Extensions();
        // ブランドID
        exts.put(XApiConstant.EXT_KEY_BRAND_ID, brandCd);
        // ログインID
        exts.put(XApiConstant.EXT_KEY_AFTTER_USR_ID, user.getAfterUsrId());
        // 利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, user.getUsrId());
        // 利用者名前
        exts.put(XApiConstant.EXT_KEY_USER_NAME, username);
        // ロール区分
        exts.put(XApiConstant.EXT_KEY_ROLE_DIV, StringUtils.trim(user.getRoleDiv()) + ":" + user.getRoleName());
        // 組織ID
        exts.put(XApiConstant.EXT_KEY_ORG_ID, user.getOrgId());
        // 組織名
        exts.put(XApiConstant.EXT_KEY_ORG_NAME, user.getOrgNm());
        // ログイン時間
        exts.put(XApiConstant.EXT_KEY_LOGIN_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));

        XApiUtils.saveStatement(Verbs.loggedIn(), Activitys.system(), exts);
        return R.ok().put("strUrl",strUrl);
    }

    /**
     * 最初のログイン
     *
     * @return
     */
    @LoginLog
    public R firstLoginUrl() {
        //セッションでユーザー情報を取得する
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        String strUrl = null;
        //gidフラグを取得する
        //NWT　楊　2021/06/23　MANAMIRU1-698.2　Edit　Start
        String gidFlg = sysUserEntity.getGidFlg();
        logger.info("------------gidFlg:" + gidFlg);
        //NWT　楊　2021/06/23　MANAMIRU1-698.2　Edit　End
        String roleDiv = sysUserEntity.getRoleDiv().trim();
        //brandCodeを取得
        //        String brandCd = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);
        //GID登録フラグ＝「1：GID」の場合
        if (StringUtils.equals(gidFlg, "1")) {
            String mailAddress = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_MAIL_ADDRESS);
            if (!StringUtils.isEmpty(mailAddress)) {
                //API返却したメールアドレス≠NULLの場合、ログインユーザのマナミルDBのメールアドレスを更新する。
                if (StringUtils.equals(roleDiv, "3")) {
                    //「ロール区分」が「3：保護者」の場合、ログインユーザのマナミルDBのメールアドレスを更新する。
                    shiroService.updateMailAddress(sysUserEntity.getAfterUsrId(), roleDiv, mailAddress, sysUserEntity.getUsrId());
                }
            }
        }
        //「ロール区分」が「1：管理者　又は　2：メンター　又は　3：保護者」の場合、メールアドレスの認証処理
        strUrl = "/common/F40021.html";
        if (StringUtils.equals(sysUserEntity.getManaRuleFlg(), "1") && ((StringUtils.equals(sysUserEntity.getGidRuleFlg(), "1") && StringUtils.equals(
                gidFlg, "1")) || StringUtils.equals(gidFlg, "0"))) {

            switch (roleDiv) {
                case "3":
                    strUrl = "/common/F40017.html";
                    MstGuardEntity guardEntity = mstGuardService.getOne(
                            new QueryWrapper<MstGuardEntity>().eq("guard_id", ShiroUtils.getUserEntity().getUsrId()));
                    if (StringUtils.isNotBlank(guardEntity.getMailad())) {
                        strUrl = "/common/F40008.html";
                        //NWT　楊　2021/06/25　MANAMIRU1-709.1　Edit　Start
                        //初回の場合
                        if (!StringUtils.equals("0",ShiroUtils.getUserEntity().getFstLoginFlg())){
                        //NWT　楊　2021/06/25　MANAMIRU1-709.1　Edit　End
                            strUrl = getGuardLoginUrl(sysUserEntity).get("url").toString();
                        }
                    }
                    break;
                case "1":
                case "2":
                    R result = sysLoginController.getUrl(sysUserEntity);
                    if (!result.get("code").equals(-1)){
                        strUrl = result.get("strUrl").toString();
                    }else {
                        return result;
                    }
                    break;
                default:
                    break;
            }
        }
        //「ロール区分」が「4：生徒」の場合、
        if (StringUtils.equals(roleDiv, "4")) {
            // 塾時期チェック
            if (!hasLearnPrd(sysUserEntity.getUsrId())) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
            } else {
                //初回登録ユーザのパスワードを変更するために、「F40008_パスワード変更画面」へ遷移する。
                strUrl = "/common/F40008.html";
            }
        }
        return R.ok().put("strUrl",strUrl);
    }

    /**
     * <p>マニフェストの作成処理</p>
     *
     * @param brandcd
     * @return
     */
    @RequestMapping(value = {"/manifest.json"})
    @ResponseBody
    @LoginLog
    public String getJson(String brandcd) {

        JSONObject result = new JSONObject();
        int count = mstOrgService.count(new QueryWrapper<MstOrgEntity>().eq("brand_cd", brandcd).eq("del_flg", 0));
        if (count > 0) {
            result.put("start_url", HttpContextUtils.getHttpServletRequest().getContextPath() + "/?utm_source=homescreen&brandcd=" + brandcd);
            result.put("name", "マナミル");
            result.put("short_name", "マナミル");

            result.put("display", "standalone");
            result.put("background_color", "#e2ffef");
            result.put("theme_color", "#278fef");

            Map<String, String> pixs = new HashMap<String, String>();
            pixs.put("512x512", "android");
            pixs.put("192x192", "android");
            pixs.put("180x180", "apple");
            pixs.put("152x152", "apple");
            pixs.put("120x120", "apple");

            JSONArray icons = new JSONArray();
            for (String pix : pixs.keySet()) {
                JSONObject icon = new JSONObject();
                if ("android".equals(pixs.get(pix))) {
                    icon.put("sizes", pix);
                    icon.put("type", "image/png");
                    icon.put("src", "img/icons/android-chrome-" + pix + ".png");
                    icons.add(icon);
                } else {
                    icon.put("sizes", pix);
                    icon.put("type", "image/png");
                    icon.put("src", "img/icons/apple-touch-icon-" + pix + ".png");
                    icons.add(icon);
                }
            }

            result.put("icons", icons);
        }

        return JSON.toJSONString(result);
    }

    /**
     * ログイン
     *
     * @throws Exception
     */
    @RequestMapping("/signLogin")
    @LoginLog
    public String signLogin(RedirectAttributes attributes, String userid, String sign, HttpServletResponse response) throws Exception {

        // MD5暗号化された文字列が一致しないの場合、
        if (!sign.equals(DigestUtils.md5DigestAsHex((userid + secret).getBytes()))) {
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
        }

        // ユーザーの登録IDとブランドコード
        SysUserEntity userEntity = shiroService.getUserByUserId(userid);
        if (userEntity == null) {
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
        }

        Subject subject = ShiroUtils.getSubject();
        UserAuthToken token = null;
        // 保護者の場合、rememberMe＝true
        //        if (StringUtils.defaultString(GakkenConstant.ROLE_DIV.GUARD.getValue()).equals(StringUtils.trim(userEntity.getRoleDiv()))) {
        //            token = new UserAuthToken(StringUtils.defaultString(userEntity.getId()), "", Constant.LOGIN_TYPE_LE, true, null);
        //        } else {
        //            token = new UserAuthToken(StringUtils.defaultString(userEntity.getId()), "", Constant.LOGIN_TYPE_LE, false, null);
        //        }
        token = new UserAuthToken(StringUtils.defaultString(userEntity.getId()), "", Constant.LOGIN_TYPE_LE, false, null);
        if (StringUtils.defaultString(GakkenConstant.ROLE_DIV.STUDENT.getValue()).equals(StringUtils.trim(userEntity.getRoleDiv()))) {
            ShiroUtils.getSubject().getSession().setTimeout(expireTimeStu.longValue() * 60 * 1000);
        }
        try {
            subject.login(token);

            // ブランドコードをセッションに格納する
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, userEntity.getBrandCd());
        } catch (UnknownAccountException e) {
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
        } catch (IncorrectCredentialsException e) {
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
        } catch (LockedAccountException e) {
            attributes.addFlashAttribute("lockFlg", "1");
            return redirectLogin(attributes, e.getMessage());
        } catch (AuthenticationException e) {
            attributes.addFlashAttribute("lockFlg", "1");
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
        }

        String strUrl = sysLoginController.getUrl(userEntity).get("strUrl").toString();

        // 子供個数が0個の場合
        if (StringUtils.equals(strUrl, "countFlg")) {
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0102"));
        }

        //        // ブランドコードをセッションに格納する
        //        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, brandcd);

        String csrfToken = jwtUtils.generateToken(userEntity.getId());
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_CSRF_TOKEN, csrfToken);

        // 塾学習期間取得
        if ("".equals(strUrl)) {
            return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
        } else {
            Cookie cookie = new Cookie("brandcd", userEntity.getOrgId());
            cookie.setPath("/");
            //            cookie.setHttpOnly(true);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);

            response.addHeader("token", csrfToken);

            return "redirect:" + strUrl;
        }
    }

    /**
     * 組織を選択後、セッションログインと初回ログイン判定を実施
     *
     * @param response
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/sys/chooseOrg", method = RequestMethod.POST)
    @ResponseBody
    @LoginLog
    public R chooseOrg(HttpServletResponse response, String orgId) {
        //NWT　楊　2021/08/26　MANAMIRU1-766　Delete　Start
        //NWT　楊　2021/08/26　MANAMIRU1-766　Delete　End
        SysUserEntity sysUserEntity = null;
        //ログインユーザーIDを取得
        //NWT　楊　2021/06/30　MANAMIRU1-699 --> 727　Edit　Start
        String gidFlg = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG);
        String gidpk = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
        String tchCd = (String) ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
        if (StringUtils.equals("1",gidFlg)){
            if (StringUtils.isNotBlank(gidpk)){
                sysUserEntity = shiroService.getUserByGidpk(gidpk,orgId);
            }
            if (StringUtils.isNotBlank(tchCd) && sysUserEntity == null){
                sysUserEntity = shiroService.selectUserByOrgIdAndTchCd(tchCd,orgId);
            }
        }else {
            //ログインユーザーIDを取得
            String afterUsrId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID);
            //ログインユーザーデータを取得する
            sysUserEntity = shiroService.selectUserByOrgIdAndAfterUsrId(afterUsrId, orgId);
        }
        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End
        //NWT　楊　2021/06/30　MANAMIRU1-699 --> 727　Edit　End
        //ユーザーデータが存在しません
        if (sysUserEntity == null) {
            //｛MSGCOMN0106：｛登録認証が失敗しました。｝｝
            return R.error(MessageUtils.getMessage("MSGCOMN0106"));
        }
        //セッションログイン
        R r = sysLoginController.loginUser(sysUserEntity);
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",sysUserEntity.getUsrId()).eq("del_flg",0));
        if (ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_LOGIN_APP) != null) {
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            commonService.updateDeviceToken(null,mstUsrEntity,null,request);
            // add at 2021/9/30 for V9.02 by NWT HuangXL START
            // add at 2021/11/18 for V9.02 by NWT HuangXL START
//            commonService.deleteRedundantDeviceToken(mstUsrEntity.getUsrId());
            // add at 2021/9/30 for V9.02 by NWT HuangXL END
            // add at 2021/11/18 for V9.02 by NWT HuangXL END
        }
        //セッションのログインに失敗しました
        if ((int)r.get("code") != 0) {
            return r;
        }
        if (StringUtils.equals(mstUsrEntity.getRoleDiv().trim(), "4")){
            List<MstOrgEntity> mstOrgEntities =  mstOrgService.list(new QueryWrapper<MstOrgEntity>().eq("org_id",mstUsrEntity.getOrgId()).eq("del_flg",0));
            // 学生ログインgkgc
            if (StringUtils.equals(mstOrgEntities.get(0).getBrandCd(),commonService.getOrgIdLower())) {
                return R.error("お子さまのアカウントとなりますので、保護者さま用のアカウントにてログインしてください");
            }
        }
        //組織名をセッションに保存します
        ShiroUtils.setSessionAttribute(GakkenConstant.ORG_NM, sysUserEntity.getOrgNm());
        //初回ログイン判定
        return sysLoginController.afterSessionLogin(response, sysUserEntity);
    }

    /**
     * キャラクター登録最初のアドレスを取得
     *
     * @return
     */
    @RequestMapping("/sys/getUrl")
    @ResponseBody
    @LoginLog
    public R getHomePageUrl() {
        String url;
        R result = sysLoginController.getUrl(ShiroUtils.getUserEntity());
        if (!result.get("code").equals(-1)){
            url = result.get("strUrl").toString();
        }else {
            return result;
        }
        return R.ok().put("url", url);
    }

    /**
     * リダイレクトログイン
     *
     * @param attributes リダイレクト属性
     * @param errorMsg エラーメッセージ
     * @return ログインパス
     */
    @LoginLog
    public String redirectLogin(RedirectAttributes attributes, String errorMsg) {
        attributes.addFlashAttribute("errorMsg", errorMsg);
        return "redirect:/login";
    }

    /**
     * ログイン
     *
     * @param attributes
     * @param userid
     * @param key
     * @param equipment
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/pLogin")
    public String pLogin(RedirectAttributes attributes, String userid, String key, String equipment, HttpServletResponse response, HttpServletRequest request) throws Exception {
        //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　Start
        ShiroUtils.setSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG,true);
        //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　End
        String useridFake = userid;
        //今回のログインした端末類型を取得する。
        switch (equipment) {
            case "tablet":
                //タブレットの場合、端末区分＝「2：タブレット」
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, TABLET);
                break;
            case "phone":
                //スマホの場合、端末区分＝「3：スマホ」
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, GakkenConstant.EQUIPMENT_DIV.PHONE);
                break;
        }
        //NWT　楊　2021/08/02　MANAMIRU1-675　Edit　Start
        logger.info("------userid:" + userid);
        //NWT　楊　2021/08/02　MANAMIRU1-675　Edit　End
        // add at 2021/11/19 for V9.02 by NWT HuangXL START
        // add at 2021/12/01 for V9.02 by NWT HuangXL START
        if (userid.split(",").length < 3){
            throw new RRException(GakkenConstant.DEVICE_TOKEN_NULL_MSG);
        }
        // add at 2021/12/01 for V9.02 by NWT HuangXL END
        String deviceToken = userid.split(",")[2];
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_DEVICETOKEN, deviceToken);
        // add at 2021/11/19 for V9.02 by NWT HuangXL END
        String userNameHead = StringUtils.indexOf(userid, "GID-") >= 0? userid.split("-")[1].split(",")[0]:userid.split(",")[0];
        String userNameTail = StringUtils.indexOf(userid, "GID-") >= 0? userid.split("-")[1].split(",")[1]:userid.split(",")[1];
        userid = userid.split(",")[0];
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_LOGIN_APP,true);
        /* 2021/09/16 manamiru1-772 cuikl del start */
        //NWT　楊　2021/08/26　MANAMIRU1-766　Delete　Start
        //NWT　楊　2021/08/26　MANAMIRU1-766　Delete　End
        /* 2021/09/16 manamiru1-772 cuikl del end */
        String stuId = null;
        String strUrl = "";
        String brandCd = null;
        // MD5暗号化された文字列が一致しないの場合、
        if (key != null) {
            if (!key.equals(DigestUtils.md5DigestAsHex((useridFake + "appmsg").getBytes()))) {
                return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
            }
            else {
                if (StringUtils.indexOf(userid, "GID-") >= 0) {

                    MstUsrEntity mstUsrEntity = null;
                    //NWT　楊　2021/07/21　MANAMIRU1-727.③　Edit　Start
                    String gidFlg = "0";
                    if (StringUtils.equals("0",userNameTail)){
                        mstUsrEntity = mstUsrService.list(new QueryWrapper<MstUsrEntity>().eq("after_usr_id",userNameHead).eq("del_flg",0)).get(0);
                    }else if (StringUtils.equals("1",userNameTail)){
                        gidFlg = "1";
                        mstUsrEntity = mstUsrService.list(new QueryWrapper<MstUsrEntity>().eq("gidpk",userNameHead).eq("del_flg",0)).get(0);
                    }else if (StringUtils.equals("2",userNameTail)){
                        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
                        gidFlg = "1";
                        //NWT　楊　2021/07/21　MANAMIRU1-727.③　Edit　End
                        String userId = "";
                        List<MstManagerEntity> mstManagerEntityList = mstManagerService.list(new QueryWrapper<MstManagerEntity>().eq("tch_cd",userNameHead).eq("del_flg",0));
                        if (mstManagerEntityList.size() == 0){
                            List<MstMentorEntity> mstMentorEntityList = mstMentorService.list(new QueryWrapper<MstMentorEntity>().eq("tch_cd",userNameHead).eq("del_flg",0));
                            userId = mstMentorEntityList.get(0).getMentorId();
                        }else {
                            userId = mstManagerEntityList.get(0).getMgrId();
                        }
                        mstUsrEntity = mstUsrService.list(new QueryWrapper<MstUsrEntity>().eq("usr_id",userId).eq("del_flg",0)).get(0);
                    }
                    //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End
                    logger.info("gidFlg:" + mstUsrEntity.getGidFlg());
                    logger.info("role:" + mstUsrEntity.getRoleDiv());
                    //NWT　楊　2021/07/21　MANAMIRU1-727.③　Delete　Start
                    //NWT　楊　2021/07/21　MANAMIRU1-727.③　Delete　End
                    String role = mstUsrEntity.getRoleDiv();

                    String username;
                    //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
                    String tchCdFlg = "0";
                    //NWT　楊　2021/07/20　MANAMIRU1-727-②　Edit　Start
                    if (!StringUtils.equals("1",StringUtils.trim(role)) && !StringUtils.equals("2",StringUtils.trim(role))){
                    //NWT　楊　2021/07/20　MANAMIRU1-727-②　Edit　End
                        /* 2021/09/16 manamiru1-772 cuikl del start */
                        /* 2021/09/16 manamiru1-772 cuikl del end */
                        username =  StringUtils.equals("0",gidFlg)?userid.split("-")[1]:mstUsrEntity.getGidpk();
                        /* 2021/09/16 manamiru1-772 cuikl edit start */
                        logger.debug("================username1:" + username);
                        /* 2021/09/16 manamiru1-772 cuikl edit end */
                    }else {
                        /* 2021/09/16 manamiru1-772 cuikl del start */
                        /* 2021/09/16 manamiru1-772 cuikl del end */
                        String tchCd = mstManagerService.list(new QueryWrapper<MstManagerEntity>().eq("mgr_id",mstUsrEntity.getUsrId()).eq("del_flg",0)).get(0).getTchCd();
                        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　Start
                        ShiroUtils.setSessionAttribute(GakkenConstant.TCH_CD,tchCd);
                        //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　Start
                        boolean allTchCdUsrsFirstLoginFlg = shiroService.selectFirstLoginUsrByTchcd(tchCd) == 0 ? true:false;
                        //NWT　楊　2021/07/22　MANAMIRU1-737　Edit　End
                        ShiroUtils.setSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG,allTchCdUsrsFirstLoginFlg);
                        //NWT　楊　2021/07/20　MANAMIRU1-727-①　Edit　End
                        /* 2021/09/16 manamiru1-772 cuikl del start */
                        /* 2021/09/16 manamiru1-772 cuikl del end */
                        //NWT　楊　2021/07/21　MANAMIRU1-727-③　Edit　Start
                        if (StringUtils.equals("1",userNameTail)){
                            username = userid.split("-")[1];
                        }else if (StringUtils.equals("2",userNameTail)){
                            tchCdFlg = "1";
                            username = tchCd;
                            /* 2021/09/16 manamiru1-772 cuikl edit start */
                            logger.debug("================username3:" + username);
                            /* 2021/09/16 manamiru1-772 cuikl edit end */
                            allTchCdUsrsFirstLoginFlg = shiroService.selectFirstLoginUsrByTchcd(tchCd) == 0 ? true:false;
                            ShiroUtils.setSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG,allTchCdUsrsFirstLoginFlg);
                        }else {
                            username = userid.split("-")[1];
                            /* 2021/09/16 manamiru1-772 cuikl del start */
                            /* 2021/09/16 manamiru1-772 cuikl del end */
                            //NWT　楊　2021/07/21　MANAMIRU1-727-③　Edit　End
                        }
                        //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　End
                    }
                    // ユーザーの登録IDとブランドコード
                    List<SysUserEntity> userEntityList = shiroService.getUserByLoginId(username, "1", "manamiru", gidFlg, tchCdFlg);
                    //NWT　楊　2021/07/20　MANAMIRU1-727-②　Edit　Start
                    if (userEntityList.size() == 0) {
                        return MessageUtils.getMessage("MSGCOMN0001");
                    }
                    //NWT　楊　2021/07/20　MANAMIRU1-727-②　Edit　End
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_PASSWORD,userEntityList.get(0).getUsrPassword());
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID,userEntityList.get(0).getAfterUsrId());
                    ShiroUtils.setSessionAttribute(GakkenConstant.MANA_FLAG,"1");
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GID_FLG,userEntityList.get(0).getGidFlg());
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GIDPK,userEntityList.get(0).getGidpk());
                    sysLoginController.loginUser(userEntityList.get(0));
                    String roleDiv = userEntityList.get(0).getRoleDiv().trim();
                    if (StringUtils.equals(roleDiv, "3")) {
                        List<F30002Dto> stuMstEntities = new ArrayList<>();
                        List<String> guardIds = new ArrayList<>();
                        //複数教室
                        for (MstUsrEntity user : userEntityList) {
                            guardIds.add(user.getUsrId());
                        }
                        String guards = String.join(",", guardIds);
                        stuMstEntities = f30002Service.getStudents(guards);
                        if (stuMstEntities.size() > 1) {
                            R r = sysLoginController.afterSessionLogin(response, userEntityList.get(0));
                            strUrl = r.get("url").toString();
                        } else {
                            // 塾時期チェック
                            if (hasLearnPrd(stuMstEntities.get(0).getStuId())) {
                                R r = sysLoginController.afterSessionLogin(response, userEntityList.get(0));
                                strUrl = r.get("url").toString();
                            } else {
                                return MessageUtils.getMessage("MSGCOMN0017", "塾学習期間");
                            }
                        }
                    } else {
                            //ログインユーザーが複数組織に存在する場合、
                        EQUIPMENT_DIV equipment_div = (EQUIPMENT_DIV)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV);
                        int equipDiv = equipment_div == null ? 0 : equipment_div.getValue();
                        if (equipDiv == PC.getValue()) {
                            strUrl = "/common/F40013.html";
                        } else if (equipDiv == TABLET.getValue()) {
                            strUrl = "/common/F40014.html";
                        } else if (equipDiv == PHONE.getValue() && (Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.MANAGER.getValue() || Integer.parseInt(
                                roleDiv) == GakkenConstant.ROLE_DIV.MENTOR.getValue())) {
                            strUrl = "/common/F40015.html";
                        } else if (equipDiv == PHONE.getValue() && Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.STUDENT.getValue()) {
                            strUrl = "/common/F40016.html";
                        }
                    }
                }
                else{
                    SysUserEntity sysUserEntity = shiroService.getUserByUserId(userid);
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_PASSWORD,sysUserEntity.getUsrPassword());
                    ShiroUtils.setSessionAttribute(GakkenConstant.MANA_FLAG, "0");
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID,sysUserEntity.getAfterUsrId());
                    sysLoginController.loginUser(sysUserEntity);
                    R info = sysLoginController.afterSessionLogin(response, sysUserEntity);
                    strUrl = info.get("url").toString();
                }
            }
        }
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_LOGIN_URL,request.getRequestURL().toString());
        Cookie cookieKey = new Cookie("key", "PUSHAPI");
        cookieKey.setPath("/");
        response.addCookie(cookieKey);
        // 2020/11/26 huangxinliang modify start
        // equipment cookie
        Cookie cookieEquipment = new Cookie("equipment", equipment);
        cookieEquipment.setPath("/");
        response.addCookie(cookieEquipment);
        // 2020/11/26 huangxinliang modify end
        return "redirect:" + strUrl;
    }

    /**
     * ログイン
     *
     * @throws Exception
     */
    @RequestMapping("/msgLogin")
    @LoginLog
    public String msgLogin(RedirectAttributes attributes, String msgId, String deviceToken,String equipment, String stuid, HttpServletResponse response) throws Exception {
        logger.info("=====================================deviceToken:" + deviceToken + "||stuid:" + stuid + "||msgId:" + msgId);
        /* 2021/09/16 manamiru1-772 cuikl del start */
        /* 2021/09/16 manamiru1-772 cuikl del end */
        MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(
                new QueryWrapper<MstDeviceTokenEntity>().eq("device_token", deviceToken).eq("del_flg", 0));
        String url = "";
        if (mstDeviceTokenEntity != null) {
            String urlText = "/QRATAPI";
            Subject subject = ShiroUtils.getSubject();
            String userId = mstDeviceTokenEntity.getUsrId();
            if (StringUtils.isNotBlank(userId)){
                // ユーザーの登録IDとブランドコード
                SysUserEntity userEntity = shiroService.getUserByUserId(userId);
                if (userEntity != null){
                    String roleDiv = StringUtils.trim(userEntity.getRoleDiv());
                    UserAuthToken token = null;
                    token = new UserAuthToken(StringUtils.defaultString(userEntity.getId()), "", Constant.LOGIN_TYPE_LE, false, null);
                    if (StringUtils.defaultString(GakkenConstant.ROLE_DIV.STUDENT.getValue()).equals(StringUtils.trim(userEntity.getRoleDiv()))) {
                        ShiroUtils.getSubject().getSession().setTimeout(expireTimeStu.longValue() * 60 * 1000);
                    }
                    try {
                        subject.login(token);
                        // ブランドコードをセッションに格納する
                        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, userEntity.getBrandCd());
                        CM0006.insertMstLoginData("0", null, userEntity.getUsrId(), urlText, "3", userEntity.getOrgId());
                    } catch (UnknownAccountException e) {
                        CM0006.insertMstLoginData("1", "4", userEntity.getUsrId(), urlText, "3", null);
                        return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
                    } catch (IncorrectCredentialsException e) {
                        CM0006.insertMstLoginData("1", "4", userEntity.getUsrId(), urlText, "3", null);
                        return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
                    } catch (LockedAccountException e) {
                        CM0006.insertMstLoginData("1", "4", userEntity.getUsrId(), urlText, "3", null);
                        attributes.addFlashAttribute("lockFlg", "1");
                        return redirectLogin(attributes, e.getMessage());
                    } catch (AuthenticationException e) {
                        CM0006.insertMstLoginData("1", "4", userEntity.getUsrId(), urlText, "3", null);
                        attributes.addFlashAttribute("lockFlg", "1");
                        return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0106"));
                    }
                    String brandCd = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", userEntity.getOrgId()).eq("del_flg", 0)).getBrandCd();
                    String strUrl = "";
                    switch (roleDiv) {
                        case "1":
                            strUrl = "/manager/F21024.html";
                            break;
                        case "2":
                            strUrl = "/manager/F21024.html";
                            break;
                        case "3":
                            //ログイン後最初に表示されるページが「ホーム」に修正して下さい GKGC組織判断 2020/12/11 modify yang --start
                            strUrl = StringUtils.equals(commonService.getOrgIdAdd(), brandCd.toUpperCase()) ? "/guard/F30421.html" : "/guard/F30112.html";
                            //ログイン後最初に表示されるページが「ホーム」に修正して下さい KGC組織判断 2020/12/11 modify yang --end
                            // 生徒ID
                            ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuid);
                            // 塾学習期間ID取得
                            Integer learnPrdId = shiroService.getLearnPrdIdByUserId(stuid, userEntity.getOrgId());
                            if (learnPrdId == null){
                                return strUrl = "";
                            }
                            // 塾学習期間ID
                            ShiroUtils.setSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID, learnPrdId);
                            MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", stuid).eq("del_flg", 0));
                            if (mstUsrEntity != null) {
                                // 組織ID
                                ShiroUtils.setSessionAttribute("orgId", mstUsrEntity.getOrgId());
                            }else {
                                return strUrl = "";
                            }
                            // 子供個数が0個の場合
                            if (StringUtils.equals(strUrl, "countFlg")) {
                                return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0102"));
                            }
                            break;
                        case "4":
                            strUrl = "/student/F11010.html";
                    }
                    String csrfToken = jwtUtils.generateToken(userEntity.getId());
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_CSRF_TOKEN, csrfToken);
                    Cookie cookieEquipment = new Cookie("equipment", StringUtils.isBlank(equipment)?"phone":equipment);
                    Cookie cookieKey = new Cookie("key", "PUSHAPI");
                    cookieEquipment.setPath("/");
                    cookieKey.setPath("/");
                    response.addCookie(cookieEquipment);
                    response.addCookie(cookieKey);
                    // 塾学習期間取得
                    if ("".equals(strUrl)) {
                        return redirectLogin(attributes, MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                    } else {
                        Cookie cookie = new Cookie("brandcd", brandCd);
                        cookie.setPath("/");
                        cookie.setMaxAge(-1);
                        response.addCookie(cookie);
                        response.addHeader("token", csrfToken);
                        return "redirect:" + strUrl;
                    }
                }
            }
        }
        return "redirect:" + url;
    }

    /**
     * login user to shiro
     *
     * @param sysUserEntity userData
     * @return
     */
    @LoginLog
    public R loginUser(SysUserEntity sysUserEntity) {
        //「Subject」を取得
        Subject subject = ShiroUtils.getSubject();
        //ユーザー名/パスワード認証トークンを作成する
        UserAuthToken token = null;
        //セッションでパスワードを取得する
        String password = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_PASSWORD);

        String loginUrl = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_LOGIN_URL);
        token = new UserAuthToken(StringUtils.defaultString(sysUserEntity.getId()), password, Constant.LOGIN_TYPE_GAKKEN, false, null);
        if (StringUtils.defaultString(GakkenConstant.ROLE_DIV.STUDENT.getValue()).equals(StringUtils.trim(sysUserEntity.getRoleDiv()))) {
            ShiroUtils.getSubject().getSession().setTimeout(expireTimeStu.longValue() * 60 * 1000);
        }
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "1", null);
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "1", null);
            return updateLockState(sysUserEntity.getAfterUsrId());
        } catch (LockedAccountException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "1", null);
            return R.error(MessageUtils.getMessage("MSGCOMN0002")).put("lockFlg", "1");
        } catch (AuthenticationException e) {
            CM0006.insertMstLoginData("1", "4", sysUserEntity.getAfterUsrId(), loginUrl, "1", null);
            return updateLockState(sysUserEntity.getAfterUsrId());
        }

        String csrfToken = jwtUtils.generateToken(sysUserEntity.getId());
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_CSRF_TOKEN, csrfToken);

        // ブランドコードをセッションに格納する
        ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, sysUserEntity.getBrandCd());

        if (ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_LOGIN_APP) == null) {
            CM0006.insertMstLoginData("0", null, sysUserEntity.getAfterUsrId(), loginUrl, "1", sysUserEntity.getOrgId());
        }

        if (StringUtils.equals(sysUserEntity.getRoleDiv().trim(), "4")) {
            //下記条件でログイン管理からログイン者の登録情報を取得する。
            Integer count = mstLoginService.count(
                    new QueryWrapper<MstLoginEntity>().eq("org_id", sysUserEntity.getOrgId())
                            .eq("login_id", sysUserEntity.getAfterUsrId())
                            .eq("del_flg", 0)
                            .eq("login_result_flg", "0")
                            .last("and to_char(login_time,'yyyy-mm-dd') = '" + DateUtils.format(DateUtils.getSysTimestamp(), Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'"));
            //取得したログイン回数　＝　1の場合、生徒付与ポイント処理を行う。
            // 2020/12/7 huangxinliang modify start
            CM0005.PointVo pointVo = CM0005.getPointVo(sysUserEntity.getOrgId());
            if (count == 1){
                // 2020/12/4 huangxinliang modify start
                CM0005.addPoint(sysUserEntity.getUsrId(), sysUserEntity.getOrgId(), pointVo, 1, sysUserEntity.getUsrId(),DateUtils.getSysTimestamp());
                // 2020/12/4 huangxinliang modify end
            }
            // 2020/12/7 huangxinliang modify end
            // add at 2021/08/19 for V9.02 by NWT wen START
            MstStuEntity stuEntity = mstStuService.getOne(
                    new QueryWrapper<MstStuEntity>().select("birthd").eq("stu_id", sysUserEntity.getUsrId()).eq("del_flg", 0));
            if (stuEntity == null) {
                return R.error("生徒がありません");
            }
            Date birthDay = stuEntity.getBirthd();
            // 上記取得した生徒の生年月日(MMDD)　≦　システム日時(MMDD)の場合、誕生日時ポイント付与を行う。
            if (birthDay != null) {
                String now = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                String birth = DateUtils.format(birthDay, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
                if (now.substring(5).compareTo(birth.substring(5)) >= 0) {
                    String timeStr = now.substring(0, 4) + birth.substring(4);
                    // この学生の今年の誕生日
                    Timestamp timeParam = DateUtils.toTimestamp(DateUtils.parse(timeStr, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
                    CM0005.addPoint(sysUserEntity.getUsrId(), sysUserEntity.getOrgId(), pointVo, 7, sysUserEntity.getUsrId(), timeParam);
                }
            }
            // add at 2021/08/19 for V9.02 by NWT wen END
        }
        return R.ok();
    }

    /**
     * セッションログイン後の初回ログイン判定
     *
     * @param response
     * @param sysUserEntity
     * @return
     */
    @LoginLog
    public R afterSessionLogin(HttpServletResponse response, SysUserEntity sysUserEntity) {
        String strUrl;
        //初期以外のログイン
        R result = sysLoginController.getUrl(sysUserEntity);
        if (!result.get("code").equals(-1)){
            strUrl = result.get("strUrl").toString();
        }else {
            return result;
        }
        //         子供個数が0個の場合
        if (StringUtils.equals(strUrl, "countFlg")) {
            return R.error(MessageUtils.getMessage("MSGCOMN0102"));
        }
        // 塾学習期間取得
        if ("".equals(strUrl)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾時期"));
        }
        //初回ログイン判定
        //NWT　楊　2021/07/15　MANAMIRU1-724　Edit　Start
        if (StringUtils.equals("3",sysUserEntity.getRoleDiv().trim()) && StringUtils.equals("0",sysUserEntity.getFstLoginFlg())){
        //NWT　楊　2021/07/15　MANAMIRU1-724　Edit　End
            //最初のログイン
            result = sysLoginController.firstLoginUrl();
            if (!result.get("code").equals(-1)){
                strUrl = result.get("strUrl").toString();
            }else {
                return result;
            }
        }else {
            //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　Start
            if (StringUtils.equals("0", sysUserEntity.getFstLoginFlg()) &&
                (Boolean) ShiroUtils.getSessionAttribute(GakkenConstant.ALL_TCHCD_USERS_FIRST_LOGIN_FLG)) {
            //NWT　楊　2021/07/15　MANAMIRU1-727　Edit　End
                //最初のログイン
                result = sysLoginController.firstLoginUrl();
                if (!result.get("code").equals(-1)){
                    strUrl = result.get("strUrl").toString();
                }else {
                    return result;
                }
            }
        }

        //クロスドメインリクエストトークン
        String csrfToken = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_CSRF_TOKEN);
        //ブランドコードをセッションに保存
        Cookie cookie = new Cookie("brandcd", (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD));
        cookie.setPath("/");
        //            cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        //manaFlagをセッションに保存
        Cookie manaCookie = new Cookie(GakkenConstant.MANA_FLAG, (String)ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG));
        manaCookie.setPath("/");
        //            manaCookie.setHttpOnly(true);
        manaCookie.setMaxAge(-1);
        response.addCookie(manaCookie);
        //クロスドメインリクエストトークンをセッションに保存する
        response.addHeader("token", csrfToken);
        //リダイレクトURLを返す
        return R.ok().put("url", strUrl);
    }

    /**
     * 学期のデータを確認する
     *
     * @param stuId
     * @return
     */
    @LoginLog
    public boolean hasLearnPrd(String stuId) {
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", stuId).eq("del_flg", 0));
        if (mstUsrEntity == null){
            logger.error(MessageUtils.getMessage("MSGCOMN0017","ユーザー"));
            return false;
        }
        // １個子供
        try {
            // 塾学習期間ID取得
            int learnPrdId = shiroService.getLearnPrdIdByUserId(stuId, mstUsrEntity.getOrgId());
            if ("".equals(learnPrdId + "")) {
                return false;
            } else {
                //塾学習期間IDより塾情報を取得する
                MstCrmschLearnPrdEntity mstCrmschLearnPrdEntity = mstCrmschLearnPrdService.getById(learnPrdId);
                //or 計画期間終了日 < システム日付　or 計画期間開始日 > システム日付
                if (mstCrmschLearnPrdEntity.getPlanPrdStartDy().compareTo(new Date()) > 0 || mstCrmschLearnPrdEntity.getPlanPrdEndDy().compareTo(
                        new Date()) < 0) {
                    return false;
                }
            }
            // 生徒ID
            ShiroUtils.setSessionAttribute(GakkenConstant.STU_ID, stuId);
            // 塾学習期間ID
            ShiroUtils.setSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID, learnPrdId);
            // 組織ID
            ShiroUtils.setSessionAttribute("orgId", mstUsrEntity.getOrgId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * <p>SAML 認証</p>
     *
     * @return パス
     */
    @RequestMapping("/saml2/authenticate/manamirusp")
    public String authenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestString = service.generateSAMLRequest(idpSSOLocation, regId, "transient");
        return "redirect:" + idpSSOLocation + "?SAMLRequest=" + requestString;
    }

    /**
     * SAML ログイン
     *
     * @throws Exception
     */
    @RequestMapping("/login/saml2/sso/manamirusp")
    @LoginLog
    public Object samlLogin(final HttpServletRequest request, final HttpServletResponse response)  throws Exception {
        logger.info("START Manamirusp");
        String equipment = "tablet";
        try {
            if (request.getParameter(SamlConstants.SAML_RESPONSE) == null) {
                logger.error("パラメータが空白です。");
                return "redirect:" + failureUrl;
            }
            String responseStr = (String) request.getParameter(SamlConstants.SAML_RESPONSE);
            String username = service.validateSAMLResponse(responseStr, idpCertificate);
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            if (StringUtils.isEmpty(username)) {
                logger.error("uidが空白です。");
                return "redirect:" + failureUrl;
            }
            ShiroUtils.getSession().setAttribute(SamlConstants.SAML_RESPONSE, request.getParameter(SamlConstants.SAML_RESPONSE));

            String brandCd = "onair";
            CM0003.LoginCheckResultDto loginCheckResultDto = new CM0003.LoginCheckResultDto();
            Cookie cookieEquipment = new Cookie("equipment", equipment);
            cookieEquipment.setPath("/");
            response.addCookie(cookieEquipment);
            ShiroUtils.setSessionAttribute(GakkenConstant.MANA_FLAG, "1");
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_BRAND_CD, brandCd);
            // 今回のログインした端末類型を取得する。
            switch (equipment) {
                case "otherEquip":
                    // PCの場合、端末区分＝「1：PC」
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, PC);
                    break;
                case "tablet":
                    // タブレットの場合、端末区分＝「2：タブレット」
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, TABLET);
                    break;
                case "phone":
                    // スマホの場合、端末区分＝「3：スマホ」
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, GakkenConstant.EQUIPMENT_DIV.PHONE);
                    break;
                default:
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV, PC);
            }
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            // ユーザーの登録IDとブランドコード
            List<SysUserEntity> userEntityList = shiroService.getSamlUserByLoginId(username);
            loginCheckResultDto.setUserExistsFlag(GakkenConstant.OK_FLAG);
            loginCheckResultDto.setLoginCheckFlag(GakkenConstant.OK_FLAG);
            logger.info("ユーザ" + userEntityList.size());
            if (userEntityList.size() == 0) {
                return "redirect:" + failureUrl;
            } else {
                Integer maxErrorCount = userEntityList.stream().max(Comparator.comparing(SysUserEntity::getErrorCount)).get().getErrorCount();
                ShiroUtils.setSessionAttribute(GakkenConstant.MAX_ERROR_COUNT, maxErrorCount);
                if (!StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getLoginCheckFlag())) {
                    return updateLockState(userEntityList.get(0).getAfterUsrId());
                }
                String roleDiv = userEntityList.get(0).getRoleDiv().trim();
                if (StringUtils.equals(roleDiv, "4")) {
                    // 学生ログインgkgc
                    if (StringUtils.equals(brandCd, commonService.getOrgIdLower()) || (userEntityList.size() == 1
                            && StringUtils.equals(userEntityList.get(0).getBrandCd(), commonService.getOrgIdLower()))) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0189"));
                    }
                }
                String strUrl = null;
                if (StringUtils.equals(roleDiv, "4")) {
                    if (userEntityList.size() == 1) {
                        R r = sysLoginController.loginUser(userEntityList.get(0));
                        if ((int) r.get("code") != 0) {
                            return r;
                        }
                        // 塾時期チェック
                        if (StringUtils.equals(userEntityList.get(0).getRoleDiv().trim(), "4")) {
                            if (!hasLearnPrd(userEntityList.get(0).getUsrId())) {
                                return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                            }
                        }
                        return "redirect:" + sysLoginController.afterSessionLogin(response, userEntityList.get(0)).get("url");
                    } else if (userEntityList.size() > 1) {
                        // ブランドコードをセッションに保存
                        Cookie cookie = new Cookie("brandcd", (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD));
                        cookie.setPath("/");
                        // cookie.setHttpOnly(true);
                        cookie.setMaxAge(-1);
                        response.addCookie(cookie);
                        // ログインユーザーが複数組織に存在する場合、
                        EQUIPMENT_DIV equipment_div = (EQUIPMENT_DIV) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_EQUIPMENT_DIV);
                        int equipDiv = equipment_div == null ? 0 : equipment_div.getValue();
                        if (equipDiv == PC.getValue()) {
                            strUrl = "/common/F40013.html";
                        } else if (equipDiv == TABLET.getValue()) {
                            strUrl = "/common/F40014.html";
                        } else if (equipDiv == PHONE.getValue() && (Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.MANAGER.getValue()
                                || Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.MENTOR.getValue())) {
                            strUrl = "/common/F40015.html";
                        } else if (equipDiv == PHONE.getValue() && Integer.parseInt(roleDiv) == GakkenConstant.ROLE_DIV.STUDENT.getValue()) {
                            strUrl = "/common/F40016.html";
                        }
                    }
                } else {
                    logger.error("生徒だけが登録できます。");
                    return "redirect:" + failureUrl;
                }
                return "redirect:" + strUrl;
            }
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return "redirect:" + failureUrl;
        }
    }
    public R getGuardLoginUrl(SysUserEntity sysUserEntity){
        String url = "";
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",sysUserEntity.getOrgId()).eq("del_flg",0));
        if (mstOrgEntity != null){
            //複数教室
            List<MstUsrEntity> users = new ArrayList<>();
            if (StringUtils.equals("0",sysUserEntity.getGidFlg())){
                users = mstUsrService.list(new QueryWrapper<MstUsrEntity>().select("usr_id", "org_id").eq("after_usr_id", sysUserEntity.getAfterUsrId()).eq("del_flg",0));
            }else {
                users = mstUsrService.list(new QueryWrapper<MstUsrEntity>().select("usr_id", "org_id").eq("gidpk", sysUserEntity.getGidpk()).eq("del_flg",0));
            }
            List<String> guardIds = new ArrayList<>();
            //複数教室
            for (MstUsrEntity mstUsrEntity : users) {
                guardIds.add(mstUsrEntity.getUsrId());
            }
            String guards = String.join(",", guardIds);
            List<F30002Dto> stuList = f30002Service.getStudents(guards);
            Integer stuCount = stuList.size();
            if (stuCount == 0){
                // 子供個数flg
                return R.ok().put("strUrl","countFlg");
            }else if (stuCount == 1){
                String brandCd = mstOrgEntity.getBrandCd();
                //ログイン後最初に表示されるページが「ホーム」に修正して下さい GKGC組織判断 2020/12/11 modify yang --start
                // １個子供
                url = brandCd.toUpperCase().equals(commonService.getOrgIdAdd()) ? "/guard/F30421.html" : "/guard/F30112.html";
                //ログイン後最初に表示されるページが「ホーム」に修正して下さい GKGC組織判断 2020/12/11 modify yang --start
                // 塾時期チェック
                if (!hasLearnPrd(stuList.get(0).getStuId())) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "塾学習期間"));
                }
            }else {
                url = "/guard/F30002.html";
            }
        }
        return R.ok().put("url",url);
    }
}