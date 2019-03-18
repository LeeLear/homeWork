package com.gupaoedu.vip.pattern.factory.factory.normalFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;

public class ICourseFactoryTest {
    public static void main(String[] args) {
        ICourseFactory courseFactory = new JavaCourseFactory();
        ICourse course = courseFactory.create();
        course.record();
    }
}
