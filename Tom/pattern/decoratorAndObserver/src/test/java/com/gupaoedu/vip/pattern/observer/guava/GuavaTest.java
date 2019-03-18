package com.gupaoedu.vip.pattern.observer.guava;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 17:09
 * @description：
 * @modified By：
 * @version: $
 */
public class GuavaTest {
    @Test
    public void test(){
        EventBus bus = new EventBus();
        Message message =new Message("到账成功，欢迎您下次再来，祝您旅途愉快","如家旅店");
        Receiver tom = new Receiver("Tom");
        Receiver mic = new Receiver("Mic");
        bus.register(tom);
        bus.register(mic);
        bus.post(message);
    }
}
