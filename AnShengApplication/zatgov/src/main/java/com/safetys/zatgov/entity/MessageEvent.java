package com.safetys.zatgov.entity;

/**
 * Author:Created by Ricky on 2017/10/27.
 * Description: EventBus消息载体
 */
public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
