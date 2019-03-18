package com.gupaoedu.vip.pattern.observer.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 17:07
 * @description：
 * @modified By：
 * @version: $
 */
public class GuavaEvent {
    @Subscribe
    public void subscribe(String str){
        System.out.println("执行subscribe方法，传入的参数是：" + str);
    }
}
