package org.spring.framework.context;

import org.spring.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.spring.framework.beans.factory.support.BeanDefinitionReader;

public interface ConfigurableApplicationContext extends ApplicationContext{
    void refresh() throws Exception;
    ConfigurableListableBeanFactory getBeanFactory();

}
