package org.spring.framework.web.servlet;

import com.gupaoedu.vip.spring.framework.webmvc.servlet.GPHandlerAdapter;
import com.gupaoedu.vip.spring.framework.webmvc.servlet.GPHandlerMapping;
import com.gupaoedu.vip.spring.framework.webmvc.servlet.GPViewResolver;
import lombok.extern.slf4j.Slf4j;
import org.spring.framework.context.ApplicationContext;
import org.spring.framework.context.support.AbstractRefreshableApplicationContext;
import org.spring.framework.stereotype.Controller;
import org.spring.framework.web.bind.annotation.RequestMapping;
import org.spring.framework.web.servlet.handler.AbstractHandlerMapping;
import org.spring.framework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.spring.framework.web.servlet.view.AbstractViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/22 12:10
 * @description：
 * @modified By：
 * @version: $
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION="contextConfigLocation";
    private ApplicationContext context ;

    private List<AbstractHandlerMapping> handlerMappings =new ArrayList<AbstractHandlerMapping>();;

    private Map<AbstractHandlerMapping, HttpRequestHandlerAdapter> handlerAdapters = new ConcurrentHashMap<>();

    private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Details:\r\n"+ Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = new AbstractRefreshableApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        initStrategies(context);
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        HandlerMapping handlerMapping = getHandler(request);
        if (handlerMapping==null){
            return;
        }
        //2、准备调用前的参数
        HttpRequestHandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);
        //3、真正的调用方法,返回ModelAndView存储了要传到页面上的值，和页面的模板名称
        ModelAndView mv = handlerAdapter.handle(request,response,handlerMapping);
        
        processDispatchResult(request,response,mv);
    }
    protected HandlerMapping getHandler(HttpServletRequest request) throws Exception{
        if (this.handlerMappings != null){
            String url = request.getRequestURI();
            String contextPath = request.getContextPath();
            url = url.replace(contextPath,"").replaceAll("/+","/");

            for (AbstractHandlerMapping handlerMapping : handlerMappings) {
                try{
                    Matcher matcher = handlerMapping.getPattern().matcher(url);
                    //如果没有匹配上继续下一个匹配
                    if(!matcher.matches()){
                        continue;
                    }
                    return handlerMapping;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        //把给我的ModelAndView变成一个HTML，OUTPUTSTREAM
        if (null==mv){
            return;
        }
        //如果ModelAndView不为null，怎么办？
        if (this.viewResolvers.isEmpty()){
            return;
        }
        for (ViewResolver viewResolver : this.viewResolvers) {
           View view =  viewResolver.resolveViewName(mv.getViewName(),null);
           view.render(mv.getModel(),request,response);
           return;
        }

    }

    private HttpRequestHandlerAdapter getHandlerAdapter(HandlerMapping handlerMapping) {
        if(this.handlerMappings.isEmpty()){return null;}
        HttpRequestHandlerAdapter handlerAdapter =  this.handlerAdapters.get(handlerMapping);
        if (handlerAdapter.supports(handlerMapping)){
            return handlerAdapter;
        }
        return null;
    }

    protected void initStrategies(ApplicationContext context) {
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(Controller.class)){
                    continue;
                }
                String baseUrl = "";
                //获得Controller的url配置
                if(clazz.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = requestMapping.value()[0];
                }
                //获得Method的url配置
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(RequestMapping.class)){
                        continue;
                    }
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    String regex = ("/"+baseUrl+"/"+requestMapping.value()[0].
                            replaceAll("\\*",".*")).// . 表示除换行外的任意字符
                            replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new AbstractHandlerMapping(controller,method,pattern));
                    log.info("Mapped"+regex+","+method);
                }
            }
        }catch (Exception e){

        }
    }

    private void initHandlerAdapters(ApplicationContext context) {
        //把一个request请求变成一个handler，参数都是字符串的，自动匹配到handler中的形参

        //可想而知，它要拿到HandlerMapping才能干活
        //意味着，有几个HandlerMapping，就有几个HandlerAdapter
        for (AbstractHandlerMapping handlerMapping : this.handlerMappings) {
            this.handlerAdapters.put(handlerMapping,new HttpRequestHandlerAdapter());
        }
    }

    private void initViewResolvers(ApplicationContext context) {
        //其实也就一个解析器，装个样子而已
        //拿到模板的存放目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        String[] templates = templateRootDir.list();
        for (String template : templates) {
            this.viewResolvers.add(new AbstractViewResolver(templateRoot));
        }
//        for (File template : templateRootDir.listFiles()) {
//            this.viewResolvers.add(new AbstractViewResolver(template));
//        }
    }

    private void initFlashMapManager(ApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
    }

    private void initThemeResolver(ApplicationContext context) {
    }

    private void initLocaleResolver(ApplicationContext context) {
    }

    private void initMultipartResolver(ApplicationContext context) {
    }

}
