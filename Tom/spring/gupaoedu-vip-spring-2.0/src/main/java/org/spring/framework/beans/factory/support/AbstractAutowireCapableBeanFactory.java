package org.spring.framework.beans.factory.support;

import com.gupaoedu.vip.spring.framework.annotation.GPAutowired;
import com.gupaoedu.vip.spring.framework.annotation.GPController;
import com.gupaoedu.vip.spring.framework.annotation.GPService;
import com.gupaoedu.vip.spring.framework.aop.config.GPAopConfig;
import com.gupaoedu.vip.spring.framework.aop.support.GPAdvisedSupport;
import com.gupaoedu.vip.spring.framework.beans.GPBeanWrapper;
import com.gupaoedu.vip.spring.framework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.framework.beans.config.GPBeanPostProcessor;
import com.sun.istack.internal.Nullable;
import org.spring.framework.aop.framework.*;
import org.spring.framework.aop.target.SingletonTargetSource;
import org.spring.framework.beans.BeanWrapper;
import org.spring.framework.beans.factory.annotation.Autowired;
import org.spring.framework.beans.factory.config.BeanDefinition;
import org.spring.framework.beans.factory.config.BeanPostProcessor;
import org.spring.framework.stereotype.Controller;
import org.spring.framework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/21 13:46
 * @description：
 * @modified By：
 * @version: $
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);

    private final Map<String,Object> proxyBeanCache = new ConcurrentHashMap<>();


    @Override
    protected Object createBean(String beanName, BeanDefinition bd) throws Exception{
        Object beanInstance = doCreateBean(beanName, bd);
        return beanInstance;
    }

    protected Object doCreateBean(final String beanName,final BeanDefinition bd)throws Exception{
        Object instanceWrapper = null;

        //保证初始化的时候只有一个
        instanceWrapper = this.factoryBeanInstanceCache.get(beanName);

        /*BeanPostProcessor postProcessor = new BeanPostProcessor();
        postProcessor.postProcessBeforeInitialization(instanceWrapper,beanName);*/

        Object exposedObject=instanceWrapper;

        if (instanceWrapper == null) {
            instanceWrapper = instantiateBean(beanName,bd);
            //3、把这个对象封装到BeanWrapper中
            BeanWrapper beanWrapper = new BeanWrapper(instanceWrapper);
            //4、把BeanWrapper存到IOC容器里面
//        //1、初始化

//        //class A{ B b;}
//        //class B{ A a;}
//        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次

            //2、拿到BeanWrapper之后，把BeanWrapper保存到IOC容器中去
            this.factoryBeanInstanceCache.put(beanName,beanWrapper);

            exposedObject=instanceWrapper;

            populateBean(beanName, beanWrapper);

        }else{
            exposedObject = ((BeanWrapper) instanceWrapper).getWrappedInstance();
        }
        exposedObject = initializeBean(beanName, exposedObject, bd);
        return exposedObject;
    }



    protected Object initializeBean(final String beanName, final Object bean,BeanDefinition bd){
        Object proxyBean =  bean;
        if (!proxyBeanCache.containsKey(beanName)||!proxyBeanCache.containsKey(bd.getFactoryBeanName())) {
            AdvisedSupport advisedSupport = new AdvisedSupport(new SingletonTargetSource(bean));
            if (advisedSupport.pointCutMatch()){
                try {
                    AopProxy aopProxy = getProxy(advisedSupport);
                    proxyBean = aopProxy.getProxy(bean.getClass().getClassLoader());
                    proxyBeanCache.put(beanName,proxyBean);
                    proxyBeanCache.put(bd.getFactoryBeanName(),proxyBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            proxyBean = proxyBeanCache.get(beanName)==null?proxyBeanCache.get(bd.getFactoryBeanName()):proxyBeanCache.get(beanName);
        }
        return proxyBean;
    }

    private AopProxy getProxy(AdvisedSupport advisedSupport) throws Exception {
        if (advisedSupport.getTargetClass().getInterfaces().length>0){
            return new JdkDynamicAopProxy(advisedSupport);
        }else {
            return new CglibAopProxy(advisedSupport);
        }
    }

    private void populateBean(String beanName, BeanWrapper instanceWrapper) {
        Object instance = instanceWrapper.getWrappedInstance();
        Class<?> clazz = instanceWrapper.getWrappedClass();
        if(!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))){
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAnnotationPresent(Autowired.class)){ continue;}

            Autowired autowired = field.getAnnotation(Autowired.class);



            //强制访问
            field.setAccessible(true);
            String fieldName = field.getType().getName();
            try {
                BeanDefinition bd = this.getBeanDefinition(fieldName);
                Object bean=null;
                if(bd==null) {continue;}
                if(!this.factoryBeanInstanceCache.containsKey(bd.getBeanClassName())){
                    bean = getBean(fieldName);
                }else {
                    bean =factoryBeanInstanceCache.get(bd.getBeanClassName()).getWrappedInstance();
                }
                field.set(instance, bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    private Object instantiateBean(final String beanName,final BeanDefinition bd) {
        //1、拿到要实例化的对象的类名
        String className = bd.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
//            gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if(factoryBeanObjectCache.containsKey(className)){
                instance = this.factoryBeanObjectCache.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

               // AopProxy aopProxy = getPoxy

               // AdvisedSupport config = instantionAopConfig(bd);
               // config.setTargetClass(clazz);
               // config.setTarget(instance);

                //符合PointCut的规则的话，闯将代理对象
                //if(config.pointCutMatch()) {
                //    instance = createProxy(config).getProxy();
                //}

                this.factoryBeanObjectCache.put(className,instance);
                this.factoryBeanObjectCache.put(bd.getFactoryBeanName(),instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  instance;
    }




}
