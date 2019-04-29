package com.gupaoedu.vip.framework.beans.factory;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 14:13
 * @description： 单例工厂的顶层设计
 * @modified By：
 * @version: $
 */
public interface BeanFactory {
    /**
     * 单例的全局访问点，某种容器如果不是单例，则会不便于维护
     * @param beanName
     * @return
     */
    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>This method allows a Spring BeanFactory to be used as a replacement for the
     * Singleton or Prototype design pattern. Callers may retain references to
     * returned objects in the case of Singleton beans.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     */
    Object getBean(String name);
}
