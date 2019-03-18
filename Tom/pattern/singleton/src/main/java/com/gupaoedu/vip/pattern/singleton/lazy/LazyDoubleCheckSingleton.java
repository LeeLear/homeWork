package com.gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Tom.
 */

public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton lazy = null;

    private LazyDoubleCheckSingleton(){}
    public static LazyDoubleCheckSingleton getInstance(){
        //防止由于synchronized带来的性能开销，在后续获取的时候，用个简单if判断来取代获取锁操作
        if(lazy == null){
            //用了synchronized，可以保证线程安全，但是，每次调用这个方法都要进行获取锁操作，势必增加运行时间。
            synchronized (LazyDoubleCheckSingleton.class){
                //只用lazy==null会导致，线程不安全
                if(lazy == null){
                    lazy = new LazyDoubleCheckSingleton();
                    //1.分配内存给这个对象
                    //2.初始化对象
                    //3.设置lazy指向刚分配的内存地址
                    //4.初次访问对象
                }
            }
        }
        return lazy;
    }
}
