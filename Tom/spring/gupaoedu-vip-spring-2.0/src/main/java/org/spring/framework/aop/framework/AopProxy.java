package org.spring.framework.aop.framework;


/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/21 13:24
 * @description：
 * @modified By：
 * @version: $
 */
public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
