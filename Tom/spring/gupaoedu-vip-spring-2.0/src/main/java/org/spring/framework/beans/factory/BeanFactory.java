package org.spring.framework.beans.factory;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/17 15:03
 * @description：
 * @modified By：
 * @version: $
 */
public interface BeanFactory {
    /**
     * 根据beanName从IOC容器中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> beanClass) throws Exception;
}
