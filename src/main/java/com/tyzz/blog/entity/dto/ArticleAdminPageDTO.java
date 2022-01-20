package com.tyzz.blog.entity.dto;

import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.enums.ArticleType;
import lombok.Data;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-20 16:32
 */
@Data
public class ArticleAdminPageDTO extends BasePageDTO {
    private String searchValue;

    private ArticleType articleType;

    private ArticleStatus status;
}
