package org.spring.framework.aop.framework.adapter;

import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/29 8:58
 * @description：
 * @modified By：
 * @version: $
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor {
    Joinpoint joinpoint;

    MethodAndTarget mat;

    public AfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        mat = new MethodAndTarget(aspectMethod, aspectTarget);
    }
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        this.joinpoint = invocation;
        this.afterReturning(retVal,invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        mat.invokeAdviceMethod(this.joinpoint,retVal,null);
    }
}
