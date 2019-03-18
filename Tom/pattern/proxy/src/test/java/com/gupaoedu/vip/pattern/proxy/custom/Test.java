package com.gupaoedu.vip.pattern.proxy.custom;

import com.gupaoedu.vip.pattern.proxy.cglib.CY;
import com.gupaoedu.vip.pattern.proxy.entity.MeiPo;

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
            MeiPo obj = (MeiPo) new CustomMeiPo().getInstance(new CY());
            System.out.println(obj.getClass());
            obj.findLove();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
