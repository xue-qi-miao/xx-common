package com.xx.exception;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/27 14:29
 */
public class ParamVerifyException extends RuntimeException{

    public ParamVerifyException() {
        super();
    }

    public ParamVerifyException(String message) {
        super(message);
    }

    public ParamVerifyException(String message, Throwable errorCourse) {
        super(message, errorCourse);
    }
}
