package com.gupaoedu.vip.spring.framework.beans.support;

import com.gupaoedu.vip.spring.framework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.framework.context.support.GPAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tom
 */
public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext{

    //存储注册信息的BeanDefinition,伪IOC容器
    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();
}
