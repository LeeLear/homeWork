package com.gupaoedu.vip.pattern.decorator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        PancakeWrap pancakeWrap = new PancakeWrap(new Pancake());
        pancakeWrap=new EggWrap(pancakeWrap).wrap(2);
        pancakeWrap=new SausageWrap(pancakeWrap).wrap();
        pancakeWrap.result();
    }
}
