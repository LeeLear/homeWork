package com.gupaoedu.vip.pattern.factory.factory.simpleFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;
import com.gupaoedu.vip.pattern.factory.entity.JavaCourse;
import com.gupaoedu.vip.pattern.factory.entity.PythonCourse;

public class CourseFactory {
    public Object createByReflect(String name){
        try {
            return Class.forName(name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ICourse createByClassName(String name){
       if("java".equals(name)){
           return new JavaCourse();
       }else if("python".equals(name)){
           return new PythonCourse();
       }
       return null;
    }
    public ICourse createByClass(Class clazz){

        try {
            return (ICourse)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
