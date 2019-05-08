package org.springframework.context;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.Nullable;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 15:50
 * @description：
 * @modified By：
 * @version: $
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * Load or refresh the persistent representation of the configuration,
     * which might an XML file, properties file, or relational database schema.
     * <p>As this is a startup method, it should destroy already created singletons
     * if it fails, to avoid dangling resources. In other words, after invocation
     * of that method, either all or no singletons at all should be instantiated.
     * attempts are not supported
     */
    void refresh();

    /**
     * Set the parent of this application context.
     * <p>Note that the parent shouldn't be changed: It should only be set outside
     * a constructor if it isn't available when an object of this class is created,
     * for example in case of WebApplicationContext setup.
     * @param parent the parent context
     * @see org.springframework.web.context.ConfigurableWebApplicationContext
     */
    void setParent(@Nullable ApplicationContext parent);

    /**
     * Return the internal bean factory of this application context.
     * Can be used to access specific functionality of the underlying factory.
     * <p>Note: Do not use this to post-process the bean factory; singletons
     * will already have been instantiated before. Use a BeanFactoryPostProcessor
     * to intercept the BeanFactory setup process before beans get touched.
     * <p>Generally, this internal factory will only be accessible while the context
     */
    ConfigurableListableBeanFactory getBeanFactory();
}
