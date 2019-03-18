package com.gupaoedu.vip.pattern.proxy.stationary;

import com.gupaoedu.vip.pattern.proxy.entity.MeiPo;
import com.gupaoedu.vip.pattern.proxy.entity.Person;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/15 23:05
 * @description：
 * @modified By：
 * @version: $
 */
public class Test {
    public static void main(String[] args) {
        MeiPo father = new Father(new Son());
        father.findLove();
    }
}
