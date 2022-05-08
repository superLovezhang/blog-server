package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * 文章列表VO类
 * @Author: ZhangZhao
 * DateTime: 2022-05-08 10:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long articleId;

    private String articleName;

    private String previewContent;

    private String cover;

    private Integer commentCount;

    private Integer viewCount;

    private long likes = 0;

    private long collects = 0;

    private Date createTime;

    private Date updateTime;
}
