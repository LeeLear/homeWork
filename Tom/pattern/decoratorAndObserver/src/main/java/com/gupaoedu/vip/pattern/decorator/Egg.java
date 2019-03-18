package com.gupaoedu.vip.pattern.decorator;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 14:05
 * @description：
 * @modified By：
 * @version: $
 */
public class Egg extends SideDish{
    public Egg(Integer count){
        this.price=0.5;
        this.count=count;
    }
    public Egg() {
        this.price=0.5;
        this.count=1;
    }
}
