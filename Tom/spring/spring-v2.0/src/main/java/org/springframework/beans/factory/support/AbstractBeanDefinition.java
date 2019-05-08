package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.config.ConstructorArgumentValues;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 15:36
 * @description：
 * @modified By：
 * @version: $
 */
public class AbstractBeanDefinition implements BeanDefinition {

    @Nullable
    private volatile Object beanClass;
    private boolean lazyInit = false;
    private String factoryBeanName;

    /**
     * Create a new AbstractBeanDefinition with default settings.
     */
    protected AbstractBeanDefinition() {
        this(null, null);
    }
    /**
     * Create a new AbstractBeanDefinition with the given
     * constructor argument values and property values.
     */
    protected AbstractBeanDefinition(@Nullable ConstructorArgumentValues cargs, @Nullable MutablePropertyValues pvs) {
        this.constructorArgumentValues = cargs;
        this.propertyValues = pvs;
    }

    /**
     * Return whether this bean should be lazily initialized, i.e. not
     * eagerly instantiated on startup. Only applicable to a singleton bean.
     */
    @Override
    public boolean isLazyInit() {
        return this.lazyInit;
    }

    /**
     * Set whether this bean should be lazily initialized.
     * <p>If {@code false}, the bean will get instantiated on startup by bean
     * factories that perform eager initialization of singletons.
     */
    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public boolean isAutowireCandidate() {
        return false;
    }

    @Override
    public void setFactoryMethodName(String factoryMethodName) {

    }

    /**
     * Specify the factory bean to use, if any.
     * This the name of the bean to call the specified factory method on.
     * @see #setFactoryMethodName
     */
    @Override
    public void setFactoryBeanName(@Nullable String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    /**
     * Specify the bean class name of this bean definition.
     */
    @Override
    public void setBeanClassName(@Nullable String beanClassName) {
        this.beanClass = beanClassName;
    }

    @Override
    public String getParentName() {
        return null;
    }

    /**
     * Return the factory bean name, if any.
     */
    @Override
    @Nullable
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    @Override
    public String getFactoryMethodName() {
        return null;
    }

    /**
     * Return the current bean class name of this bean definition.
     */
    @Override
    @Nullable
    public String getBeanClassName() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject instanceof Class) {
            return ((Class<?>) beanClassObject).getName();
        }
        else {
            return (String) beanClassObject;
        }
    }
    @Override
    public void setParentName(String parentName) {

    }

    @Override
    public Object getSource() {
        return null;
    }

    /**
     * Specify the class for this bean.
     */
    public void setBeanClass(@Nullable Class<?> beanClass) {
        this.beanClass = beanClass;
    }

}
