package org.spring.framework.context.support;

import org.spring.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.spring.framework.context.ConfigurableApplicationContext;


/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/17 15:09
 * @description：
 * @modified By：
 * @version: $
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {


    public AbstractApplicationContext(String...configLocations) {
        setConfigLocations(configLocations);
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void setConfigLocations(String[] configLocations);


    @Override
    public void refresh() throws Exception {


        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();



        //4、把不是延时加载的类，有提前初始化
        finishBeanFactoryInitialization(beanFactory);
    }

    private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        // Instantiate all remaining (non-lazy-init) singletons.
        try {
            beanFactory.preInstantiateSingletons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        return beanFactory;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory();


    @Override
    public Object getBean(String beanName) throws Exception {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return null;
    }

    protected abstract void refreshBeanFactory();

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public static void main(String[] args) {
        new AbstractRefreshableApplicationContext("application.properties");
    }

}
