package com.tyzz.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 16:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO {
    private Long articleId;

    private String articleName;

    private UserVO user;

    private String content;

    private String previewContent;

    private String cover;

    private Integer viewCount;

    private Integer like;

    private Date createTime;

    private Date updateTime;
}
