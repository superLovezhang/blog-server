package com.tyzz.blog.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 9:38
 */
@Data
public class CommentVO {
    @NotEmpty
    private String content;

    private Long parentId;

    private Long replyId;

    private Long articleId;
}
