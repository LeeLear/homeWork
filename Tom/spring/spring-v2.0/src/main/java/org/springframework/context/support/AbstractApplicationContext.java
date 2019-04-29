package org.springframework.context.support;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 14:21
 * @description：IOC容器实现的顶层设计
 * @modified By：
 * @version: $
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext, ResourcePatternResolver {

    @Override
    public void refresh(){}

}
