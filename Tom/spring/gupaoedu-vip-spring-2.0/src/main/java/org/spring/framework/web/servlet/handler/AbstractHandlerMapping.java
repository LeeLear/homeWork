package org.spring.framework.web.servlet.handler;

import org.spring.framework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/22 13:00
 * @description：
 * @modified By：
 * @version: $
 */
public class AbstractHandlerMapping implements HandlerMapping {

    private Object controller;
    private Method method;
    private Pattern pattern;

    public AbstractHandlerMapping() {
    }

    public AbstractHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
