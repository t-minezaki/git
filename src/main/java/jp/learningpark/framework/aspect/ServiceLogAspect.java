package jp.learningpark.framework.aspect;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class ServiceLogAspect {

    @Pointcut("execution(* jp.learningpark.*.*.service.*.*ServiceImpl.*(..))")
    private void pointCutMethodService() {
    }

    @Around("pointCutMethodService()")
    public Object doAroundService(ProceedingJoinPoint pjp) throws Throwable {
        if (StringUtils.equals("QRResponseApiServiceImpl.download(..)", pjp.getSignature().toShortString())){
            return pjp.proceed();
        }
        Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
        long begin = System.currentTimeMillis();
        /* 2021/09/15 manamiru1-772 cuikl edit start */
        if (logger.isDebugEnabled()) {
            // 2021/09/22 manamiru1-772 cuikl edit start
            String params = "";
            try {
                params = JSON.toJSONString(pjp.getArgs());
            } catch(Exception e) {
            }
            // 2021/09/22 manamiru1-772 cuikl edit end
            logger.info("START method：{}，param：{}", pjp.getSignature().getName(), params);
        } else {
            logger.info("START method：{}", pjp.getSignature().getName());
        }
        /* 2021/09/15 manamiru1-772 cuikl edit end */

        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("END   method：{}，exec time : {}ms", pjp.getSignature().getName(), (end - begin));
        return obj;

    }

    // 2021/09/22 manamiru1-772 cuikl del start
    // 2021/09/22 manamiru1-772 cuikl del end
}