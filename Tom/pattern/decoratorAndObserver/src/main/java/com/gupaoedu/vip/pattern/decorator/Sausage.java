package com.gupaoedu.vip.pattern.decorator;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 14:07
 * @description：
 * @modified By：
 * @version: $
 */
public class Sausage extends SideDish{
    public Sausage(){
        this.price=2.5;
        this.count=1;
    }
    public Sausage(Integer count){
        this.price=2.5;
        this.count=count;
    }
}
