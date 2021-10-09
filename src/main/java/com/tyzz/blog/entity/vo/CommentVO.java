package com.tyzz.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 14:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {
    private String content;

    private UserVO user;

    private CommentVO replyComment;

    private Integer like;

    private Date createTime;

    private Date updateTime;
}
