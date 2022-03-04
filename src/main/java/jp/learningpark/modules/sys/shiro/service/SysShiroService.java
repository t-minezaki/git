package jp.learningpark.modules.sys.shiro.service;

import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;
import jp.learningpark.modules.sys.service.SysUserOnlineService;
import jp.learningpark.modules.sys.shiro.session.OnlineSession;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话db操作处理
 * 
 * @author ruoyi
 */
@Component
public class SysShiroService
{
    @Autowired
    private SysUserOnlineService onlineService;

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession) {
        onlineService.deleteOnlineById(onlineSession.getId().toString());
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId)
    {
        SysUserOnlineEntity userOnline = onlineService.selectById(sessionId.toString());
        if (userOnline == null) {
            return null;
        } else {
            return createSession(userOnline);
        }
    }

    public Session createSession(SysUserOnlineEntity userOnline) {
        OnlineSession onlineSession = new OnlineSession();
        if (userOnline != null) {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpAddr());
            onlineSession.setBrowser(userOnline.getBrowserType());
            onlineSession.setOs(userOnline.getSysOs());
//            onlineSession.setDeptName(userOnline.get);
//            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getSesStartDt());
            onlineSession.setLastAccessTime(userOnline.getSesLastAccessDt());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
