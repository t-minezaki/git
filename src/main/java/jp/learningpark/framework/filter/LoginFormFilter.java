///*
// * (C) 2018 LIGHTWORKS CORP.
// * システム名 : 学研アプリ
// * 注意事項 :
// */
//package jp.learningpark.framework.filter;
//
//import java.io.IOException;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import jp.learningpark.framework.utils.StringUtils;
//
///**
// * <p>ログインフィルタ</p >
// *
// * @author NWT : huangyong <br />
// * 変更履歴 <br />
// * 2018/11/01 : huangyong: 新規<br />
// * @version 1.0
// */
//public class LoginFormFilter extends FormAuthenticationFilter {
//    private static final Logger log = LoggerFactory.getLogger(LoginFormFilter.class);
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        if (isLoginRequest(request, response)) {
//            if (isLoginSubmission(request, response)) {
//                return executeLogin(request, response);
//            } else {
//                return true;
//            }
//        } else {
//            // ajax请求
//            if (isAjaxRequest(httpServletRequest)) {
//                String message ="セッションタイムアウトが発生しました。再ログインしてください。";
//                response.setContentType("text/html;charset=UTF-8");
//                // ステータスは401とする。
//                httpServletResponse.setStatus(401);
//                httpServletResponse.setHeader("session-status", "timeout");
//                httpServletResponse.getWriter().write(message);
//
//                return false;
//            }else {
//                saveRequestAndRedirectToLogin(request, response);
//            }
//
//            return false;
//        }
//    }
//    /**
//     * Ajaxリクエスト判定
//     *
//     * @param request リクエスト
//     * @return true : Ajaxリクエスト / false : そうではない
//     */
//    private boolean isAjaxRequest(ServletRequest request) {
//        return StringUtils.equals("XMLHttpRequest",
//                ((HttpServletRequest) request).getHeader("X-Requested-With"));
//    }
//
//    @Override
//    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        String loginUrl = getLoginUrl();
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        Cookie[] cookies = httpServletRequest.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("brandcd".equals(cookie.getName())) {
//                    loginUrl = loginUrl + "/" + cookie.getValue();
//                }
//            }
//        }
//        WebUtils.issueRedirect(request, response, loginUrl);
//    }
//}
