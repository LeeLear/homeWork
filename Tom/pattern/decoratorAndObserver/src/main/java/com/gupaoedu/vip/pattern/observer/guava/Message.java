package com.gupaoedu.vip.pattern.observer.guava;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/18 17:15
 * @description：
 * @modified By：
 * @version: $
 */
public class Message {
    private String content;
    private String sender;

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
