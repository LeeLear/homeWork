package com.gupaoedu.vip.pattern.decorator;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 14:50
 * @description：
 * @modified By：
 * @version: $
 */
public class SausageWrap {
    private PancakeWrap pancakeWrap;
    public SausageWrap(PancakeWrap pancakeWrap){
        this.pancakeWrap=pancakeWrap;
    }
    public PancakeWrap wrap(){
        return wrap(1);
    }

    public PancakeWrap wrap(int num){
        if (null==pancakeWrap.getSausage()){
            pancakeWrap.setSausage(new Sausage(num));
        }else{
            pancakeWrap.getSausage().setCount(pancakeWrap.getSausage().getCount()+num);
        }
        return pancakeWrap;
    }
}
