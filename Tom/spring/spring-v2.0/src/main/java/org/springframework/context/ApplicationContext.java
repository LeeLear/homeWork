package org.springframework.context;


import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.lang.Nullable;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/14 14:18
 * @description：
 * @modified By：
 * @version: $
 */
public interface ApplicationContext extends ListableBeanFactory {

    /**
     * Return the parent context, or {@code null} if there is no parent
     * and this is the root of the context hierarchy.
     * @return the parent context, or {@code null} if there is no parent
     */
    @Nullable
    ApplicationContext getParent();
}
