package jp.learningpark.framework.gakkenID;

import com.alibaba.fastjson.JSON;
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
 * 2021/1/15 ： NWT)hxl ： 新規作成
 * @date 2021/1/15 11:29
 */
@Aspect
@Component
public class GakkenIdApiAspect {
    @Pointcut("execution(* jp.learningpark.framework.gakkenID.GakkenIdAPI.*(..))")
    private void pointCutMethodService() {
    }

    @Around("pointCutMethodService()")
    public Object doAroundService(ProceedingJoinPoint pjp) throws Throwable {
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
            logger.info("START method：{}，param：{},{}", pjp.getSignature().getName(), params, begin);
        } else {
            logger.info("START method：{},{}", pjp.getSignature().getName(), begin);
        }
        /* 2021/09/15 manamiru1-772 cuikl edit end */

        Object obj = pjp.proceed();
        String returnObj = JSON.toJSONString(obj);
        long end = System.currentTimeMillis();
        logger.info("END   {}():{}", pjp.getSignature().getName(), end);
        logger.info("{}()処理時間: {}ms", pjp.getSignature().getName(), (end - begin));
        /* 2021/09/15 manamiru1-772 cuikl edit start */
        logger.debug("return obj：{}", returnObj);
        /* 2021/09/15 manamiru1-772 cuikl edit end */
        return obj;

    }

    // 2021/09/22 manamiru1-772 cuikl del start
    // 2021/09/22 manamiru1-772 cuikl del end
}
