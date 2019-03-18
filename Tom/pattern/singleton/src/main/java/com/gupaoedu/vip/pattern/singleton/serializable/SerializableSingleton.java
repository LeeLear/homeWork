package com.gupaoedu.vip.pattern.singleton.serializable;

import java.io.Serializable;

//反序列化时导致单例破坏
public class SerializableSingleton implements Serializable {
    public SerializableSingleton() {
    }

    public final static SerializableSingleton INSTANCE = new SerializableSingleton();
    public static SerializableSingleton getInstance(){
        return INSTANCE;
    }

    public Object readResolve(){
        return INSTANCE;
    }
}
