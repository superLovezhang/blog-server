package com.tyzz.blog.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Category)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 829568101196271234L;
    
    private Long categoryId;
    
    private String categoryName;
    
    private String iconClass;
    
    private Byte show;
    
    private Byte state;
    
    private Date updateTime;
    
    private Date createTime;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public Byte getShow() {
        return show;
    }

    public void setShow(Byte show) {
        this.show = show;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}