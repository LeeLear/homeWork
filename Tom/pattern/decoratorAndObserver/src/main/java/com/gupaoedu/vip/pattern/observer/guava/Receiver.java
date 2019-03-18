package com.gupaoedu.vip.pattern.observer.guava;

import com.google.common.eventbus.Subscribe;

import java.lang.reflect.Method;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 17:19
 * @description：
 * @modified By：
 * @version: $
 */
public class Receiver {
    private String name;

    public Receiver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Subscribe
    public void updateForGuava(Message message){
        System.out.println(this.getName()+"先生，你好！\r\n   收到来自"+message.getSender()+"的消息，内容如下:\r\n"+message.getContent());
    }
}
