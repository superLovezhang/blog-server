package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.CategoryDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("categoryService")
public class CategoryService {
    @Resource
    private CategoryDao categoryDao;

}