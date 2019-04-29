package org.spring.framework.context.support;



import com.gupaoedu.vip.spring.framework.beans.GPBeanWrapper;
import com.sun.istack.internal.Nullable;
import org.spring.framework.beans.factory.config.BeanDefinition;
import org.spring.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.spring.framework.beans.factory.support.BeanDefinitionReader;
import org.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import org.spring.framework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/21 14:32
 * @description：
 * @modified By：
 * @version: $
 */
public class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;
    private String [] configLocations;
    private BeanDefinitionReader reader;




    public AbstractRefreshableApplicationContext() {

    }

    public AbstractRefreshableApplicationContext(String...configLocations) {
        super(configLocations);
    }

    @Override
    protected void setConfigLocations(String[] configLocations) {
        this.configLocations=configLocations;
    }

    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory =  new DefaultListableBeanFactory();

        try {
            loadBeanDefinitions(beanFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.beanFactory =beanFactory;
    }

    private void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception {
        //1、定位，定位配置文件
        reader = new BeanDefinitionReader(this.configLocations);
        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3、注册，把配置信息放到容器里面(伪IOC容器)
        beanFactory.doRegisterBeanDefinition(beanDefinitions);
    }



    @Override
    public final ConfigurableListableBeanFactory getBeanFactory() {


        return this.beanFactory;

    }


    //方便自己使用加的，不是spring的
    @Override
    public Properties getConfig(){
        return this.reader.getConfig();
    }


}
