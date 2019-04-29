package org.spring.framework.aop.target;

import org.spring.framework.aop.TargetSource;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/23 9:45
 * @description：
 * @modified By：
 * @version: $
 */
public class SingletonTargetSource implements TargetSource {

    private final Object target;

    public SingletonTargetSource(Object target) {
        this.target = target;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.target.getClass();
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public Object getTarget() throws Exception {
        return this.target;
    }

    @Override
    public void releaseTarget(Object target) throws Exception {

    }
}
