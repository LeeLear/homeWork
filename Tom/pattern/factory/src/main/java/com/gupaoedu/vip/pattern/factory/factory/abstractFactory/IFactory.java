package com.gupaoedu.vip.pattern.factory.factory.abstractFactory;

import com.gupaoedu.vip.pattern.factory.entity.ICourse;
import com.gupaoedu.vip.pattern.factory.entity.INote;
import com.gupaoedu.vip.pattern.factory.entity.IVideo;

public interface IFactory {
    public ICourse createCourse();
    public INote createNote();
    public IVideo createVideo();
}
