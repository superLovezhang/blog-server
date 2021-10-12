package com.tyzz.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.tyzz.blog.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Article)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:51:06
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    private static final long serialVersionUID = -17576930236206221L;

    @TableId
    private Long articleId;
    
    private String articleName;
    
    private Long userId;
    
    private String content;

    private String linkAddress;

    private String articleType;

    private String cover;

    private Long categoryId;
    
    private Integer viewCount;
    
    private Integer like;
    
    private Boolean state;
    
    private Date createTime;
    
    private Date updateTime;

    public String getPreviewContent() {
        return StringUtils.htmlToPlainText(this.content);
    }
}