package org.spring.framework.beans.factory.support;


import org.spring.framework.beans.factory.config.BeanDefinition;
import org.spring.framework.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/17 15:13
 * @description：
 * @modified By：
 * @version: $
 */
public class BeanDefinitionReader {
    private List<String> registryBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    //固定配置文件中的key，相对于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    public BeanDefinitionReader(String... locations){
        //通过URL定位找到其所对应的文件，然后转换为文件流

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""))){
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        //转换为文件路径
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }
            else {
                if (!file.getName().endsWith(".class")){continue;}
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                registryBeanClasses.add(className);
            }

        }

    }

    //把配置文件中扫描到的所有的配置信息转换为GPBeanDefinition对象，以便于之后IOC操作方便
    public List<BeanDefinition> loadBeanDefinitions(){
        List<BeanDefinition> result = new ArrayList<BeanDefinition>();
        try{
            for (String registryBeanClass : registryBeanClasses) {
                Class<?> beanClass = Class.forName(registryBeanClass);
                if (beanClass.isInterface()){continue;}
                result.add(doCreateBeanDefinition(StringUtils.uncapitalize(beanClass.getSimpleName()),beanClass.getName()));
                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    result.add(doCreateBeanDefinition(anInterface.getName(),beanClass.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //把每一个配信息解析成一个BeanDefinition
    private BeanDefinition doCreateBeanDefinition(String factoryBeanName,String beanClassName){
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);//全名称
        beanDefinition.setFactoryBeanName(factoryBeanName);//simpleName
        return beanDefinition;
    }

    public Properties getConfig() {
        return config;
    }
}
