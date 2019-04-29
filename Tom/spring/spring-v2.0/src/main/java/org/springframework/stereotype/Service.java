package com.gupaoedu.vip.framework.stereotype;

import java.lang.annotation.*;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 15:08
 * @description：
 * @modified By：
 * @version: $
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    String value() default "";
}
