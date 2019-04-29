package com.gupaoedu.vip.framework.stereotype;

import java.lang.annotation.*;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 15:10
 * @description：
 * @modified By：
 * @version: $
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}
