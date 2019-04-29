package org.spring.framework.aop.framework;

import com.gupaoedu.vip.spring.demo.service.IModifyService;
import com.gupaoedu.vip.spring.demo.service.impl.ModifyService;
import com.gupaoedu.vip.spring.framework.aop.aspect.GPAfterReturningAdviceInterceptor;
import com.gupaoedu.vip.spring.framework.aop.aspect.GPAfterThrowingAdviceInterceptor;
import com.gupaoedu.vip.spring.framework.aop.aspect.GPMethodBeforeAdviceInterceptor;
import com.sun.istack.internal.Nullable;
import org.spring.framework.aop.TargetSource;
import org.spring.framework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.spring.framework.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/23 9:35
 * @description：
 * @modified By：
 * @version: $
 */
public class AdvisedSupport implements Advised{

    TargetSource targetSource ;//EMPTY_TARGET_SOURCE;

    /** Cache with Method as key and advisor chain List as value */
    //private transient Map<MethodCacheKey, List<Object>> methodCache;
    private transient Map<Method, List<Object>> methodCache;//简化代码。。。用Method作key

    private Pattern pointCutClassPattern;//自己弄的一个，方便写

    public AdvisedSupport() {
    }

    public AdvisedSupport(TargetSource targetSource) {
        this.targetSource = targetSource;
        parse();
    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetSource.getTargetClass();
    }

    @Override
    public TargetSource getTargetSource() {
        return this.targetSource;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {

        List<Object> cached = this.methodCache.get(method);
        if (cached == null) {
            Method m = targetSource.getTargetClass().getMethod(method.getName(),method.getParameterTypes());

            cached = methodCache.get(m);
            //获取所有的拦截器   这里又有点复杂了，需要扫描配置文件，定位加载和注册来得到对应的后继拦截器
            //cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);
            //存入缓存
            this.methodCache.put(method, cached);
        }
        return cached;
    }


    private void parse() {
        String pointCut = ProxyConfig.instance.getPointCut()
                .replaceAll("\\.","\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\(")
                .replaceAll("\\)","\\)");
        //pointCut=public .* com.gupaoedu.vip.spring.demo.service..*Service..*(.*)
        //玩正则
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4);//方法是需要点的，类.方法名
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));

        try {

            methodCache = new HashMap<Method, List<Object>>();
            Pattern pattern = Pattern.compile(pointCut);



            Class aspectClass = Class.forName(ProxyConfig.instance.getAspectClass());
            Map<String,Method> aspectMethods = new HashMap<String,Method>();
            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(),m);
            }

            for (Method m : this.targetSource.getTargetClass().getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);
                if(matcher.matches()){
                    //执行器链
                    List<Object> advices = new LinkedList<Object>();
                    //把每一个方法包装成 MethodIterceptor
                    //before
                    if(StringUtils.hasText(ProxyConfig.instance.getAspectBefore())) {
                        //创建一个Advise
                        advices.add(new MethodBeforeAdviceInterceptor(aspectMethods.get(ProxyConfig.instance.getAspectBefore()),aspectClass.newInstance()));
                    }
                    //after
                    if(StringUtils.hasText(ProxyConfig.instance.getAspectAfter())) {
                        //创建一个Advise
                        advices.add(new GPAfterReturningAdviceInterceptor(aspectMethods.get(ProxyConfig.instance.getAspectAfter()),aspectClass.newInstance()));
                    }
                    //afterThrowing
                    if(StringUtils.hasText(ProxyConfig.instance.getAspectAfterThrow())) {
                        //创建一个Advise
                        GPAfterThrowingAdviceInterceptor throwingAdvice =
                                new GPAfterThrowingAdviceInterceptor(
                                        aspectMethods.get(ProxyConfig.instance.getAspectAfterThrow()),
                                        aspectClass.newInstance());
                        throwingAdvice.setThrowName(ProxyConfig.instance.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    methodCache.put(m,advices);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetSource.getTargetClass().toString()).matches();
    }

    public static void main(String[] args) {

        String str="class com\\.gupaoedu\\.vip\\.spring\\.demo\\.service\\.impl\\..*Service";
        System.out.println(str);
        Pattern pattern =Pattern.compile(str);
        Matcher matcher = pattern.matcher("class com.gupaoedu.vip.spring.demo.service.impl.ModifyService");
        System.out.println(matcher.matches());
        String pointCut = ProxyConfig.instance.getPointCut()
                .replaceAll("\\.","\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\(")
                .replaceAll("\\)","\\)");
        System.out.println(pointCut);
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4);
        System.out.println(pointCutForClassRegex);
        String patternBefore = "class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1);
        System.out.println(patternBefore);
        System.out.println(str==patternBefore);
        Pattern pointCutClassPattern = Pattern.compile(patternBefore);
        System.out.println(pointCutClassPattern);
        boolean isMatch = false;
        try {
            Class clazz = Class.forName(ModifyService.class.getName());
            String clazzStr =clazz.toString();
            System.out.println(clazzStr);
            isMatch = pointCutClassPattern.matcher(clazzStr).matches();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean isMatch2 = pointCutClassPattern.matcher(ModifyService.class.toString()).matches();
        System.out.println(isMatch);
        System.out.println(isMatch2);
    }

}
