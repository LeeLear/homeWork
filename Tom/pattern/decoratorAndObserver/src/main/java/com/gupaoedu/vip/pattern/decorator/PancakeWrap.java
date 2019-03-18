package com.gupaoedu.vip.pattern.decorator;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 14:32
 * @description：
 * @modified By：
 * @version: $
 */
public class PancakeWrap extends Pancake{
    private Pancake pancake;
    private Egg egg;
    private Sausage sausage;

    public PancakeWrap(Pancake pancake) {
        this.pancake = pancake;
    }

    public Egg getEgg() {
        return egg;
    }

    public void setEgg(Egg egg) {
        this.egg = egg;
    }

    public Sausage getSausage() {
        return sausage;
    }

    public void setSausage(Sausage sausage) {
        this.sausage = sausage;
    }

    public void result(){
        System.out.println("煎饼果子"+pancake.getPrice()+"元,"+egg.getCount()+"个"+egg.getPrice()+"元鸡蛋，"+sausage.getCount()+"个"+sausage.getPrice()+"元香肠"+"\r\n"
        +"一共花费了"+(pancake.getPrice()+egg.getPrice()*egg.getCount()+sausage.getPrice()*sausage.getCount())+"元");
    }
}
