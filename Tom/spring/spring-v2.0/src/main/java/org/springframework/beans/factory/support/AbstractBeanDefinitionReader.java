package org.springframework.beans.factory.support;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Set;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/15 10:18
 * @description：
 * @modified By：
 * @version: $
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    @Nullable
    private ClassLoader beanClassLoader;

    @Nullable
    private ResourceLoader resourceLoader;


    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        this.registry = registry;


        // Determine ResourceLoader to use.
        if (this.registry instanceof ResourceLoader) {
            this.resourceLoader = (ResourceLoader) this.registry;
        }
        else {
            //this.resourceLoader = new PathMatchingResourcePatternResolver();
        }


    }

    @Override
    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    @Nullable
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
    @Override
    public int loadBeanDefinitions(Resource... resources) {
        Assert.notNull(resources, "Resource array must not be null");
        int counter = 0;
        for (Resource resource : resources) {
            counter += loadBeanDefinitions(resource);
        }
        return counter;
    }

    public int loadBeanDefinitions(String location, @Nullable Set<Resource> actualResources){
        ResourceLoader resourceLoader = getResourceLoader();
//        if (resourceLoader == null) {
//            //throw new BeanDefinitionStoreException(
//                   // "Cannot import bean definitions from location [" + location + "]: no ResourceLoader available");
//        }

        if (resourceLoader instanceof ResourcePatternResolver) {
            // Resource pattern matching available.
            try {
                Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);
                int loadCount = loadBeanDefinitions(resources);
                if (actualResources != null) {
                    for (Resource resource : resources) {
                        actualResources.add(resource);
                    }
                }
//                if (logger.isDebugEnabled()) {
//                    logger.debug("Loaded " + loadCount + " bean definitions from location pattern [" + location + "]");
//                }
                return loadCount;
            }
            catch (IOException ex) {
               throw new RuntimeException(
                        "Could not resolve bean definition resource pattern [" + location + "]", ex);
            }
        }
        else {
            // Can only load single resources by absolute URL.
            Resource resource = resourceLoader.getResource(location);
            int loadCount = loadBeanDefinitions(resource);
            if (actualResources != null) {
                actualResources.add(resource);
            }
//            if (logger.isDebugEnabled()) {
//                logger.debug("Loaded " + loadCount + " bean definitions from location [" + location + "]");
//            }
            return loadCount;
        }
    }

    @Override
    public int loadBeanDefinitions(String location) {
        return loadBeanDefinitions(location, null);
    }

    @Override
    public int loadBeanDefinitions(String... locations) {
        Assert.notNull(locations, "Location array must not be null");
        int counter = 0;
        for (String location : locations) {
            counter += loadBeanDefinitions(location);
        }
        return counter;
    }

    @Override
    @Nullable
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }
}
