package com.tyzz.blog.exception;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 13:05
 */
public class BlogLoginInvalidException extends RuntimeException {
    public BlogLoginInvalidException(String message) {
        super(message);
    }
}