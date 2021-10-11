package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.BasePageDTO;
import com.tyzz.blog.service.BingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-11 12:26
 */
@RestController
@RequestMapping("/open/bing")
public class BingController {
    @Resource
    private BingService bingService;

    @GetMapping("/list")
    public Result list(BasePageDTO pageDTO) {
        return Result.success(bingService.list(pageDTO));
    }
}
