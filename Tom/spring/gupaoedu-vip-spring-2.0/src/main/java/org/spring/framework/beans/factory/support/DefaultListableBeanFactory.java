package org.spring.framework.beans.factory.support;


import org.spring.framework.beans.factory.config.BeanDefinition;
import org.spring.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.spring.framework.context.support.AbstractApplicationContext;
import org.spring.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/17 15:08
 * @description：
 * @modified By：
 * @version: $
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {
    //存储注册信息的BeanDefinition,伪IOC容器
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);





    public void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
            this.beanDefinitionNames.add(beanDefinition.getFactoryBeanName());
        }
    }

    @Override
    public void preInstantiateSingletons()throws Exception{
        // Iterate over a copy to allow for init methods which in turn register new bean definitions.
        // While this may not be part of the regular factory bootstrap, it does otherwise work fine.
        List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
        for (String beanName : beanNames) {
            if(!(beanDefinitionMap.get(beanName).isLazyInit())){
                getBean(beanName);
            }
        }
    }

    public BeanDefinition getBeanDefinition(String beanName) throws Exception {
        BeanDefinition bd = this.beanDefinitionMap.get(beanName);
        if(null==bd){
            throw  new Exception("没有这个"+beanName+"的BeanDefinition");
        }
        return bd;
    }

    @Override
    public String[] getBeanDefinitionNames() {

        return StringUtils.toStringArray(this.beanDefinitionNames);

    }

}
