package com.gupaoedu.vip.mebatis.v2.executor.resultSet;

import com.gupaoedu.vip.mebatis.v2.executor.Executor;
import com.gupaoedu.vip.mebatis.v2.generator.internal.util.StringUtils;
import com.gupaoedu.vip.mebatis.v2.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 13:52
 * @description：
 * @modified By：
 * @version: $
 */
public class DefaultResultSetHandler implements ResultSetHandler{

    private final MappedStatement mappedStatement;

    private final Executor executor;

    public DefaultResultSetHandler(MappedStatement mappedStatement, Executor executor) {
        this.mappedStatement = mappedStatement;
        this.executor = executor;
    }

    @Override
    public <T> T handle(ResultSet resultSet, Class type)  {
        // 直接调用Class的方法产生一个实例
        Object pojo = null;
        try {
            pojo = type.newInstance();
            // 遍历结果集
            if (resultSet.next()) {
                for (Field field : pojo.getClass().getDeclaredFields()) {
                    setValue(pojo, field, resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T)pojo;
    }

    @Override
    public <T> List handle(ResultSet resultSet) {
        // 直接调用Class的方法产生一个实例
        List<T> list=new ArrayList<>();
        Object pojo = null;
        try {

            resultSet.last(); // 将光标移动到最后一行
            int totalRowCount = resultSet.getRow();
            resultSet.beforeFirst();
            // 遍历结果集
            int currentRowCount = 1;

            while (resultSet.next()) {

                if(resultSet.getRow()==currentRowCount){
                    currentRowCount+=1;
                    pojo = mappedStatement.getType().newInstance();
                    for (Field field : pojo.getClass().getDeclaredFields()) {
                        setValue(pojo, field, resultSet);
                    }
                    list.add((T) pojo);
                }else{

                   break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        return null;
    }

    /**
     * 通过反射给属性赋值
     */
    private void setValue(Object pojo, Field field, ResultSet rs)  {
        try{
            Method setMethod = pojo.getClass().getMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
            setMethod.invoke(pojo, getResult(rs, field));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据反射判断类型，从ResultSet中取对应类型参数
     */
    private Object getResult(ResultSet rs, Field field) throws SQLException {
        Class type = field.getType();
        String dataName = HumpToUnderline(field.getName()); // 驼峰转下划线
        if (Integer.class == type ) {
            return rs.getInt(dataName);
        }else if (String.class == type) {
            return rs.getString(dataName);
        }else if(Long.class == type){
            return rs.getLong(dataName);
        }else if(Boolean.class == type){
            return rs.getBoolean(dataName);
        }else if(Double.class == type){
            return rs.getDouble(dataName);
        }else{
            return rs.getString(dataName);
        }
    }

    // 数据库下划线转Java驼峰命名
    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 单词首字母大写
     */
    private String firstWordCapital(String word){
        String first = word.substring(0, 1);
        String tail = word.substring(1);
        return first.toUpperCase() + tail;
    }
}
