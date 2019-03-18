package com.gupaoedu.vip.pattern.factory.factory.simpleFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;

public class simpleTest {
    public static void main(String[] args) {
        ICourse course = new CourseFactory().createByClassName("java");
        course.record();
    }
}
