package com.tyzz.blog.common;

import com.tyzz.blog.entity.enums.ResponseCode;

import java.io.Serializable;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-26 12:30
 */
public class Result implements Serializable {
    private String message;
    private Long code;
    private Object data;

    public Result(String message, Long code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public Result(ResponseCode responseCode, Object data) {
        this.message = responseCode.getDesc();
        this.code = responseCode.getCode();
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success() {
        return new Result(ResponseCode.SUCCESS, null);
    }

    public static Result success(Object data) {
        return new Result(ResponseCode.SUCCESS, data);
    }

    public static Result fail() {
        return new Result(ResponseCode.FAIL, null);
    }

    public static Result result(ResponseCode responseCode, Object data) {
        return new Result(responseCode, data);
    }

    public static Result result(ResponseCode responseCode) {
        return new Result(responseCode, null);
    }
}
