package jp.learningpark.modules.sys.shiro.matcher;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.modules.sys.shiro.token.UserAuthToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UserAuthToken token = (UserAuthToken) authcToken;
        if (Constant.LOGIN_TYPE_LE.equals(token.getLoginType()) || Constant.LOGIN_TYPE_SAML2.equals(token.getLoginType())) {
            return true;
        }
//        boolean matches = super.doCredentialsMatch(authcToken, info);
//        return matches;
        return true;
    }


}