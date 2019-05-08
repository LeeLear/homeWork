package org.springframework.context.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 14:21
 * @description：IOC容器实现的顶层设计
 * @modified By：
 * @version: $
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext, ResourcePatternResolver {

    /** Parent context */
    @Nullable
    private ApplicationContext parent;

    @Override
    public void refresh(){}

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    /**
     * Subclasses must return their internal bean factory here. They should implement the
     * lookup efficiently, so that it can be called repeatedly without a performance penalty.
     * <p>Note: Subclasses should check whether the context is still active before
     * returning the internal bean factory. The internal factory should generally be
     * considered unavailable once the context has been closed.
     * @return this application context's internal bean factory (never {@code null})
     * @throws IllegalStateException if the context does not hold an internal bean factory yet
     * (usually if {@link #refresh()} has never been called) or if the context has been
     * closed already

     */
    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * Set the parent of this application context.
     */
    @Override
    public void setParent(@Nullable ApplicationContext parent) {

    }

    /**
     * Return the internal bean factory of the parent context if it implements
     * ConfigurableApplicationContext; else, return the parent context itself.
     * @see org.springframework.context.ConfigurableApplicationContext#getBeanFactory
     */
    @Nullable
    protected BeanFactory getInternalParentBeanFactory() {
        return (getParent() instanceof ConfigurableApplicationContext) ?
                ((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent();
    }


    /**
     * Return the parent context, or {@code null} if there is no parent
     * (that is, this context is the root of the context hierarchy).
     */
    @Override
    @Nullable
    public ApplicationContext getParent() {
        return this.parent;
    }

}
