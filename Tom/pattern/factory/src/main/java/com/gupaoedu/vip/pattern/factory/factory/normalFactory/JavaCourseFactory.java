package com.gupaoedu.vip.pattern.factory.factory.normalFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;
import com.gupaoedu.vip.pattern.factory.entity.JavaCourse;

//维度单一
public class JavaCourseFactory implements ICourseFactory{
    public ICourse create(){
        ICourse javaCourse=new JavaCourse();
        //这里可以对javaCourse添加一些新的特性，或者初始化成员变量
        return javaCourse;
    }
}
