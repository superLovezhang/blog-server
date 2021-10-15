package com.tyzz.blog.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 14:26
 */
@Data
public class CommentTreeVO extends CommentVO {
    private List<CommentVO> children = new ArrayList<CommentVO>();
}
