package org.springframework.context;

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
}
