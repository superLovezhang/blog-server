package com.tyzz.blog.controller.open;

import com.tyzz.blog.service.LabelService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Label selectOne(Long id) {
        return this.labelService.queryById(id);
    }

}