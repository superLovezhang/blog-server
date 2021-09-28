package com.tyzz.blog.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 16:12
 */
@Data
public class ArticleDTO {
    private Long articleId;

    @NotBlank
    private String articleName;

    @NotBlank
    private String content;
}
