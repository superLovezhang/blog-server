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

    private String[] pics;

    private UserVO user;

    private CommentVO replyComment;

    private Integer likes;

    private Date createTime;

    private Date updateTime;
}
