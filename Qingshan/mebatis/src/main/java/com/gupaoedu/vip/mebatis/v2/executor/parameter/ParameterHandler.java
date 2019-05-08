package com.gupaoedu.vip.mebatis.v2.executor.parameter;

import java.util.Map;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 14:01
 * @description：
 * @modified By：
 * @version: $
 */
public interface ParameterHandler {


    void setParameters(Object parameterObject, Map<Integer, String> properties);
}
