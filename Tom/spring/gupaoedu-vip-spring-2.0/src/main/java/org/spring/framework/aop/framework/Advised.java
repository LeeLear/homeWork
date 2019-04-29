package org.spring.framework.aop.framework;

import org.spring.framework.aop.TargetClassAware;
import org.spring.framework.aop.TargetSource;

public interface Advised extends TargetClassAware {

    TargetSource getTargetSource();
}
