package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.CategoryDTO;
import com.tyzz.blog.entity.dto.CategoryPageDTO;
import com.tyzz.blog.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Category)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("/open/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 保存目录
     * @param categoryDTO
     * @return
     */
    @PostMapping("/save")
    public Result save(@Validated CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 删除目录
     * @param categoryId
     * @return
     */
    @DeleteMapping("/remove/{categoryId}")
    public Result remove(@PathVariable Long categoryId) {
        categoryService.remove(categoryId);
        return Result.success();
    }

    /**
     * 分页查询分类列表
     * @param categoryPageDTO
     * @return
     */
    @GetMapping("/list")
    public Result list(CategoryPageDTO categoryPageDTO) {
        return Result.success(categoryService.listPage(categoryPageDTO));
    }

    /**
     * 查询热门前十分类
     * @return
     */
    @GetMapping("/hot")
    public Result hot() {
        return Result.success(categoryService.hotList());
    }
}