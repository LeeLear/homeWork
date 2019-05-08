/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.support;


import org.apache.commons.lang3.AnnotationUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import sun.plugin.com.TypeConverter;
import org.springframework.util.ClassUtils;

import javax.xml.ws.Provider;
import java.beans.PropertyEditor;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of the
 * {@link org.springframework.beans.factory.ListableBeanFactory} and
 * {@link BeanDefinitionRegistry} interfaces: a full-fledged bean factory
 * based on bean definition objects.
 *
 * <p>Typical usage is registering all bean definitions first (possibly read
 * from a bean definition file), before accessing beans. Bean definition lookup
 * is therefore an inexpensive operation in a local bean definition table,
 * operating on pre-built bean definition metadata objects.
 *
 * <p>Can be used as a standalone bean factory, or as a superclass for custom
 * bean factories. Note that readers for specific bean definition formats are
 * typically implemented separately rather than as bean factory subclasses:
 * see for example {@link PropertiesBeanDefinitionReader} and
 * {@link org.springframework.beans.factory.xml.XmlBeanDefinitionReader}.
 *
 * <p>For an alternative implementation of the
 * {@link org.springframework.beans.factory.ListableBeanFactory} interface,
 * have a look at {@link StaticListableBeanFactory}, which manages existing
 * bean instances rather than creating new ones based on bean definitions.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Costin Leau
 * @author Chris Beams
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @since 16 April 2001
 * @see StaticListableBeanFactory
 * @see PropertiesBeanDefinitionReader
 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 */
@SuppressWarnings("serial")
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {



	@Nullable
	private static Class<?> javaxInjectProviderClass;

	static {
		try {
			javaxInjectProviderClass =
					ClassUtils.forName("javax.inject.Provider", DefaultListableBeanFactory.class.getClassLoader());
		}
		catch (ClassNotFoundException ex) {
			// JSR-330 API not available - Provider interface simply not supported then.
			javaxInjectProviderClass = null;
		}
	}


	/** Map from serialized id to factory instance */
	private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories =
			new ConcurrentHashMap<>(8);

	/** Optional id for this factory, for serialization purposes */
	@Nullable
	private String serializationId;

	/** Whether to allow re-registration of a different definition with the same name */
	private boolean allowBeanDefinitionOverriding = true;

	/** Whether to allow eager class loading even for lazy-init beans */
	private boolean allowEagerClassLoading = true;

	/** Optional OrderComparator for dependency Lists and arrays */
	@Nullable
	private Comparator<Object> dependencyComparator;

	/** Resolver to use for checking if a bean definition is an autowire candidate */
	private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();

	/** Map from dependency type to corresponding autowired value */
	private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(16);

	/** Map of bean definition objects, keyed by bean name */
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

	/** Map of singleton and non-singleton bean names, keyed by dependency type */
	private final Map<Class<?>, String[]> allBeanNamesByType = new ConcurrentHashMap<>(64);

	/** Map of singleton-only bean names, keyed by dependency type */
	private final Map<Class<?>, String[]> singletonBeanNamesByType = new ConcurrentHashMap<>(64);

	/** List of bean definition names, in registration order */
	private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

	/** List of names of manually registered singletons, in registration order */
	private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

	/** Cached array of bean definition names in case of frozen configuration */
	@Nullable
	private volatile String[] frozenBeanDefinitionNames;

	/** Whether bean definition metadata may be cached for all beans */
	private volatile boolean configurationFrozen = false;


	/**
	 * Create a new DefaultListableBeanFactory.
	 */
	public DefaultListableBeanFactory() {
		super();
	}

	/**
	 * Create a new DefaultListableBeanFactory with the given parent.
	 * @param parentBeanFactory the parent BeanFactory
	 */
	public DefaultListableBeanFactory(@Nullable BeanFactory parentBeanFactory) {
		super(parentBeanFactory);
	}


	@Override
	public void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException {

	}

	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {

	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return null;
	}

	@Override
	public void setTempClassLoader(ClassLoader tempClassLoader) {

	}

	@Override
	public ClassLoader getTempClassLoader() {
		return null;
	}

	@Override
	public void setCacheBeanMetadata(boolean cacheBeanMetadata) {

	}

	@Override
	public boolean isCacheBeanMetadata() {
		return false;
	}

	@Override
	public void setBeanExpressionResolver(BeanExpressionResolver resolver) {

	}

	@Override
	public BeanExpressionResolver getBeanExpressionResolver() {
		return null;
	}

	@Override
	public void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass) {

	}

	@Override
	public boolean hasEmbeddedValueResolver() {
		return false;
	}

	@Override
	public String resolveEmbeddedValue(String value) {
		return null;
	}

	@Override
	public int getBeanPostProcessorCount() {
		return 0;
	}

	@Override
	public void registerScope(String scopeName, Scope scope) {

	}

	@Override
	public String[] getRegisteredScopeNames() {
		return new String[0];
	}

	@Override
	public Scope getRegisteredScope(String scopeName) {
		return null;
	}

	@Override
	public AccessControlContext getAccessControlContext() {
		return null;
	}

	@Override
	public void copyConfigurationFrom(ConfigurableBeanFactory otherFactory) {

	}

	@Override
	public BeanDefinition getMergedBeanDefinition(String beanName) {
		return null;
	}

	@Override
	public boolean isFactoryBean(String name) {
		return false;
	}

	@Override
	public void destroyBean(String beanName, Object beanInstance) {

	}

	@Override
	public void destroyScopedBean(String beanName) {

	}

	@Override
	public boolean containsBean(String name) {
		return false;
	}

	@Override
	public Object getBean(String name) {
		return null;
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

	}

	@Override
	public void removeBeanDefinition(String beanName) {

	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return null;
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return false;
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return new String[0];
	}

	@Override
	public int getBeanDefinitionCount() {
		return 0;
	}

	@Override
	public boolean isBeanNameInUse(String beanName) {
		return false;
	}
}
