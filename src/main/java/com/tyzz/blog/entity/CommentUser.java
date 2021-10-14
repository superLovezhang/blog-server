package com.tyzz.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CommentUser)实体类
 *
 * @author makejava
 * @since 2021-10-14 20:46:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUser implements Serializable {
    private static final long serialVersionUID = 693168820629216202L;

    @TableId
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long commentUserId;
    
    private Long commentId;
    
    private Long userId;

    private Boolean state;
}