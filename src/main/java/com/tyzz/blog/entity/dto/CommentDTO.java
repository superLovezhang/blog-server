package com.tyzz.blog.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 9:38
 */
@Data
public class CommentDTO {
    @NotEmpty
    private String content;

    private String pics;

    private Long parentId;

    private Long replyId;

    private Long articleId;
}
