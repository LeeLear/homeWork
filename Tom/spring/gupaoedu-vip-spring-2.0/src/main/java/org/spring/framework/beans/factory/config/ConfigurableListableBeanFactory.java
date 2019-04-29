package org.spring.framework.beans.factory.config;


import org.spring.framework.beans.factory.ListableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory {
    void preInstantiateSingletons() throws Exception;
}
