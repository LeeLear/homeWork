package com.gupaoedu.vip.spring.demo.aspect;


import lombok.extern.slf4j.Slf4j;
import org.spring.framework.aop.ProxyMethodInvocation;

import java.util.Arrays;

/**
 * Created by Tom.
 */
@Slf4j
public class LogAspect {

    //在调用一个方法之前，执行before方法
    public void before(ProxyMethodInvocation methodInvocation){
        methodInvocation.setUserAttribute("startTime_" + methodInvocation.getMethod().getName(),System.currentTimeMillis());
        //这个方法中的逻辑，是由我们自己写的
        log.info("Invoker Before Method!!!" +
                "\nTargetObject:" +  methodInvocation.getThis() +
                "\nArgs:" + Arrays.toString(methodInvocation.getArguments()));
    }

    //在调用一个方法之后，执行after方法
    public void after(ProxyMethodInvocation methodInvocation){
        log.info("Invoker After Method!!!" +
                "\nTargetObject:" +  methodInvocation.getThis() +
                "\nArgs:" + Arrays.toString(methodInvocation.getArguments()));
        long startTime = (Long) methodInvocation.getUserAttribute("startTime_" + methodInvocation.getMethod().getName());
        long endTime = System.currentTimeMillis();
        System.out.println("use time :" + (endTime - startTime));
    }

    public void afterThrowing(ProxyMethodInvocation methodInvocation, Throwable ex){
        log.info("出现异常" +
                "\nTargetObject:" +  methodInvocation.getThis() +
                "\nArgs:" + Arrays.toString(methodInvocation.getArguments()) +
                "\nThrows:" + ex.getMessage());
    }

}
