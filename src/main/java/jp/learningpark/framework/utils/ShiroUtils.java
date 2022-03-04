/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.shiro.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * シラティルズ
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;

    /**
     * 加密算法
     */
    public final static String SHA_256 = "SHA-256";
    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(SHA_256, password, salt, HASH_ITERATIONS).toString();
    }

    private static final char[][] MARX = new char[][]{
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'},
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}};
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SysUserEntity getUserEntity() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * ユーザIDを取得する
     */
    public static String getUserId() {
        try {
            return getUserEntity().getUsrId();
        }catch (NullPointerException e){
            return "";
        }
    }

    /**
     * 塾IDを取得する
     */
    public static String getCrmschId() {
        return getUserEntity().getOrgId();
    }

    /**
     * 塾学習期間IDを取得する
     */
    public static Integer getCrmLearnPrdId() {
        Object prdId = getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);
        return NumberUtils.toInt(StringUtils.defaultString(prdId));
    }

    /**
     * ブランドコードを取得する
     */
    public static String getBrandcd() {
        Object brandcd = getSessionAttribute(GakkenConstant.SESSION_BRAND_CD);
        return StringUtils.defaultString(brandcd);
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new RRException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

    public static boolean getFirstFlag(String userId) {

        return false;
    }

    /**
     * ユーザーIDに基づいて初期パスワードを生成する
     * @param value     ユーザーID
     * @return
     */
    public static String stringToAscii(String value) {
//        StringBuffer sbu = new StringBuffer();
//        char[] chars = value.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            if (i != chars.length - 1) {
//                sbu.append((int) chars[i]);
//            } else {
//                sbu.append((int) chars[i]);
//            }
//        }
//        return encode(Double.valueOf(sbu.toString()), 8);
        Random random = new Random(1);
        String password = ShiroUtils.sha256(value, value);
        while (password.length() > 8){
            if (password.length() > 16){
                password = password.substring(password.length() / 2);
            }else {
                password = password.substring(0, 8);
            }
        }
        StringBuilder stringBuffer = new StringBuilder();
        char[] chars = password.toCharArray();
        for (int i = 0; i < (int)chars[0]; i++){
            random.nextInt();
        }
        for (char aChar : chars) {
            switch ((int)aChar % 3){
                case 0:
                    stringBuffer.append(MARX[(int)aChar % 3][random.nextInt(10)]);
                    break;
                case 1:
                case 2:
                    stringBuffer.append(MARX[(int)aChar % 3][random.nextInt(26)]);
                    break;
                default:
                    stringBuffer.append(0);
            }
        }
        if (!StringUtils.PWDCheck(stringBuffer.toString())){
            stringBuffer.append("0aA");
            password = stringBuffer.substring(3);
        }else {
            password = stringBuffer.toString();
        }
        return password;
    }

    public static String encode(Double num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale - 1) {
            remainder = Double.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }
        sb.append(chars.charAt(Double.valueOf(num).intValue()));
        String value = sb.reverse().toString();

        if (value.length() < 8) {
            value = StringUtils.rightPad(value, length, "0");
        } else {
            value = StringUtils.substring(value, 0, length);
        }
        return value;
    }

//    /**
//     * Token
//     * @return
//     */
//    public static String getToken(){
//        String token= UUID.randomUUID().toString().replace("-","");
//        token=ShiroUtils.sha256(token,"12345!@#$%");
//        return token;
//    }
//
//    /**
//     * Token
//     * @return
//     */
//    public static void setCookieToken(HttpServletResponse resp) {
//        String token = UUID.randomUUID().toString().replace("-", "");
//        token = ShiroUtils.sha256(token, "12345!@#$%");
//
//        ShiroUtils.setSessionAttribute("token", token);
//        Cookie tokenCookie = new Cookie("token", token);
//        tokenCookie.setPath("/");
//        tokenCookie.setMaxAge(-1);
//        resp.addCookie(tokenCookie);
//    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    /**
     * ログインURLを取得する
     * @param ログインURL
     * @return ログインURL()
     */
    public static String getLoginUrl(String loginUrl) {
        if (StringUtils.isEmpty(loginUrl)) {
            return loginUrl;
        }
        if (getSession() != null) {
            loginUrl = loginUrl + "/" + getBrandcd();
        } else {
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            if (request != null) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("brandcd".equals(cookie.getName())) {
                            loginUrl = loginUrl + "/" + cookie.getValue();
                            break;
                        }
                    }
                }
            }
        }

        return loginUrl;
    }
}
