package com.gupaoedu.vip.pattern.factory.factory.normalFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;
import com.gupaoedu.vip.pattern.factory.entity.PythonCourse;

public class PythonCourseFactory implements ICourseFactory{

    @Override
    public ICourse create() {
        ICourse pythonCourse = new PythonCourse();
        //这里可以扩展生成的pythonCourse，如set什么属性之类的
        return pythonCourse;
    }
}
