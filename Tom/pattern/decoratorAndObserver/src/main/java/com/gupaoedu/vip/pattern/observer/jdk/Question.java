package com.gupaoedu.vip.pattern.observer.jdk;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 15:40
 * @description：
 * @modified By：
 * @version: $
 */
public class Question {
    private String username;
    private String content;

    public Question() {
    }

    public Question(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
