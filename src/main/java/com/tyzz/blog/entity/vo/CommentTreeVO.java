package com.tyzz.blog.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 14:26
 */
@Data
public class CommentTreeVO {
    private String content;

    private String[] pics;

    private UserVO user;

    private List<CommentVO> children = new ArrayList<CommentVO>();

    private Integer likes;

    private Date createTime;

    private Date updateTime;
}
