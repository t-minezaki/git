package jp.learningpark.framework.annotation;

import java.lang.annotation.*;

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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
    String value() default "";
}
