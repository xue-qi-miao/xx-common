package com.xx.exception;


/**
 * @author xueqimiao
 * @description 自定义异常
 * @date 2020-09-24 9:52
 * @return
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable errorCourse) {
        super(message, errorCourse);
    }

}
