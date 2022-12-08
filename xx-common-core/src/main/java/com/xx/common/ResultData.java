package com.xx.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xx.utils.ValidationUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xueqimiao
 * @Date: 2021/11/22 10:21
 * @desc 参数返回封装对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultData<T> implements Serializable {

    @ApiModelProperty("是否正常返回")
    private boolean success;

    @ApiModelProperty("返回数据")
    private T data;

    @ApiModelProperty("响应错误信息")
    private String error;

    @ApiModelProperty("返回码：200为成功")
    private int code = ResultCodeEnum.REQUEST_SUCCESS.getCode();

    @ApiModelProperty("异常信息")
    private String msg;

    @ApiModelProperty("当前时间戳")
    private Long timestamp = (new Date()).getTime();

    @ApiModelProperty("分页信息")
    private PageData pageData;

    public ResultData() {
    }

    public ResultData(T data) {
        this.success = true;
        this.data = data;
    }

    /**
     * 失败时返回的数据
     *
     * @param resultCodeEnum 执行失败时代码
     * @param error          失败时给出的错误提示信息
     */
    public ResultData(ResultCodeEnum resultCodeEnum, String error) {
        this.success = false;
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
        if(!ValidationUtil.isEmpty(error)){
            this.msg = error;
        }
        this.error = error;
    }

    public ResultData(ResultCodeEnum resultCodeEnum, String error,String msg) {
        this.success = false;
        this.code = resultCodeEnum.getCode();
        this.msg = msg;
        this.error = error;
    }

    public ResultData(ResultCodeEnum resultCodeEnum) {
        this.success = false;
        this.error = this.msg;
        int code = resultCodeEnum.getCode();
        if(code == 200){
            this.success = true;
            this.error = null;
        }
        this.code = code;
        this.msg = resultCodeEnum.getMsg();
    }

    public ResultData(int coder, String error) {
        this.success = false;
        this.code = coder;
        this.msg = error;
        this.error = error;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
}
