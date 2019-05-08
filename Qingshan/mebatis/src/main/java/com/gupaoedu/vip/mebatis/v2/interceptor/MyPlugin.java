package com.gupaoedu.vip.mebatis.v2.interceptor;


import com.gupaoedu.vip.mebatis.v2.annotation.Intercepts;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;
import com.gupaoedu.vip.mebatis.v2.plugin.Interceptor;
import com.gupaoedu.vip.mebatis.v2.plugin.Invocation;
import com.gupaoedu.vip.mebatis.v2.plugin.Plugin;


import java.util.Arrays;
import java.util.List;

/**
 * 自定义插件
 */
@Intercepts("query")
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        List parameter = (List) invocation.getArgs()[1];
        Class pojo = (Class) invocation.getArgs()[2];
        System.out.println("插件输出：SQL：["+statement+"]");
        System.out.println("插件输出：Parameters："+parameter.toString());

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
