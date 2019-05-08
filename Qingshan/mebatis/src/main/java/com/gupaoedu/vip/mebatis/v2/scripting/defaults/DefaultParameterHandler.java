package com.gupaoedu.vip.mebatis.v2.scripting.defaults;


import com.gupaoedu.vip.mebatis.v2.executor.parameter.ParameterHandler;
import com.gupaoedu.vip.mebatis.v2.generator.internal.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 14:02
 * @description：
 * @modified By：
 * @version: $
 */
public class DefaultParameterHandler implements ParameterHandler {
    private PreparedStatement psmt;

    private Map<Integer, String> properties;

    public DefaultParameterHandler(PreparedStatement statement) {
        this.psmt = statement;
    }

    /**
     * 从方法中获取参数，遍历设置SQL中的？占位符
     * @param parameters
     */

    private void setParameters(Object parameters) {


        try {
            // PreparedStatement的序号是从1开始的
            parameters = (ArrayList)parameters;
            for (int i = 0; i <((ArrayList) parameters).size(); i++) {
                int k =i+1;
                Object parameter = ((ArrayList) parameters).get(i);
                if (parameter instanceof Integer) {
                    psmt.setInt(k, (Integer) parameter);
                } else if (parameter instanceof Long) {
                    psmt.setLong(k, (Long) parameter);
                } else if (parameter instanceof String) {
                    psmt.setString(k , String.valueOf(parameter));
                } else if (parameter instanceof Boolean) {
                    psmt.setBoolean(k, (Boolean) parameter);
                } else {
                    psmt.setString(k, String.valueOf(parameter));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setParameters(Object parameterObject, Map<Integer, String> properties) {
        if(!(parameterObject instanceof List)){
            throw  new RuntimeException("类型不对啊");
        }

        if (properties==null){
            setParameters( parameterObject);
        }else {
            this.properties = properties;
            for (int i = 0; i <((List) parameterObject).size();i++){
                List parameter = getParameter(((List) parameterObject).get(i));
                setParameters( parameter);
            }
        }
    }

    private List getParameter(Object o) {
        List list = new ArrayList();
        for (int i=0;i<properties.size();i++) {
            try {
                Method method = o.getClass().getDeclaredMethod("get" + StringUtils.capitalize(properties.get(i+1)));
                Object result =method.invoke(o);
                list.add(result);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
