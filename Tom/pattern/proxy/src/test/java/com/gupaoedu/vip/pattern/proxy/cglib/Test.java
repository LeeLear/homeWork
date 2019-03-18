package com.gupaoedu.vip.pattern.proxy.cglib;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/15 23:05
 * @description：
 * @modified By：
 * @version: $
 */
public class Test {
    public static void main(String[] args) {
        try {
            CY obj = (CY)new CglibMeipo().getInstance(CY.class);
            obj.findLove();
            System.out.println("--------------------------------");
            // System.out.println(obj.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
