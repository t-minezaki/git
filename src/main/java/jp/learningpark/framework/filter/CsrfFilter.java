package jp.learningpark.framework.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSRF跨域请求伪造拦截 除登录以外的post方法，都需要携带token，如果token为空或token错误，则返回异常提示
 * 注意在filter初始化参数内配置排除的url
 *
 * @author yangwk
 */
public class CsrfFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(CsrfFilter.class);

    public List<String> excludes = new ArrayList<String>();

    /**
     * 是否开启该filter
     */
    private boolean isOpen = true;
    
    /**
     * ログインURL
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (!isOpen) {
            filterChain.doFilter(request, response);
            return;
        }

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String tarUri = uri.substring(ctx.length());

        if (!"POST".equalsIgnoreCase(req.getMethod()) || handleExcludeURL(req, resp)) {
            filterChain.doFilter(request, response);
            return;
        }
        Object token = session.getAttribute(GakkenConstant.SESSION_CSRF_TOKEN);
        String requestToken = req.getHeader("csrfToken");
        if (StringUtils.isBlank(requestToken)) {
            requestToken = req.getParameter("csrfToken");
        }

        if (StringUtils.isBlank(requestToken) || !requestToken.equals(token)) {
            logger.warn("CSRF error! request URL:" + tarUri + ", sessionToken: " + token + ", requestToken: " + requestToken);
            if (isAjaxRequest(req)) {
                String message ="セッションタイムアウトが発生しました。再ログインしてください。";
                response.setContentType("text/html;charset=UTF-8");
                // ステータスは401とする。
                resp.setStatus(401);
                resp.getWriter().write(message);
            } else {
                redirectToLogin(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        
        PatternMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : excludes) {
            if (pathMatcher.matches(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("csrf filter init");
        }

        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            JSONObject object = JSON.parseObject(temp);
            for (String key : object.keySet()) {
                if ("anon".equals(object.get(key))) {
                    excludes.add(key);
                }
            }
            
        }
        temp = filterConfig.getInitParameter("isOpen");
        if (StringUtils.isNotBlank(temp) && "true".equals(temp)) {
            isOpen = true;
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * Ajaxリクエスト判定
     *
     * @param request リクエスト
     * @return true : Ajaxリクエスト / false : そうではない
     */
    private boolean isAjaxRequest(ServletRequest request) {
        return StringUtils.equals("XMLHttpRequest",
                ((HttpServletRequest) request).getHeader("X-Requested-With"));
    }
    
    private void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, ShiroUtils.getLoginUrl(loginUrl));
    }
}