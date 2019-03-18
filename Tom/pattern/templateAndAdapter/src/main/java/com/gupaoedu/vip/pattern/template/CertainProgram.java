package com.gupaoedu.vip.pattern.template;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/17 9:31
 * @description：
 * @modified By：
 * @version: $
 */
public class CertainProgram extends BaseLifeCycle{
    private boolean isStop;

    public CertainProgram() {
    }

    public CertainProgram(boolean isStop) {
        this.isStop = isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
    public void run(){
        this.init();
        this.start();
        if (isStop){
            this.stop();
        }
        this.destroy();
    }
}
