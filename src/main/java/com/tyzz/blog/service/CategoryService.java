package com.tyzz.blog.service;

import com.tyzz.blog.dao.CategoryDao;
import com.tyzz.blog.entity.Category;
import com.tyzz.blog.entity.dto.CategoryDTO;
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
}