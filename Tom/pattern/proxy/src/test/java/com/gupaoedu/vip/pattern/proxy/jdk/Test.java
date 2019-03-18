package com.gupaoedu.vip.pattern.proxy.jdk;

import com.gupaoedu.vip.pattern.proxy.entity.Person;
import com.gupaoedu.vip.pattern.proxy.entity.Worker;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/15 23:05
 * @description：
 * @modified By：
 * @version: $
 */
public class Test {
    public static void main(String[] args) {
        JDK58 object=new JDK58();
        Worker lee = object.wrapPerson(new Lee());
        lee.findWork();
    }
}
