package jp.learningpark.modules.sys.shiro.session;

import jp.learningpark.framework.utils.Constant;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * オンラインセッション
 * 
 * @author ruoyi
 */
public class OnlineSession extends SimpleSession {
    private static final long serialVersionUID = 1L;

    /** ユーザーID */
    private Integer userId;

    /** ユーザーCD */
    private String userCd;

    /** ユーザー名 */
    private String loginName;

    /** 部門名 */
    private String deptName;

    /** ログインIP */
    private String host;

    /** 浏览器类型 */
    private String browser;

    /** システム */
    private String os;

    /** 状態 */
    private String status = Constant.SESSION_ON_LINE;
    
    /** ログインタイプ */
    private String loginType = Constant.LOGIN_TYPE_GAKKEN;

    /** 変更フラグ */
    private transient boolean attributeChanged = false;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void markAttributeChanged() {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged() {
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged() {
        return attributeChanged;
    }

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key) {
        return super.removeAttribute(key);
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
