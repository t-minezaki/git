package jp.learningpark.modules.sys.manager.factory;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.SpringContextUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.sys.entity.SysAsyncTaskEntity;
import jp.learningpark.modules.sys.entity.SysLogEntity;
import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;
import jp.learningpark.modules.sys.service.SysAsyncTaskService;
import jp.learningpark.modules.sys.service.SysLogService;
import jp.learningpark.modules.sys.service.SysUserOnlineService;
import jp.learningpark.modules.sys.shiro.session.OnlineSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author liuhulu
 *
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger(AsyncFactory.class);

    /**
     * 同步session到数据库
     * 
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    sys_user_logger.info("START method：syncSessionToDb");

                    SysUserOnlineEntity online = new SysUserOnlineEntity();
                    online.setSessionId(StringUtils.defaultString(session.getId()));
                    online.setUserId(StringUtils.defaultString(session.getUserId()));
                    online.setUserCd(session.getUserCd());
                    online.setIpAddr(session.getHost());
//                  online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                    online.setBrowserType(session.getBrowser());
                    online.setSysOs(session.getOs());
                    online.setOnlineStatus(session.getStatus());
                    online.setSesStartDt(DateUtils.toTimestamp(session.getStartTimestamp()));
                    online.setSesLastAccessDt(DateUtils.toTimestamp(session.getLastAccessTime()));
                    online.setExpireTime(session.getTimeout());
                    online.setLoginType(session.getLoginType());
                    online.setCretDatime(DateUtils.getSysTimestamp());
                    online.setUpdateTime(DateUtils.getSysTimestamp());
                    // online.setDeptName(session.getDeptName());
                    // online.setLoginName(session.getLoginName());

                    SpringContextUtils.getBean(SysUserOnlineService.class).saveOnline(online);

                } catch (Exception e) {
                    sys_user_logger.error(e.getMessage());
                } finally {
                    sys_user_logger.info("END   method：syncSessionToDb");
                }
                
            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysLogEntity operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                //                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringContextUtils.getBean("sysLogService", SysLogService.class).save(operLog);
            }
        };
    }

    /**
     * 非同期タスクマスタ登録処理
     * 
     * @param asyncTask 非同期タスクマスタデータ
     * @return task
     */
    public static TimerTask saveStatements(final SysAsyncTaskEntity asyncTask) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    sys_user_logger.info("START method：saveStatements");
                    SpringContextUtils.getBean("sysAsyncTaskService", SysAsyncTaskService.class).saveStatements(asyncTask);
                } catch (Exception e) {
                    sys_user_logger.error(e.getMessage());
                } finally {
                    sys_user_logger.info("END   method：saveStatements");
                }
            }
        };
    }

    //    /**
    //     * 记录登陆信息
    //     * 
    //     * @param username 用户名
    //     * @param status 状态
    //     * @param message 消息
    //     * @param args 列表
    //     * @return 任务task
    //     */
    //    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    //    {
    //        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
    //        final String ip = ShiroUtils.getIp();
    //        return new TimerTask()
    //        {
    //            @Override
    //            public void run()
    //            {
    //                StringBuilder s = new StringBuilder();
    //                s.append(LogUtils.getBlock(ip));
    //                s.append(AddressUtils.getRealAddressByIP(ip));
    //                s.append(LogUtils.getBlock(username));
    //                s.append(LogUtils.getBlock(status));
    //                s.append(LogUtils.getBlock(message));
    //                // 打印信息到日志
    //                sys_user_logger.info(s.toString(), args);
    //                // 获取客户端操作系统
    //                String os = userAgent.getOperatingSystem().getName();
    //                // 获取客户端浏览器
    //                String browser = userAgent.getBrowser().getName();
    //                // 封装对象
    //                Logininfor logininfor = new Logininfor();
    //                logininfor.setLoginName(username);
    //                logininfor.setIpaddr(ip);
    //                logininfor.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
    //                logininfor.setBrowser(browser);
    //                logininfor.setOs(os);
    //                logininfor.setMsg(message);
    //                // 日志状态
    //                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status))
    //                {
    //                    logininfor.setStatus(Constants.SUCCESS);
    //                }
    //                else if (Constants.LOGIN_FAIL.equals(status))
    //                {
    //                    logininfor.setStatus(Constants.FAIL);
    //                }
    //                // 插入数据
    //                SpringUtils.getBean(LogininforServiceImpl.class).insertLogininfor(logininfor);
    //            }
    //        };
    //    }
}
