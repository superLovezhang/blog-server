package com.tyzz.blog.controller.open;

import com.tyzz.blog.service.impl.LabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Label)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("label")
public class LabelController {
    /**
     * 服务对象
     */
    @Resource
    private LabelService labelService;

}