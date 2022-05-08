package com.tyzz.blog.common;

import com.tyzz.blog.enums.ResponseCode;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.exception.BlogLoginInvalidException;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result argumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        return Result.fail(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BlogLoginInvalidException.class)
    public Result blogLoginInvalidException(BlogLoginInvalidException e) {
        return Result.result(ResponseCode.LOGIN_STATUS_ILLEGAL, e.getMessage());
    }

    @ExceptionHandler(BlogException.class)
    public Result blogException(BlogException e) {
        log.error(e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(e);
        return Result.fail(e.getMessage());
    }
}
