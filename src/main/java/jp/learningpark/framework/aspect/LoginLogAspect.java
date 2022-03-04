package jp.learningpark.framework.aspect;

import jp.learningpark.modules.sys.controller.SysLoginController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/10/26 ： NWT)hxl ： 新規作成
 * @date 2020/10/26 16:40
 */
@Aspect
@Component
public class LoginLogAspect {

    Logger logger = LoggerFactory.getLogger(SysLoginController.class);

    @Pointcut("@annotation(jp.learningpark.framework.annotation.LoginLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        logger.info("Login :START method：{}", point.getSignature().getName());
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        logger.info("Login :END   method：{}，exec time : {}ms", point.getSignature().getName(), (time));
        return result;
    }
}
