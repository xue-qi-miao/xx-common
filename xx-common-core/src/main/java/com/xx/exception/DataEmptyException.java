package com.xx.exception;

/**
 * @Author: xueqimiao
 * @Date: 2022/1/19 14:39
 */
public class DataEmptyException extends RuntimeException {
    public DataEmptyException() {
        super();
    }

    public DataEmptyException(String message) {
        super(message);
    }

    public DataEmptyException(String message, Throwable errorCourse) {
        super(message, errorCourse);
    }
}
