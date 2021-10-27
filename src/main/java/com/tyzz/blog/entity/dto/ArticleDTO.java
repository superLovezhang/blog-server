package com.tyzz.blog.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 16:12
 */
@Data
public class ArticleDTO {
    private Long articleId;

    @NotNull
    private ArrayList<Long> labelIds = new ArrayList<>();

    @NotNull
    private Long categoryId;

    @NotBlank
    private String articleName;

    @NotBlank
    private String content;

    @NotBlank
    private String htmlContent;

    @NotNull
    private String articleType;

    private String linkAddress;
}
