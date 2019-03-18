package com.gupaoedu.vip.pattern.singleton.hungry;

public class HungryStaticSingleton {
    private static final HungryStaticSingleton hungrySingleton ;

    static {
        hungrySingleton=new HungryStaticSingleton();
    }

    public static HungryStaticSingleton getInstance(){
        return  hungrySingleton;
    }
}
