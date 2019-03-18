package com.gupaoedu.vip.pattern.factory.factory.abstractFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;
import com.gupaoedu.vip.pattern.factory.entity.INote;
import com.gupaoedu.vip.pattern.factory.entity.IVideo;
import com.gupaoedu.vip.pattern.factory.entity.JavaCourse;

public class JavaFactory implements IFactory {
    @Override
    public ICourse createCourse() {
        return new JavaCourse();
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
