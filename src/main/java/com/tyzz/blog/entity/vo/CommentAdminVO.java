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
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-14 17:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentAdminVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long commentId;

    private String content;

    private String[] pics;

    private UserVO user;

    private ArticleVO article;

    private Integer likes;

    private Date createTime;

    private Date updateTime;
}