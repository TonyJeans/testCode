package com.syl.exception;

/**
 * Created by ainsc on 2017/7/31.
 */
public class MessageException extends Exception {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageException(String msg) {
        super();
        this.msg = msg;
    }

}
