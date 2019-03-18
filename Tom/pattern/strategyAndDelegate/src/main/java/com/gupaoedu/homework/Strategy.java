package com.gupaoedu.homework;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/16 15:29
 * @description： 这次我们可以结合工厂模式来一起实现业务
 * @modified By：
 * @version: $
 */
public class Strategy {
    public void put(String name){
        try {
            Role role =(Role) Class.forName(name).newInstance();
            role.attack();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
