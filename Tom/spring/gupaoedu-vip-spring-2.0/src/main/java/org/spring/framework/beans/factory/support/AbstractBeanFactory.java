package org.spring.framework.beans.factory.support;

import com.sun.istack.internal.Nullable;
import org.spring.framework.beans.factory.BeanFactory;
import org.spring.framework.beans.factory.config.BeanDefinition;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/21 15:09
 * @description：
 * @modified By：
 * @version: $
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements BeanFactory {
    @Override
    public Object getBean(String name)throws Exception{
        return doGetBean(name);
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return null;
    }

    protected <T> T doGetBean(final String name) throws Exception {
        BeanDefinition bd = getBeanDefinition(name);
        return (T) createBean(name,bd);
    }

    protected abstract BeanDefinition getBeanDefinition(String name) throws Exception;

    protected abstract Object createBean(String beanName, BeanDefinition bd) throws Exception;


}
