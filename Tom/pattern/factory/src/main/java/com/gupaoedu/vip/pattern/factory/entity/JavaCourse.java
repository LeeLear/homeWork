package com.gupaoedu.vip.pattern.factory.entity;

public class JavaCourse implements ICourse{
    @Override
    public void record() {
        System.out.println("这里是录制Java课程的地方");
    }
}
