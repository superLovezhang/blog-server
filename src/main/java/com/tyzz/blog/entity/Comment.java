package com.tyzz.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:51:06
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = 322677803620436197L;

    @TableId
    private Long commentId;
    
    private String content;

    private String pics;

    private Long userId;
    
    private Long parentId;
    
    private Long replyId;
    
    private Long articleId;
    
    private Boolean state;
    
    private Integer likes;
    
    private Date createTime;
    
    private Date updateTime;

    public String[] getArrayPics() {
        if (StringUtils.isNotBlank(pics)) {
            return pics.split(",");
        }
        return null;
    }
}