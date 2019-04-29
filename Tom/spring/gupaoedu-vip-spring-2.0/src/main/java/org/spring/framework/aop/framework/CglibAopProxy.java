package org.spring.framework.aop.framework;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/23 9:30
 * @description：
 * @modified By：
 * @version: $
 */
public class CglibAopProxy implements AopProxy{

    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) throws Exception {

        this.advised = config;
    }


    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
