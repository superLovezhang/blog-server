package com.tyzz.blog.entity.dto;

import lombok.Data;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 17:14
 */
@Data
public class ArticlePageDTO extends BasePageDTO {
    private String searchValue = "";
    private Long categoryId;
    private Long labelId;
}
