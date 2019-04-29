package org.spring.framework.aop.framework.adapter;

import com.gupaoedu.vip.spring.framework.aop.aspect.GPJoinPoint;
import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInvocation;
import org.spring.framework.aop.ProxyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/29 9:15
 * @description：
 * @modified By：
 * @version: $
 */
public class MethodAndTarget {
    Method aspectMethod;
    Object aspectTarget;

    public MethodAndTarget(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Method getAspectMethod() {
        return aspectMethod;
    }

    public void setAspectMethod(Method aspectMethod) {
        this.aspectMethod = aspectMethod;
    }

    public Object getAspectTarget() {
        return aspectTarget;
    }

    public void setAspectTarget(Object aspectTarget) {
        this.aspectTarget = aspectTarget;
    }

    public Object invokeAdviceMethod(Joinpoint joinpoint, Object returnValue, Throwable tx) throws Throwable{
        Class<?> [] paramTypes = this.aspectMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object [] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i ++) {
                System.out.println(paramTypes[i]);
                if(paramTypes[i] == ProxyMethodInvocation.class){//暂时没有想到什么好办法，先写死吧
                    args[i] = joinpoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }
}
