package com.gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Tom.
 */

//懒汉式单例


//这种形式兼顾饿汉式的内存浪费，也兼顾synchronized性能问题
//完美地屏蔽了这两个缺点
//史上最牛B的单例模式的实现方式
public class LazyInnerClassSingleton {
    //默认使用LazyInnerClassGeneral的时候，会先初始化内部类
    //如果没使用的话，内部类是不加载的
    private LazyInnerClassSingleton(){
        if(LazyHolder.LAZY != null){
            //防止别人暴力破解，调用newInstance来实现
            System.out.println("外部类构造函数被调用");
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    static {
        System.out.println("外部类静态代码块被调用");
    }
    //每一个关键字都不是多余的
    //static 是为了使单例的空间共享
    //保证这个方法不会被重写，重载
    public static final LazyInnerClassSingleton getInstance(){
        System.out.println("调用get方法");
        //在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }

    //默认不加载
    private static class LazyHolder{
        LazyHolder(){

            System.out.println("内部类构造函数被调用");
        }
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();

        static{
            System.out.println("内部类静态代码块被调用");
        }
    }

}
