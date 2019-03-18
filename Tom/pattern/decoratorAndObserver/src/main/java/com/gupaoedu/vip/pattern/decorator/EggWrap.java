package com.gupaoedu.vip.pattern.decorator;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 14:45
 * @description：
 * @modified By：
 * @version: $
 */
public class EggWrap {
    private PancakeWrap pancakeWrap;
    public EggWrap(PancakeWrap pancakeWrap){
        this.pancakeWrap=pancakeWrap;
    }
    public PancakeWrap wrap(){
        return wrap(1);
    }
    public PancakeWrap wrap(int num){
        if (null==pancakeWrap.getEgg()){
            pancakeWrap.setEgg(new Egg(num));
        }else{
            pancakeWrap.getEgg().setCount(pancakeWrap.getEgg().getCount()+num);
        }
        return pancakeWrap;
    }
}
