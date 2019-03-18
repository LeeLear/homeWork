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
public class Father implements MeiPo {
    private MeiPo son;

    public Father(MeiPo son) {
        this.son = son;
    }

    @Override
    public void findLove() {
        System.out.println("儿子没有时间，忙着事业，爸爸帮你把关");
        son.findLove();
        System.out.println("儿子降低点标准，你不想结婚也用不着这样写要求");
    }
}
