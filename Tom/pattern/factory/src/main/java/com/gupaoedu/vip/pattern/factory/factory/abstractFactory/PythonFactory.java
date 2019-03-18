package com.gupaoedu.vip.pattern.factory.factory.abstractFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;
import com.gupaoedu.vip.pattern.factory.entity.INote;
import com.gupaoedu.vip.pattern.factory.entity.IVideo;
import com.gupaoedu.vip.pattern.factory.entity.PythonCourse;
import com.gupaoedu.vip.pattern.factory.factory.normalFactory.ICourseFactory;

class PythonFactory implements IFactory {

    @Override
    public ICourse createCourse() {
        ICourse course = new PythonCourse();
        //这样一来就又可以添加
        return course;
    }

    @Override
    public INote createNote() {
        return null;
    }

    @Override
    public IVideo createVideo() {
        return null;
    }
}
