package com.tyzz.blog.entity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * (CommentUser)实体类
 *
 * @author makejava
 * @since 2021-10-14 20:46:54
 */
@Data
public class CommentUser implements Serializable {
    private static final long serialVersionUID = 693168820629216202L;

    @TableId
    private Long commentUserId;
    
    private Long commentId;
    
    private Long userId;

    private Boolean state;
}