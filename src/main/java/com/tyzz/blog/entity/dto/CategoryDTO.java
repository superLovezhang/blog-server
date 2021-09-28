package com.tyzz.blog.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 9:25
 */
@Data
public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "目录名称不能为空")
    private String categoryName;

    @NotBlank(message = "图标不能为空")
    private String iconClass;

    private Boolean show;
}
