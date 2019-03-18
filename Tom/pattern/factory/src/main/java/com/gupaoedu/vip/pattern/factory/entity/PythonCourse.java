package com.gupaoedu.vip.pattern.factory.entity;

public class PythonCourse implements ICourse{
    @Override
    public void record() {
        System.out.println("这里是录制Python课程的地方");
    }
}
