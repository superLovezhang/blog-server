package com.tyzz.blog.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.enums.ArticleType;
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
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long articleId;
    
    private String articleName;
    
    private Long userId;

    private String htmlContent;

    private String content;

    private String linkAddress;

    private ArticleType articleType;

    private String cover;

    private Long categoryId;
    
    private Integer viewCount;

    private Integer commentCount;

    private ArticleStatus status;

    private String refuseReason;
    
    private Boolean state;
    
    private Date createTime;
    
    private Date updateTime;

    public String getPreviewContent() {
        String previewContent = StringUtils.htmlToPlainText(this.htmlContent);
        if (previewContent.length() >= 200) {
            return previewContent.substring(0, 200);
        } else {
            return previewContent;
        }
    }
}