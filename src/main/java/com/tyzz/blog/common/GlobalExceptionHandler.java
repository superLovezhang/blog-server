package com.tyzz.blog.common;

import com.tyzz.blog.enums.ResponseCode;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.exception.BlogLoginInvalidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result argumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        return Result.fail(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BlogLoginInvalidException.class)
    public Result blogLoginInvalidException(BlogLoginInvalidException e) {
        return Result.result(ResponseCode.LOGIN_STATUS_ILLEGAL, e.getMessage());
    }

    @ExceptionHandler(BlogException.class)
    public Result blogException(BlogException e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
}
