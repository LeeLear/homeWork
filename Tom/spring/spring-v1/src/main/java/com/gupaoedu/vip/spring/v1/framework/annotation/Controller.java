package com.gupaoedu.vip.spring.v1.framework.annotation;

import java.lang.annotation.*;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/25 10:00
 * @description：
 * @modified By：
 * @version: $
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value()default "";
}
