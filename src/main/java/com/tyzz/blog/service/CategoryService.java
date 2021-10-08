package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyzz.blog.dao.CategoryDao;
import com.tyzz.blog.entity.Category;
import com.tyzz.blog.entity.dto.CategoryDTO;
import com.tyzz.blog.entity.dto.CategoryPageDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public void save(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .categoryId(categoryDTO.getCategoryId())
                .categoryName(categoryDTO.getCategoryName())
                .iconClass(categoryDTO.getIconClass())
                .show(categoryDTO.getShow())
                .build();
        categoryDao.insert(category);
    }

    public void remove(Long categoryId) {
        categoryDao.deleteById(categoryId);
    }

    public Page<Category> listPage(CategoryPageDTO categoryPageDTO) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        Page<Category> page = Page.of(categoryPageDTO.getPage(), categoryPageDTO.getSize());
        wrapper.like("categoryName", categoryPageDTO.getSearchValue());
        wrapper.orderByDesc(categoryPageDTO.getSortColumn());
        return categoryDao.selectPage(page, wrapper);
    }

    public List<Category> hotList() {
        List<Long> categoryIds = categoryDao.hotList();
        return categoryDao.listByIds(categoryIds);
    }
}