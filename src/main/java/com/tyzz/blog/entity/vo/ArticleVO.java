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
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 16:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long articleId;

    private String articleName;

    private UserVO user;

    private String content;

    private String htmlContent;

    private String previewContent;

    private String cover;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likes;

    private boolean collected = false;

    private Date createTime;

    private Date updateTime;
}
