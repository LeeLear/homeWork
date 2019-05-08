package org.springframework.beans.support;
import org.springframework.beans.factory.config.GPBeanDefinition;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 14:29
 * @description：
 * @modified By：
 * @version: $
 */
public class GPDefaultListableBeanFactory {
    /** Map of bean definition objects, keyed by bean name */
    private final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
}
