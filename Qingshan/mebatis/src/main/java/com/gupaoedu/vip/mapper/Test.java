package com.gupaoedu.vip.mapper;

/**
 * @Author: qingshan
 * @Date: 2019/5/3 18:26
 * @Description: 咕泡学院，只为更好的你
 */
public class Test {
    public static void main(String[] args) {
        String input = "%d %s %d";
        Object[] objs = new Object[]{1,"2",3};
        String result = String.format(input,objs);
        System.out.println(result);
    }
}
