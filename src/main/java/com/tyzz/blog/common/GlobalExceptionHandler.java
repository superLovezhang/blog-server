package com.tyzz.blog.common;

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
    public String test(Exception e) {
        return "error";
    }
}
