package jp.learningpark.modules.sys.shiro.token;

import jp.learningpark.framework.utils.Constant;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * ユーザ認証トークン
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   209/06/26
 */
public class UserAuthToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -2564928913725078138L;

    /**
     * ログインタイプ 0:本システム 1:LEシステム 2:SAML
     */
    private String loginType;

    /**
     * SAMLトークン
     */
    private String samlToken;

    public UserAuthToken() {
        super();
    }


    public UserAuthToken(String username, String password, String loginType, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }
//    /**
//     * LEシステムから、ログイン
//     *
//     * @param username ユーザー名
//     */
//    public UserAuthToken(String username) {
//        super(username, "", false, null);
//        this.loginType = Constant.LOGIN_TYPE_LE;
//    }

    /**
     * 本システムから、ログイン
     *
     * @param username ユーザー名
     * @param password パスワード
     */
    public UserAuthToken(String username, String password) {
        super(username, password, false, null);
        this.loginType = Constant.LOGIN_TYPE_GAKKEN;
    }

    /**
     * ログインタイプを取得する
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * ログインタイプを設定する
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    
    public String getSamlToken() {
        return samlToken;
    }


    public void setSamlToken(String samlToken) {
        this.samlToken = samlToken;
    }

}