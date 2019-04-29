package org.spring.framework.beans.factory.config;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/17 16:07
 * @description：
 * @modified By：
 * @version: $
 */
public class BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
