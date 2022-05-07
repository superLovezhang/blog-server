package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.service.OssService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-06 10:57
 */
@RestController
@RequestMapping("/open/oss")
public class OssController {
    @Resource
    private OssService ossService;

    @GetMapping("/credential")
    public Result credential() {
        return Result.success(ossService.getCredentials());
    }
}
