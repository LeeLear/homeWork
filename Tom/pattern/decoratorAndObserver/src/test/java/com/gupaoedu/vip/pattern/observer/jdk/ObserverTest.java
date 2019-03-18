package com.gupaoedu.vip.pattern.observer.jdk;

import org.junit.Test;
/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 15:53
 * @description：
 * @modified By：
 * @version: $
 */
public class ObserverTest {

    @Test
    public void test(){
        GPer gper = GPer.getInstance();
        Teacher tom = new Teacher("Tom");
        Teacher mic = new Teacher("Mic");
        //这为没有@Tom老师
        Question question = new Question();
        question.setUsername("小明");
        question.setContent("观察者设计模式适用于哪些场景？");
        gper.addObserver(tom);
        gper.addObserver(mic);
        gper.publishQuestion(question);
    }
}
