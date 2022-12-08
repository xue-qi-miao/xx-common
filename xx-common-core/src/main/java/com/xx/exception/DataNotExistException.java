package com.xx.exception;

/**
 * @Author: xueqimiao
 * @Date: 2022/1/19 14:39
 */
public class DataNotExistException extends RuntimeException {
    public DataNotExistException() {
        super();
    }

    public DataNotExistException(String message) {
        super(message);
    }

    public DataNotExistException(String message, Throwable errorCourse) {
        super(message, errorCourse);
    }
}
