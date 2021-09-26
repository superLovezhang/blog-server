package com.tyzz.blog.controller.open;

import com.tyzz.blog.service.CollectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Collection)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("collection")
public class CollectionController {
    /**
     * 服务对象
     */
    @Resource
    private CollectionService collectionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Collection selectOne(Long id) {
        return this.collectionService.queryById(id);
    }

}