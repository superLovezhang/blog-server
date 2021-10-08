package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.entity.Category;

import java.util.List;

/**
 * (Category)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public interface CategoryDao extends BaseMapper<Category> {
    List<Long> hotList();

    List<Category> listByIds(List<Long> categoryIds);
}