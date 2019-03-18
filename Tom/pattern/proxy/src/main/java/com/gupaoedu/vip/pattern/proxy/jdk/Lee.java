package com.gupaoedu.vip.pattern.proxy.jdk;

import com.gupaoedu.vip.pattern.proxy.entity.Worker;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/15 23:28
 * @description：
 * @modified By：
 * @version: $
 */
public class Lee implements Worker {
    @Override
    public void findWork() {
        System.out.println("10~15k就好");
    }

}
