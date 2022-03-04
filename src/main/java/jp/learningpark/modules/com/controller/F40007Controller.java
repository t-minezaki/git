/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import io.jsonwebtoken.Claims;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.service.F40007Service;
import jp.learningpark.modules.common.GakkenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>F40007_パスワード再設定画面 Controller</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/29 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/com/F40007")
@RestController
public class F40007Controller {

    /**
     * F40007_パスワード再設定画面 Service
     */
    @Autowired
    F40007Service f40007Service;

    //    /**
    //     * システムユーザーオンライン
    //     */
    //    @Autowired
    //    SysUserOnlineService sysUserOnlineService;

    @Autowired
    private JwtUtils jwtUtils;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * パスワード再設定画面初期画面
     *
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String sessionId, String brandCd, String token) {
        try {

            Claims tokenObject = jwtUtils.getClaimByToken(token);
            if (tokenObject == null){
                return R.error().put("url", HttpContextUtils.getHttpServletRequest().getContextPath()+"/error/F40000.html?brandCd=" + brandCd);
            }

            Boolean isExpired = jwtUtils.isTokenExpired(tokenObject.getExpiration());
            if (isExpired) {
                return R.error().put("url", HttpContextUtils.getHttpServletRequest().getContextPath()+"/error/F40000.html?brandCd=" + brandCd);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok();
    }

    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
    /**
     * パスワード再設定画面初期
     * @return
     */
    @RequestMapping(value = "/init2", method = RequestMethod.GET)
    public R init2() {
        String afterUsrId;
        try {
            afterUsrId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID_FOR_RESET_PASSWORD);
        }catch (Exception e){
            logger.error(e.getMessage());
            return R.error().put("url", HttpContextUtils.getHttpServletRequest().getContextPath()+"/common/F40006.html");
        }
        if (StringUtils.isEmpty(afterUsrId)){
            return R.error().put("url", HttpContextUtils.getHttpServletRequest().getContextPath()+"/common/F40006.html");
        }
        return R.ok();
    }
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End

    /**
     * パスワード再設定画面
     *
     * @param userInfo
     * 2021/10/11　MANAMIRU1-776 cuikl　Edit
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R getUpdate(@RequestBody Map<String,String> userInfo) {
        return f40007Service.resetPwd(userInfo);
    }

}
