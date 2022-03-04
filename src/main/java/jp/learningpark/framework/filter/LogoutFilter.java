package jp.learningpark.framework.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.SpringContextUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * ログアウトフィルタ
 * </p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/11/01 : huangyong: 新規<br />
 * @version 1.0 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

    /** ログインURL */
    private String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * mstCodDDao
     */
    MstCodDDao mstCodDDao;
    @Autowired
    CommonService commonService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try {
                SysUserEntity user = ShiroUtils.getUserEntity();
                if (user != null) {
                    // ユーザログアウトログ
//                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
                    String manaFlag = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MANA_FLAG);
                    String brandCd = ShiroUtils.getBrandcd();
                    if (StringUtils.equals(manaFlag, "1")){
                        brandCd = getManaUrl();
                    }
                    if (!StringUtils.equals("3",user.getRoleDiv().trim())) {
                        redirectUrl = redirectUrl + "/" + brandCd;
                    }else {
                        redirectUrl = "/" + brandCd;
                    }
                } else {
                        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                        Cookie[] cookies = httpServletRequest.getCookies();
                        if (cookies != null) {
                            String brandCd = null;
                            String manaFlag = null;
                            for (Cookie cookie : cookies) {
                                if ("brandcd".equals(cookie.getName())) {
                                    brandCd = cookie.getValue();
                                    continue;
                                }
                                if (StringUtils.equals(cookie.getName(), GakkenConstant.MANA_FLAG)){
                                    manaFlag = cookie.getValue();
                                }
                            }
                            if (StringUtils.equals(manaFlag, "1")){
                                brandCd = getManaUrl();
                            }
                            brandCd = StringUtils.isEmpty(brandCd) ? commonService.getOrgIdAdd() : brandCd;
                            redirectUrl = redirectUrl + "/" + brandCd;
                        }
                    }
//                redirectUrl = ShiroUtils.getLoginUrl(redirectUrl);
                subject.logout();
            } catch (SessionException ise) {
                log.error("logout fail.", ise);
            }
            issueRedirect(request, response, redirectUrl);
        } catch (Exception e) {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }
        return false;
    }

    private String getManaUrl() {
        String brandCd;
        if (mstCodDDao == null){
            mstCodDDao = (MstCodDDao) SpringContextUtils.getBean("mstCodDDao");
        }
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "URL_ADD_KEY").eq("del_flg", 0));
        brandCd = mstCodDEntity.getCodValue();
        return brandCd;
    }

    /** ログアウトURL */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String url = getLoginUrl();
        if (!StringUtils.isEmpty(url)) {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
