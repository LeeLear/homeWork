package org.spring.framework.web.servlet.view;

import org.spring.framework.utils.StringUtils;
import org.spring.framework.web.servlet.View;
import org.spring.framework.web.servlet.ViewResolver;

import java.io.File;
import java.util.Locale;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/22 14:18
 * @description： spring中没有这个类，写个模板来混搭
 * @modified By：
 * @version: $
 */
public class AbstractViewResolver implements ViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File templateRootDir;
    private String viewName ;

    public AbstractViewResolver(File templateRootDir) {
        this.templateRootDir = templateRootDir;
    }

    public AbstractViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if(StringUtils.hasText(viewName)){
            viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)?viewName:viewName+DEFAULT_TEMPLATE_SUFFIX;
            File templateFile = new File((templateRootDir.getPath()+"/"+viewName).replaceAll("/+","/"));
            return new AbstractView(templateFile);
        }
        return null;
    }
}
