/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.modules.sys.shiro;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import jp.learningpark.modules.sys.shiro.matcher.UserCredentialsMatcher;
import jp.learningpark.modules.sys.shiro.token.UserAuthToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 認証
 *
 * @author gong
 * @date 2018年9月14日 上午11:55:49
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    /**
     * 権限情報を取得する
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String role = shiroService.getUserRole(user.getId());
        info.addRole(role);
        Set<String> permissions = shiroService.getUserPermissions(user.getId());
        if (!permissions.isEmpty()) {
            info.setStringPermissions(permissions);
        }
        return info;
    }

    /**
     * 認証情報を取得する
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UserAuthToken token = (UserAuthToken) authcToken;
        // ユーザー情報を取得する
        SysUserEntity user = null;
        if (!StringUtils.isEmpty(token.getUsername())) {
            user = shiroService.getUserById(NumberUtils.toInt(token.getUsername()));
        }

        // ユーザー情報が存在しないの場合
        if (user == null) {
            throw new UnknownAccountException(MessageUtils.getMessage("MSGCOMN0001"));
        }

        // アカウントをロックの場合
        if (StringUtils.equals(user.getLockFlg(), "1") || user.getErrorCount() > 100) {
            throw new LockedAccountException(MessageUtils.getMessage("MSGCOMN0002"));
        }
        // ログインタイプ
        user.setLoginType(token.getLoginType());
        
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUsrPassword(), ByteSource.Util.bytes(user.getUsrId()), getName());
        return info;

    }

    /**
     * 暗号化
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        UserCredentialsMatcher shaCredentialsMatcher = new UserCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.SHA_256);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

    /**
     * shiro一時ファイルをクリアする
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
