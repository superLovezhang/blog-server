package com.tyzz.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-06 10:01
 */
@RestController()
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test() {
        return "this is my test你好吗!";
    }
}
