package com.tyzz.blog.service;

import com.tyzz.blog.dao.CategoryDao;
import com.tyzz.blog.entity.Category;
import com.tyzz.blog.entity.vo.CategoryVO;
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

    public void save(CategoryVO categoryVO) {
        Category category = Category.builder()
                .categoryId(categoryVO.getCategoryId())
                .categoryName(categoryVO.getCategoryName())
                .iconClass(categoryVO.getIconClass())
                .show(categoryVO.getShow())
                .build();
        categoryDao.insert(category);
    }

    public void remove(Long categoryId) {
        categoryDao.deleteById(categoryId);
    }
}