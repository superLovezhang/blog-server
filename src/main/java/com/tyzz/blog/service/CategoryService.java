package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyzz.blog.dao.CategoryDao;
import com.tyzz.blog.entity.pojo.Category;
import com.tyzz.blog.entity.dto.CategoryDTO;
import com.tyzz.blog.entity.dto.CategoryPageDTO;
import com.tyzz.blog.entity.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
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
        return categoryDao.hotList();
    }

    public List<Category> listAll() {
        return categoryDao.selectList(new QueryWrapper<Category>());
    }

    public CategoryVO pojoToVO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

    public Category selectOneById(Long categoryId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        return categoryDao.selectOne(wrapper);
    }
}