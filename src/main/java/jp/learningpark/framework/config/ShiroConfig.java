package jp.learningpark.framework.config;

import jp.learningpark.framework.filter.LogoutFilter;
import jp.learningpark.modules.sys.shiro.UserRealm;
import jp.learningpark.modules.sys.shiro.filter.OnlineSessionFilter;
import jp.learningpark.modules.sys.shiro.filter.SyncOnlineSessionFilter;
import jp.learningpark.modules.sys.shiro.session.OnlineSessionDAO;
import jp.learningpark.modules.sys.shiro.session.OnlineSessionFactory;
import jp.learningpark.modules.sys.shiro.web.OnlineWebSessionManager;
import jp.learningpark.modules.sys.shiro.web.SpringSessionValidationScheduler;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro設定
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2017-09-27
 */
@Configuration
public class ShiroConfig {
    /**
     * ログインURL
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    /**
     * 認証失敗URL
     */
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * セッションタイムアウト時間(ミリ秒)
     */
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    /**
     * セッション検証間隔時間(ミリ秒)
     */
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    /**
     * クッキードメイン
     */
    @Value("${shiro.cookie.domain}")
    private String domain;

    /**
     * クッキーパス
     */
    @Value("${shiro.cookie.path}")
    private String path;

    /**
     * クッキー期限
     */
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("ruoyi");
        EhCacheManager em = new EhCacheManager();
        if (cacheManager == null) {
            em.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
            return em;
        } else {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

    /**
     * 返回配置文件流 避免ehcache配置文件一直被占用，无法完全销毁项目重新部署
     */
    protected InputStream getCacheManagerConfigFileInputStream() {
        String configFile = "classpath:ehcache-shiro.xml";
        InputStream inputStream = null;
        try {
            inputStream = ResourceUtils.getInputStreamForPath(configFile);
            byte[] b = IOUtils.toByteArray(inputStream);
            InputStream in = new ByteArrayInputStream(b);
            return in;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ConfigurationException("Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 自定义Realm
     */
    @Bean
    public UserRealm userRealm(EhCacheManager cacheManager) {
        UserRealm userRealm = new UserRealm();
//        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }

    /**
     * 自定义sessionDAO会话
     */
    @Bean
    public OnlineSessionDAO sessionDAO() {
        OnlineSessionDAO sessionDAO = new OnlineSessionDAO();
        return sessionDAO;
    }

    /**
     * 自定义sessionFactory会话
     */
    @Bean
    public OnlineSessionFactory sessionFactory() {
        OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
        return sessionFactory;
    }

    /**
     * 自定义sessionFactory调度器
     */
    @Bean
    public SpringSessionValidationScheduler sessionValidationScheduler() {
        SpringSessionValidationScheduler sessionValidationScheduler = new SpringSessionValidationScheduler();
        // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
        sessionValidationScheduler.setSessionValidationInterval(validationInterval * 60 * 1000);
        // 设置会话验证调度器进行会话验证时的会话管理器
        sessionValidationScheduler.setSessionManager(sessionValidationManager());
        return sessionValidationScheduler;
    }

    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionValidationManager() {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // 加入缓存管理器
//        manager.setCacheManager(getEhCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionManager() {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // 加入缓存管理器
//        manager.setCacheManager(getEhCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // 定义要使用的无效的Session定时调度器
        manager.setSessionValidationScheduler(sessionValidationScheduler());
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        manager.setSessionFactory(sessionFactory());

        Cookie cookie = new SimpleCookie("gakkenCk");
//        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setHttpOnly(true); //more secure, protects against XSS attacks
        manager.setSessionIdCookie(cookie);
        return manager;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm);
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        // 注入缓存管理器;
//        securityManager.setCacheManager(getEhCacheManager());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 退出过滤器
     */
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }

    /**
     * 自定义在线用户处理过滤器
     */
    @Bean
    public OnlineSessionFilter onlineSessionFilter() {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(loginUrl);
        return onlineSessionFilter;
    }

    /**
     * 自定义在线用户同步过滤器
     */
    @Bean
    public SyncOnlineSessionFilter syncOnlineSessionFilter() {
        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
        return syncOnlineSessionFilter;
    }

//    /**
//     * セッションタイムアウト
//     */
//    @Bean
//    public SessionTimeoutFilter sessionTimeoutFilter() {
//        SessionTimeoutFilter sessionTimeoutFilter = new SessionTimeoutFilter();
//        return sessionTimeoutFilter;
//    }

    /**
     * cookie 属性设置
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

//    /**
//     * brandCookie 属性设置
//     */
//    public SimpleCookie brandCookie() {
//        SimpleCookie cookie = new SimpleCookie("brandCookie");
//        cookie.setDomain(domain);
//        cookie.setPath(path);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(maxAge * 24 * 60 * 60);
//        return cookie;
//    }

    /**
     * 记住我
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // CODE
        // KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // SecretKey deskey = keygen.generateKey();
        // System.out.println(Base64.encodeToString(deskey.getEncoded()));
        cookieRememberMeManager.setCipherKey(Base64.decode("B4JDPlsaqNHazKUrQhpTgg=="));
        return cookieRememberMeManager;
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro过滤器配置
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
//        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
//        filterChainDefinitionMap.put("/favicon.ico**", "anon");
//        filterChainDefinitionMap.put("/ruoyi.png**", "anon");
//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/docs/**", "anon");
//        filterChainDefinitionMap.put("/fonts/**", "anon");
//        filterChainDefinitionMap.put("/img/**", "anon");
//        filterChainDefinitionMap.put("/ajax/**", "anon");
//        filterChainDefinitionMap.put("/js/**", "anon");
//        filterChainDefinitionMap.put("/ruoyi/**", "anon");
//        filterChainDefinitionMap.put("/druid/**", "anon");
//        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
//        // 退出 logout地址，shiro去清除session
//        filterChainDefinitionMap.put("/logout", "logout");
//        // 不需要拦截的访问
//        filterChainDefinitionMap.put("/login", "anon,captchaValidate");
//        // 系统权限列表
//        filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

        Map<String, Filter> filters = new LinkedHashMap<>();
//        filters.put("sessionTimeout", sessionTimeoutFilter());
        filters.put("onlineSession", onlineSessionFilter());
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
//        filters.put("captchaValidate", captchaValidateFilter());
        // 注销成功，则跳转到指定页面
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 所有请求需要认证
//        filterChainDefinitionMap.put("/**", "sessionTimeout");
        Map<String, String>  filterChainDefinitionMap = getFilterMap();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
    /**
     * フィルタ設定を取得する
     */
    public Map<String, String> getFilterMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "anon");
        filterMap.put("/login/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/chooseOrg", "anon");
        filterMap.put("/signLogin", "anon");
        filterMap.put("/pLogin", "anon");
        // SMAL2 の対応 START
        filterMap.put("/saml2/**", "anon");
        // SMAL2 の対応
        filterMap.put("/msgLogin", "anon");
        filterMap.put("/PUSHAPI", "anon");
        filterMap.put("/manager/BTGKA1004/importCsvFile", "anon");
        filterMap.put("/manager/BTGKA1010/monthExit", "anon");
        filterMap.put("/manager/BTGKA1009/dayRegistbulk", "anon");
        filterMap.put("/manager/BTGKA1005/prepare", "anon");
        filterMap.put("/error/F40000.html", "anon");
        filterMap.put("/common/NoticeApiTest.html", "anon");
        filterMap.put("/common/F40006.html", "anon");
        filterMap.put("/common/F40009.html", "anon");
        filterMap.put("/common/F40007.html", "anon");
        filterMap.put("/common/F40011/*", "anon");
        filterMap.put("/common/F40011-1.html", "anon");
        filterMap.put("/common/F40013.html", "anon");
        filterMap.put("/common/F40013/**", "anon");
        filterMap.put("/common/F40014.html", "anon");
        filterMap.put("/common/F40014/init", "anon");
        filterMap.put("/common/F40015.html", "anon");
        filterMap.put("/common/F40015/init", "anon");
        filterMap.put("/common/F40016.html",  "anon");
        filterMap.put("/common/F40016/*", "anon");
        filterMap.put("/QRATAPI", "anon");
        filterMap.put("/QRAPI", "anon");
        filterMap.put("/SamlDataSync", "anon");
        filterMap.put("/common/OnLineApiTest.html",  "anon");
        filterMap.put("/download/**", "anon");
        filterMap.put("/common/QRResponse.html", "anon");
        filterMap.put("/com/F40006/reset", "anon");
        filterMap.put("/com/F40007/init", "anon");
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
        filterMap.put("/com/F40007/init2", "anon");
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
        filterMap.put("/com/F40007/update", "anon");
        filterMap.put("/com/F40009/doPostEmail", "anon");
        filterMap.put("/pop/F21074/search", "anon");

        filterMap.put("/service-worker.js", "anon");
        filterMap.put("/manifest.json", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/img/**", "anon");

        //サーバから画像リソースを取得します。
        filterMap.put("/server-image/**", "anon");

        filterMap.put("/font/**", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/logout", "logout");
//        filterMap.put("/**", "sessionTimeout");
        filterMap.put("/manager/**/getPage", "anon");
        filterMap.put("/**", "user,onlineSession,syncOnlineSession");
        return filterMap;
    }
    
}
