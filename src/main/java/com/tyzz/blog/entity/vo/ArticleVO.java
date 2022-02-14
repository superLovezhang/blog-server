package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String linkAddress;

    private ArticleType articleType;

    private CategoryVO category;

    private List<LabelVO> labels = new ArrayList();

    private String cover;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likes;

    private boolean collected = false;

    private ArticleStatus status;

    private Date createTime;

    private Date updateTime;
}
