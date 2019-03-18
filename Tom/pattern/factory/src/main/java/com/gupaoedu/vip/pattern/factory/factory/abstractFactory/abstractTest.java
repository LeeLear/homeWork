package com.gupaoedu.vip.pattern.factory.factory.abstractFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;

public class abstractTest {
    public static void main(String[] args) {
        IFactory factory = new JavaFactory();
        ICourse course = factory.createCourse();
        course.record();
    }
}
