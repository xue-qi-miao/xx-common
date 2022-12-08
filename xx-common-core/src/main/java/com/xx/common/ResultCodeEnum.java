package com.xx.common;

/**
 * @Author: xueqimiao
 * @Date: 2021/11/22 10:21
 */
public enum ResultCodeEnum {

    /**
     * 请求成功
     */
    REQUEST_SUCCESS(200, "请求成功!"),

    /**
     * 请求失败
     */
    REQUEST_FAIL(1002, "请求失败!"),

    /**
     * 上传失败
     */
    UPLOAD_FAIL(1013, "上传失败!"),

    /**
     * 请求参数错误
     */
    REQUEST_PARAM_FAIL(1003, "请求参数错误!"),

    /**
     * 参数校验失败
     */
    PARAM_VERIFICATION_FAIL(1006, "请求校验错误!"),

    /**
     * 业务数据校验错误
     */
    BUSINESS_DATA_VERIFICATION_FAIL(1008, "业务数据校验错误!"),

    /**
     * 数据不存在错误
     */
    DATA_NOT_EXIST_FAIL(1004, "数据不存在错误!"),

    /**
     * 数据为空错误
     */
    DATA_EMPTY_FAIL(1004, "数据为空错误!"),

    /**
     * 参数校验失败
     */
    PARAM_EMPTY_FAIL(1007, "请求参数为空错误!"),

    /**
     * 请求禁止
     */
    REQUEST_FORBIDDEN(1004, "请求禁止!"),

    /**
     * 系统出错
     */
    SYSTEM_ERROR(1005, "系统异常!"),

    /**
     * token不存在
     */
    TOKEN_NOT_EXISTS(2001, "token不存在!"),
    /**
     * token过期
     */
    TOKEN_EXPIRE(2002, "token过期!"),
    /**
     * token认证失败
     */
    TOKEN_ERROR(2003, "token认证失败!"),

    /**
     * token解码失败
     */
    TOKEN_DECODER_FAIL(2004, "token解码失败!"),

    /**
     * 请求过于频繁错误
     */
    REQUEST_OFTEN_FAIL(3001,"请求过于频繁")
    ;

    private Integer code;

    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
