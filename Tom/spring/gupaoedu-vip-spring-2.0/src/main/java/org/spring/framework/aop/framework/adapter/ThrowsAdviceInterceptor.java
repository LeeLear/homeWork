package org.spring.framework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/29 8:59
 * @description：
 * @modified By：
 * @version: $
 */
public class ThrowsAdviceInterceptor implements MethodInterceptor {

    MethodAndTarget mat;

    public ThrowsAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        mat = new MethodAndTarget(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }catch (Throwable e){
            mat.invokeAdviceMethod(invocation,null,e.getCause());
            throw e;
        }
    }
}
