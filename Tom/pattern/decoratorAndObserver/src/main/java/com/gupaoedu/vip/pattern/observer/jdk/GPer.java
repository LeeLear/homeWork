package com.gupaoedu.vip.pattern.observer.jdk;


import java.util.Observable;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 15:39
 * @description：
 * @modified By：
 * @version: $
 */
public class GPer extends Observable {
    private static String name ="GPer生态圈";
    private static GPer gPer=null;
    public static GPer getInstance(){
        if (gPer==null){
            synchronized (name){
                if (gPer==null){
                    gPer=new GPer();
                }
            }
        }
        return gPer;
    }

    public String getName() {
        return name;
    }

    public void publishQuestion(Question question) {
        System.out.println(question.getUsername()+"在"+this.name+"上"+"提交了一个问题");
        setChanged();
        notifyObservers(question);
    }
}
