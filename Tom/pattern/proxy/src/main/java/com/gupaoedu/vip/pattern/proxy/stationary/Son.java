package com.gupaoedu.vip.pattern.proxy.stationary;

import com.gupaoedu.vip.pattern.proxy.entity.MeiPo;
import com.gupaoedu.vip.pattern.proxy.entity.Person;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/15 23:06
 * @description：
 * @modified By：
 * @version: $
 */
public class Son implements MeiPo {
    @Override
    public void findLove() {
        System.out.println("身高：170；肤白貌美气质佳，人气旺，有过主播经验最好，能完全管住自己，不依赖其他人");
    }
}
