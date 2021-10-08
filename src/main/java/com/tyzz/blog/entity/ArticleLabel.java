package com.tyzz.blog.entity;

import java.io.Serializable;

/**
 * (ArticleLabel)实体类
 *
 * @author makejava
 * @since 2021-10-05 23:05:22
 */
public class ArticleLabel implements Serializable {
    private static final long serialVersionUID = 773524810726726146L;
    
    private Long articleLabelId;
    
    private Long articleId;
    
    private Long labelId;
    
    private Boolean state;


    public Long getArticleLabelId() {
        return articleLabelId;
    }

    public void setArticleLabelId(Long articleLabelId) {
        this.articleLabelId = articleLabelId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}