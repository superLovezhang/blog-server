package com.tyzz.blog.entity;

import java.io.Serializable;

/**
 * (Collection)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public class Collection implements Serializable {
    private static final long serialVersionUID = -64067811861740145L;
    
    private Long collectionId;
    
    private Long articleId;
    
    private Long userId;
    
    private Byte state;


    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

}