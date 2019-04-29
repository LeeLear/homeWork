package org.spring.framework.context;

import org.spring.framework.beans.factory.BeanFactory;
import org.spring.framework.beans.factory.ListableBeanFactory;

import java.util.Properties;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/17 15:06
 * @description：
 * @modified By：
 * @version: $
 */
public interface ApplicationContext extends ListableBeanFactory {

    public Properties getConfig();

}
