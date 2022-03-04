/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.controller;

import jp.learningpark.framework.annotation.SysLog;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;
import jp.learningpark.modules.sys.service.SysUserOnlineService;
import jp.learningpark.modules.sys.shiro.session.OnlineSession;
import jp.learningpark.modules.sys.shiro.session.OnlineSessionDAO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * システムユーザーオンライン
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("sys/online")
public class SysUserOnlineController {
    @Autowired
    private SysUserOnlineService sysUserOnlineService;
    
    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("sys:online:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserOnlineService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * ユーザー強制ログアウト
     */
    @PostMapping(value="/forceLogout")
    @RequiresPermissions("sys:online:forceLogout")
    @SysLog("ユーザー強制ログアウト")
    @ResponseBody
    public R forceLogout(@RequestBody List<String> sessionIds)
    {
        for (String sessionId : sessionIds)
        {
            SysUserOnlineEntity online = sysUserOnlineService.selectById(sessionId);
            if (online == null) {
                return R.error("ユーザーがログアウトしました。");
            }
            OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
            if (onlineSession == null) {
                return R.error("ユーザーがログアウトしました。");
            }
            if (sessionId.equals(ShiroUtils.getSession().getId())) {
                return R.error("現在のログインユーザーのため、ログアウトできません。");
            }
            onlineSession.setStatus(Constant.SESSION_OFF_LINE);
            onlineSessionDAO.update(onlineSession);
            online.setOnlineStatus(Constant.SESSION_OFF_LINE);
            sysUserOnlineService.saveOnline(online);
        }
        return R.ok();
    }


}
