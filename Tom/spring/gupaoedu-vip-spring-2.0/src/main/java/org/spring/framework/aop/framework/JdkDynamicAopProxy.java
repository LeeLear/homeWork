package org.spring.framework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;
import org.spring.framework.aop.TargetSource;
import org.spring.framework.utils.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/23 9:29
 * @description：
 * @modified By：
 * @version: $
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport config) throws Exception {

        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        Class<?>[] proxiedInterfaces = this.advised.getTargetClass().getInterfaces();//简化spring的流程
        return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TargetSource targetSource = this.advised.targetSource;
        Object target = null;
        Object retVal;
        target = targetSource.getTarget();
        Class<?> targetClass = (target != null ? target.getClass() : null);

        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);

        MethodInvocation invocation;
        // We need to create a method invocation...
        invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
        // Proceed to the joinpoint through the interceptor chain.
        retVal = invocation.proceed();

        return retVal;
    }
}
