package com.tyzz.blog.common;

import com.tyzz.blog.entity.enums.ResponseCode;
import com.tyzz.blog.exception.BlogLoginInvalidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-26 9:49
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result test(Exception e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(BlogLoginInvalidException.class)
    public Result loginException(BlogLoginInvalidException e) {
        return Result.result(ResponseCode.LOGIN_STATUS_ILLEGAL, e.getMessage());
    }
}
