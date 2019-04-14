package com.gupaoedu.vip.spring.v1.framework.servlet;

import com.gupaoedu.vip.spring.v1.demo.mvc.action.DemoAction;
import com.gupaoedu.vip.spring.v1.framework.annotation.Autowired;
import com.gupaoedu.vip.spring.v1.framework.annotation.Controller;
import com.gupaoedu.vip.spring.v1.framework.annotation.RequestMapping;
import com.gupaoedu.vip.spring.v1.framework.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/25 10:08
 * @description：
 * @modified By：
 * @version: $
 */
public class DispatchServlet extends HttpServlet{
    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String ,Object> ioc = new HashMap<String ,Object>();

    //思考：为什么不用Map
    //你用Map的话，key，只能是url
    //Handler 本身的功能就是把url和method对应关系，已经具备了Map的功能
    //根据设计原则：冗余的感觉了，单一职责，最少知道原则，帮助我们更好的理解
    private List<Handler> handlerMapping = new ArrayList<Handler>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail:"+Arrays.toString(e.getStackTrace()));
        }
        //System.out.println("请调用doGet方法");
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Handler handler=getHandler(req);
        if (handler==null){
            resp.getWriter().write("404 Not Found");
            return;
        }
        //获得方法的形参列表
        Class<?> [] paramTypes = handler.getParamTypes();
        Object[] paramValues = new Object[paramTypes.length];
        Map<String,String[]> params = req.getParameterMap();//https://blog.csdn.net/u012730299/article/details/45717365
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String value = Arrays.toString(entry.getValue()).
                    replaceAll("\\[|\\]","").
                    replaceAll("\\s",",");//匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。注意 Unicode 正则表达式会匹配全角空格符。
            if (!handler.getParamIndexMapping().containsKey(entry.getKey())){continue;}//这里存在一个致命问题，只有加了@RequestaParam的才能取到值，在method#invoke中使用，否则调不到
            int index = handler.getParamIndexMapping().get(entry.getKey());
            paramValues[index]=convert(paramTypes[index],value);
        }
        if (handler.getParamIndexMapping().containsKey(HttpServletRequest.class.getName())){
            int reqIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getName());
            paramValues[reqIndex]=req;
        }
        if (handler.getParamIndexMapping().containsKey(HttpServletResponse.class.getName())){
            int reqIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getName());
            paramValues[reqIndex]=resp;
        }
        Object returnValue = handler.getMethod().invoke(handler.getController(),paramValues);
        if (returnValue==null||returnValue instanceof Void){
            return;
        }
        resp.getWriter().write(returnValue.toString());
    }

    private Object convert(Class<?> type,String value){
        //如果是int
        if(Integer.class == type){
            return Integer.valueOf(value);
        }
        else if(Double.class == type){
            return Double.valueOf(value);
        }
        //如果还有double或者其他类型，继续加if
        //这时候，我们应该想到策略模式了
        //在这里暂时不实现，希望小伙伴自己来实现
        return value;
    }

    private Handler getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        for (Handler handler : this.handlerMapping) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()){
                continue;
            }
            return handler;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //定位,通过扫描资源文件，得到需要定位的包位置
        doLoadConfig(config);
        //加载，把扫描得到的包下的类文件，记录下来
        doScanner(contextConfig.getProperty("scanPackage"));
        //注册,把记录下来的类文件，带有注解的，全部初始化并且保存下来
        doRegister();
        //注入
        doAutowired();
        /*DemoAction action = (DemoAction) ioc.get("demoAction");
        action.query(null,null,"lee");*/
        //如果是SpringMVC会多设计一个HandleMapping

        //将@RequestMapping中配置的url和一个Method关联上
        //以便于从浏览器获得用户输入的url以后，能够找到具体执行的Method通过反射去调用
        //这个方法其实可以在初始化ioc的时候就可以做，只不过为了保证单一职责，和扩展，分开写
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()){
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(Controller.class)){
                continue;
            }
            String baseUrl="";
            if (clazz.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }

            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(RequestMapping.class)){continue;}
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String regex = ("/"+baseUrl+"/"+requestMapping.value()).
                        replaceAll("/+","/");
                Pattern pattern =Pattern.compile(regex);
                this.handlerMapping.add(new Handler(pattern,entry.getValue(),method));

            }
        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()){return;}
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)){continue;}
                Autowired autowired = field.getAnnotation(Autowired.class);
                String beanName = autowired.value();
                if ("".equals(beanName.trim())){
                    beanName=field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void doRegister() {
        if (classNames.isEmpty()){return;}

        try {
            for (String className : classNames) {
                Class clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)){
                    String beanName=lowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)){
                    Service service = (Service) clazz.getAnnotation(Service.class);
                    String beanName = service.value();
                    if ("".equals(beanName.trim())){
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        ioc.put(anInterface.getName(),instance);
                    }
                }else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().
                getResource("/"+packageName.replaceAll("\\.","/"));
        File classDir = null;
        try {
            classDir=new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()){
                doScanner(packageName+"."+file.getName());
            }else {
                classNames.add(packageName+"."+file.getName().replace(".class",""));
            }
        }
    }

    private void doLoadConfig(ServletConfig config) {
        String location = config.getInitParameter("contextConfigLocation");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath:",""));
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }
}
