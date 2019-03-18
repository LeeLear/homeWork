package com.gupaoedu.vip.pattern.observer.events.mouseevent;

import com.gupaoedu.vip.pattern.observer.events.core.EventListener;

/**
 * Created by Tom.
 */
public class Mouse extends EventListener {

    public void click(){
        System.out.println("调用单击方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    public void doubleClick(){
        System.out.println("调用双击方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void up(){
        System.out.println("调用弹起方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void down(){
        System.out.println("调用按下方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void move(){
        System.out.println("调用移动方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void wheel(){
        System.out.println("调用滚动方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void over(){
        System.out.println("调用悬停方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void blur(){
        System.out.println("调用获焦方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public void focus(){
        System.out.println("调用失焦方法");
        this.trigger(Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
