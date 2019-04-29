package org.spring.framework.aop.framework.adapter;

import com.gupaoedu.vip.spring.framework.aop.intercept.GPMethodInvocation;
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
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    Joinpoint joinpoint;

    MethodAndTarget mat;

    public MethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        mat = new MethodAndTarget(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinpoint = invocation;
        before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }

    private void before(Method method, Object[] args, Object target) throws Throwable{
        //传送了给织入参数
        //method.invoke(target);
        mat.invokeAdviceMethod(this.joinpoint,null,null);

    }

}
