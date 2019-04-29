package org.spring.framework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/21 15:29
 * @description：
 * @modified By：
 * @version: $
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /** Cache of singleton objects created by FactoryBeans: FactoryBean name --> object */
    public final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);
}
